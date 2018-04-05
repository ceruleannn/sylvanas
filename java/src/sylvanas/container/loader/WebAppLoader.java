package sylvanas.container.loader;

/**
 *
 */
public class WebAppLoader {

    private WebAppClassLoader classLoader;
    private static String classesPath = "/WEB-INF/classes";
    private static String libPath = "/WEB-INF/lib";

    public WebAppLoader(){

    }

    public WebAppClassLoader createClassLoader(){
       // return new WebAppClassLoader();
        return null;
    }

    public void loadStaticResource(){

    }

    public void loadClass(){

    }

    public void loadContext(){

    }

    public void loadJars(){

    }
}
