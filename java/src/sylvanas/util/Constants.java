package sylvanas.util;

/**
 *
 *
 *
 */
public final  class Constants {

    public static final String ROOT = System.getProperty("user.dir");

    public static final String APP_BASE = ROOT + "/webapps/";

    public static final String CLASS_PATH = "/WEB-INF/classes/";

    public static final String LIB_PATH = "/WEB-INF/lib/";

    public static final String WEB_XML_PATH = "/WEB-INF/web.xml";

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static final String SESSION_ID_NAME = "Ssessionid";


    /**
     * CRLF.
     */
    public static final byte[] CRLF = "\r\n".getBytes();

    /**
     *  protocol
     */
    public static final byte[] HTTP_11 = "HTTP/1.1".getBytes();

    /**
     * Server string.
     */
    public static final String SERVER_STRING = "Sylvanas/1.0";


    /**
     * CR. 回车
     */
    public static final byte CR = (byte) '\r';


    /**
     * LF. 换行
     */
    public static final byte LF = (byte) '\n';


    /**
     * SP.
     */
    public static final byte SP = (byte) ' ';


    /**
     * HT.
     */
    public static final byte HT = (byte) '\t';


    /**
     * COLON.
     */
    public static final byte COLON = (byte) ':';

    /**
     * SEMI_COLON.
     */
    public static final byte SEMI_COLON = (byte) ';';
}
