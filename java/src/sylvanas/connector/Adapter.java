package sylvanas.connector;

import sylvanas.connector.http.HttpConnector;
import sylvanas.util.JavaUtils;

import java.io.IOException;
import java.net.Socket;

/**
 * @Description:
 *  Adapter for Connector and Container
 *  连接器与容器的适配器
 *
 *  1. 创建 Request
 *  2. 创建 Response
 *  3. 连接 Request 和 Response
 *  4. 解析 MimeHeaders, cookie, parameters, sessionID
 *  5. 传入 Container
 *  6. 最终返回
 */
public class Adapter {

    private HttpConnector connector = null;

    public Adapter(HttpConnector connector){
        this.connector = connector;
    }

    public void service(Socket socket, String raw){

        RawRequest rawRequest = new RawRequest(raw);
        RawResponse rawResponse = new RawResponse(socket);

        Request request = new Request(rawRequest);
        Response response = new Response(rawResponse);

        request.setResponse(response);
        response.setRequest(request);
        rawResponse.setResponse(response);
        rawResponse.setRawRequest(rawRequest);

        request.parse();

        connector.getContainer().doChain(request, response);

        try {
            rawResponse.doWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            JavaUtils.closeQuietly(socket);
        }

    }

}
