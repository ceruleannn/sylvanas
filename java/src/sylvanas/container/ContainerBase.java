package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;
import sylvanas.container.pipeline.Pipeline;
import sylvanas.container.pipeline.StandardPipeline;

import java.util.TreeMap;

/**
 * difference between child and nextContainer is the former for initialization ,
 * the latter for processing request
 *
 */
public abstract class ContainerBase implements Container {

    protected Container nextContainer = null;

    protected final Pipeline pipeline = new StandardPipeline();

    /**
     * Mapper for request / 请求映射
     *
     * <Context path="/index/abc" docBase="Sylvanas" reloadable="true" />
     * path = 访问url , docBase = 项目磁盘路径, 当放在webApp文件夹中为项目名
     *
     * for host:
     * key = path (默认是项目名) ;  value = context
     * if url starts with host key (或逐字符匹配,同时匹配取较长的 /app, /app/a1 匹配后者)
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
    protected final TreeMap<String, Container> children = new TreeMap<>();

    @Override
    public final void doChain(Request request, Response response){

        if (!doHandle(request,response)){
            return;
        }

        Container next = getNextContainer();
        if (next!=null){
            next.doChain(request, response);
        }
    }

    /**
     * specific task 具体的任务
     * @return  do next or not
     */
    @Override
    public abstract boolean doHandle(Request request, Response response);

    /**
     * if has duplicate keys then replace the older one
     * 若有重复的 key 键 , 则替换旧值
     * @param path 匹配串
     * @param container 容器
     */
    @Override
    public void addChild(String path, Container container){
        if (container==null||path==null){
            return;
        }
        children.put(path, container);
    }

    @Override
    public void findChild(String path){
       if (path==null){
           return;
       }
        children.get(path);
    }

    @Override
    public void removeChild(String path){
        if (path==null){
            return;
        }

        children.remove(path);
    }

    @Override
    public abstract Container map(String uri);



    @Override
    public void addNextContainer(Container nextContainer) {
        this.nextContainer = nextContainer;
    }

    @Override
    public Container getNextContainer() {
        return nextContainer;
    }
}
