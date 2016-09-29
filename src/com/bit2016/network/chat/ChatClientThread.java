package com.bit2016.network.chat;


import java.io.PrintWriter;

import java.util.Scanner;

public class ChatClientThread extends Thread {
	private Scanner scanner = null;
	private PrintWriter pw = null;
	
	public ChatClientThread( Scanner scanner, PrintWriter pw){
		this.scanner = scanner;
		this.pw = pw;
	}
	
	@Override
	public void run() {
		while(true){
			System.out.print( ">>" );
			String line = scanner.nextLine();
		
			pw.println("MESSAGE:"+line);
		}
	}
	
}
