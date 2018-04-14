package sylvanas.bootstrap;

import sylvanas.container.Container;
import sylvanas.container.Host;
import sylvanas.connector.http.HttpConnector;

/**
 * Sylvanas 1.0
 *
 *
 *
 */
public class Server {

    public static void main(String[] args) {
        long before = System.currentTimeMillis();

        Server server = new Server();
        server.start();

        long after = System.currentTimeMillis();
        System.out.println("INFO: Server start successfully in " + (after - before) + " ms");
        System.out.println();
    }

    public void start(){
        HttpConnector connector = new HttpConnector();
        Container host = new Host();
        connector.setContainer(host);
        connector.start();
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

// 优化策略 为什么同样的静态项目 tomcat 1.6秒 syl要3秒
// NIO? ETAG 缓存 长连接?
