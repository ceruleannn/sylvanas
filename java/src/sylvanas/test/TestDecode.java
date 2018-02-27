package sylvanas.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description:
 */
public class TestDecode {
    public static void main(String[] args) {
//        try {
//            String s = URLDecoder.decode("5&%E4%BD%A0=3","UTF-8");
//            System.out.println(s);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        String encoding2="utf-8";

        String query = "Sylvanas/index?=3=4&b&iii====&lo==?&545=1=2&545==3";
        String params = query.substring(query.indexOf('?')+1);
        String[] res = params.split("&");
        for (String s : res) {
            int index = s.indexOf('=');
            if (index <= 0){
                if (s.length()>0){
                    System.out.println(s+"-"+"");
                }
                continue;
            }
            String key = s.substring(0,index);
            String value = s.substring(s.indexOf('=')+1);

            try {
                String decodeKey = URLDecoder.decode(key,encoding2);
                String decodeValue = URLDecoder.decode(value,encoding2);
                System.out.println(decodeKey+"-"+decodeValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }
}
