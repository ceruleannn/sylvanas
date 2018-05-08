package sylvanas.connector.http;

import sylvanas.connector.Adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 对每个请求创建一个独立的HttpProcessor, 并将其委派至Manager中调用executor在
 * 新线程中启用
 * <p>
 * 以字符串形式采集http请求 ,并调用适配器
 */
public class HttpProcessor implements Runnable {


    private Socket socket = null;
    private Connector connector = null;


    public HttpProcessor(Connector connector, Socket socket) {
        if (socket == null) throw new NullPointerException();
        this.socket = socket;
        this.connector = connector;
    }


    @Override
    public void run() {

        try (
            InputStream in = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
        ) {
            StringBuilder sb = new StringBuilder();

            //TODO 判断缓冲区是否超过而不是使用大缓冲区

            char[] buf = new char[1024 * 8];
            do {
                if (br.read(buf) != -1) {
                    sb.append(buf);
                }

            } // the key point to read a complete arrival socket stream with bio but without block
            while (br.ready());

            String raw = sb.toString();
            //System.out.println("主机收到信息：\n" + raw);

            if (raw.length() == 0) {
                return;
            }

            Adapter adapter = new Adapter(connector);
            adapter.handle(socket, raw);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
