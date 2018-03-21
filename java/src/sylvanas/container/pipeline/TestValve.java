package sylvanas.container.pipeline;

import sylvanas.connector.Request;
import sylvanas.connector.Response;

/**
 *
 *
 *
 */
public class TestValve extends ValveBase{

    @Override
    public void invoke(Request request, Response response) {

    }

    @Override
    public String getInfo() {
        return "iloveyou";
    }
}
