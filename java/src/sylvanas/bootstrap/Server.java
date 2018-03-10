package sylvanas.bootstrap;

import sylvanas.container.Container;
import sylvanas.container.Host;
import sylvanas.http.connector.HttpConnector;

/**
 * @Date:
 * @Modified:
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
