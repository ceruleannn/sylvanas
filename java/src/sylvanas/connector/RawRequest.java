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

    private String method = null;

    private String query = null;

    private String protocol = null;

    private Parameters parameters = null;

    private MimeHeaders mimeHeaders = null;


    public RawRequest(String rawData){
        this.rawRequest = rawData;
        parseRequestLine();
        parseHeaders();
    }

    public void parseRequestLine(){

        rawRequestLine = rawRequest.substring(0, rawRequest.indexOf("\r\n"));
        String[] result = rawRequestLine.split(" ");

        if (result.length!=3){
            //TODO
        }

        method = result[0];
        query = result[1];
        protocol = result[2];

        parameters = new Parameters();
        parameters.setQuery(query);
        parameters.parse();

    }

    public void parseHeaders(){
        String temp = rawRequest.substring(rawRequestLine.length()+2);
        rawHeaders = temp.substring(0,temp.indexOf("\r\n\r\n"));
        mimeHeaders = new MimeHeaders(rawHeaders);

    }
}
