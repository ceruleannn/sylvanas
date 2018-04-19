package sylvanas.component.lifecycle;


public enum LifecycleState {
    NEW(false, null),
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),
    STARTED(true, Lifecycle.AFTER_START_EVENT),
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),
    DESTROYED(false, Lifecycle.AFTER_DESTROY_EVENT),
    FAILED(false, null);

    private final boolean available;
    private final String lifecycleEvent;

    private LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    /**
     * May the public methods other than property getters/setters and lifecycle
     * methods be called for a component in this state? It returns
     * <code>true</code> for any component in any of the following states:
     * <ul>
     * <li>{@link #STARTED}</li>
     * </ul>
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     *
     */
    public String getLifecycleEvent() {
        return lifecycleEvent;
    }
}
