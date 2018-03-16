package sylvanas.container;

import sylvanas.component.context.ErrorPageHandler;
import sylvanas.component.context.FilterHandler;
import sylvanas.component.exception.Assert;
import sylvanas.connector.Request;
import sylvanas.connector.Response;
import sylvanas.connector.session.SessionHandler;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * context container for the web application
 *
 * @author piao
 */
public class Context extends ContainerBase{

    protected ServletContext servletContext = null;

    protected Mapper mapper = null;

    protected String displayName = null;

    protected boolean distributable = false;

    protected ErrorPageHandler errorPageHandler = null;

    protected FilterHandler filterHandler = null;

    protected SessionHandler sessionHandler = null;

    /**
     * The init Parameter(s) for Servlet context, mostly from web.xml ?
     *
     * <context-param>
     * <param-name>home-page</param-name>
     * <param-value>home.jsp</param-value>
     * </context-param>
     *
     * @see ServletContext#getInitParameter(String)
     * @see ServletContext#getInitParameterNames()
     */
    protected Map<String, String> contextParameters = new HashMap<>();

    public Context(){
        super();
    }

    public void init(){
        mapper = new Mapper();

        // digester.read web.XML
        // Wrapper Servlet = new Servlet();
        // mapper.add(uri, Servlet)
    }

    public void setDisplayName(String displayName){

        Assert.notNull(displayName);
        this.displayName = displayName;
        //firePropertyChange?
    }

    public void setDistributable(boolean distributable){

        this.distributable = distributable;
        //firePropertyChange?
    }

    public void addParameter(String name, String value){

        Assert.notNull(name, value);
        contextParameters.put(name, value);
        // ignore duplicate
        // fireContainerEvent("addParameter", name)?
    }

    @Override
    public boolean doHandle(Request request, Response response) {
        return false;
    }

    public ErrorPageHandler getErrorPageHandler() {
        return errorPageHandler;
    }

    public FilterHandler getFilterHandler() {
        return filterHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
