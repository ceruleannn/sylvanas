package sylvanas.util;

import java.io.*;

/**
 * @Description:
 */
public class ResourceUtils {

    public static <T extends Serializable> T clone(T obj){

        T clonedObj = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(in);
            clonedObj = (T) ois.readObject();
            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }

        return clonedObj;
    }
}
