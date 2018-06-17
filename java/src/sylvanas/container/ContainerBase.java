package sylvanas.container;

import sylvanas.component.lifecycle.LifecycleBase;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.component.lifecycle.LifecycleState;
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
public abstract class ContainerBase extends LifecycleBase implements Container{

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

    /**
     * chain of Responsibility
     */
    @Override
    public final String doChain(Request request, Response response){

        // maybe int code is better
        String message = doHandle(request,response);
        if (message!=null){

            //TODO ERROR MESSAGE PROCESS
            return message;
        }

        Container next = getNextContainer();
        if (next!=null){
            next.doChain(request, response);
        }
        return null;
    }


    /**
     * specific task 具体的任务
     * @return  do next or not
     */
    @Override
    public abstract String doHandle(Request request, Response response);


    @Override
    protected void initInternal() throws LifecycleException {
        for (Container child : children) {
            child.init();
        }
        setState(LifecycleState.INITIALIZED,null);
    }

    @Override
    protected void startInternal() throws LifecycleException {
        for (Container child : children) {
            child.start();
        }
        setState(LifecycleState.STARTED,null);
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        for (Container child : children) {
            child.stop();
        }
        setState(LifecycleState.STOPPED,null);
    }

    @Override
    protected void destroyInternal() throws LifecycleException {

        for (Container child : children) {
            this.removeChild(child);
            child.destroy();
        }
        setState(LifecycleState.DESTROYED,null);
    }


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
