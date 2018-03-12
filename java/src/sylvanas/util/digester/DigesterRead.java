package sylvanas.util.digester;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
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


        digester.setNamespaceAware(false);
        digester.setValidating(false);
        digester.addRuleSet(webRuleSet);

        digester.getParser();



        try {
            InputSource is = new InputSource();

            digester.parse(new FileInputStream("C:\\Users\\1\\Desktop\\web.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Map<String, ServletDef> map = webXml.getServlets();
        for (Map.Entry<String, ServletDef> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getServletClass());
        }
    }
    public void readWebXML(){
        if (digester==null){

        }



    }

    public void readServerXML(){

    }
}
