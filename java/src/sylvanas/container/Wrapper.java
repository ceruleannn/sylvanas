package sylvanas.container;

import sylvanas.component.exception.Assert;
import sylvanas.connector.Request;
import sylvanas.connector.Response;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *    a container for one specify servlet
 *
     <servlet>
        <display-name></display-name>
        <description></description>
        <security-role-ref></security-role-ref>
        <jsp-file></jsp-file>
        <run-as></run-as>

        <servlet-name>business</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>publishContext</param-name>
            <param-value>false</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
     </servlet>
 */
public class Wrapper extends ContainerBase {

    /**
     * url mapping
     */
    private String path;

    private int loadOnStartup;

    private String name;

    private final Map<String, String> initParameters = new HashMap<>();

    private String runAs;

    private String servletClass;

    public Wrapper(){

        super();
    }



    @Override
    public boolean doHandle(Request request, Response response) {

        return false;
    }

    public int getLoadOnStartup() {
        return loadOnStartup;
    }

    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getInitParameters() {
        return initParameters;
    }

    public void addInitParameter(String key, String value){
        Assert.notNull(name, value);
        initParameters.put(key,value);
    }

    public String getRunAs() {
        return runAs;
    }

    public void setRunAs(String runAs) {
        this.runAs = runAs;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
