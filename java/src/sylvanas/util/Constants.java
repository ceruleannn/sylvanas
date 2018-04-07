package sylvanas.util;

/**
 *
 *
 *
 */
public final  class Constants {

   // public static final String
    public static final String WEB_XML_PATH = "/WEB-INF/web.xml";
    public static final String ROOT = System.getProperty("user.dir");
    public static final String APP_BASE = ROOT + "/webapps/";
    public static final String CLASS_PATH = "/WEB-INF/classes/";
    public static final String LIB_PATH = "/WEB-INF/lib/";


    public static void main(String[] args) {


        System.out.println(ROOT);
        System.out.println(APP_BASE);
        System.out.println(System.getProperty("user.dir"));
    }
}
