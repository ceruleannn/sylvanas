package sylvanas.component.http;

import java.io.PrintWriter;

/**
 *
 *
 *
 */
public class ResponseWriter extends PrintWriter{


    public ResponseWriter(OutputBuffer ob){
        super(ob);
    }
}
