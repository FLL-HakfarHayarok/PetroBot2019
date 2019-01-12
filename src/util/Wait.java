package util;

import robotUtils.RunHandler;

public class Wait {
	
	public static void waitForSeconds(double sec){
		long startWait = System.currentTimeMillis();
		while(System.currentTimeMillis()-startWait < (sec*1000) && RunHandler.getCurrentRun().isActive());
	}
	
	public static void waitUntil(boolean condition) {
		while(!condition && RunHandler.getCurrentRun().isActive());
	}
	
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  
}
