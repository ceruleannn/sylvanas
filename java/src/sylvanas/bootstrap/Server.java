package sylvanas.bootstrap;

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
        connector.start();
    }
}
