package sylvanas.container.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 *
 *
 */
public class CommonClassLoader extends URLClassLoader {

    public CommonClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        System.class.getClassLoader();
    }
}
