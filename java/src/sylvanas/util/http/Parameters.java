package sylvanas.util.http;

import sylvanas.util.ResourceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description: Request Parameters 请求参数 包含解析,存储,读取
 */
public final class Parameters {

    private final LinkedHashMap<String,ArrayList<String>> paramMap =
            new LinkedHashMap<>();

    private boolean isParametersParsed = false;

    private String query = null;

    private String encoding = "UTF-8";

    //parameter limit
    //FailReason
    //URLDecode

    public Parameters(){

    }

    public void parse(){
        parseRequestLineParam();
        parseRequestBodyParam();
        isParametersParsed = true;
    }

    public void parseRequestLineParam(){

        if (query==null){
            return;
        }

        String params = query.substring(query.indexOf('?')+1);
        String[] res = params.split("&");

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

    public void parseRequestBodyParam(){

    }

    public void addParameter(String key, String value){

        if (key==null){
            return;
        }

        if (paramMap.containsKey(key)){
            ArrayList<String> list = paramMap.get(key);
            list.add(value);
        }else {
            ArrayList<String> newList = new ArrayList<>(1);
            newList.add(value);
            paramMap.put(key,newList);
        }
    }

    //----------data access
    public String[] getParameterValues(String name) {
        if (isParametersParsed){

        }

        ArrayList<String> values = paramMap.get(name);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }

    public Enumeration<String> getParameterNames() {
        if (isParametersParsed){

        }

        return Collections.enumeration(paramMap.keySet());
    }

    public String getParameter(String name ) {
        if (isParametersParsed){

        }

        ArrayList<String> values = paramMap.get(name);
        if (values != null) {
            if(values.size() == 0) {
                return "";
            }
            return values.get(0);
        } else {
            return null;
        }
    }

    public LinkedHashMap<String,ArrayList<String>> getParameterMap(){
        if (isParametersParsed){

        }

        return ResourceUtils.deepClone(this.paramMap);
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
