package sylvanas.connector;

import sylvanas.connector.session.SessionHandler;
import sylvanas.connector.session.StandardSession;
import sylvanas.container.Context;
import sylvanas.util.Constants;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 *
 */
public class Request implements HttpServletRequest {

    private Context context = null;

    private ServletContext servletContext;

    private RawRequest rawRequest = null;

    private Response response = null;

    private StandardSession session = null;

    private String uri;

    private final HashMap<String, Object> attributes = new HashMap<>();

    public Request(RawRequest rawRequest){

        this.rawRequest = rawRequest;
    }

    public void parse(){
        rawRequest.parse();
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }


    @Override
    public long getDateHeader(String name) {
        return 0;
    }

    @Override
    public String getHeader(String name) {
        return rawRequest.getMimeHeaders().getHeader(name);
    }


    @Override
    public Enumeration<String> getHeaders(String name) {
        return rawRequest.getMimeHeaders().getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {

        return rawRequest.getMimeHeaders().getHeaderNames();
    }


    @Override
    public int getIntHeader(String name) {
        return 0;
    }


    @Override
    public String getMethod() {
        return rawRequest.getMethod();
    }


    @Override
    public String getPathInfo() {
        return null;
    }


    @Override
    public String getPathTranslated() {
        return null;
    }


    @Override
    public String getContextPath() {
        return null;
    }


    @Override
    public String getQueryString() {
        return null;
    }


    @Override
    public String getRemoteUser() {
        return null;
    }


    @Override
    public boolean isUserInRole(String role) {
        return false;
    }


    @Override
    public Principal getUserPrincipal() {
        return null;
    }


    @Override
    public String getRequestedSessionId() {
        return null;
    }


    @Override
    public String getRequestURI() {

        if (uri==null){
            return rawRequest.getUri();
        }
        return uri;
    }

    public void setRequestURI(String uri) {
         this.uri = uri;
    }


    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    /**
     * Returns the part of this request's URL that calls the servlet. This path
     * starts with a "/" character and includes either the servlet name or a
     * path to the servlet, but does not include any extra path information or a
     * query string. Same as the value of the CGI variable SCRIPT_NAME.
     * <p>
     * This method will return an empty string ("") if the servlet used to
     * process this request was matched using the "/*" pattern.
     *
     * @return a <code>String</code> containing the name or path of the servlet
     * being called, as specified in the request URL, decoded, or an
     * empty string if the servlet used to process the request is
     * matched using the "/*" pattern.
     */
    @Override
    public String getServletPath() {
        return null;
    }

    /**
     * Returns the current <code>HttpSession</code> associated with this request
     * or, if there is no current session and <code>create</code> is true,
     * returns a new session.
     * <p>
     * If <code>create</code> is <code>false</code> and the request has no valid
     * <code>HttpSession</code>, this method returns <code>null</code>.
     * <p>
     * To make sure the session is properly maintained, you must call this
     * method before the response is committed. If the container is using
     * cookies to maintain session integrity and is asked to create a new
     * session when the response is committed, an IllegalStateException is
     * thrown.
     *
     * @param create <code>true</code> to create a new session for this request if
     *               necessary; <code>false</code> to return <code>null</code> if
     *               there's no current session
     * @return the <code>HttpSession</code> associated with this request or
     * <code>null</code> if <code>create</code> is <code>false</code>
     * and the request has no valid session
     * @see #getSession()
     */
    @Override
    public HttpSession getSession(boolean create) {

        StandardSession session =  doGetSession(create);
        if (session==null){
            return null;
        }

        return session.getSessionFacade();
    }


    /**
     * 1 判断当前Request对象是否已经存在有效的Session信息，如果存在则返回此Session，否则进入下一步；
     * 2 获取SessionManager；
     * 3 从StandardManager的Session缓存中获取Session，如果有则返回此Session，否则进入下一步；
     * 4 创建Session；
     * 5 创建保存Session ID的Cookie；
     * 6 通过调用Session的access方法更新Session的访问时间以及访问次数。
     *
     * @param create
     * @return
     */
    private StandardSession doGetSession(boolean create){

        if ((session != null) && !session.isValid()){
            session = null;
        }

        if (session != null){
            return session;
        }

        if (context==null){
            return null;
        }

        SessionHandler sessionHandler = context.getSessionHandler();

        if (sessionHandler ==null){
            return null;
        }

        StandardSession session = sessionHandler.getSession(rawRequest.getSessionID());
        if ((session != null) && !session.isValid())
            session = null;

        if (session != null) {
            session.access();
            return session;
        }

        if (!create){
            return null;
        }

        session = sessionHandler.createSession();
        sessionHandler.addSession(session);

        if (session!=null){
            this.getResponse().addCookie(new Cookie(Constants.SESSION_ID_NAME,session.getId()));
            session.access();
        }
        return session;
    }

    /**
     * Returns the current session associated with this request, or if the
     * request does not have a session, creates one.
     *
     * @return the <code>HttpSession</code> associated with this request
     * @see #getSession(boolean)
     */
    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    /**
     * Checks whether the requested session ID is still valid.
     *
     * @return <code>true</code> if this request has an id for a valid session
     * in the current session context; <code>false</code> otherwise
     * @see #getRequestedSessionId
     * @see #getSession
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    /**
     * Checks whether the requested session ID came in as a cookie.
     *
     * @return <code>true</code> if the session ID came in as a cookie;
     * otherwise, <code>false</code>
     * @see #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * Checks whether the requested session ID came in as part of the request
     * URL.
     *
     * @return <code>true</code> if the session ID came in as part of a URL;
     * otherwise, <code>false</code>
     * @see #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    /**
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     * {@link #isRequestedSessionIdFromURL} instead.
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * Triggers the same authentication process as would be triggered if the
     * request is for a resource that is protected by a security constraint.
     *
     * @param response The response to use to return any authentication
     *                 challenge
     * @return <code>true</code> if the user is successfully authenticated and
     * <code>false</code> if not
     * @since Servlet 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response) {
        return false;
    }

    /**
     * Authenticate the provided user name and password and then associated the
     * authenticated user with the request.
     *
     * @param username The user name to authenticate
     * @param password The password to use to authenticate the user
     * @throws ServletException If any of {@link #getRemoteUser()},
     *                          {@link #getUserPrincipal()} or {@link #getAuthType()} are
     *                          non-null, if the configured authenticator does not support
     *                          user name and password authentication or if the
     *                          authentication fails
     * @since Servlet 3.0
     */
    @Override
    public void login(String username, String password) {

    }

    /**
     * Removes any authenticated user from the request.
     *
     * @throws ServletException If the logout fails
     * @since Servlet 3.0
     */
    @Override
    public void logout() {

    }

    /**
     * Return a collection of all uploaded Parts.
     *
     * @return A collection of all uploaded Parts.
     * @throws IOException           if an I/O error occurs
     * @throws IllegalStateException if size limits are exceeded
     * @throws ServletException      if the request is not multipart/form-data
     * @since Servlet 3.0
     */
    @Override
    public Collection<Part> getParts() throws IllegalStateException {
        return null;
    }

    /**
     * Gets the named Part or null if the Part does not exist. Triggers upload
     * of all Parts.
     *
     * @param name
     * @return The named Part or null if the Part does not exist
     * @throws IOException           if an I/O error occurs
     * @throws IllegalStateException if size limits are exceeded
     * @throws ServletException      if the request is not multipart/form-data
     * @since Servlet 3.0
     */
    @Override
    public Part getPart(String name) throws IllegalStateException {
        return null;
    }

    /**
     * Returns the value of the named attribute as an <code>Object</code>, or
     * <code>null</code> if no attribute of the given name exists.
     * <p>
     * Attributes can be set two ways. The servlet container may set attributes
     * to make available custom information about a request. For example, for
     * requests made using HTTPS, the attribute
     * <code>javax.servlet.request.X509Certificate</code> can be used to
     * retrieve information on the certificate of the client. Attributes can
     * also be set programatically using {@link ServletRequest#setAttribute}.
     * This allows information to be embedded into a request before a
     * {@link RequestDispatcher} call.
     * <p>
     * Attribute names should follow the same conventions as package names. This
     * specification reserves names matching <code>java.*</code>,
     * <code>javax.*</code>, and <code>sun.*</code>.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     * @return an <code>Object</code> containing the value of the attribute, or
     * <code>null</code> if the attribute does not exist
     */
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Returns an <code>Enumeration</code> containing the names of the
     * attributes available to this request. This method returns an empty
     * <code>Enumeration</code> if the request has no attributes available to
     * it.
     *
     * @return an <code>Enumeration</code> of strings containing the names of the
     * request's attributes
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    /**
     * Returns the name of the character encoding used in the body of this
     * request. This method returns <code>null</code> if the request does not
     * specify a character encoding
     *
     * @return a <code>String</code> containing the name of the character
     * encoding, or <code>null</code> if the request does not specify a
     * character encoding
     */
    @Override
    public String getCharacterEncoding() {
        return null;
    }

    /**
     * Overrides the name of the character encoding used in the body of this
     * request. This method must be called prior to reading request parameters
     * or reading input using getReader().
     *
     * @param env a <code>String</code> containing the name of the character
     *            encoding.
     * @throws UnsupportedEncodingException if this is not a valid encoding
     */
    @Override
    public void setCharacterEncoding(String env) {

    }

    /**
     * Returns the length, in bytes, of the request body and made available by
     * the input stream, or -1 if the length is not known. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_LENGTH.
     *
     * @return an integer containing the length of the request body or -1 if the
     * length is not known
     */
    @Override
    public int getContentLength() {
        return 0;
    }

    /**
     * Returns the MIME type of the body of the request, or <code>null</code> if
     * the type is not known. For HTTP servlets, same as the value of the CGI
     * variable CONTENT_TYPE.
     *
     * @return a <code>String</code> containing the name of the MIME type of the
     * request, or null if the type is not known
     */
    @Override
    public String getContentType() {
        return null;
    }

    /**
     * Retrieves the body of the request as binary data using a
     * {@link ServletInputStream}. Either this method or {@link #getReader} may
     * be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the
     * request
     * @throws IllegalStateException if the {@link #getReader} method has already been called
     *                               for this request
     * @throws IOException           if an input or output exception occurred
     */
    @Override
    public ServletInputStream getInputStream() {
        return null;
    }

    /**
     * Returns the value of a request parameter as a <code>String</code>, or
     * <code>null</code> if the parameter does not exist. Request parameters are
     * extra information sent with the request. For HTTP servlets, parameters
     * are contained in the query string or posted form data.
     * <p>
     * You should only use this method when you are sure the parameter has only
     * one value. If the parameter might have more than one value, use
     * {@link #getParameterValues}.
     * <p>
     * If you use this method with a multivalued parameter, the value returned
     * is equal to the first value in the array returned by
     * <code>getParameterValues</code>.
     * <p>
     * If the parameter data was sent in the request body, such as occurs with
     * an HTTP POST request, then reading the body directly via
     * {@link #getInputStream} or {@link #getReader} can interfere with the
     * execution of this method.
     *
     * @param name a <code>String</code> specifying the name of the parameter
     * @return a <code>String</code> representing the single value of the
     * parameter
     * @see #getParameterValues
     */
    @Override
    public String getParameter(String name) {
        return rawRequest.getParameters().getParameter(name);
    }

    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of the parameters contained in this request. If the
     * request has no parameters, the method returns an empty
     * <code>Enumeration</code>.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects, each
     * <code>String</code> containing the name of a request parameter;
     * or an empty <code>Enumeration</code> if the request has no
     * parameters
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return rawRequest.getParameters().getParameterNames();
    }

