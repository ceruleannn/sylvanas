package sylvanas.test;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 *
 *
 */
public class DigesterTest {

    private SylvanasDef sylvanasDef = null;

    public SylvanasDef getSylvanasDef() {
        return sylvanasDef;
    }

    public void setSylvanasDef(SylvanasDef sylvanasDef) {
        this.sylvanasDef = sylvanasDef;
    }

    protected Digester createStartDigester() {

        Digester digester = new Digester();
        digester.setValidating(true);
        digester.push(this);

        digester.addObjectCreate("Sylvanas","sylvanas.test.SylvanasDef","className");
        digester.addSetProperties("Sylvanas");
        digester.addSetNext("Sylvanas",
                "setSylvanasDef",
                "sylvanas.test.SylvanasDef");

        digester.addObjectCreate("Sylvanas/Connector",
                "sylvanas.test.ConnectorDef","className");
        digester.addSetProperties("Sylvanas/Connector");

        digester.addSetNext("Sylvanas/Connector",
                "addConnector",
                "sylvanas.test.ConnectorDef");

        digester.addObjectCreate("Sylvanas/Host",
                "sylvanas.test.HostDef","className");
        digester.addSetProperties("Sylvanas/Host");

        digester.addSetNext("Sylvanas/Host",
                "addHost",
                "sylvanas.test.HostDef");

        digester.addObjectCreate("Sylvanas/Host/Valve",
                "sylvanas.test.ValveDef","className");
        digester.addSetProperties("Sylvanas/Host/Valve");

        digester.addSetNext("Sylvanas/Host/Valve",
                "addValve",
                "sylvanas.test.ConnectorDef");

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

        ConnectorDef[] connectors = test.getSylvanasDef().getConnectors();
        for (ConnectorDef connector : connectors) {
            System.out.println(connector.getProtocol());
        }
        for (HostDef host:test.getSylvanasDef().getHosts()){
            for (ConnectorDef valveDef : host.getValves()) {
                System.out.println(valveDef.getProtocol());
            }
        }
        System.out.println("done");
    }
}




