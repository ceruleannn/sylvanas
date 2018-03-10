package sylvanas.container.pipeline;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public interface Valve {

    Valve getNext();

    void setNext(Valve next);

    void invoke(Request request, Response response);

    String getInfo();
}
