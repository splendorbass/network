package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient2 {
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
			
			BufferedReader br = 
					new BufferedReader( new InputStreamReader(socket.getInputStream(),"UTF-8") );
			PrintWriter pw = 
					new PrintWriter( new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);
			while(true){
			//4. 쓰기
				System.out.print(">>");
			String data = scanner.nextLine();
			if( "exit".equals(data) ){
				break;
			}
			pw.println( data );

			
			//5. 읽기
			
			String data2 = br.readLine();
			if( data2 == null ){
				System.out.println("[server] closed by client");
				break;
			}
			
			System.out.println("<<" + data2 );
			
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
