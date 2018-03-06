package sylvanas.http.connector;

import java.io.*;
import java.net.Socket;

/**
 * @Description:
 * 对每个请求创建一个独立的HttpProcessor, 并将其委派至Manager中调用executor在
 * 新线程中启用
 *
 */
public class HttpProcessor implements Runnable{


    private Socket socket = null;


    private static final String HTML = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "Content-Length: %d\r\n" + "\r\n"
            + "%s";


    public HttpProcessor(Socket socket) {
        if (socket==null) throw new NullPointerException();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in  = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // handle inputStream
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder reqStr = new StringBuilder();


            //TODO 判断缓冲区是否超过而不是使用大缓冲区

            char[] buf = new char[1024*8];
            do {
                if (br.read(buf) != -1) {
                    reqStr.append(buf);
                }

            } // the key point to read a complete arrival socket stream with bio but without block
            while (br.ready());

            // get uri in http request line
            String respStr = reqStr.toString();

            //
            RawRequest rawRequest = new RawRequest(respStr);
            Request request = new Request(rawRequest);


            // join the html content
            respStr = "<h1>" + respStr + "</h1>";
            out.write(String.format(HTML, respStr.length(), respStr).getBytes());
            out.flush();

            in.close();
            out.close();

            //String str = new String(reqStr);
            //System.out.println("主机收到信息：\n" + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
