package sylvanas.container.startup;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import sylvanas.component.digester.WebRuleSet;
import sylvanas.component.digester.WebXml;
import sylvanas.component.resource.Resource;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description:
 */
public class ContextConfig {

    /**
     * 解析器
     */
    private Digester digester = new Digester();

    /**
     * 解析结果对象
     */
    private WebXml webXml = new WebXml();

    /**
     * 解析规则 (不支持xml - fragment)
     */
    private WebRuleSet webRuleSet = new WebRuleSet(false);

    public ContextConfig(){

    }

    /**
     * 启动方法
     * 配置一个Context工程
     */
    public void webConfig(){

    }

    public void parseWebXml(){

        digester.push(webXml);

        digester.setNamespaceAware(true);
        digester.setValidating(false);
        digester.addRuleSet(webRuleSet);

        try {
            // File, inputStream, inputSource, uri,
            digester.parse(new FileInputStream("C:\\Users\\1\\Desktop\\web.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    protected Resource getWebXmlSource() {
        return null;
    }
}
