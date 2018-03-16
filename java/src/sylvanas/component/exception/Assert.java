package sylvanas.component.exception;

/**
 * check parameter basically
 */
public final class Assert {

    public static void notNull(Object object, String message){
        if (object==null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object){
        notNull(object,"parameter must not be null");
    }

    public static void notNull(Object... objects){
        for (Object object : objects) {
            notNull(object);
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
