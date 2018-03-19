package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public interface Container {


    boolean doHandle(Request request, Response response);

    void doChain(Request request, Response response);

    void addNextContainer(Container container);

    Container getNextContainer();

    void addChild(String path, Container container);

    void findChild(String path);

    void removeChild(String path);

    Container map(String uri);

}
