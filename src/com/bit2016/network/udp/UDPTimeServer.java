package com.bit2016.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public static final int PORT = 9900;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			//1. socket 생성
			socket = new DatagramSocket(PORT);
			
			while (true) {
				// 2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);

				socket.receive(receivePacket); // blocking

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");


				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a" );
				String data = format.format(new Date());
				System.out.println( data );
				
				// 3. 데이터 송신
				
				byte[] sendData = data.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
						receivePacket.getPort());
				socket.send(sendPacket);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if( socket != null && socket.isClosed() == false ){
				socket.close();
			}
		}

	}

}
