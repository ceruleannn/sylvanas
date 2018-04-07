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

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("common load");
        return loadClass(name, false);

    }
}
