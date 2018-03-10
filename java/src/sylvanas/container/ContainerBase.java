package sylvanas.container;

import sylvanas.container.pipeline.Pipeline;
import sylvanas.container.pipeline.StandardPipeline;
import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 * @Description:
 */
public abstract class ContainerBase implements Container {

    protected Container nextContainer = null;

    protected final Pipeline pipeline = new StandardPipeline();

    @Override
    public final void doChain(Request request, Response response){

        if (!doHandle(request,response)){
            return;
        }

        Container next = getNextContainer();
        if (next!=null){
            next.doChain(request, response);
        }
    }

    /**
     * specific task 具体的任务
     * @param request
     * @param response
     * @return  do next or not
     */
    @Override
    public abstract boolean doHandle(Request request, Response response);

    @Override
    public void addNextContainer(Container nextContainer) {
        this.nextContainer = nextContainer;
    }

    @Override
    public Container getNextContainer() {
        return nextContainer;
    }
}
