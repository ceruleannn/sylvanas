package sylvanas.util.http;

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

    private MimeHeaders headers = null;

    private List<Cookie> list = new ArrayList<>(8);

    private boolean isCookiesParsed = false; //lazy evaluated

    public Cookies(MimeHeaders headers){
        this.headers = headers;
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
            name = cookie.substring(0,index);
            value = cookie.substring(index+1);
            list.add(new Cookie(name.trim(),value.trim()));
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
