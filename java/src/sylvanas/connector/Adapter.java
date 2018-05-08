package sylvanas.connector;

import sylvanas.connector.http.Connector;

import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 *  one request -  one thread - one adapter
 *
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

    private Connector connector = null;

    public Adapter(Connector connector){
        this.connector = connector;
    }

    public void handle(Socket socket,String raw){
        RawResponse rawResponse = new RawResponse(socket);
        service(rawResponse,raw);
    }

    public void handle(SocketChannel socketChannel, String raw){
        RawResponse rawResponse = new RawResponse(socketChannel);
        service(rawResponse,raw);
    }

    private void service(RawResponse rawResponse,String raw){

        RawRequest rawRequest = new RawRequest(raw);

        Request request = new Request(rawRequest);
        Response response = new Response(rawResponse);

        request.setResponse(response);
        response.setRequest(request);
        rawResponse.setResponse(response);
        rawResponse.setRawRequest(rawRequest);

        request.parse();

        String message = connector.getContainer().doChain(request, response);

        Returner returner = new Returner(message);
        returner.doReturn(request,response);

    }


}
