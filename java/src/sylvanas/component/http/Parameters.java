package sylvanas.component.http;

import sylvanas.component.resource.ResourceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description: Request Parameters 请求参数 包含解析,存储,读取
 */
public final class Parameters {

    private final LinkedHashMap<String,String[]> paramMap =
            new LinkedHashMap<>();

    private boolean isParametersParsed = false;

    private boolean isParseBody = false;

    private String query = null;

    private String body = null;

    private String encoding = "UTF-8";

    //parameter limit
    //FailReason
    //URLDecode

    public Parameters(boolean isParseBody, String query, String body){
        this.isParseBody = isParseBody;
        this.query = query;
        this.body = body;
    }

    public void parse(){

        if (isParametersParsed){
            return;
        }

        String str = query.substring(query.indexOf('?')+1);
        parseParam(str);

        if (isParseBody){
            parseParam(body);
        }

        isParametersParsed = true;

        //printParameters();
    }

    /**
     * : key=v1&oo=11 从此格式字符串中提取请求参数
     *
     */
    public void parseParam(String str){

        if (str==null){
            return;
        }

        String[] res = str.split("&");

        for (String s : res) {
            int index = s.indexOf('=');
            if (index == 0 ) continue;
            if (index < 0){
                if (s.length()>0){
                    addParameter(s,"");
                }
                continue;
            }
            String key = s.substring(0,index);
            String value = s.substring(s.indexOf('=')+1);
            try {
                String decodeKey = URLDecoder.decode(key,encoding);
                String decodeValue = URLDecoder.decode(value,encoding);
                addParameter(decodeKey,decodeValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }


    public void addParameter(String key, String value){

        if (key==null){
            return;
        }

        if (paramMap.containsKey(key)){

            //TODO 更有效率的数组复制 不用每加一个就创建新数组

            String[] arr  = paramMap.get(key);
            String[] newarr = new String[2];
            System.arraycopy(arr,0,newarr,0,arr.length);
            newarr[newarr.length-1] = value;
            paramMap.put(key,newarr);

        }else {
            String[] s = new String[1];

            s[s.length-1] = value;
            paramMap.put(key,s);
        }
    }

    //----------data access
    public String[] getParameterValues(String name) {
        if (isParametersParsed) {

        }

        String[] values = paramMap.get(name);
        if (values == null) {
            return null;
        }
        return values;
    }

    public Enumeration<String> getParameterNames() {
        if (isParametersParsed){

        }

        return Collections.enumeration(paramMap.keySet());
    }

    public String getParameter(String name ) {
        if (isParametersParsed){

        }

        String[] values = paramMap.get(name);
        if (values != null) {
            if(values.length == 0) {
                return "";
            }
            return values[0];
        } else {
            return null;
        }
    }

    public LinkedHashMap<String,String[]> getParameterMap(){
        if (isParametersParsed){

        }

        return ResourceUtils.deepClone(this.paramMap);
    }

    public void printParameters(){

        System.out.println("------Parameters------");
        Map<String, String[]> map = getParameterMap();

        for (Map.Entry<String,String[]> entry: map.entrySet()){
            System.out.print(entry.getKey()+" # ");
            for (String s : entry.getValue()) {
                System.out.print(s+" ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
        System.out.println();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
