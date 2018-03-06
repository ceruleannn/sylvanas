package sylvanas.http.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.*;

/**
 * @Description:
 */
public class StandardSession implements HttpSession{

    //TODO: SESSION EVENT LISTENER

    private final HashMap<String, Object> attributes = new HashMap<>();

    private String id = null;

    private long lastAccessedTime = 0;

    private long creationTime = 0;

    private ServletContext context = null;

    private int maxInactiveInterval = 0;

    private boolean isValid = true;

    private boolean isExpired = false;

    private boolean isNew = false;

    SessionManager sessionManager = null;

    public StandardSession(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }


    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    @Override
    public ServletContext getServletContext() {
        return context;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    @Deprecated
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {

        return attributes.get(name);
    }

    @Override
    @Deprecated
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    @Deprecated
    public String[] getValueNames() {
        Enumeration<String> enumeration = getAttributeNames();
        List<String> list = new ArrayList<>();

        while (enumeration.hasMoreElements()){
            list.add(enumeration.nextElement());
        }

        return list.toArray(new String[list.size()]);
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (!isValid){
            throw new IllegalStateException("session has been invalidated");
        }

        if (name==null){
            return;
        }
        if (value==null){
            removeAttribute(name);
            return;
        }
        attributes.put(name, value);
    }

    @Override
    @Deprecated
    public void putValue(String name, Object value) {
        setAttribute(name,value);
    }

    @Override
    public void removeAttribute(String name) {
        if (!isValid){
            throw new IllegalStateException("session has been invalidated");
        }

        attributes.remove(name);
    }

    @Override
    @Deprecated
    public void removeValue(String name) {
        removeAttribute(name);
    }


    /**
     * Invalidates this session and unbinds any objects bound to it.
     *
     * @exception IllegalStateException if this method is called on
     *  an invalidated session
     */
    @Override
    public void invalidate() {
        if (!isValid){
            throw new IllegalStateException("session has been invalidated");
        }

        Set keys = attributes.keySet();
        for (Object key : keys) {
            attributes.remove(key);
        }

        isValid = false;
    }

    /**
     * Return <code>true</code> if the client does not yet know about the
     * session, or if the client chooses not to join the session.  For
     * example, if the server used only cookie-based sessions, and the client
     * has disabled the use of cookies, then a session would be new on each
     * request.
     *
     * 浏览器请求返回的session = false
     * 服务器新创建 = true
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public boolean isNew() {
        if (!isValid){
            throw new IllegalStateException("session has been invalidated");
        }
        return this.isNew;
    }

    public boolean checkExpire(){

        if (maxInactiveInterval > 0) {
            long now = System.currentTimeMillis();
            int timeIdle;

            timeIdle = (int) ((now - lastAccessedTime) / 1000L);

            if (timeIdle >= maxInactiveInterval) {
                expire();
            }
        }
        return isExpired;
    }

    public void expire(){

        this.setExpired(true);
        this.setValid(false);
        //TODO LISTENER
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
