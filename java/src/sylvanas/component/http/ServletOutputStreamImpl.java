package sylvanas.component.http;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class ServletOutputStreamImpl extends ServletOutputStream {


    // ----------------------------------------------------- Instance Variables


    protected OutputBuffer ob;


    // ----------------------------------------------------------- Constructors


    public ServletOutputStreamImpl(OutputBuffer ob) {
        this.ob = ob;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Prevent cloning the facade.
     */
    @Override
    protected Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }


    // -------------------------------------------------------- Package Methods


    /**
     * Clear facade.
     */
    void clear() {
        ob = null;
    }


    // --------------------------------------------------- OutputStream Methods


    @Override
    public void write(int i)
            throws IOException {
        ob.write(i);
    }


    @Override
    public void write(byte[] b)
            throws IOException {
        write(b, 0, b.length);
    }


    @Override
    public void write(byte[] b, int off, int len)
            throws IOException {
        ob.write(b, off, len);
    }


    /**
     * Will send the buffer to the client.
     */
    @Override
    public void flush()
            throws IOException {
        ob.flush();
    }


    @Override
    public void close()
            throws IOException {
        ob.close();
    }


}

