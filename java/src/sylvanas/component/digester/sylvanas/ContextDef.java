package sylvanas.component.digester.sylvanas;

import sylvanas.container.pipeline.Valve;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 */
public class ContextDef {
    private String name ;
    private String docBase;
    private List<Valve> valves = new ArrayList<>(4);

    public void addValve(Valve g){
        valves.add(g);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocBase() {
        return docBase;
    }

    public List<Valve> getValves() {
        return valves;
    }
}
