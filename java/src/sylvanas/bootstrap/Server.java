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
        HttpConnector connector = new HttpConnector();
        Container host = new Host();
        connector.setContainer(host);
        connector.start();
    }
}

// TODO
// 1.DIGESTER MAPPER
// 2.DEPLOYER RESOURCE
// 3.LIFESTYLE