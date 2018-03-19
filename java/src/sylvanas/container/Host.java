package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public class Host extends ContainerBase{

    protected HostMapper mapper = null;

    public Host(){
        super();
    }

    public void init(){
        mapper = new HostMapper();



        // digester.read deploy
        // Context context = new Context()
        // mapper.add(projectName, context)
    }

    public void createContext(){

    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }
}
