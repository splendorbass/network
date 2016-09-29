package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "192.168.1.22";
	private static final int SERVER_PORT = 9990;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		
		try{
			//1. Scanner 생성(표준입력 연결)
			scanner = new Scanner( System.in );
			
			//2. Socket 생성
			socket = new Socket();
			
			//3. connect to server
			socket.connect( new InetSocketAddress( SERVER_IP, SERVER_PORT ) );
			
			//4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ), true );
			
			System.out.print("닉네임>>");
			String name = scanner.nextLine();
			pw.println("JOIN:"+name);
			
//			Thread thread1 = new ChatClientThread(scanner, pw);
			Thread thread2 = new ChatClientThread2(br, socket);
			String line = null; 
//			thread1.start();
//			thread2.start();
			thread2.start();
			while(true){
		
			//System.out.print(">>");
			
			line = scanner.nextLine();
			
			if( "QUIT".equals(line) ){
				pw.println(line);
				socket.close();
				break;
			}
			
			pw.println("MESSAGE:"+line);
			
			//System.out.println(br.readLine());
			}

		
			
			
		}catch(SocketException ex){
			System.out.println( "abnormal closed by server");
		}catch( IOException ex ) {
			System.out.println( "error" + ex );
		} finally {
			try {
				if( scanner != null ) {
					scanner.close();
				}
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}
	}
}

