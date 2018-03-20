package sylvanas.test;

/**
 *
 *
 *
 */
public class SylvanasDef {

    private ConnectorDef[] connectors = new ConnectorDef[0];
    private HostDef[] hosts = new HostDef[0];
    private String name;

    public void addConnector(ConnectorDef g){

        ConnectorDef results[] = new ConnectorDef[connectors.length + 1];
        System.arraycopy(connectors, 0, results, 0, connectors.length);
        results[connectors.length] = g;
        connectors = results;
    }

    public void addHost(HostDef g){

        HostDef results[] = new HostDef[hosts.length + 1];
        System.arraycopy(hosts, 0, results, 0, hosts.length);
        results[hosts.length] = g;
        hosts = results;
    }

    public HostDef[] getHosts() {
        return hosts;
    }

    public ConnectorDef[] getConnectors() {
        return connectors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
