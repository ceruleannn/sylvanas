package sylvanas.connector;

import sylvanas.util.http.Cookies;
import sylvanas.util.http.MimeHeaders;
import sylvanas.util.http.Parameters;

import java.util.HashMap;

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

    private Cookies cookies = null;

    private HashMap<String, Object> attributes = new HashMap<>();


    public RawRequest(String rawRequest){
        this.rawRequest = rawRequest;
        parseDepart();
        parseRequestLine();
        parseHeaders();
        parseParameters();
        parseCookies();
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

    public void parseCookies(){

        cookies = new Cookies(mimeHeaders);
    }

    public String getRawRequest() {
        return rawRequest;
    }

    public String getRawRequestLine() {
        return rawRequestLine;
    }

    public String getRawHeaders() {
        return rawHeaders;
    }

    public String getRawBody() {
        return rawBody;
    }

    public String getMethod() {
        return method;
    }

    public String getQuery() {
        return query;
    }

    public String getProtocol() {
        return protocol;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public MimeHeaders getMimeHeaders() {
        return mimeHeaders;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public Object getAttribute(String name) {

        Object attr = attributes.get(name);

        if(attr!=null) {
            return(attr);
        }
        return null;
    }

    public void setAttribute(String name, Object object) {

        if (name == null) {
            return;
        }

        if (object == null) {
            removeAttribute(name);
            return;
        }

        attributes.put(name, object);
    }

    public void removeAttribute(String name){

        Object object = attributes.get(name);
        if (object != null){
            attributes.remove(object);
        }
    }
}
