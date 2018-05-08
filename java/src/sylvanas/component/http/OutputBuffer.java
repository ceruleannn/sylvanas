package sylvanas.component.http;

import sylvanas.bootstrap.Server;
import sylvanas.connector.Response;
import sylvanas.util.Constants;
import sylvanas.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * a buffer for response
 */
public class OutputBuffer extends Writer{

    private int io_mode;

    private int bufferSize = 2048;

    private byte[] buf = new byte[bufferSize];

    private int pos = 0;

    private Response response;

    // 返回客户端的流或channel
    private OutputStream outputStream ;
    private SocketChannel socketChannel;

    // has been written to stream
    private boolean commited;

    private boolean isFlushed;

    private String encode = Constants.DEFAULT_ENCODING;

    public OutputBuffer(Response response, OutputStream outputStream){
        this.io_mode = Server.BIO_MODE;
        this.response = response;
        this.outputStream = outputStream;
    }

    public OutputBuffer(Response response, SocketChannel socketChannel){
        this.io_mode = Server.NIO_MODE;
        this.response = response;
        this.socketChannel = socketChannel;
    }

    public void write(int i) {

        write(String.valueOf(i));
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

        // deal with the encoding problem;
        Charset cs = Charset.forName (encode);
        CharBuffer cb = CharBuffer.allocate (len);
        cb.put(cbuf,off,len);
        cb.flip();

        ByteBuffer bb = cs.encode(cb);

        //TODO remain is better?
        byte[] nb = new byte[bb.limit()];
        bb.get(nb);
        write(nb);

    }

    @Override
    public void write(String string){
        byte[] b = StringUtils.getBytes(string);
        write(b);
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {

        checkLength(len);
        System.arraycopy(b,off,buf,pos,len);
        pos += len;
    }

    public void reset(){
        Arrays.fill(buf,(byte)0);
        pos = 0;
    }

    @Override
    public void flush() throws IOException{
        if (commited){
            if (io_mode==Server.BIO_MODE){
                outputStream.flush();
            }
            return;
        }
        doWrite();
    }

    @Override
    public void close(){

        try {
            if (io_mode==Server.BIO_MODE){
                outputStream.close();
            }else {
                socketChannel.close();
            }

        } catch (IOException e) {
            //NOOP
        }
    }

    public void doWrite() throws IOException{

        if (commited){
            return;
        }
        commited = true;

        writeHeaders();
        if (io_mode==Server.BIO_MODE){
            outputStream.write(buf,0,pos);
            outputStream.flush();
        }else {

            ByteBuffer bb = ByteBuffer.wrap(buf,0,pos);

            // wow! byteBuffer一次只写256kb !!!
            // 好大一个坑...
            while(bb.hasRemaining()){
                socketChannel.write(bb);
            }

        }

    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {

        if (bufferSize>buf.length){
            this.bufferSize = bufferSize;
            byte[] newBuf = new byte[bufferSize];
            System.arraycopy(buf,0,newBuf,0,buf.length);
            buf = newBuf;
        }
    }

    public void writeHeaders(){

        // copy the buf and reset it
        byte[] contentBuf = new byte[pos];

        System.arraycopy(buf,0,contentBuf,0,pos);
        reset();

        //set protocol and status , message maybe
        //eg. HTTP/1.1 200 (OK)
        write(Constants.HTTP_11);
        buf[pos++] = Constants.SP;
        write(response.getStatus());
        buf[pos++] = Constants.CR;
        buf[pos++] = Constants.LF;


        MimeHeaders mimeHeaders = response.getMimeHeaders();

        //set content length
        if (response.getContentLength()==-1){
            response.setContentLength(contentBuf.length);
        }

        response.addHeader("Server",Constants.SYLVANAS_VERSION);

        //set headers
        Enumeration<String> enumeration = mimeHeaders.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = mimeHeaders.getHeader(name);

            //eg. Connection: Keep-Alive
            write(name);
            buf[pos++] = Constants.COLON;
            buf[pos++] = Constants.SP;
            write(value);
            fillCRLF();
        }

        // must have a blank line between header and body
        fillCRLF();

        // expand the buf and append body
        byte[] newBuf = new byte[contentBuf.length+pos+4];
        System.arraycopy(buf,0,newBuf,0,pos);
        buf = newBuf;

        System.arraycopy(contentBuf,0,buf,pos,contentBuf.length);
        pos += contentBuf.length;

        // end of the response
        fillCRLF();
    }

    private void fillCRLF(){
        buf[pos++] = Constants.CR;
        buf[pos++] = Constants.LF;
    }

    private void checkLength(int length){
        int size = buf.length;
        if (pos + length + 4 > size) {
            byte[] newBuf = new byte[size+length];
            System.arraycopy(buf,0,newBuf,0,size);
            buf = newBuf;
        }
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public boolean isCommited() {
        return commited;
    }
}