    /**
     * Returns an array of <code>String</code> objects containing all of the
     * values the given request parameter has, or <code>null</code> if the
     * parameter does not exist.
     * <p>
     * If the parameter has a single value, the array has a length of 1.
     *
     * @param name a <code>String</code> containing the name of the parameter
     *             whose value is requested
     * @return an array of <code>String</code> objects containing the parameter's
     * values
     * @see #getParameter
     */
    @Override
    public String[] getParameterValues(String name) {

        return rawRequest.getParameters().getParameterValues(name);
    }

    /**
     * Returns a java.util.Map of the parameters of this request. Request
     * parameters are extra information sent with the request. For HTTP
     * servlets, parameters are contained in the query string or posted form
     * data.
     *
     * @return an immutable java.util.Map containing parameter names as keys and
     * parameter values as map values. The keys in the parameter map are
     * of type String. The values in the parameter map are of type
     * String array.
     */
    @Override
    public Map<String, String[]> getParameterMap()
    {
        return rawRequest.getParameters().getParameterMap();
    }

    /**
     * Returns the name and version of the protocol the request uses in the form
     * <i>protocol/majorVersion.minorVersion</i>, for example, HTTP/1.1. For
     * HTTP servlets, the value returned is the same as the value of the CGI
     * variable <code>SERVER_PROTOCOL</code>.
     *
     * @return a <code>String</code> containing the protocol name and version
     * number
     */
    @Override
    public String getProtocol() {

        return rawRequest.getProtocol();
    }

