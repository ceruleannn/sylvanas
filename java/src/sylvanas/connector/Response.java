package sylvanas.connector;

import sylvanas.component.exception.Assert;
import sylvanas.component.http.Cookies;
import sylvanas.component.http.MimeHeaders;
import sylvanas.component.http.OutputBuffer;
import sylvanas.component.http.ServletOutputStreamImpl;
import sylvanas.util.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 */
public class Response implements HttpServletResponse{

    private Locale locale = Locale.getDefault();

    private RawResponse rawResponse = null;

    private Request request = null;

    private MimeHeaders headers = new MimeHeaders();

    private int status = 200;

    private String message = "";

    private OutputBuffer outputBuffer;

    private boolean commited = false;

    private String contentType = null;

    private String contentLanguage = null;

    private String characterEncoding = Constants.DEFAULT_ENCODING;

    private long contentLength = -1;

    private ServletOutputStream outputStream;

    private SimpleDateFormat format = new SimpleDateFormat("\"EEE, dd MMM yyyy HH:mm:ss zzz\";",getLocale());


    private PrintWriter printWriter;

    public Response(RawResponse rawResponse){
       this.rawResponse = rawResponse;

       outputBuffer = new OutputBuffer(this,rawResponse.getOutputStream());
    }


    @Override
    public void addCookie(Cookie cookie) {
        addHeader("Set-Cookie", Cookies.toHeaderString(cookie));
    }

    @Override
    public boolean containsHeader(String name) {
        return headers.getHeader(name) != null;
    }

    @Override
    public String encodeURL(String url) {

        // TODO APPEND SESSION ID
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {

        return encodeURL(url);
    }

    @Override
    public String encodeRedirectUrl(String url) {

        return encodeRedirectURL(url);
    }

    @Override
    public void sendError(int sc, String msg) {
       status = sc;
       message = msg;
    }

    @Override
    public void sendError(int sc) {
        sendError(status, "");
    }

    @Override
    public void sendRedirect(String location) {
        outputBuffer.reset();

        try {
            String absolute = toAbsolute(location);
            setStatus(SC_FOUND);
            setHeader("Location", absolute);

//            if (getContext().getSendRedirectBody()) {
//                PrintWriter writer = getWriter();
//                writer.print(sm.getString("coyoteResponse.sendRedirect.note",
//                        RequestUtil.filter(absolute)));
//                flushBuffer();
//            }
        } catch (IllegalArgumentException e) {
            setStatus(SC_NOT_FOUND);
        }

    }

    @Override
    public void setDateHeader(String name, long date) {
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        setHeader(name, format.format(date));
    }

    @Override
    public void addDateHeader(String name, long date) {
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        addHeader(name, format.format(date));
    }

    @Override
    public void setHeader(String name, String value) {
        Assert.notNull(name,value);
        headers.setHeader(name,value);
    }

    @Override
    public void addHeader(String name, String value) {
        Assert.notNull(name,value);
        headers.addHeader(name,value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        setHeader(name, "" + value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        addHeader(name, "" + value);
    }

    @Override
    public void setStatus(int sc) {
        status = sc;
    }

    @Override
    public void setStatus(int sc, String sm) {
        status = sc;
        message = sm;
    }

    @Override
    public int getStatus() {
       return this.status;
    }

    @Override
    public String getHeader(String name) {

        return headers.getHeader(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {

        return Collections.list(headers.getHeaders(name));
    }

    @Override
    public Collection<String> getHeaderNames() {

        return Collections.list(headers.getHeaderNames());
    }

    @Override
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (outputStream == null) {
            outputStream = new ServletOutputStreamImpl(outputBuffer);
        }
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {

        if (printWriter==null){
            printWriter = new PrintWriter(outputBuffer);
        }

        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String charset) {

        this.characterEncoding = charset;
        outputBuffer.setEncode(charset);
    }

    @Override
    public void setContentLength(int len) {
        headers.setHeader("Content-Length",String.valueOf(len));
        this.contentLength = len;
    }

    @Override
    public void setContentType(String type) {
        headers.setHeader("Content-Type",type);
        this.contentType = type;
    }

    @Override
    public void setBufferSize(int size) {
        outputBuffer.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return outputBuffer.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException{
        outputBuffer.flush();
    }

    @Override
    public void resetBuffer() {
        outputBuffer.reset();
    }

    @Override
    public boolean isCommitted() {
        return commited;
    }

    @Override
    public void reset() {
        outputBuffer.reset();
    }

    @Override
    public void setLocale(Locale loc) {
        this.locale = loc;
    }

    @Override
    public Locale getLocale() {

       return locale;
    }

    private String toAbsolute(String path){

        //TODO RETURN ABSOLUTE PATH FROM RELATIVE PATH
        return null;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public OutputBuffer getOutputBuffer() {
        return outputBuffer;
    }

    public void setOutputBuffer(OutputBuffer outputBuffer) {
        this.outputBuffer = outputBuffer;
    }

    public boolean isCommited() {
        return commited;
    }

    public void setCommited(boolean commited) {
        this.commited = commited;
    }

    public MimeHeaders getMimeHeaders() {
        return headers;
    }

    public long getContentLength() {
        return contentLength;
    }

    public RawResponse getRawResponse() {
        return rawResponse;
    }

}
