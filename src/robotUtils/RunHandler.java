package robotUtils;

public class RunHandler {

	private static RobotRun currentRun;
	
	/**
	 * Sets the current run.
	 * 
	 * @param run The current run.
	 */
	public static void setCurrentRun(RobotRun run) {
		currentRun = run;
	}
	
	/**
	 * Returns the current run
	 * 
	 * @return The run currently active.
	 */
	public static RobotRun getCurrentRun() {
		return currentRun;
	}
	
}
