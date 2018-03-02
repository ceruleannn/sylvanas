package sylvanas.util.http;

import java.util.*;

/**
 * @Description: request headers 请求头
 */
public class MimeHeaders {

    public static final int DEFAULT_SIZE = 10;

    private final ArrayList<MimeHeaderField> list = new ArrayList<>(DEFAULT_SIZE);

    private String rawHeaders = null;

    public MimeHeaders(String rawHeaders){
        this.rawHeaders = rawHeaders;
        parseHeaders();
    }

    public void parseHeaders(){

        if (rawHeaders==null){
            return;
        }

        String[] line = rawHeaders.split("\r\n");
        for (String s : line) {
            int index = s.indexOf(':');
            if (index<=0){
                System.out.println("error"+rawHeaders);
                continue;
            }
            String name = s.substring(0,index).trim();
            String value = s.substring(index+1).trim();
            addHeader(name, value);
        }

        //printHeaders();
    }

    public void addHeader(String name, String value){

        if ((name == null) || name.equals(""))
            throw new IllegalArgumentException("Illegal MimeHeader name");

        int length = list.size();
        for(int i=length-1; i>= 0; i--) {
            MimeHeaderField field = list.get(i);
            if (field.getName().equalsIgnoreCase(name)) {
                list.add(i+1,new MimeHeaderField(name, value));
                return;
            }
        }
        list.add(new MimeHeaderField(name, value));
    }

    public void setHeader(String name, String value){

        boolean existed = false;

        if ((name == null) || name.equals(""))
            throw new IllegalArgumentException("Illegal MimeHeader name");

        for (int i=0;i<list.size();i++){
            MimeHeaderField field = list.get(i);
            if (field.getName().equalsIgnoreCase(name)){
                if (!existed){
                    field.setValue(value);
                    existed = true;
                }else {
                    list.remove(i);  //remove duplicate header
                }
            }
        }

        if (!existed){
            list.add(new MimeHeaderField(name, value));
        }
    }

    public String getHeader(String name){

        for (MimeHeaderField mimeHeaderField : list) {
            if (mimeHeaderField.getName().equalsIgnoreCase(name)){
                return mimeHeaderField.getValue();
            }
        }
        return null;
    }

    public void removeHeader(String name){

        for (int i=0;i<list.size();i++){
            if (list.get(i).getName().equalsIgnoreCase(name)){
                list.remove(i);
            }
        }
    }

    /**
     * Return the value of the specified date header, if any; otherwise
     * return -1.
     *
     * @param name Name of the requested date header
     *
     * @exception IllegalArgumentException if the specified header value
     *  cannot be converted to a date
     */
    public long getDateHeader(String name) {

//        String value = getHeader(name);
//        if (value == null) {
//            return (-1L);
//        }
//
//        // Attempt to convert the date header in a variety of formats
//        long result = FastHttpDateFormat.parseDate(value, formats);
//        if (result != (-1L)) {
//            return result;
//        }
//        throw new IllegalArgumentException(value);
        return -1L;

    }


    /**
     * Return all of the values of the specified header, if any; otherwise,
     * return an empty enumeration.
     *
     * @param name Name of the requested header
     */
    public Enumeration<String> getHeaders(String name) {
        List<String> values = new ArrayList();
        for (MimeHeaderField field : list) {
            if (field.getName().equalsIgnoreCase(name)){
                values.add(field.getValue());
            }
        }
        return new Enumerator(values);
    }


    /**
     * Return the names of all headers received with this request.
     */
    public Enumeration<String> getHeaderNames() {
        List<String> names = new ArrayList();
        for (MimeHeaderField field : list) {
            names.add(field.getName());
        }
        return new Enumerator(names);
    }

    public void printHeaders(){

        Enumeration<String> enumeration = getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = getHeader(name);

            System.out.println(name+" #-#"+value);
        }
    }

}

class Enumerator implements Enumeration<String> {

    private Iterator<String> iterator = null;

    public Enumerator(Iterable<String> iterable){
        iterator = iterable.iterator();
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public String nextElement() {
        return iterator.next();
    }
}

class MimeHeaderField {
    // multiple headers with same name - a linked list will
    // speed up name enumerations and search ( both cpu and
    // GC)
    MimeHeaderField next;
    MimeHeaderField prev;

    private String name = null;
    private String value = null;

    /**
     * Creates a new, uninitialized header field.
     */
    public MimeHeaderField() {
        // NO-OP
    }

    public MimeHeaderField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
