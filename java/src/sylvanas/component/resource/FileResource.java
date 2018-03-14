package sylvanas.component.resource;

import sylvanas.component.exception.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * file://
 * @Description:
 */
public class FileResource extends AbstractResource{

    private final File file;

    private final String path;

    public FileResource(String path){
        Assert.notEmpty(path,"path must not be empty");
        this.path = path;
        this.file = new File(path);
    }

    public FileResource(File file){
        Assert.notNull(file, "file must not be null");
        this.path = file.getPath();
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String getFilename() {
        return file.getName();
    }

    @Override
    public URI getURI() {
        return file.toURI();
    }

    @Override
    public URL getURL() throws MalformedURLException{
        return file.toURI().toURL();
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    /**
     *  TODO:
     *  NIO implement:
     *  Files.newInputStream(this.file.toPath());
     */
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file) ;
    }
}
