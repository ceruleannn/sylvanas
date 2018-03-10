package sylvanas.container;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public interface Container {


    public boolean doHandle(Request request, Response response);

    public void doChain(Request request, Response response);

    public void addNextContainer(Container container);

    public Container getNextContainer();

}
