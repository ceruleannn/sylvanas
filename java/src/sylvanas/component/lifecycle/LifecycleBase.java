package sylvanas.component.lifecycle;

import sylvanas.component.exception.ExceptionUtils;

/**
 *
 *
 *
 */
public abstract  class LifecycleBase implements Lifecycle{

    private LifecycleSupport support = new LifecycleSupport(this);
    private LifecycleState state = LifecycleState.NEW;

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        support.addLifecycleListener(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return support.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        support.findLifecycleListeners();
    }

    protected void fireLifecycleEvent(String type, Object data) {
        support.fireLifecycleEvent(type, data);
    }

    @Override
    public void init() throws LifecycleException {

        if (!LifecycleState.NEW.equals(state)){
            return;
        }

        try {
            initInternal();
        } catch (Throwable t) {
           // ExceptionUtils.handleThrowable(t);
            setStateInternal(LifecycleState.FAILED, null);
            throw new LifecycleException("lifecycleBase.initFail "+ ExceptionUtils.Stack2String(t));
        }
        setStateInternal(LifecycleState.INITIALIZED, null);
    }

    protected void initInternal() throws LifecycleException{

    }

    @Override
    public void start() throws LifecycleException {

        if (LifecycleState.STARTED.equals(state)){
            return;
        }

        if (LifecycleState.NEW.equals(state)){
            init();
        }
        else if (LifecycleState.FAILED.equals(state)){
            stop();
        }

        try {
            startInternal();
        } catch (Throwable t) {
            t.printStackTrace();
           // ExceptionUtils.handleThrowable(t);
            setStateInternal(LifecycleState.FAILED, null);
            throw new LifecycleException("lifecycleBase.startFail");
        }

        if (LifecycleState.FAILED.equals(state)){
            stop();
        }
        else{
            setStateInternal(LifecycleState.STARTED, null);
        }

    }

    protected void startInternal() throws LifecycleException{

    }

    @Override
    public void stop() throws LifecycleException {


        if (LifecycleState.STOPPED.equals(state)){
            return;
        }

        if (LifecycleState.NEW.equals(state)){
            return;
        }

        try {
            stopInternal();
        } catch (Throwable t) {
            // ExceptionUtils.handleThrowable(t);
            setStateInternal(LifecycleState.FAILED, null);
            throw new LifecycleException("lifecycleBase.stopFail");
        }

        if (!LifecycleState.FAILED.equals(state)){
            setStateInternal(LifecycleState.STOPPED, null);
        }
    }

    protected void stopInternal() throws LifecycleException{

    }

    @Override
    public void destroy() throws LifecycleException {

        if (LifecycleState.FAILED.equals(state)){
            try {
                stop();
            }catch (Throwable t){
                // log
            }
        }

        try {
            destroyInternal();
        } catch (Throwable t) {
            //ExceptionUtils.handleThrowable(t);
            setStateInternal(LifecycleState.FAILED, null);
            throw new LifecycleException("lifecycleBase.destroyFail");
        }

        setStateInternal(LifecycleState.DESTROYED, null);
    }

    protected void destroyInternal() throws LifecycleException{

    }

    @Override
    public LifecycleState getState() {
        return state;
    }

    @Override
    public String getStateName() {
        return state.name();
    }

    public void setStateInternal(LifecycleState state, Object data){
        this.state = state;
        String lifecycleEvent = state.getLifecycleEvent();
        if (lifecycleEvent != null) {
            fireLifecycleEvent(lifecycleEvent, data);
        }
    }

    public void setState(LifecycleState state, Object data){
        setStateInternal(state,data);
    }
}
