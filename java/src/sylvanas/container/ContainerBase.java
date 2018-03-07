package sylvanas.container;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public abstract class ContainerBase implements Container, Handler{

    protected Handler nextHandler = null;

    @Override
    public void doChain(Request request, Response response){

        doHandle(request,response);

        Handler next = getNextHandler();
        if (next!=null){
            next.doChain(request, response);
        }
    }

    /**
     * specific
     * @param request
     * @param response
     */
    @Override
    public abstract void doHandle(Request request, Response response);

    @Override
    public void addNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public Handler getNextHandler() {
        return nextHandler;
    }
}
