package sylvanas.container.core;

import sylvanas.connector.Request;
import sylvanas.container.Context;
import sylvanas.container.Wrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *
 *
 *
 */
public class DefaultDispatcher implements RequestDispatcher{

    private Context context;
    private String path;
    public DefaultDispatcher(Context context,String path){
        this.context = context;
        this.path = path;
    }

    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        doHandle(request,response);

        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        doHandle(request,response);
    }

    private void doHandle(ServletRequest request, ServletResponse response) throws IOException, ServletException{
        Request request1 = new Request(null);
        request1.setRequestURI(context.getPath()+path);

        Wrapper wrapper = context.getMapper().map(path);
        wrapper.getInstance().service(request1,response);
    }
}
