package sylvanas.connector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description:
 */
public class RawResponse {

    private Socket socket = null;
    private RawRequest rawRequest = null;


    private static final String HTML = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "Content-Length: %d\r\n" + "\r\n"
            + "%s";

    public RawResponse(){

    }


    public void doWrite() throws IOException{

        String raw = rawRequest.getRaw();
        OutputStream out = socket.getOutputStream();

        raw = "<h1>" + raw + "</h1>";
        out.write(String.format(HTML, raw.length(), raw).getBytes());
        out.flush();
        out.close();
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
}
