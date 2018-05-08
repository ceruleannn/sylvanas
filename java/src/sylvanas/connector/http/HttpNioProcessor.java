package sylvanas.connector.http;

import sylvanas.connector.Adapter;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 *
 *
 */
public class HttpNioProcessor implements Runnable {

    private int bufferSize = 4096;
    private ByteBuffer readBuffer;

    private SocketChannel channel;
    private Connector connector;

    public HttpNioProcessor(Connector connector ,SocketChannel channel,ByteBuffer readBuffer) {
        this.channel = channel;
        this.connector = connector;

        this.readBuffer = readBuffer;
    }

    @Override
    public void run() {
        readBuffer.flip();

        byte[] bytes = new byte[readBuffer.remaining()];
        readBuffer.get(bytes);
        String raw = new String(bytes);
        //System.out.println(raw);

        Adapter adapter = new Adapter(connector);
        adapter.handle(channel, raw);
    }
}
