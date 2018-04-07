package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;
import sylvanas.container.startup.HostConfig;

/**
 * @Description:
 */
public class Host extends ContainerBase{

    protected HostMapper mapper = null;

    protected HostConfig hostConfig = null;

    public Host(){
        init();

    }

    public void init(){
        mapper = new HostMapper();
        hostConfig = new HostConfig(this);

        // digester.read deploy
        // Context context = new Context()
        // mapper.add(projectName, context)
    }


    @Override
    public String doHandle(Request request, Response response) {
        Context context = mapper.map(request.getRequestURI());
        addNextContainer(context);
        return null;
    }

    public HostMapper getMapper() {
        return mapper;
    }
}
