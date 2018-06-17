package sylvanas.component.context;

import sylvanas.component.exception.ExceptionUtils;

/**
 *
 *
 *
 */
public class ExceptionHandler {

    public static void handleServerException(Throwable e,String message){
        //log
        String info = message+"\n"+ExceptionUtils.Stack2String(e);
        System.out.println(info);
    }
}
