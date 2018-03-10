package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public class Host extends ContainerBase{

    protected Mapper mapper = null;

    public Host(){
        super();
    }

    public void init(){
        mapper = new Mapper();

        // digester.read deploy
        // Context context = new Context()
        // mapper.add(projectName, context)
    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
