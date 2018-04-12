package sylvanas.component.context;

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

    public DefaultDispatcher(){

    }

    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {


    }
}
