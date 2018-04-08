package sylvanas.component.http;

import sylvanas.connector.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * a buffer for response
 */
public class OutputBuffer extends Writer{

    private int bufferSize = 2048;

    private byte[] buf;

    private int pos = 0;

    private Response response;


    private OutputStream outputStream ;

    public OutputBuffer(Response response, OutputStream outputStream){
        this.response = response;
        this.outputStream = outputStream;
    }

    public void writeByte(int i){
        checkLength(1);
        buf[pos] = (byte)i;
        pos++;
    }

    public void write(int i) {
        checkLength(1);
        buf[pos] = (byte)i;
        pos++;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        checkLength(cbuf.length);

        for(int i=pos;i<len;i++){
            buf[i] = (byte)cbuf[off+i];
        }

    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        checkLength(len);
        System.arraycopy(b,off,buf,pos,len);
    }



    public void reset(){
        Arrays.fill(buf,(byte)0);
    }

    @Override
    public void flush() throws IOException{
        outputStream.flush();
    }

    @Override
    public void close() throws IOException{
        outputStream.close();
    }

    public void doWrite() throws IOException{

        writeHeaders();

        outputStream.write(buf);
        outputStream.flush();
        outputStream.close();
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

    private void writeHeaders(){
        MimeHeaders mimeHeaders = response.getMimeHeaders();
        Enumeration<String> enumeration = mimeHeaders.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = mimeHeaders.getHeader(name);

            System.out.println(name+" #-#"+value);
        }
    }

    private void checkLength(int length){
        int size = buf.length;
        if (pos + length + 4 > size) {
            byte[] newBuf = new byte[size*2];
            System.arraycopy(buf,0,newBuf,0,size);
            buf = newBuf;
        }
    }
}
