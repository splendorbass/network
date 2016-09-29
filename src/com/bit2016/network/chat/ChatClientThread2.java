package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread2 extends Thread {
	private BufferedReader br = null;
	private Socket socket = null;
	
	public ChatClientThread2(BufferedReader br, Socket socket){
		this.br = br;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			while(true){
			String data = br.readLine();

			if ( data == null) {
				break;
			}

			// 8. 출력
			System.out.println(data);
			}
		}catch(SocketException ex){
			ex.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if( br != null ){
				br.close();
				//socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
