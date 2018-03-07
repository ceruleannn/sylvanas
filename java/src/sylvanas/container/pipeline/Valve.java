package sylvanas.container.pipeline;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public interface Valve {

    public Valve getNext();

    public void setNext(Valve next);

    public void invoke(Request request, Response response);

    public String getInfo();
}
