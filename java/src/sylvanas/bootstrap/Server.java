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
// 3.LIFESTYLE LISTENER

// CONTAINER CLASSLOADER

// LOG / ERROR /ACCESS LOG //EXCEPTION ERROR PAGE
// NIO / SSL
// JSP