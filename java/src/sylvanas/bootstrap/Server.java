package sylvanas.bootstrap;

import sylvanas.component.lifecycle.LifecycleBase;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.component.server.PerformanceMonitor;
import sylvanas.connector.http.HttpConnector;
import sylvanas.container.Host;

/**
 * Sylvanas 1.0
 *
 * @author piao
 * 2018.02 - 2018.05
 */
public class Server extends LifecycleBase{

    private Host host;
    private HttpConnector connector;
    private PerformanceMonitor monitor;

    @Override
    protected void initInternal() throws LifecycleException {
        connector = new HttpConnector();
        host = new Host();
        connector.setContainer(host);

        monitor = new PerformanceMonitor();

        host.init();
        connector.init();
    }

    @Override
    protected void startInternal() throws LifecycleException {
        host.start();
        connector.start();
        monitor.start();
    }

    @Override
    protected void stopInternal() throws LifecycleException {

    }

    @Override
    protected void destroyInternal() throws LifecycleException {


    }

    public static void main(String[] args) {
        long before = System.currentTimeMillis();

        Server server = new Server();
        try {
            server.init();
            server.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

        long after = System.currentTimeMillis();
        System.out.println("INFO: Server start successfully in " + (after - before) + " ms");
        System.out.println();

    }


}



// TODO
// 1.DIGESTER (WEB,SERVER) MAPPER
// 2.DEPLOYER RESOURCE
// 3.RESPONSE / DEFAULT SERVLET
// 4.CONTAINER CLASSLOADER

//

// JSP
// WEB DEPLOY
// FILE UPLOAD
// LIFECYCLE LISTENER
// LOG / ERROR PAGE /ACCESS LOG /EXCEPTION HANDLER
// 读写Syl.xml 配置容器, 容器管理Factory

// NIO / SSL

// DEBUG AND TO DO
//1. 检测静态资源并更新到缓冲 defaultServlet

// 优化策略 为什么同样的静态项目 tomcat 1.6秒 syl要3秒
// NIO? ETAG 缓存 长连接?

// 优化策略 stop关闭后台线程  timer等
