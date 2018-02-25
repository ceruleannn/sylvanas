package connector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 *
 *
 */
public class HttpConnector implements Runnable{

    //TODO: ServerSocketFactory

    private String protocol = "http";
    private int port = 8080;
    private String ip = "127.0.0.1";
    private boolean stopped = false;
    HttpProcessorManager httpProcessorManager = null;

    public HttpConnector(){
        httpProcessorManager = new HttpProcessorManager();
    }

    @Override
    public void run() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!stopped){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                httpProcessorManager.processSocket(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
}
