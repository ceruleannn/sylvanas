package sylvanas.connector.http;

import sylvanas.container.Container;

/**
 *
 *
 *
 */
public interface Connector {

    Container getContainer();

    void setContainer(Container container);
}
