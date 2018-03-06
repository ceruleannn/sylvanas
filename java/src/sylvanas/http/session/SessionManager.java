package sylvanas.http.session;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 */
public class SessionManager {

    private Map<String, StandardSession> sessions = new ConcurrentHashMap<String, StandardSession>();

    /**
     * session 默认最大无活动等待时间 (s)
     */
    private int maxInactiveInterval = 30 * 60;

    public SessionManager(){

    }

    public void init(){
        Thread thread = new Thread(new checkExpireThread(this));
        thread.setDaemon(true);
        thread.start();
    }

    private String createSessionID(){

        String id = null;
        do {
            id = UUID.randomUUID().toString().replaceAll("-", "");
        }while(sessions.containsKey(id));

        return id;
    }

    public StandardSession createSession(){
        StandardSession session = new StandardSession(this);

        session.setNew(true);
        session.setValid(true);
        session.setCreationTime(System.currentTimeMillis());
        session.setMaxInactiveInterval(this.maxInactiveInterval);

        String id = createSessionID();
        session.setId(id);

        return session;
    }

    public StandardSession getSession(String id){
        return null;
    }

    public Collection<StandardSession> getSessions(){
        return sessions.values();
    }

    public void removeSession(StandardSession standardSession){

        sessions.remove(standardSession);
    }

    public void updateLastAccess(String id){

    }


    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }
}


class checkExpireThread implements Runnable{

    private SessionManager sessionManager = null;

    //TODO 中断 唤醒
    private boolean isStop = false;
    public checkExpireThread(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    @Override
    public void run() {
        while (!isStop){
            try {

                //30秒执行一次 session超时检查
                Thread.sleep(1000 * 30);

                Iterator<StandardSession> iterator = sessionManager.getSessions().iterator();
                while (iterator.hasNext()){
                    StandardSession session = iterator.next();
                    if (session.checkExpire()){
                        sessionManager.removeSession(session);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}