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

    protected ContextMapper mapper = null;

    protected String displayName = null;

    protected boolean distributable = false;

    protected ErrorPageHandler errorPageHandler = null;

    protected FilterHandler filterHandler = null;

    protected SessionHandler sessionHandler = null;

    private Map<String , String> servletMapping = new HashMap<>();



    /**
     *  context configure.xml path property
     *  default value is the project name
     */
    private String path = null;

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
    protected Map<String, String> initParameters = new HashMap<>();

    public Context(){
        super();
    }

    public void init(){


        // digester.read web.XML
        // mapper.add(uri, Servlet)

        mapper = new ContextMapper(getChildren(),servletMapping);
    }

    public Wrapper createWrapper(){
        Wrapper wrapper = new Wrapper();

        //TODO LISTENER


        return wrapper;
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
        initParameters.put(name, value);
        // ignore duplicate
        // fireContainerEvent("addParameter", name)?
    }

    public void addServletMapping(String displayName, String value){

        Assert.notNull(displayName,value);
        servletMapping.put(displayName,value);
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
