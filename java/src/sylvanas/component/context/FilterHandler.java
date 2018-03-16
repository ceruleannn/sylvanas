package sylvanas.component.context;

import sylvanas.component.digester.FilterDef;
import sylvanas.component.digester.FilterMap;

import java.util.ArrayList;
import java.util.List;

/**
     <filter>
        <filter-name>filter</filter-name>
        <filter-class>dc.gz.filters.MyCharsetFilter</filter-class>
        <init-param>
            <param-name>charset</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
     </filter

     <filter-mapping>
        <filter-name>testFilter</filter-name>
        <url-pattern>/index.jsp</url-pattern>
        <servlet-name>servlet</servlet-name>
        <dispatcher>REQUEST</dispatcher>
        <dispatcher>FORWARD</dispatcher>
     </filter-mapping>

    <dispatcher> 子元素可以设置的值及其意义：
    REQUEST：当用户直接访问页面时，Web容器将会调用过滤器。RequestDispatcher的include()或forward()不会被调用。
    INCLUDE：仅RequestDispatcher的include()调用。
    FORWARD：仅RequestDispatcher的forward()调用。
    ERROR：仅通过声明式异常处理机制调用。

    attention: 过滤器的执行顺序与 <filter-mapping> 在web.xml上的先后顺序一致


    request → host → context → wrapper.doHandle()  → get info from FilterHandler → process

    for each mapping
        if url && servlet-name && dispatcher match
            find filterDef by filter-name

            instance filter

            createFilterChain , add match filter

            invoke chain's doFilter()

 */
public class FilterHandler {

    protected List<FilterDef> defs = new ArrayList<>();

    protected List<FilterMap> maps = new ArrayList<>();

    public FilterHandler(){

    }

    public void addFilterDef(FilterDef filterDef){
        defs.add(filterDef);
    }

    public void addFilterMap(FilterMap filterMap){
        maps.add(filterMap);
    }
}

