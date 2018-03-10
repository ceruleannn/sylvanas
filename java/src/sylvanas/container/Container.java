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

}
