package sylvanas.container;

import sylvanas.container.pipeline.Pipeline;
import sylvanas.container.pipeline.StandardPipeline;
import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public abstract class ContainerBase implements Container, Handler{

    protected Handler nextHandler = null;

    protected final Pipeline pipeline = new StandardPipeline();

    @Override
    public final void doChain(Request request, Response response){

        if (!doHandle(request,response)){
            return;
        }

        Handler next = getNextHandler();
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
    public void addNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public Handler getNextHandler() {
        return nextHandler;
    }
}
