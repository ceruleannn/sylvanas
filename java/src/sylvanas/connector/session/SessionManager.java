package sylvanas.connector.session;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * 1.维护 map<id,session>
 * 2.开启timer线程检查session超时
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
//        Thread thread = new Thread(new Timer(this));
//        thread.setDaemon(true);
//        thread.start();

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Timer(this), 10, 30, TimeUnit.SECONDS);
        //TODO: LIFECYCLE END THIS TASK
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

        long now = System.currentTimeMillis();
        session.setCreationTime(now);
        session.setLastAccessedTime(now);
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

    public void addSession(StandardSession standardSession){

        String id = standardSession.getId();
        if (sessions.containsKey(id)){
            return;
        }
        sessions.put(id, standardSession);
    }

    public void removeSession(StandardSession standardSession){

        sessions.remove(standardSession);
    }

    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }
}


class Timer implements Runnable{

    private SessionManager sessionManager = null;

    //TODO 中断 唤醒
    private boolean isStop = false;
    public Timer(SessionManager sessionManager){
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