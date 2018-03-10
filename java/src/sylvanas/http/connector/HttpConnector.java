package sylvanas.http.connector;

import sylvanas.container.Container;

import java.io.IOException;
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
    private HttpProcessorManager httpProcessorManager = null;
    private Container container = null;


    public HttpConnector(){
        httpProcessorManager = new HttpProcessorManager(this);
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
