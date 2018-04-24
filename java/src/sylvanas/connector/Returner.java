package sylvanas.connector;

import sylvanas.component.http.OutputBuffer;

import java.io.IOException;

/**
 *
 *
 *
 */
public class Returner {

    private String message;

    Returner(String message){
        this.message = message;
    }

    public void doReturn(Request request, Response response){

        RawResponse rawResponse = response.getRawResponse();
        OutputBuffer ob = rawResponse.getOutputBuffer();

        if (ob.isCommited()){
            return;
        }

        try {
            ob.doWrite();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ob.close();
        }
    }
}
