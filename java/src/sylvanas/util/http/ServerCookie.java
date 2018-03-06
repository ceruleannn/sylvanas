package sylvanas.util.http;

import java.io.Serializable;

/**
 * @Description:
 */
public class ServerCookie implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name = null;

    private String value = null;

    public ServerCookie(String name ,String value){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
