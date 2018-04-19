package sylvanas.component.lifecycle;

/**
 *
 *
 *
 */
public class LifecycleSupport {

    private Lifecycle lifecycle = null;
    private LifecycleListener[] listeners = new LifecycleListener[0];

    public LifecycleSupport(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
    }

    public void addLifecycleListener(LifecycleListener listener) {

        LifecycleListener[] newListeners = new LifecycleListener[listeners.length+1];
        System.arraycopy(listeners,0,newListeners,0,listeners.length);
        newListeners[listeners.length] = listener;
        listeners = newListeners;
    }

    public LifecycleListener[] findLifecycleListeners() {
        return listeners;
    }

    public void removeLifecycleListener(LifecycleListener listener) {
        LifecycleListener[] newListeners = new LifecycleListener[listeners.length-1];
        for (int i=0;i<listeners.length;i++){
            if (listeners[i].equals(listener)){
                System.arraycopy(listeners,0,newListeners,0,i);
                System.arraycopy(listeners,i+1,newListeners,i,listeners.length-1-i);
                listeners = newListeners;
                break;
            }
        }
    }

    public void fireLifecycleEvent(String type,Object data){
        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        LifecycleListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].lifecycleEvent(event);
    }

}
