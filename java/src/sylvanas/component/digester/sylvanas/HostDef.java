package sylvanas.component.digester.sylvanas;

import sylvanas.container.pipeline.Valve;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 */
public class HostDef {

    private String name ;
    private String appBase;
    private List<Valve> valves = new ArrayList<>(4);
    private List<ContextDef> contexts = new ArrayList<>(4);

    public void addValve(Valve g){
        valves.add(g);
    }

    public void addContext(ContextDef contextDef){
        contexts.add(contextDef);
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

    public List<Valve> getValves() {
        return valves;
    }

    public List<ContextDef> getContexts() {
        return contexts;
    }



}
