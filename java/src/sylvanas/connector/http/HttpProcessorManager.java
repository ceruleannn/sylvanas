package sylvanas.connector.http;

import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * Processor object pool and ThreadPool
 *
 */
public class HttpProcessorManager {

    //TODO: Limit latch 流量控制

    private ThreadPoolExecutor executor = null;
    private int maxSize = 200;
    private int coreSize = 10;

    private HttpConnector connector = null;

    public HttpProcessorManager(HttpConnector connector){
        this.connector = connector;

        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setMaximumPoolSize(maxSize);
        executor.setCorePoolSize(coreSize);
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
    }

    public void processSocket(Socket socket){

        getExecutor().execute(new HttpProcessor(connector, socket));
    }


    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        executor.setMaximumPoolSize(maxSize);
    }

    public int getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(int coreSize) {
        this.coreSize = coreSize;
        executor.setCorePoolSize(coreSize);
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

}
