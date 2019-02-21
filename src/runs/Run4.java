package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run4 extends RobotRun {

	int linesPassed = 0;
	
	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(15, 0, 400, Chassis.distToDeg(25), Direction.FORWARD, false);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(300);
		Chassis.rotateRightArm(45, 9);
		GyroSensor.gyroFollower(30, 0, 400, Chassis.distToDeg(70), Direction.FORWARD, false);
		Chassis.rotateRightArm(-200, 9);
//		Chassis.tankDrive(-400, -400, Chassis.distToDeg(5));
		

	}
}
