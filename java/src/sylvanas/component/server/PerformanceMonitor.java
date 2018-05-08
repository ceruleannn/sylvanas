package sylvanas.component.server;

import org.hyperic.sigar.Sigar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PerformanceMonitor implements Runnable{

    private static int cpu = 0;
    private static int mem = 0;

    public static int getCpu() {
        return cpu;
    }

    public static int getMem() {
        return mem;
    }

    private Sigar sigar = new Sigar();

    private  long pid = sigar.getPid(); //javaw的pid 或者注释掉上边的Thread，在资源管理器看pid
    private  int availableProcessors = Runtime.getRuntime().availableProcessors();

    @Override
    public void run() {

        try {
            double d = sigar.getProcCpu(pid).getPercent() / availableProcessors * 100;
            cpu = (int)d;
            mem =(int)(sigar.getProcMem(pid).getResident()/1024);

            //System.out.println(format.format() + '%');

            //System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void start(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        PerformanceMonitor timer = new PerformanceMonitor();
        service.scheduleAtFixedRate(timer, 0, 800, TimeUnit.MILLISECONDS);
    }
}