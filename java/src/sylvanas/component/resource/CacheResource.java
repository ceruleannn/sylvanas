package sylvanas.component.resource;

import java.io.*;

/**
 *
 *
 * cache for static resource
 */
public class CacheResource extends AbstractResource{

    private byte[] content;
    private String name;
    private String path;
    private int size;
    private long timestamp;

    private volatile long accessCount = 0;

    public CacheResource(){

    }

    public void recycle(){

    }

    public boolean toCache(String absolutePath){

        path = absolutePath;
        File file = new File(path);

        if (!file.exists()){
            return false;
        }

        if (!file.isFile()){
            return false;
        }

        try {
            InputStream input = new FileInputStream(file);
            content = new byte[input.available()];
            size = input.read(content);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        name = file.getName();
        timestamp = file.lastModified();

        return true;
    }

    @Override
    public String getDescription() {
        return "file [cache: " + path + "]" ;
    }

    @Override
    public InputStream getInputStream()  {

        synchronized (this){
            accessCount++;
        }

        return new ByteArrayInputStream(content);

    }

    public long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getSize() {
        return size;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public byte[] getContent() {
        synchronized (this){
            accessCount++;
        }
        return content;
    }
}
