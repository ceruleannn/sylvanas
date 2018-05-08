package sylvanas.connector.http;

import sylvanas.component.lifecycle.LifecycleBase;
import sylvanas.container.Container;

/**
 *
 *
 *
 */
public abstract class AbstractConnector extends LifecycleBase implements Connector{

    private Container container;

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }
}
