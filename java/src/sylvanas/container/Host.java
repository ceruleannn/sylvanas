package sylvanas.container;

import sylvanas.component.lifecycle.LifecycleException;
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

    @Override
    protected void initInternal() throws LifecycleException {
        mapper = new HostMapper();
        hostConfig = new HostConfig(this);

        // digester.read deploy
        // Context context = new Context()
        // mapper.add(projectName, context)

        super.initInternal();

    }

    @Override
    protected void startInternal() throws LifecycleException {
        super.startInternal();
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        super.stopInternal();
    }

    @Override
    protected void destroyInternal() throws LifecycleException {
        super.destroyInternal();
    }

    public HostConfig getHostConfig() {
        return hostConfig;
    }
}
