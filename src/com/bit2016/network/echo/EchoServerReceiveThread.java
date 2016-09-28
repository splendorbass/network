package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {
	private Socket socket;
	private PrintWriter[] pw = null;
	private int count = 0;

	public EchoServerReceiveThread( Socket socket ) {
		this.socket = socket;
	}
	
	public EchoServerReceiveThread( Socket socket, PrintWriter[] pw, int count ) {
		this.socket = socket;
		try {
			pw[count] = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ), true );
			this.pw = pw;
			this.count = count;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try{
			InetSocketAddress isa = (InetSocketAddress)socket.getRemoteSocketAddress();
			System.out.println(
					"[server#" + getId() + "] connected by client[" +
			isa.getAddress().getHostAddress() +":" +
			isa.getPort() + "]");
			
			//4. IOStream 생성(받아오기)
			BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream(), "UTF-8" ) );
			//PrintWriter pw = new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8" ), true );
			while( true ) {
				String data = br.readLine();
				if( data == null ) {
					EchoServer3.log( "closed by client" );
					break;
				}
				
				EchoServer3.log( "received:" + data );
				pw[count].println( data );
			}
		} catch( SocketException ex ){
			EchoServer3.log( "abnormal closed by client" );
		} catch( IOException ex ) {
			EchoServer3.log( "error:" + ex );
		} finally {
			try {
				if( socket != null && socket.isClosed() == false ){
					socket.close();
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
