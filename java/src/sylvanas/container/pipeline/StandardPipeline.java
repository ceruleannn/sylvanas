package sylvanas.container.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * pipeline 管道
 * typical linked list 链表管理
 */
public class StandardPipeline implements Pipeline{

    Valve first = null;

    @Override
    public void addValue(Valve valve) {
        if (first==null){
            first = valve;
        }

        Valve t = first;
        while(t != null){
            if (t.getNext()==null){
                t.setNext(valve);
                break;
            }
            t = t.getNext();
        }
    }

    @Override
    public Valve getFirstValue() {
        return first;
    }

    @Override
    public void removeValue(Valve valve) {

        Valve cur = null;
        if (valve==first){
            first = first.getNext();
        }else{
            cur = first;
        }
        while (cur!=null){
            if (cur.getNext()==valve){
                cur.setNext(valve.getNext());
                break;
            }
            cur = cur.getNext();
        }
    }

    @Override
    public Valve[] getValues() {
        if (first==null){
            return null;
        }

        List<Valve> list = new ArrayList<>();
        while (first!=null){
            list.add(first);
            first = first.getNext();
        }
        return list.toArray(new Valve[list.size()]);
    }
}
