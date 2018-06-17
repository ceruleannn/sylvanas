package sylvanas.component.servlet;


import com.alibaba.fastjson.JSON;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.component.lifecycle.LifecycleState;
import sylvanas.component.server.PerformanceMonitor;
import sylvanas.container.Container;
import sylvanas.container.Context;
import sylvanas.container.Host;
import sylvanas.container.Wrapper;
import sylvanas.util.Constants;
import sylvanas.util.EnvUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ManagerServlet extends HttpServlet implements ContainerServlet {

    private Host host;

    private Context context;

    private Wrapper wrapper;

    public ManagerServlet(){

    }

    static {
        //获取存放dll文件的绝对路径（假设将dll文件放在系统根目录下的WEB-INF文件夹中）
        String path = Constants.APP_BASE+"manager"+Constants.LIB_PATH;
        //将此目录添加到系统环境变量中
        addDirToPath(path);
        //加载相应的dll文件，注意要将'\'替换为'/'
        System.load(path.replaceAll("\\\\","/")+"sigar-amd64-winnt.dll");
    }

    private static void addDirToPath(String s){
        try {
            //获取系统path变量对象
            Field field=ClassLoader.class.getDeclaredField("sys_paths");
            //设置此变量对象可访问
            field.setAccessible(true);
            //获取此变量对象的值
            String[] path=(String[])field.get(null);
            //创建字符串数组，在原来的数组长度上增加一个，用于存放增加的目录
            String[] tem=new String[path.length+1];
            //将原来的path变量复制到tem中
            System.arraycopy(path,0,tem,0,path.length);
            //将增加的目录存入新的变量数组中
            tem[path.length]=s;
            //将增加目录后的数组赋给path变量对象
            field.set(null,tem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // /manager/html?query=contexts
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession();

        String query = req.getParameter("query");
        if (query==null){

            // 返回完整页面
            resp.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/managerord.html").forward(req,resp);
        }
        else if ("contexts".equals(query)) {
            // 返回部署信息
            List<ContextsDefinition> contexts = new ArrayList<>();
            for (Container container : host.getChildren()) {
                Context context = (Context) container;
                ContextsDefinition definition = new ContextsDefinition();
                definition.setPath(context.getPath());
                definition.setProjectName(context.getName());

                if (context.getState().equals(LifecycleState.STARTED)) {
                    definition.setStatus("Running");
                } else {
                    definition.setStatus("Stopped");
                }
                definition.setSessions(context.getSessionHandler().getSessions().size());
                contexts.add(definition);
            }

            write(resp,JSON.toJSONString(contexts));

        } else if ("system".equals(query)) {
            // 返回系统信息

            write(resp, JSON.toJSONString(new SystemDefinition()));
        } else if ("deploy".equals(query)) {
            String path = req.getParameter("path");
            if (path!=null){
                try {
                    host.getHostConfig().deployOutsideDir(new File(path));
                } catch (LifecycleException | IOException e) {
                    e.printStackTrace();
                    resp.sendError(503);
                }
            }
            write(resp, 200);
        } else if("cpu".equals(query)){

            try {

                int cpuint = PerformanceMonitor.getCpu();
                int memint = PerformanceMonitor.getMem();
                int thread = ManagementFactory.getThreadMXBean().getThreadCount();

                String json = "{\"cpu\":"+cpuint+",\"mem\":"+memint+",\"thread\":"+thread+"}";
                write(resp,json);

            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(502);
            }



        } else {
            String name = req.getParameter("name");

            if (name == null) {
                resp.sendError(404);
            }

            try {

                Container container = null;
                for (Container ele : host.getChildren()) {
                    if (ele.getName().equals(name)) {
                        container = ele;
                        break;
                    }
                }

                if (container == null) {
                    write(resp, 404);
                    return;
                }

                LifecycleState state = container.getState();
                if ("Start".equals(query)) {
                    if (LifecycleState.STOPPED.equals(state)) {
                        container.start();
                    }
                }
                else if ("Stop".equals(query)) {
                    if (LifecycleState.STARTED.equals(state)) {
                        container.stop();
                    }
                }
                else if ("Undeploy".equals(query)) {
                    container.stop();
                    host.getHostConfig().undeploy(name);

                }
                else if ("Sessions".equals(query)) {
                    Context context = (Context) container;
                    context.getSessionHandler().removeAllSession();
                }

            } catch (LifecycleException | IOException e) {
                e.printStackTrace();
                resp.sendError(500);
            }
            write(resp, 200);
        }


    }

    private void write(HttpServletResponse response, String json) {
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

    private void write(HttpServletResponse response, int code) {
        write(response, "{\"code\":\"" + code + "\"}");
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


class ContextsDefinition {

    private String path;
    private String projectName;
    private String status;
    private int sessions;

    public ContextsDefinition() {

    }

    public ContextsDefinition(String path, String projectName, String status, int sessions) {
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

class SystemDefinition {

    private String serverVersion = Constants.SYLVANAS_VERSION;
    private String version = System.getProperty("java.version");
    private String vendor = System.getProperty("java.vendor");

    private String osName = System.getProperty("os.name");
    private String osVersion = System.getProperty("os.arch");
    private String osArch = System.getProperty("os.version");

    private String host = System.getenv("COMPUTERNAME");
    private String ip = EnvUtils.getRealIp();


    public String getServerVersion() {
        return serverVersion;
    }

    public String getVersion() {
        return version;
    }

    public String getVendor() {
        return vendor;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public String getHost() {
        return host;
    }

    public String getIp() {
        return ip;
    }

}








