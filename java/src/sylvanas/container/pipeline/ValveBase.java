package sylvanas.container.pipeline;

import sylvanas.http.connector.Request;
import sylvanas.http.connector.Response;

/**
 * @Description:
 */
public abstract  class ValveBase implements Valve{

    protected Valve next = null;

    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve next) {
        this.next = next;
    }

    @Override
    public abstract void invoke(Request request, Response response);


    @Override
    public String getInfo() {
        return "";
    }
}
