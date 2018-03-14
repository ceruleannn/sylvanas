package sylvanas.component.exception;

/**
 * @Description:
 */
public final class Assert {

    public static void notNull(Object object, String message){
        if (object==null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String string, String message){
        if (string==null||string.trim().length()==0){
            throw new IllegalArgumentException(message);
        }
    }




    /**
     *  this class can not be created or make it abstract
     */
    private Assert(){

    }
}
