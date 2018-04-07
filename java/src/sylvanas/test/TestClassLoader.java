package sylvanas.test;

import sylvanas.container.loader.CommonClassLoader;
import sylvanas.container.loader.WebAppClassLoader;

import java.io.File;
import java.net.URL;

/**
 *
 *
 *
 */
public class TestClassLoader {

    public static void main(String[] args) {
        try {
            File file = new File("D:/mymy/fly/test/bin/test.jar");
            URL url = file.toURI().toURL();

            File file3 = new File("D:/mymy/fly/test/bin/");
            URL url3 = file3.toURI().toURL();

            URL[] urls = new URL[]{url3};

            CommonClassLoader commonClassLoader = new CommonClassLoader(new URL[]{},Thread.currentThread().getContextClassLoader());
            WebAppClassLoader classLoader = new WebAppClassLoader(urls,commonClassLoader);

            Class test3 = classLoader.loadClass("com.Test3");
            Class test4 = classLoader.loadClass("com.Test3");

            Class test5 = classLoader.loadClass("com.Test3");

            //Class test4 = classLoader.loadClass("com.Test3");
//            Method method = test3.getMethod("run");
//            Object obj = test3.newInstance();
//
//            method.invoke(obj);

//            Method[] ms = test3.getMethods();
//            for (Method m : ms) {
//                System.out.println(m.getName());
//            }

//            Class<?> test1 = classLoader.loadClass("testurlclassloader.Test1");
//            Method[] ms2 = test1.getMethods();
//            for (Method m : ms2) {
//                System.out.println(m.getName());
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
