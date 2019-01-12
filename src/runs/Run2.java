package runs;

import robotUtils.RobotRun;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run2 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(30, 0, 500, Chassis.distToDeg(80), Direction.FORWARD, false);
	}

}
