package sylvanas.container.loader;

import sylvanas.container.Context;
import sylvanas.util.Constants;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
public class WebAppLoader {

    private Context context;
    private WebAppClassLoader classLoader;

    public WebAppLoader(Context context){
        this.context = context;
        startInternal();
    }


    public void startInternal(){

        File file1 = new File(context.getDocBase()+ Constants.CLASS_PATH);
        File file2 = new File(context.getDocBase()+Constants.LIB_PATH);

        URL url1;
        URL url2;
        try {
            url1 = file1.toURI().toURL();
            url2 = file2.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("unknown url" + e);
        }

        CommonClassLoader commonClassLoader = new CommonClassLoader(new URL[0],ClassLoader.getSystemClassLoader());
        classLoader = new WebAppClassLoader(new URL[]{url1,url2},commonClassLoader);
    }


    public void loadContext(){

    }


    public WebAppClassLoader getClassLoader() {
        return classLoader;
    }
}
