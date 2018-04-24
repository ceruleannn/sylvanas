package sylvanas.component.servlet;

import sylvanas.component.exception.Assert;
import sylvanas.component.resource.CacheResource;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */
public class CacheHandler {

    private Map<String, CacheResource> caches = new HashMap<>();

    public CacheHandler(){

    }

    public CacheResource getCache(String path){
        return caches.get(path);
    }

    public void addCache(String path, CacheResource resource){
        Assert.notNull(path,resource);
        caches.put(path,resource);
    }

    public void addCache(String path){
        CacheResource resource = new CacheResource();

        resource.toCache(path);
        caches.put(path,resource);
    }
}

//TODO NEW THREAD FOR FILE MODIFY CHECK
//TODO 可以解决加入缓存后undeploy删除文件的异常