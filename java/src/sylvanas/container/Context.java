package sylvanas.container;

import sylvanas.component.context.ErrorPageHandler;
import sylvanas.component.context.FilterHandler;
import sylvanas.component.context.InstanceManager;
import sylvanas.component.exception.Assert;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.connector.Request;
import sylvanas.connector.Response;
import sylvanas.connector.session.SessionHandler;
import sylvanas.container.core.ApplicationContext;
import sylvanas.container.loader.WebAppLoader;
import sylvanas.container.startup.ContextConfig;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * context container for the web application
 *
 * @author piao
 */
public class Context extends ContainerBase{

    private String XMLPath;

    private ContextConfig contextConfig;

    protected ServletContext servletContext = null;

    protected ContextMapper mapper = null;

    protected String displayName = null;

    protected boolean distributable = false;

    protected ErrorPageHandler errorPageHandler = null;

    protected FilterHandler filterHandler = null;

    protected SessionHandler sessionHandler = null;

    private File docBase;

    //  key url  value name
    private Map<String , String> servletMapping = new HashMap<>();

    protected WebAppLoader webAppLoader;

    protected InstanceManager instanceManager;



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


    }

    @Override
    protected void initInternal() throws LifecycleException {

        servletContext = new ApplicationContext(this);

        filterHandler = new FilterHandler();
//        ServletContext servletContext = new ServletContext() {
//        }s
        // digester.read web.XML
        // mapper.add(uri, Servlet)
        webAppLoader = new WebAppLoader(this);
        instanceManager = new InstanceManager(webAppLoader.getClassLoader());

        contextConfig = new ContextConfig(this);
        contextConfig.webConfig();


        mapper = new ContextMapper(getChildren(),servletMapping);

        sessionHandler = new SessionHandler();

        super.initInternal();
    }


    public Wrapper createWrapper(){

        //TODO LISTENER


        return new Wrapper(this);
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

    public void addServletMapping(String url, String name){

        Assert.notNull(url,name);
        servletMapping.put(url,name);
    }


    @Override
    public String doHandle(Request request, Response response) {

        request.setContext(this);
        request.setServletContext(servletContext);


        String uri = request.getRequestURI();
        Wrapper wrapper = mapper.map(uri.substring(path.length(),uri.length()));

        addNextContainer(wrapper);

       // System.out.println("wrapper mapped "+ uri.substring(path.length(),uri.length()));
        return null;
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

    public File getDocBase() {
        return docBase;
    }

    public void setDocBase(File docBase) {
        this.docBase = docBase;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ContextMapper getMapper() {
        return mapper;
    }

    public WebAppLoader getWebAppLoader() {
        return webAppLoader;
    }

    public InstanceManager getInstanceManager() {
        return instanceManager;
    }
}
