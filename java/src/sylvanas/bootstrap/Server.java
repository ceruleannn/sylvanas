package sylvanas.bootstrap;

import sylvanas.component.lifecycle.LifecycleBase;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.component.server.PerformanceMonitor;
import sylvanas.connector.http.HttpConnector;
import sylvanas.connector.http.HttpNioConnector;
import sylvanas.container.Host;

/**
 * Sylvanas 1.0
 *
 * @author piao
 * 2018.02 - 2018.05
 */
public class Server extends LifecycleBase{

    public final static int BIO_MODE = 1;
    public final static int NIO_MODE = 2;

    //private int IOmode = BIO_MODE;
    private int IOmode = NIO_MODE;


    private Host host;
    private HttpConnector connector;
    private PerformanceMonitor monitor;
    private HttpNioConnector nioConnector;


    @Override
    protected void initInternal() throws LifecycleException {

        host = new Host();
        host.init();
        monitor = new PerformanceMonitor();

        if (IOmode==NIO_MODE){
            nioConnector = new HttpNioConnector();
            nioConnector.setContainer(host);
            nioConnector.init();
        }

        if (IOmode==BIO_MODE){
            connector = new HttpConnector();
            connector.setContainer(host);
            connector.init();
        }

    }

    @Override
    protected void startInternal() throws LifecycleException {
        host.start();
        monitor.start();

        if (IOmode==NIO_MODE){
            nioConnector.start();
        }

        if (IOmode==BIO_MODE){
            connector.start();
        }
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
// NETTY3

// LOG / ERROR PAGE /ACCESS LOG /EXCEPTION HANDLER
// 读写Syl.xml 配置容器, 容器管理Factory
//1. 检测静态资源并更新到缓冲 defaultServlet

// NIO / SSL

// DEBUG AND TO DO


// 优化策略 stop关闭后台线程  timer等
