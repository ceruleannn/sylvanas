package sylvanas.connector;

import sylvanas.bootstrap.Server;
import sylvanas.component.http.OutputBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * response for socket
 */
public class RawResponse {

    private int IOmode;

    private Response response;

    private Socket socket = null;

    private SocketChannel socketChannel;

    private RawRequest rawRequest = null;

    private OutputStream outputStream ;

    private OutputBuffer outputBuffer;

    private boolean commited = false;

    public RawResponse(Socket socket){
        this.socket = socket;
        IOmode = Server.BIO_MODE;

        try {
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("get out put stream failed");
        }
    }

    public RawResponse(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        IOmode = Server.NIO_MODE;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public RawRequest getRawRequest() {
        return rawRequest;
    }

    public void setRawRequest(RawRequest rawRequest) {
        this.rawRequest = rawRequest;
    }

    public OutputStream getOutputStream(){
        return outputStream;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
        this.outputBuffer = response.getOutputBuffer();
    }

    public OutputBuffer getOutputBuffer() {
        return outputBuffer;
    }

    public int getIOmode() {
        return IOmode;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
