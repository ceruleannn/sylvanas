package sylvanas.container;

import sylvanas.connector.Request;
import sylvanas.connector.Response;
import sylvanas.container.pipeline.Pipeline;
import sylvanas.container.pipeline.StandardPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * difference between child and nextContainer is the former for initialization ,
 * the latter for processing request
 *
 */
public abstract class ContainerBase implements Container {

    protected Container nextContainer = null;

    protected final Pipeline pipeline = new StandardPipeline();

    protected final List<Container> children = new ArrayList<>();

    protected Container parent = null;

    /**
     * name for context means its project name
     * name for wrapper means its servlet name
     *
     */
    protected String name = null;

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

    @Override
    public void addChild(Container container) {
        if (container==null){
            return;
        }

        children.add(container);
    }

    @Override
    public void removeChild(Container container) {
        if (container==null){
            return;
        }

        children.remove(container);
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public void addNextContainer(Container nextContainer) {
        this.nextContainer = nextContainer;
    }

    @Override
    public Container getNextContainer() {
        return nextContainer;
    }

    @Override
    public List<Container> getChildren() {
        return children;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
