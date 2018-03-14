package sylvanas.component.resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * @Description:
 */
public interface Resource extends InputStreamResource{

    /**
     * Return whether this resource actually exists in physical form.
     */
    boolean	exists();


    /**
     * Return a description for this resource,
     * to be used for error output when working with the resource.
     */
    String	getDescription();


    /**
     * Return a File handle for this resource.
     */
    File getFile() throws IOException;


    /**
     * Determine a filename for this resource, i.e.
     */
    String getFilename();


    /**
     * Return a URI handle for this resource.
     */
    URI getURI();


    /**
     * Return a URL handle for this resource.
     */
    URL getURL() throws IOException;


    /**
     * Return whether this resource represents a handle with an open stream.
     */
    boolean	isOpen();

}
