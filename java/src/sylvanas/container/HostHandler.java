package sylvanas.container;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public class HostHandler extends ContainerBase{

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
