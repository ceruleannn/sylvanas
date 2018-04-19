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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }
}
