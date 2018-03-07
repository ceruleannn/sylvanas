package sylvanas.http.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @Description:
 * StandardSession Facade
 */


public class StandardSessionFacade implements HttpSession {


    public StandardSessionFacade(StandardSession session) {
        super();
        this.session = session;
    }

    public StandardSessionFacade(HttpSession session) {
        super();
        this.session = session;
    }

    private HttpSession session = null;


    @Override
    public long getCreationTime() {
        return session.getCreationTime();
    }


    @Override
    public String getId() {
        return session.getId();
    }


    @Override
    public long getLastAccessedTime() {
        return session.getLastAccessedTime();
    }


    @Override
    public ServletContext getServletContext() {
        return session.getServletContext();
    }


    @Override
    public void setMaxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }


    @Override
    public int getMaxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }


    @Override
    @Deprecated
    public javax.servlet.http.HttpSessionContext getSessionContext() {
        return session.getSessionContext();
    }


    @Override
    public Object getAttribute(String name) {
        return session.getAttribute(name);
    }


    @Override
    @Deprecated
    public Object getValue(String name) {
        return session.getAttribute(name);
    }


    @Override
    public Enumeration<String> getAttributeNames() {
        return session.getAttributeNames();
    }

    @Override
    @Deprecated
    public String[] getValueNames() {
        return session.getValueNames();
    }


    @Override
    public void setAttribute(String name, Object value) {
        session.setAttribute(name, value);
    }


    @Override
    @Deprecated
    public void putValue(String name, Object value) {
        session.setAttribute(name, value);
    }


    @Override
    public void removeAttribute(String name) {
        session.removeAttribute(name);
    }


    @Override
    @Deprecated
    public void removeValue(String name) {
        session.removeAttribute(name);
    }


    @Override
    public void invalidate() {
        session.invalidate();
    }


    @Override
    public boolean isNew() {
        return session.isNew();
    }

}

