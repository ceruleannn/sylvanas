package sylvanas.container;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public class Context extends ContainerBase{

    public Context(){
        super();
    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
