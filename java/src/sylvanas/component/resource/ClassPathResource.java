package sylvanas.component.resource;

import sylvanas.component.exception.Assert;
import sylvanas.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * classpath://
 * @Description:
 */
public class ClassPathResource extends AbstractResource{

    private final String path;

    /**
     * provide the classLoader
     */
    private  Class<?> clazz;

    private  ClassLoader classLoader;

    public ClassPathResource(String path, Class<?> clazz) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        this.clazz = clazz;
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        Assert.notNull(path, "path must not be null");

        if (path.startsWith("/")){
            path = path.substring(1);
        }
        this.path = path;
        this.classLoader = classLoader!=null?classLoader: ClassUtils.getDefaultClassLoader();
    }

    public ClassPathResource(String path){
        this(path, (ClassLoader) null);
    }

    public ClassLoader getClassLoader() {
        return (this.clazz != null ? this.clazz.getClassLoader() : this.classLoader);
    }

    private URL parseURL(){
        if (clazz!=null){
            return clazz.getResource(path);
        }
        else if (classLoader!=null){
            return classLoader.getResource(path);
        }
        return null;
    }

    @Override
    public boolean exists() {
        return parseURL()!=null;
    }

    @Override
    public String getDescription() {
        StringBuilder builder = new StringBuilder("class path resource [");
        String pathToUse = path;
        if (this.clazz != null && !pathToUse.startsWith("/")) {
            builder.append(parseURL());
            builder.append('/');
        }
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        builder.append(pathToUse);
        builder.append(']');
        return builder.toString();
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public String getFilename() {
        return null;
    }

    @Override
    public URI getURI() {
        try {
            return parseURL().toURI();
        } catch (URISyntaxException e) {
           return null;
        }
    }

    @Override
    public URL getURL() throws IOException {
        return parseURL();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
}
