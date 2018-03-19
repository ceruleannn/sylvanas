package sylvanas.container;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 *
 * 对于HOST级别, key = path (默认是项目名) ;  value = context
 * <Context path="" docBase="Sylvanas" reloadable="true" />
 *
 * 对于CONTEXT级别 , 1.精确匹配, 2.路径匹配 /* , 3.拓展名匹配通配符 *., 4.默认SERVLET
 */
public class Mapper {

    private List<Wrapper> exactWrappers = new ArrayList<>();
    private List<Wrapper> wildcardWrappers = new ArrayList<>();
    private List<Wrapper> extensionWrappers = new ArrayList<>();
    private Wrapper defaultWrapper ;


    public Mapper(){

    }


}
