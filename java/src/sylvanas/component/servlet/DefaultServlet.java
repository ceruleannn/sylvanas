package sylvanas.component.servlet;

import sylvanas.component.http.MimeTypes;
import sylvanas.component.resource.CacheResource;
import sylvanas.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 *
 */
public class DefaultServlet extends HttpServlet{

    private static String charset = Constants.DEFAULT_ENCODING;

    private final CacheHandler cacheHandler = new CacheHandler();



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //System.out.println("access default");

        String absolute = toAbsolute(req.getRequestURI());

        CacheResource cache = cacheHandler.getCache(absolute);
        if (cache!=null){
            // already has cache in server
            write(resp, cache);
        }else {
            // no cache , read disk file and add to cache
            CacheResource resource = new CacheResource();

            boolean ok = resource.toCache(absolute);
            if (ok){
                cacheHandler.addCache(absolute, resource);
                write(resp, resource);
            }
            else {
                resp.setStatus(404);
            }
        }

        //req.getRequestDispatcher()
    }

    private void write(HttpServletResponse resp, CacheResource cache){
        byte[] content = cache.getContent();
        String name = cache.getName();
        String extension = name.substring(name.lastIndexOf(".")+1);

        if (resp.getContentType()==null){
            resp.setContentType(MimeTypes.getContentTypeByExtension(extension)+";charset="+charset);

        }
        resp.setContentLength(content.length);

        try {
            resp.getOutputStream().write(content);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(404);
        }
    }

    private String toAbsolute(String relative){
        return (Constants.APP_BASE + relative);
    }
}

