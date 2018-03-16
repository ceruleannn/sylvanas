package sylvanas.component.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * http://TODO
 * @Description:
 */
public class UrlResource extends AbstractResource{

    @Override
    public boolean exists() {
        return super.exists();
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public URL getURL() throws IOException {
        return super.getURL();
    }

    @Override
    public URI getURI() {
        return super.getURI();
    }

    @Override
    public File getFile() throws IOException {
        return super.getFile();
    }

    @Override
    public String getFilename() {
        return super.getFilename();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }
}
