package sylvanas.connector.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * Processor object pool and ThreadPool
 *
 */
public class HttpProcessorManager {

    //TODO: Limit latch 流量控制

    private ThreadPoolExecutor executor = null;
    private int coreSize = 10;


    private static HttpProcessorManager instance = null;

    private HttpProcessorManager(){
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setCorePoolSize(coreSize);
        executor.setKeepAliveTime(5, TimeUnit.SECONDS);

    }

    public void process(Runnable runnable){
        getExecutor().execute(runnable);
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

    public static HttpProcessorManager getManager(){

        if (instance==null){
            instance = new HttpProcessorManager();
        }
        return instance;
    }

}
