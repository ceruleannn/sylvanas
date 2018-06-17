package sylvanas.container.pipeline;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 */
public abstract  class ValveBase implements Valve{

    protected Valve next = null;
    protected Map<String,String> parameters = new HashMap<>();

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

    @Override
    public void addInitParameter(String name, String value){
        if (parameters.containsKey(name)) {
            return;
        }
        parameters.put(name, value);
    }

    @Override
    public Map<String, String> getParameterMap(){
        return parameters;
    }
}
