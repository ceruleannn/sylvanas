package sylvanas.component.lifecycle;

/**
 *
 *
 *
 */
public class LifecycleException extends Exception{

    private static final long serialVersionUID = 1L;

    //------------------------------------------------------------ Constructors

    public LifecycleException() {
        super();
    }


    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(Throwable throwable) {
        super(throwable);
    }

    public LifecycleException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
