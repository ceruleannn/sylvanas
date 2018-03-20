package sylvanas.test;

/**
 *
 *
 *
 */
public class HostDef {

    private String name ;
    private String appBase;
    private ConnectorDef[] valves = new ConnectorDef[0];

    public void addValve(ConnectorDef g){

        ConnectorDef results[] = new ConnectorDef[valves.length + 1];
        System.arraycopy(valves, 0, results, 0, valves.length);
        results[valves.length] = g;
        valves = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppBase() {
        return appBase;
    }

    public ConnectorDef[] getValves() {
        return valves;
    }
}
