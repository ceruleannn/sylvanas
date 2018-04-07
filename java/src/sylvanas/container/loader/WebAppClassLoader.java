package sylvanas.container.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */
public class WebAppClassLoader extends URLClassLoader{

    private static final Map<String,Class<?>> cache = new HashMap<>(16);

    public WebAppClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    // loadClass = 双亲委托 若父类系统类没有 则调用findClass 实际载入
//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//
//        //缓存加载
//        Class<?> clazz = cache.get(name);
//        if (clazz!=null){
//            return clazz;
//        }
//
//        //双亲委托
//        clazz = super.loadClass(name);
//        if (clazz==null){
//            throw new ClassNotFoundException("can not found class " + name);
//        }
//
//        //加入缓存
//        cache.put(name,clazz);
//        return clazz;
//    }
}














