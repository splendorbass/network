package com.bit2016.network.thread;

public class DigitThread extends Thread {

	@Override
	public void run() {
		
		for( int i = 0; i <= 9 ; i++ ){
			System.out.print(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
