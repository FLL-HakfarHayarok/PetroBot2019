package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import util.Direction;
import util.GeneralUtils;
import util.GyroSensor;

public class Run2 extends RobotRun {

	@Override
	public void runInstructions() {
		
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(10, 0, 450, GeneralUtils.distToDeg(115), Direction.FORWARD, false);
		GyroSensor.turnToAngle(40, 500);
		GyroSensor.gyroFollower(5, 45, 350, GeneralUtils.distToDeg(57.17697), Direction.FORWARD, false);
		GyroSensor.turnToAngle(70, 500);
		GyroSensor.gyroFollower(10, 80, 250, GeneralUtils.distToDeg(60), Direction.FORWARD, false);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);
		RobotStructure.getInstance().armMotorRightReg.rotate(3000, true);
		GyroSensor.gyroFollower(5, 80, 250, GeneralUtils.distToDeg(30), Direction.BACKWARD, false);
	}
}