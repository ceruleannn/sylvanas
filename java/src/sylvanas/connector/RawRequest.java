package sylvanas.connector;

import sylvanas.component.http.Cookies;
import sylvanas.component.http.MimeHeaders;
import sylvanas.component.http.Parameters;

/**
 * @Description:
 * a RawRequest for process String
 * 处理请求源 字符串的解析处理
 */
public class RawRequest {

    /**
     *  Whole Request String / 全部请求字符串
     */
    private String raw = null;


    /**
     *  Request Line / 请求行
     */
    private String rawRequestLine = null;


    /**
     *  Request Headers / 请求头
     */
    private String rawHeaders = null;


    /**
     *  Request Body / 请求体
     */
    private String rawBody = null;


    /**
     *  Request Method / 请求方法
     */
    private String method = null;


    /**
     *  Request Path / 请求路径
     */
    private String query = null;


    /**
     *  Request Protocol / 请求协议
     */
    private String protocol = null;


    /**
     *  Request Parameters / 请求参数
     */
    private Parameters parameters = null;


    /**
     *  Request MimeHeaders / 请求头
     */
    private MimeHeaders mimeHeaders = null;


    /**
     *  Request Cookies / 缓存饼干 -_-||
     */
    private Cookies cookies = null;


    /**
     *  Request SessionID / 会话ID
     */
    private String sessionID = null;


    public RawRequest(String raw){
        this.raw = raw;

    }

    public void parse(){

        if (raw == null){
            throw new IllegalArgumentException("raw request can not be null");
        }

        parseDepart();
        parseRequestLine();
        parseHeaders();
        parseParameters();
        parseCookies();
    }

    private void parseDepart(){
        rawRequestLine = raw.substring(0, raw.indexOf("\r\n"));

        String temp = raw.substring(rawRequestLine.length()+2);
        int index = temp.indexOf("\r\n\r\n");
        rawHeaders = temp.substring(0,index);
        rawBody = temp.substring(index+4);

    }

    private void parseRequestLine(){

        String[] result = rawRequestLine.split(" ");

        if (result.length!=3){
            //TODO
        }

        method = result[0];
        query = result[1];
        protocol = result[2];

    }

    private void parseHeaders(){

        mimeHeaders = new MimeHeaders(rawHeaders);
    }

    private void parseParameters(){

        // 根据 Content-Type 判断请求体是否是请求参数
        boolean isParseBody = "application/x-www-form-urlencoded"
                .equalsIgnoreCase(mimeHeaders.getHeader("Content-Type"));
        parameters = new Parameters(isParseBody,query,rawBody);
        parameters.parse();
    }

    private void parseCookies(){

        cookies = new Cookies(mimeHeaders, this);
    }

    public String getRaw() {
        return raw;
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

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
