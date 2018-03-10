package sylvanas.connector;

import sylvanas.container.Container;
import sylvanas.container.Context;
import sylvanas.container.Host;
import sylvanas.container.Wrapper;
import sylvanas.connector.http.*;

/**
 * @Description:
 *  Adapter for Connector and Container
 *  连接器与容器的适配器
 *
 *  1. 创建 Request
 *  2. 创建 Response
 *  3. 连接 Request 和 Response
 *  4. 解析 MimeHeaders, cookie, parameters, sessionID
 *  4. 传入 Container
 */
public class Adapter {

    HttpConnector connector = null;

    public Adapter(HttpConnector connector){
        this.connector = connector;
    }

    public void service(RawRequest rawRequest, RawResponse rawResponse){
        Request request = new Request(rawRequest);
        Response response = new Response(rawResponse);

        request.parse();

        request.setResponse(response);
        response.setRequest(request);

        connector.getContainer().doChain(request, response);
    }

    public Container getChain(){

        // TODO: LEAVE THIS WORK TO LIFECYCLE OR INSTANCE MANAGER ?

        Container host = new Host();
        Container context = new Context();
        Container servlet = new Wrapper();
        host.addNextContainer(context);
        context.addNextContainer(servlet);

        return host;
    }

}
