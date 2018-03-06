package sylvanas.util.http;

import sylvanas.http.connector.RawRequest;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 *
 * cookie 包含name, value, expires, path等属性
 * 但浏览器向服务器发送请求时只提交name = value键值对
 * 其他属性均由浏览器解读
 *
 */
public final class Cookies {

    public static final String SESSION_ID_KEY = "sessionid";

    private RawRequest rawRequest = null;

    private MimeHeaders headers = null;

    private List<Cookie> list = new ArrayList<>(8);

    private boolean isCookiesParsed = false; //lazy evaluated

    public Cookies(MimeHeaders headers,RawRequest rawRequest){
        this.headers = headers;
        this.rawRequest = rawRequest;

        parseHeaders();
        printCookies();
    }

    public void parseHeaders(){
        isCookiesParsed = true;

        if (headers==null){
            return;
        }

        String rawCookies = headers.getHeader("Cookie");
        if (rawCookies==null||rawCookies.length()<=0){
            return;
        }

        String[] cookies  = rawCookies.split(";");
        if (cookies.length<=1){ // 1 means no match ';' and return whole String
            return;
        }


        int index = 0;
        String name = null;
        String value = null;
        for (String cookie : cookies) {
            index = cookie.indexOf('=');
            name = cookie.substring(0,index).trim();
            value = cookie.substring(index+1).trim();

            //find session id
            if (name.equalsIgnoreCase(SESSION_ID_KEY)){
                rawRequest.setSessionID(value);
            }

            list.add(new Cookie(name,value));
        }
    }

    public Cookie[] getCookies(){
        return list.toArray(new Cookie[list.size()]);
    }

    public void printCookies(){
        System.out.println("--------Cookies-------");
        System.out.println();
        for (Cookie cookie : list) {
            System.out.println(cookie.getName()+" # "+cookie.getValue());
        }
        System.out.println();
        System.out.println("----------------------");
    }
}
