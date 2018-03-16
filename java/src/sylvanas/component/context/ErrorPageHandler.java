package sylvanas.component.context;

import sylvanas.component.digester.ErrorPage;
import sylvanas.util.StringUtils;

import java.util.HashMap;

/**
 * take charge of the error page
 *
 * <error-page>
 * <error-code>404</error-code>
 * <location>/WEB-INF/404.html</location>
 * </error-page>
 *
 * <error-page>
 * <exception-type>java.lang.NullPointerException</exception-type>
 * <location>/WEB-INF/nullPointerException.html</location>
 * </error-page>
 *
 */
public class ErrorPageHandler {

    HashMap<Integer, String> codeMap = new HashMap<>(8);
    HashMap<String, String> exceptionMap = new HashMap<>(8);

    public ErrorPageHandler(){

    }

    public void addErrorPage(ErrorPage errorPage){

        String location = errorPage.getLocation();
        if (StringUtils.isEmpty(location)){
            return;
        }

        // ignore the duplicate scene ?
        if (errorPage.getErrorCode()!=0){
            codeMap.put(errorPage.getErrorCode(),location);
        }else if(errorPage.getExceptionType()!=null){
            exceptionMap.put(errorPage.getExceptionType(),location);
        }
    }
}
