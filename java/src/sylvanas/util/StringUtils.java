package sylvanas.util;

/**
 * @Description:
 */
public class StringUtils {

    public static boolean isEmpty(String string){
        if (string==null||string.trim().length()==0){
            return true;
        }
        return false;
    }
}
