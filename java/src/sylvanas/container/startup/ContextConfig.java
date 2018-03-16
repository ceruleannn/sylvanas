package sylvanas.container.startup;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import sylvanas.component.digester.*;
import sylvanas.component.resource.FileResource;
import sylvanas.component.resource.Resource;
import sylvanas.container.Context;

import javax.servlet.SessionCookieConfig;
import java.io.IOException;

/**
 * @Description:
 */
public class ContextConfig {

    /**
     * 解析器
     */
    private Digester digester = new Digester();

    /**
     * 解析结果对象
     */
    private WebXml webXml = new WebXml();

    /**
     * 解析规则 (不支持xml - fragment)
     */
    private WebRuleSet webRuleSet = new WebRuleSet(false);

    public ContextConfig(){

    }

    /**
     * 启动方法
     * 配置一个Context工程
     */
    public void webConfig(){

    }

    public void parseWebXml(){

        digester.push(webXml);

        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.addRuleSet(webRuleSet);

        try {
            // File, inputStream, inputSource, uri,
            digester.parse(getWebXmlSource().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void configureContext(Context context){

        // resolve display name and  is distributable
        context.setDisplayName(webXml.getDisplayName());
        context.setDistributable(webXml.isDistributable());

        // resolve error pages
        for (ErrorPage errorPage:webXml.getErrorPages().values()){
            context.getErrorPageHandler().addErrorPage(errorPage);
        }

        // resolve filter
        for (FilterDef filterDef:webXml.getFilters().values()){
            context.getFilterHandler().addFilterDef(filterDef);
        }

        for (FilterMap filterMap:webXml.getFilterMappings()){
            context.getFilterHandler().addFilterMap(filterMap);
        }

        // TODO resolve welcome page
        // TODO resolve application listener

        // resolve session

        SessionConfig sessionConfig = webXml.getSessionConfig();
        if (sessionConfig != null) {
            if (sessionConfig.getSessionTimeout() != null) {
                context.getSessionHandler().setMaxInactiveInterval(sessionConfig.getSessionTimeout());
            }

            SessionCookieConfig scc =
                    context.getServletContext().getSessionCookieConfig();
            scc.setName(sessionConfig.getCookieName());
            scc.setDomain(sessionConfig.getCookieDomain());
            scc.setPath(sessionConfig.getCookiePath());
            scc.setComment(sessionConfig.getCookieComment());

            if (sessionConfig.getCookieHttpOnly() != null) {
                scc.setHttpOnly(sessionConfig.getCookieHttpOnly());
            }
            if (sessionConfig.getCookieSecure() != null) {
                scc.setSecure(sessionConfig.getCookieSecure());
            }
            if (sessionConfig.getCookieMaxAge() != null) {
                scc.setMaxAge(sessionConfig.getCookieMaxAge());
            }
            if (sessionConfig.getSessionTrackingModes().size() > 0) {
                context.getServletContext().setSessionTrackingModes(
                        sessionConfig.getSessionTrackingModes());
            }
        }

        // resolve servlet



        // resolve loginConfig, security, name resource

        // resolve jsp
        /**
             for (JspPropertyGroup jspPropertyGroup : jspPropertyGroups) {
             JspPropertyGroupDescriptor descriptor =
             new ApplicationJspPropertyGroupDescriptor(jspPropertyGroup);
             context.getJspConfigDescriptor().getJspPropertyGroups().add(
             descriptor);
             }
         */



    }

    protected Resource getWebXmlSource() {

        return new FileResource("C:\\Users\\1\\Desktop\\web.xml");
    }
}
