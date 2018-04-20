package sylvanas.component.servlet;


import com.alibaba.fastjson.JSON;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.component.lifecycle.LifecycleState;
import sylvanas.container.Container;
import sylvanas.container.Context;
import sylvanas.container.Host;
import sylvanas.container.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ManagerServlet extends HttpServlet implements ContainerServlet{

    private Host host;

    private Context context;

    private Wrapper wrapper;

    public ManagerServlet(){

    }

    // /manager/html?query=contexts
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession();

        String query = req.getParameter("query");
        if (query==null){

            // 返回完整页面
            resp.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/manager.html").forward(req,resp);

        }
        else if("contexts".equals(query)){

            // 返回部署信息
            List<ContextsDefinition> contexts = new ArrayList<>();
            for (Container container : host.getChildren()) {
                Context context = (Context)container;
                ContextsDefinition definition = new ContextsDefinition();
                definition.setPath(context.getPath());
                definition.setProjectName(context.getName());

                if (context.getState().equals(LifecycleState.STARTED)){
                    definition.setStatus("Running");
                }else {
                    definition.setStatus("Stopped");
                }
                definition.setSessions(context.getSessionHandler().getSessions().size());
                contexts.add(definition);
            }

            write(resp,JSON.toJSONString(contexts));

        }
        else if("system".equals(query)){
            // 返回系统信息
        }
        else if("deploy".equals(query)){
            String path = req.getParameter("path");
            if (path!=null){
                try {
                    host.getHostConfig().deployOutsideDir(new File(path));
                } catch (LifecycleException |IOException e) {
                    resp.sendError(501);
                }
            }
            write(resp,200);
        }
        else{
            String name = req.getParameter("name");

            if(name==null){
                resp.sendError(404);
            }

            try {
                for (Container container : host.getChildren()) {

                    if (!container.getName().equals(name)){
                        continue;
                    }

                    LifecycleState state = container.getState();
                    if("Start".equals(query)){
                        if (LifecycleState.STOPPED.equals(state)){
                            container.start();
                        }
                    }
                    else if("Stop".equals(query)){
                        if (LifecycleState.STARTED.equals(state)){
                            container.stop();
                        }
                    }
                    else if("Undeploy".equals(query)){
                        container.stop();
                        host.getHostConfig().undeploy(name);

                    }
                    else if("Sessions".equals(query)){
                        Context context = (Context)container;
                        context.getSessionHandler().removeAllSession();
                    }
                }
            } catch (LifecycleException | IOException e) {
                resp.sendError(500);
            }
            write(resp,200);
        }


    }

    private void write(HttpServletResponse response,String json){
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            try {
                response.sendError(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void write(HttpServletResponse response,int code){
        write(response,"{\"code\":\""+code+"\"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public Wrapper getWrapper() {
        return wrapper;
    }

    @Override
    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
        this.context = (Context) wrapper.getParent();
        this.host = (Host) context.getParent();
    }
}


class ContextsDefinition{

    private String path;
    private String projectName;
    private String status;
    private int sessions;

    public ContextsDefinition(){

    }

    public ContextsDefinition(String path,String projectName,String status, int sessions){
        this.path = path;
        this.projectName = projectName;
        this.status = status;
        this.sessions = sessions;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }
}

class SystemDefinition{


}








