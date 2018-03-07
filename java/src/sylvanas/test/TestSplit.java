package sylvanas.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 */
public class TestSplit {

    private static String s = "iloveyou";

    public static void main(String[] args) {
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("aaa","bbb");
//        System.out.println(hashMap.remove(null));

        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("ccc");
        list.add("123");

        String[] o = new String[10];
        String[] ss = list.toArray(o);

        System.out.println(o);
        System.out.println(ss);
    }
}
