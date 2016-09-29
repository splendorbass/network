package com.bit2016.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ChatServerThread extends Thread {
	
	private Socket socket;
	private String name;
	private List<PrintWriter> listPrintWriter;
	
	public ChatServerThread( Socket socket,List<PrintWriter> listPrintWriter ){
		this.socket = socket;
		this.listPrintWriter = listPrintWriter;
	}
	
	@Override
	public void run() {
		
		try {
			//1.
			InetSocketAddress remoteSocketAddress = 
					(InetSocketAddress)socket.getRemoteSocketAddress();
			ChatServer.consoleLog("connected by client[" + 
					remoteSocketAddress.getAddress().getHostAddress()+":"+remoteSocketAddress.getPort()+"]");
			//2. Create Stream( From Basic Stream )
			BufferedReader br = 
					new BufferedReader( new InputStreamReader(socket.getInputStream(),"UTF-8"));
			PrintWriter pw =
					new PrintWriter( new OutputStreamWriter( socket.getOutputStream(), "UTF-8"),true);
		
			//3. processing...
			while(true){
				String line = br.readLine();
				if( line == null ){
					//doQuit();
					break;
				}
				String[] tokens = line.split( ":" );
				if("JOIN".equals(tokens[0])){
					doJoin(tokens[1], pw);
				}else if("MESSAGE".equals(tokens[0])){
					doMessage(tokens[1]);
				}else if("QUIT".equals(tokens[0])){
					doQuit(pw);
					socket.close();
				}else{
					System.out.println(line);
					pw.println(line);
				}
			}
		
		} catch (UnsupportedEncodingException e) {
			ChatServer.consoleLog("error:" + e );
		} catch (IOException e) {
			ChatServer.consoleLog("error:" + e );
		} finally{
			try{
				if( socket != null &&
					socket.isClosed() == false ){
					socket.close();					
				}
			}catch( IOException e ){
				ChatServer.consoleLog("error:" + e );
			}
			
		}
		
	}
	
	private void doJoin(String name, PrintWriter printWriter){
		//1. save nickname
		this.name = name;
		
		//2. broadcasting...
		String message = name + "님이 입장했습니다";
		synchronized ( listPrintWriter ) {
			for( PrintWriter pw : listPrintWriter ){
				pw.println( message );
			}
		}
		
		//3. addPrintWriter
		addPrintWriter( printWriter );
		
		//4. ack
		printWriter.println( "JOIN:OK" );
	}
	
	private void doMessage(String message){
		broadcastMessage(message);
		
	}
	
	private void doQuit(PrintWriter printWriter){
		deletePrintWriter(printWriter);
		broadcastMessage(name+"님이 퇴장하셨습니다.");
		
		return;
	}
	
	private void addPrintWriter( PrintWriter printWriter ){
		synchronized ( listPrintWriter ) {
			listPrintWriter.add( printWriter );
		}
		
	}
	
	private void deletePrintWriter( PrintWriter printWriter ){
		synchronized ( listPrintWriter ) {
			listPrintWriter.remove( printWriter );
		}
		
	}
	
	private void broadcastMessage( String message ){
		synchronized ( listPrintWriter ) {
			for( PrintWriter printWriter : listPrintWriter ){
				printWriter.println( name+":"+ message );
			}
		}
	}
}
