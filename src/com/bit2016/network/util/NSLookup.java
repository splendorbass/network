package com.bit2016.network.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		try {
			while(true){
			System.out.print("> ");
			String host = scanner.nextLine();
			if( "exit".equals(host) ){
				return;
			}
			
			InetAddress[] inetAddresses = InetAddress.getAllByName( host );
			
			for( InetAddress inetAddress : inetAddresses ){
				System.out.println( inetAddress.getHostAddress() );
			}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
}
