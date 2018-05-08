package sylvanas.bootstrap;

import sylvanas.component.lifecycle.LifecycleException;

/**
 *
 *
 *
 */
public class Bootstrap {

    public static void main(String[] args) {
        Server server = new Server();
        try {

            long before = System.currentTimeMillis();

            server.init();
            server.start();

            long after = System.currentTimeMillis();

            System.out.println("INFO: Server start successfully in " + (after - before) + " ms");
            System.out.println();

        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
