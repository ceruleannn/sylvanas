package sylvanas.connector.http;

import sylvanas.component.context.ExceptionHandler;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.container.Container;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpConnector extends AbstractConnector implements Runnable {

    //TODO: ServerSocketFactory

    private String protocol = "connector";
    private int port = 8080;
    private String ip = "127.0.0.1";
    private boolean stopped = false;
    private Container container = null;

    private ServerSocket serverSocket = null;

    private HttpProcessorManager manager = HttpProcessorManager.getManager();


    public HttpConnector(){

    }

    @Override
    public void run() {

        while(!stopped){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                manager.process(new HttpProcessor(this,socket));

            } catch (IOException e) {
                String message = "等待连接时发生 I/O 错误";
                ExceptionHandler.handleServerException(e,message);
            }
        }
    }

    @Override
    protected void initInternal() throws LifecycleException {

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(ip));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void startInternal() throws LifecycleException {
        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    protected void stopInternal() throws LifecycleException {
        //TODO 抛出中断 结束线程
    }

    @Override
    protected void destroyInternal() throws LifecycleException {

    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
