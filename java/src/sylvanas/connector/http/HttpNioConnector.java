package sylvanas.connector.http;

import sylvanas.component.exception.ExceptionUtils;
import sylvanas.component.lifecycle.LifecycleException;
import sylvanas.container.Container;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 *
 *
 */
public class HttpNioConnector extends AbstractConnector implements Runnable {

    private int port = 8080;
    private String ip = "127.0.0.1";
    private int bufferSize = 4096;

    private Container container = null;
    private ByteBuffer readBuffer;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    private HttpProcessorManager manager = HttpProcessorManager.getManager();

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator ite = selector.selectedKeys().iterator();

                while (ite.hasNext()) {
                    SelectionKey key = (SelectionKey) ite.next();
                    ite.remove(); //确保不重复处理
                    handleKey(key);

                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Override
    protected void initInternal() throws LifecycleException {

        try {
            readBuffer = ByteBuffer.allocate(bufferSize);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException("can not init nio connector : " + ExceptionUtils.Stack2String(e));
        }
    }

    @Override
    protected void startInternal() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void stopInternal() throws LifecycleException {
        try {
            serverSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleKey(SelectionKey key)
            throws IOException {
        SocketChannel channel = null;
        int i=0;
        if (key.isAcceptable()) {
            ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
            channel = serverChannel.accept();//接受连接请求
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {

            channel = (SocketChannel) key.channel();
            readBuffer.clear();
            /*当客户端channel关闭后，会不断收到read事件，但没有消息，即read方法返回-1
             * 所以这时服务器端也需要关闭channel，避免无限无效的处理*/
            int count = 0;

            try {
                count = channel.read(readBuffer);
            } catch (IOException e) {
                return;
            }

            if (count > 0) {
                manager.process(new HttpNioProcessor(this,channel,readBuffer));
            }

        }

    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