    /**
     * Returns the name of the scheme used to make this request, for example,
     * <code>connector</code>, <code>https</code>, or <code>ftp</code>. Different
     * schemes have different rules for constructing URLs, as noted in RFC 1738.
     *
     * @return a <code>String</code> containing the name of the scheme used to
     * make this request
     */
    @Override
    public String getScheme() {
        return null;
    }

    /**
     * Returns the host name of the server to which the request was sent. It is
     * the value of the part before ":" in the <code>Host</code> header value,
     * if any, or the resolved server name, or the server IP address.
     *
     * @return a <code>String</code> containing the name of the server
     */
    @Override
    public String getServerName() {
        return null;
    }

    /**
     * Returns the port number to which the request was sent. It is the value of
     * the part after ":" in the <code>Host</code> header value, if any, or the
     * server port where the client connection was accepted on.
     *
     * @return an integer specifying the port number
     */
    @Override
    public int getServerPort() {
        return 0;
    }

    /**
     * Retrieves the body of the request as character data using a
     * <code>BufferedReader</code>. The reader translates the character data
     * according to the character encoding used on the body. Either this method
     * or {@link #getInputStream} may be called to read the body, not both.
     *
     * @return a <code>BufferedReader</code> containing the body of the request
     * @throws UnsupportedEncodingException if the character set encoding used is not supported and
     *                                      the text cannot be decoded
     * @throws IllegalStateException        if {@link #getInputStream} method has been called on this
     *                                      request
     * @throws IOException                  if an input or output exception occurred
     * @see #getInputStream
     */
    @Override
    public BufferedReader getReader() {
        return null;
    }

