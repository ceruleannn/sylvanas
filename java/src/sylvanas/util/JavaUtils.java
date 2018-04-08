package sylvanas.util;

import java.io.Closeable;
import java.io.IOException;


public abstract class JavaUtils {

    public static void closeQuietly(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            // NOOP
        }
    }
}
