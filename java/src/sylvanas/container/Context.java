package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public class Context extends ContainerBase{

    protected Mapper mapper = null;

    public Context(){
        super();
    }

    public void init(){
        mapper = new Mapper();

        // digester.read web.XML
        // Wrapper Servlet = new Servlet();
        // mapper.add(uri, Servlet)
    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
