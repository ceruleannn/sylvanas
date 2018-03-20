package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

import java.util.List;

/**
 * @Description:
 */
public interface Container {


    boolean doHandle(Request request, Response response);

    void doChain(Request request, Response response);

    void addNextContainer(Container container);

    Container getNextContainer();

    void addChild(Container container);

    void removeChild(Container container);

    void setParent(Container container);

    Container getParent();

    List<Container> getChildren();

    String getName();

    void setName(String name);

}
