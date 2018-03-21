package sylvanas.container.pipeline;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

import java.util.Map;

/**
 * @Description:
 */
public interface Valve {

    Valve getNext();

    void setNext(Valve next);

    void invoke(Request request, Response response);

    String getInfo();

    void addInitParameter(String name, String value);

    Map<String,String> getParameterMap();
}