    /**
     * Returns the Internet Protocol (IP) address of the client or last proxy
     * that sent the request. For HTTP servlets, same as the value of the CGI
     * variable <code>REMOTE_ADDR</code>.
     *
     * @return a <code>String</code> containing the IP address of the client
     * that sent the request
     */
    @Override
    public String getRemoteAddr() {
        return null;
    }

    /**
     * Returns the fully qualified name of the client or the last proxy that
     * sent the request. If the engine cannot or chooses not to resolve the
     * hostname (to improve performance), this method returns the dotted-string
     * form of the IP address. For HTTP servlets, same as the value of the CGI
     * variable <code>REMOTE_HOST</code>.
     *
     * @return a <code>String</code> containing the fully qualified name of the
     * client
     */
    @Override
    public String getRemoteHost() {
        return null;
    }

    /**
     * Stores an attribute in this request. Attributes are reset between
     * requests. This method is most often used in conjunction with
     * {@link RequestDispatcher}.
     * <p>
     * Attribute names should follow the same conventions as package names.
     * Names beginning with <code>java.*</code>, <code>javax.*</code>, and
     * <code>com.sun.*</code>, are reserved for use by Sun Microsystems. <br>
     * If the object passed in is null, the effect is the same as calling
     * {@link #removeAttribute}. <br>
     * It is warned that when the request is dispatched from the servlet resides
     * in a different web application by <code>RequestDispatcher</code>, the
     * object set by this method may not be correctly retrieved in the caller
     * servlet.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     * @param object
     */
    @Override
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

    /**
     * Removes an attribute from this request. This method is not generally
     * needed as attributes only persist as long as the request is being
     * handled.
     * <p>
     * Attribute names should follow the same conventions as package names.
     * Names beginning with <code>java.*</code>, <code>javax.*</code>, and
     * <code>com.sun.*</code>, are reserved for use by Sun Microsystems.
     *
     * @param name a <code>String</code> specifying the name of the attribute to
     *             remove
     */
    @Override
    public void removeAttribute(String name) {
        Object object = attributes.get(name);
        if (object != null){
            attributes.remove(object);
        }
    }

