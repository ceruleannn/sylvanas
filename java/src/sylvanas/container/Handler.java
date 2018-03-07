package sylvanas.container;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public interface Handler {


    public boolean doHandle(Request request, Response response);

    public void doChain(Request request, Response response);

    public void addNextHandler(Handler handler);

    public Handler getNextHandler();

}
