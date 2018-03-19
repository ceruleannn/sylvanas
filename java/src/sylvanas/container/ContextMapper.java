package sylvanas.container;

import sylvanas.component.exception.Assert;
import sylvanas.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * for context:
 * 1.精确匹配, 2.路径匹配 /* , 3.拓展名匹配通配符 *., 4.默认SERVLET
 *
 * uri = url - host path
 * if uri equals context key
 * if uri ends with /*
 * if uri start with *.
 * if uri equals / for static resource
 *
 */

public class ContextMapper {

    private List<Wrapper> exactWrappers = new ArrayList<>();
    private List<Wrapper> wildcardWrappers = new ArrayList<>();
    private List<Wrapper> extensionWrappers = new ArrayList<>();
    private Wrapper defaultWrapper ;


    public ContextMapper(List<Container> children, Map<String,String> mappings){

        init();
    }

    /**
     * 初始化方法;
     * 根据 wrappers 和 mappings 创建mapper
     */
    public void init(){

    }

    /**
     * 初始化方法;
     * 添加Wrapper映射关系
     */
    public void addWrapper(Wrapper wrapper, String urlPattern){

        urlPattern = urlPattern.trim();

        if(checkURLPattern(urlPattern)){
            throw new IllegalArgumentException("invalid urlPattern");
        }

        if (urlPattern.endsWith("/*")){
            //wildcard wrapper
            String path = StringUtils.cleanPath(urlPattern.substring(0,urlPattern.length()-2));

            wrapper.setPath(path);
            wildcardWrappers.add(wrapper);

        } else if (urlPattern.startsWith("*.")){
            //extension wrapper
            String path = StringUtils.cleanPath(urlPattern.substring(2));
            wrapper.setPath(path);
            extensionWrappers.add(wrapper);

        } else if (urlPattern.equals("/")) {
            //default wrapper
            wrapper.setPath("/");
            defaultWrapper = wrapper;

        } else {
            // exact wrapper
            wrapper.setPath(StringUtils.cleanPath(urlPattern));
            exactWrappers.add(wrapper);
        }
    }

    /**
     * 请求方法;
     * 根据访问url返回对应的Wrapper实例
     */
    public Wrapper map(String requestURL){

        Assert.notNull(requestURL);
        requestURL = requestURL.trim();
        String clean = StringUtils.cleanPath(requestURL);

        // 1.first exact match
        for (Wrapper exactWrapper : exactWrappers) {
            if (clean.equals(exactWrapper.getPath())){
                return exactWrapper;
            }
        }

        //TODO /abc/* /abc/123 keep the / same to anywhere

        // 2.second match /*
        for (Wrapper wildcardWrapper : wildcardWrappers) {
            String path = wildcardWrapper.getPath();
            if (clean.startsWith(path)){
                if (clean.charAt(path.length())=='/'){
                    return wildcardWrapper;
                }
            }
        }

        // 3. extension math *.  abc.do
        for (Wrapper extensionWrapper : extensionWrappers) {
            String path = extensionWrapper.getPath();
            if (clean.endsWith(path)){
                if (clean.charAt(clean.length()-path.length())=='.'){
                    return extensionWrapper;
                }
            }
        }

        // 4. default match
        return defaultWrapper;
    }



    /**
     * 检验url的合法性
     * 通配符匹配和后缀名匹配不能同时使用
     *
     */
    private boolean checkURLPattern(String urlPattern){
        if (urlPattern==null){
            return false;
        }

        if (urlPattern.indexOf('\n')>0 || urlPattern.indexOf('\t')>0){
            return false;
        }

        if (urlPattern.startsWith("/")){
            if (urlPattern.indexOf("*.")>0){
                return false;
            }
        }

        if (urlPattern.startsWith("*.")){
            if (urlPattern.indexOf("/")>0){
                return false;
            }
        }

        return true;
    }
}
