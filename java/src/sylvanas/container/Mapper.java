package sylvanas.container;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @Description:
 *
 * 对于HOST级别, key = path (默认是项目名) ;  value = context
 * <Context path="" docBase="Sylvanas" reloadable="true" />
 *
 * 对于CONTEXT级别 , 1.精确匹配, 2.路径匹配 /* , 3.拓展名匹配通配符 *., 4.默认SERVLET
 */
public class Mapper {

    protected TreeMap<String, Container> map = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    });

    public Mapper(){

    }

    /**
     * if has duplicate keys then replace the older one
     * 若有重复的 key 键 , 则替换旧值
     * @param name 匹配串
     * @param container 容器
     */
    public void add(String name, Container container){
        map.put(name, container);
    }

    public void get(String name){
        map.get(name);
    }

    public void remove(String name){
        map.remove(name);
    }
}
