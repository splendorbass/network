package com.bit2016.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "192.168.1.22";
	private static final int SERVER_PORT = 5003;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		try {
			// 1. socket 생성
			socket = new Socket();

			// 2. 서버연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			System.out.println("[client] connected ");
			
			//3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			while(true){
			//4. 쓰기
				System.out.print(">>");
			String data = scanner.nextLine();
			if( "exit".equals(data) ){
				break;
			}
			os.write( data.getBytes("UTF-8"));
			
			//5. 읽기
			byte[] buffer = new byte[256];
			int readByteCount = is.read( buffer );
			if( readByteCount == -1 ){
				System.out.println("[client] closed by server");
				return;
			}
			
			data = new String( buffer, 0, readByteCount, "UTF-8");
			
			System.out.println("<<" + data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if( socket != null & socket.isClosed() == false ){
					socket.close();
				}
			
			}catch( IOException ex ){
				ex.printStackTrace();
			}
			
		}

	}


}
