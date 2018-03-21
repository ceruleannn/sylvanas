package sylvanas.test;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import sylvanas.component.digester.sylvanas.ConnectorDef;
import sylvanas.component.digester.sylvanas.HostDef;
import sylvanas.component.digester.sylvanas.SylvanasXML;
import sylvanas.container.pipeline.Valve;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 *
 *
 */
public class DigesterTest {

    private SylvanasXML sylvanasXML = null;

    public SylvanasXML getSylvanasXML() {
        return sylvanasXML;
    }

    public void setSylvanasXML(SylvanasXML sylvanasXML) {
        this.sylvanasXML = sylvanasXML;
    }

    protected Digester createStartDigester() {

        Digester digester = new Digester();
        digester.setValidating(true);
        digester.push(this);

        digester.addObjectCreate("Sylvanas","sylvanas.component.digester.sylvanas.SylvanasXML","className");
        digester.addSetProperties("Sylvanas");
        digester.addSetNext("Sylvanas",
                "setSylvanasXML",
                "sylvanas.component.digester.sylvanas.SylvanasXML");

        digester.addObjectCreate("Sylvanas/Connector",
                "sylvanas.component.digester.sylvanas.ConnectorDef","className");
        digester.addSetProperties("Sylvanas/Connector");

        digester.addSetNext("Sylvanas/Connector",
                "addConnector",
                "sylvanas.component.digester.sylvanas.ConnectorDef");

        digester.addObjectCreate("Sylvanas/Host",
                "sylvanas.component.digester.sylvanas.HostDef","className");
        digester.addSetProperties("Sylvanas/Host");

        digester.addSetNext("Sylvanas/Host",
                "addHost",
                "sylvanas.component.digester.sylvanas.HostDef");

        digester.addObjectCreate("Sylvanas/Host/Valve",
                "","className");
        digester.addSetProperties("Sylvanas/Host/Valve");

        digester.addSetNext("Sylvanas/Host/Valve",
                "addValve",
                "sylvanas.container.pipeline.Valve");

        digester.addCallMethod("Sylvanas/Host/Valve/init-param",
                "addInitParameter", 2);
        digester.addCallParam("Sylvanas/Host/Valve/init-param/param-name",
                0);
        digester.addCallParam("Sylvanas/Host/Valve/init-param/param-value",
                1);

        digester.addObjectCreate("Sylvanas/Host/Context",
                "sylvanas.component.digester.sylvanas.ContextDef","className");
        digester.addSetProperties("Sylvanas/Host/Context");

        digester.addSetNext("Sylvanas/Host/Context",
                "addContext",
                "sylvanas.component.digester.sylvanas.ContextDef");


        digester.addObjectCreate("Sylvanas/Host/Context/Valve",
                "","className");
        digester.addSetProperties("Sylvanas/Host/Context/Valve");

        digester.addSetNext("Sylvanas/Host/Context/Valve",
                "addValve",
                "sylvanas.container.pipeline.Valve");

        digester.addCallMethod("Sylvanas/Host/Context/Valve/init-param",
                "addInitParameter", 2);
        digester.addCallParam("Sylvanas/Host/Context/Valve/init-param/param-name",
                0);
        digester.addCallParam("Sylvanas/Host/Context/Valve/init-param/param-value",
                1);

        return (digester);

    }

    public static void main(String[] args) {
        DigesterTest test = new DigesterTest();
        Digester digester = test.createStartDigester();
        try {
            digester.parse(new FileInputStream("C:\\Users\\1\\Desktop\\sylvanas.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }


        List<ConnectorDef> connectors = test.getSylvanasXML().getConnectors();

        for (ConnectorDef connector : connectors) {
            System.out.println(connector.getProtocol());
        }
        for (HostDef host:test.getSylvanasXML().getHosts()){
            for (Valve valve : host.getValves()) {
                System.out.println(valve.getInfo());
            }
        }

        Valve valve = test.getSylvanasXML().getHosts().get(0).getValves().get(0);
        System.out.println(valve.getParameterMap());

        System.out.println(test.getSylvanasXML().getHosts().get(0).getContexts().get(0).getValves().get(0).getParameterMap().get("charset"));
        System.out.println("done");
    }
}




