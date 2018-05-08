package sylvanas.component.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 *
 *
 */
public class ExceptionUtils {

    public static String Stack2String(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
