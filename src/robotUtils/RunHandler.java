package robotUtils;

public class RunHandler {

	private static RobotRun currentRun;
	
	public static void setCurrentRun(RobotRun run) {
		currentRun = run;
	}
	
	public static RobotRun getCurrentRun() {
		return currentRun;
	}
	
}
