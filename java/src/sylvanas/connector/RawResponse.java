package sylvanas.connector;

import sylvanas.component.http.OutputBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * response for socket
 */
public class RawResponse {

    private Response response;

    private Socket socket = null;

    private RawRequest rawRequest = null;

    private OutputStream outputStream ;

    private OutputBuffer outputBuffer;

    private boolean commited = false;


    private static final String HTML = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "Content-Length: %d\r\n" + "\r\n"
            + "%s";

    public RawResponse(Socket socket){
        this.socket = socket;

        try {
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("get out put stream failed");
        }
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

    public void doWrite ()throws IOException{

        if (outputBuffer==null){
            return;
        }

        if (outputBuffer.isCommited()){
            return;
        }

        if (socket.isClosed()){
            return;
        }

        outputBuffer.setCommited(true);
        outputBuffer.doWrite();
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
        this.outputBuffer = response.getOutputBuffer();
    }
}
