package sylvanas.container.startup;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import sylvanas.component.digester.*;
import sylvanas.component.resource.FileResource;
import sylvanas.component.resource.Resource;
import sylvanas.container.Context;
import sylvanas.container.Wrapper;
import sylvanas.util.Constants;

import java.io.IOException;
import java.util.Map;


public class ContextConfig {

    private Resource XMLResource;

    private Context context;

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

    public ContextConfig(Context context){
        this.context = context;
        XMLResource = new FileResource(context.getDocBase()+Constants.WEB_XML_PATH);
    }

    /**
     * 启动方法
     * 配置一个Context工程
     */
    public void webConfig(){

        parseWebXml();
        configureContext();
    }

    private void parseWebXml(){

        digester.push(webXml);

        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.addRuleSet(webRuleSet);

        try {
            // File, inputStream, inputSource, uri,

            if (XMLResource!=null&&XMLResource.exists())
                digester.parse(XMLResource.getInputStream());
            else {
                throw new RuntimeException("can not deploy web.xml for" + context.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void configureContext(){

        // resolve display name and  is distributable
        context.setDisplayName(webXml.getDisplayName());
        context.setDistributable(webXml.isDistributable());

        // resolve error pages
//        for (ErrorPage errorPage:webXml.getErrorPages().values()){
//            context.getErrorPageHandler().addErrorPage(errorPage);
//        }

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

//        SessionConfig sessionConfig = webXml.getSessionConfig();
//        if (sessionConfig != null) {
//            if (sessionConfig.getSessionTimeout() != null) {
//                context.getSessionHandler().setMaxInactiveInterval(sessionConfig.getSessionTimeout());
//            }
//
//            SessionCookieConfig scc =
//                    context.getServletContext().getSessionCookieConfig();
//            scc.setName(sessionConfig.getCookieName());
//            scc.setDomain(sessionConfig.getCookieDomain());
//            scc.setPath(sessionConfig.getCookiePath());
//            scc.setComment(sessionConfig.getCookieComment());
//
//            if (sessionConfig.getCookieHttpOnly() != null) {
//                scc.setHttpOnly(sessionConfig.getCookieHttpOnly());
//            }
//            if (sessionConfig.getCookieSecure() != null) {
//                scc.setSecure(sessionConfig.getCookieSecure());
//            }
//            if (sessionConfig.getCookieMaxAge() != null) {
//                scc.setMaxAge(sessionConfig.getCookieMaxAge());
//            }
//            if (sessionConfig.getSessionTrackingModes().size() > 0) {
//                context.getServletContext().setSessionTrackingModes(
//                        sessionConfig.getSessionTrackingModes());
//            }
//        }

        // resolve servlet
        boolean hasDefault = false;


        for (Map.Entry<String, String> entry : webXml.getServletMappings().entrySet()) {
            String value = entry.getValue();
            if (value.equals("/")){
                hasDefault = true;
            }

            context.addServletMapping(entry.getKey(), value);
        }

        // add default servlet
        if (!hasDefault){
            context.addServletMapping("/", "syl_default_servlet");
            ServletDef defaultServlet = new ServletDef();
            defaultServlet.setServletName("syl_default_servlet");
            defaultServlet.setServletClass("sylvanas.component.servlet.DefaultServlet");
            webXml.addServlet(defaultServlet);
        }

        for (ServletDef servlet : webXml.getServlets().values()) {
            Wrapper wrapper = context.createWrapper();
            // Description is ignored
            // Display name is ignored
            // Icons are ignored

            // jsp-file gets passed to the JSP Servlet as an init-param

            if (servlet.getLoadOnStartup() != null) {
                wrapper.setLoadOnStartup(servlet.getLoadOnStartup());
            }

            String  servletName = servlet.getServletName();
            wrapper.setName(servletName);

            Map<String,String> params = servlet.getParameterMap();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                wrapper.addInitParameter(entry.getKey(), entry.getValue());
            }

            wrapper.setRunAs(servlet.getRunAs());
            wrapper.setServletClass(servlet.getServletClass());

            wrapper.setParent(context);
            context.addChild(wrapper);

            // get servlet class form class loader

        }

        // one servlet for n mapping

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

}
