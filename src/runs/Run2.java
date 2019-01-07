package runs;

import robotUtils.RobotRun;
import util.Direction;
import util.GeneralUtils;
import util.GyroSensor;

public class Run2 extends RobotRun {

	@Override
	public void runInstructions() {
		
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(10, 0, 450, GeneralUtils.distToDeg(115), Direction.FORWARD, false);
		GyroSensor.turnToAngle(35, 500);
		GyroSensor.gyroFollower(5, 45, 350, GeneralUtils.distToDeg(57.18), Direction.FORWARD, false);
		GyroSensor.turnToAngle(80, 500);
		GyroSensor.gyroFollower(5, 80, 250, GeneralUtils.distToDeg(40), Direction.FORWARD, false);
	}
}
