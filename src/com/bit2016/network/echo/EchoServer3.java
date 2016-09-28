package com.bit2016.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer3 {
	private static final int PORT = 9000;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		PrintWriter[] pwArray = new PrintWriter[2];
		int count=0;
		
		try {
			//1. Server Socket 생성
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket = new ServerSocket();
			
			//2. Binding
			serverSocket.bind( new InetSocketAddress( localHostAddress, PORT ) );
			log( "binding " + localHostAddress + ":" + PORT );
			
			while (true) {
				// 3. Accept
				Socket socket = serverSocket.accept();

				Thread thread = new EchoServerReceiveThread( socket, pwArray ,count );
				thread.start();
				count++;
			}
		} catch( IOException ex ) {
			log( "error:" + ex );
		}
	}
	
	public static void log( String message ) {
		System.out.println( "[Echo Server]" + message );
	}
}