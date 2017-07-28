package com.my.netty.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {

	public void serve(int port) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		ServerSocket ssocket = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ssocket.bind(address); //将服务器绑定到选定的端口
		Selector selector = Selector.open();	//打开Selector来处理Channel
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);	//将ServerSocket注册到Selector以接受连接
		final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
		for (;;) {
			try {
				selector.select();	//等待需要处理的新事件;阻塞将一直持续到下一个传入事件
			} catch(IOException ex) {
				ex.printStackTrace();
				break;
			}
			Set<SelectionKey> readKeys = selector.selectedKeys();	//获取所有接受事件的SelectionKey
			Iterator<SelectionKey> iterator = readKeys.iterator();
			
			while(iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if(key.isAcceptable()) {	//检查事件是否是一个新的已经就绪可以被接受的连接
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());	//接受客户端，并将它注册到选择器
						System.out.println("Accepted connection form " + client);
						
						if(key.isWritable()) {	//j检查套接字是否已经准备好写数据
							SocketChannel sclient = (SocketChannel) key.channel();
							ByteBuffer buffer = (ByteBuffer) key.attachment();
							while (buffer.hasRemaining()) {
								if(client.write(buffer) == 0) {	//将数据写到已连接的客户端
									break;
								}
							}
							sclient.close();
						}
					}
				} catch(IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException cex) {
						
					}
				}
			}
		}
	}
}
