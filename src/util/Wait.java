package util;

import robotUtils.RunHandler;

public class Wait {

	/**
	 * Wait for a duration by seconds
	 * 
	 * @param sec The seconds to wait
	 */
	public static void waitForSeconds(double sec){
		long startWait = System.currentTimeMillis();
		while(System.currentTimeMillis()-startWait < (sec*1000) && RunHandler.getCurrentRun().isActive());
	}
	
	/**
	 * Wait until a condition is met
	 * 
	 * @param condition The condition to wait for
	 */
	public static void waitUntil(boolean condition) {
		while(!condition && RunHandler.getCurrentRun().isActive());
	}
}