    /**
     * Returns the preferred <code>Locale</code> that the client will accept
     * content in, based on the Accept-Language header. If the client request
     * doesn't provide an Accept-Language header, this method returns the
     * default locale for the server.
     *
     * @return the preferred <code>Locale</code> for the client
     */
    @Override
    public Locale getLocale() {
        return null;
    }

    /**
     * Returns an <code>Enumeration</code> of <code>Locale</code> objects
     * indicating, in decreasing order starting with the preferred locale, the
     * locales that are acceptable to the client based on the Accept-Language
     * header. If the client request doesn't provide an Accept-Language header,
     * this method returns an <code>Enumeration</code> containing one
     * <code>Locale</code>, the default locale for the server.
     *
     * @return an <code>Enumeration</code> of preferred <code>Locale</code>
     * objects for the client
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    /**
     * Returns a boolean indicating whether this request was made using a secure
     * channel, such as HTTPS.
     *
     * @return a boolean indicating if the request was made using a secure
     * channel
     */
    @Override
    public boolean isSecure() {
        return false;
    }

    /**
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the
     * resource located at the given path. A <code>RequestDispatcher</code>
     * object can be used to forward a request to the resource or to include the
     * resource in a response. The resource can be dynamic or static.
     * <p>
     * The pathname specified may be relative, although it cannot extend outside
     * the current servlet context. If the path begins with a "/" it is
     * interpreted as relative to the current context root. This method returns
     * <code>null</code> if the servlet container cannot return a
     * <code>RequestDispatcher</code>.
     * <p>
     * The difference between this method and
     * {@link ServletContext#getRequestDispatcher} is that this method can take
     * a relative path.
     *
     * @param path a <code>String</code> specifying the pathname to the resource.
     *             If it is relative, it must be relative against the current
     *             servlet.
     * @return a <code>RequestDispatcher</code> object that acts as a wrapper for
     * the resource at the specified path, or <code>null</code> if the
     * servlet container cannot return a <code>RequestDispatcher</code>
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return getServletContext().getRequestDispatcher(path);
    }

    /**
     * @param path The virtual path to be converted to a real path
     * @return {@link ServletContext#getRealPath(String)}
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     * {@link ServletContext#getRealPath} instead.
     */
    @Override
    public String getRealPath(String path) {
        return null;
    }

    /**
     * Returns the Internet Protocol (IP) source port of the client or last
     * proxy that sent the request.
     *
     * @return an integer specifying the port number
     * @since 2.4
     */
    @Override
    public int getRemotePort() {
        return 0;
    }

    /**
     * Returns the host name of the Internet Protocol (IP) interface on which
     * the request was received.
     *
     * @return a <code>String</code> containing the host name of the IP on which
     * the request was received.
     * @since 2.4
     */
    @Override
    public String getLocalName() {
        return null;
    }

    /**
     * Returns the Internet Protocol (IP) address of the interface on which the
     * request was received.
     *
     * @return a <code>String</code> containing the IP address on which the
     * request was received.
     * @since 2.4
     */
    @Override
    public String getLocalAddr() {
        return null;
    }

    /**
     * Returns the Internet Protocol (IP) port number of the interface on which
     * the request was received.
     *
     * @return an integer specifying the port number
     * @since 2.4
     */
    @Override
    public int getLocalPort() {
        return 0;
    }

    /**
     * @return TODO
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @return TODO
     * @throws IllegalStateException If async is not supported for this request
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public AsyncContext startAsync() {
        return null;
    }

    /**
     * @param servletRequest  The ServletRequest with which to initialise the
     *                        asynchronous context
     * @param servletResponse The ServletResponse with which to initialise the
     *                        asynchronous context
     * @return TODO
     * @throws IllegalStateException If async is not supported for this request
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
        return null;
    }

    /**
     * @return TODO
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    /**
     * @return TODO
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    /**
     * @return TODO
     * @throws IllegalStateException if the request is not in asynchronous mode
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    /**
     * @return TODO
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @Override
    public DispatcherType getDispatcherType() {
        return null;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public RawRequest getRawRequest() {
        return rawRequest;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}