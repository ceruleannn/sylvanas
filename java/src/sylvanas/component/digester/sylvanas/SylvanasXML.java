package sylvanas.component.digester.sylvanas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 */
public class SylvanasXML {

    private List<ConnectorDef > connectors = new ArrayList<>(4);
    private List<HostDef> hosts = new ArrayList<>(4);
    private String name;

    public void addConnector(ConnectorDef g){
        connectors.add(g);
    }

    public void addHost(HostDef g){
        hosts.add(g);
    }

    public List<ConnectorDef> getConnectors() {
        return connectors;
    }

    public List<HostDef> getHosts() {
        return hosts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
