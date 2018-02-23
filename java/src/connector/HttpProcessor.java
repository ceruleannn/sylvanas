package connector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Description:
 *
 *
 */
public class HttpProcessor implements Runnable{


    private Socket socket = null;
    public HttpProcessor(Socket socket) {
        if (socket==null) throw new NullPointerException();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in  = socket.getInputStream();

            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            in.close();

            byte[] bt = outSteam.toByteArray();
            String str = new String(bt);
            System.out.println("主机收到信息：" + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
