package sylvanas.connector;

import sylvanas.util.http.MimeHeaders;
import sylvanas.util.http.Parameters;

/**
 * @Description:
 */
public class RawRequest {

    private String rawRequest = null;

    private String rawRequestLine = null;

    private String rawHeaders = null;

    private String rawBody = null;

    private String method = null;

    private String query = null;

    private String protocol = null;

    private Parameters parameters = null;

    private MimeHeaders mimeHeaders = null;


    public RawRequest(String rawRequest){
        this.rawRequest = rawRequest;
        parseDepart();
        parseRequestLine();
        parseHeaders();
        parseParameters();
    }

    public void parseDepart(){
        rawRequestLine = rawRequest.substring(0, rawRequest.indexOf("\r\n"));

        String temp = rawRequest.substring(rawRequestLine.length()+2);
        int index = temp.indexOf("\r\n\r\n");
        rawHeaders = temp.substring(0,index);
        rawBody = temp.substring(index+4);

    }

    public void parseRequestLine(){

        String[] result = rawRequestLine.split(" ");

        if (result.length!=3){
            //TODO
        }

        method = result[0];
        query = result[1];
        protocol = result[2];

    }

    public void parseHeaders(){

        mimeHeaders = new MimeHeaders(rawHeaders);
    }

    public void parseParameters(){

        boolean isParseBody = "application/x-www-form-urlencoded"
                .equalsIgnoreCase(mimeHeaders.getHeader("Content-Type"));
        parameters = new Parameters(isParseBody,query,rawBody);
        parameters.parse();
    }
}
