package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public class Wrapper extends ContainerBase {

    public Wrapper(){
        super();
    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
