package sylvanas.component.context;

/**
 *
 *
 * 管理Servlet, Filter的创建与销毁
 */
public class InstanceManager {

    private final ClassLoader classLoader;

    public InstanceManager(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    public Object newInstance(String className) {
        try {
            return classLoader.loadClass(className).newInstance();
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
           return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(""+e);
        } catch (InstantiationException e) {
            throw new RuntimeException(""+e);
        }
    }
}
