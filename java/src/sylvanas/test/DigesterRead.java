package sylvanas.test;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import sylvanas.component.digester.WebRuleSet;
import sylvanas.component.digester.WebXml;

import java.io.FileInputStream;
import java.util.Map;

/**
 * @Description:
 */
public class DigesterRead {

    protected Digester digester = null;

    public DigesterRead(Digester digester){
        this.digester = digester;
    }


    public static void main(String[] args) {
        Digester digester = new Digester();

        WebXml webXml = new WebXml();
        digester.push(webXml);

        WebRuleSet webRuleSet = new WebRuleSet(false);

        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.addRuleSet(webRuleSet);

        try {
            InputSource is = new InputSource();

            digester.parse(new FileInputStream("C:\\Users\\1\\Desktop\\web.xml"));

            Map<String,String> map = digester.getCurrentNamespaces();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey()+" - "+entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Map<String, ServletDef> map = webXml.getServlets();
//        for (Map.Entry<String, ServletDef> entry : map.entrySet()) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue().getServletClass());
//        }

        System.out.println("------------------");
        Map<String, String> map2 = webXml.getServletMappings();
        for (Map.Entry<String, String> entry2 : map2.entrySet()) {
            System.out.println(entry2.getKey());
            System.out.println(entry2.getValue());
        }
    }

}
