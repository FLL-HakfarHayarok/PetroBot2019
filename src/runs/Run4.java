package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Side;
import util.Wait;

public class Run4 extends RobotRun {

	int linesPassed = 2;

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollowerDegrees(10, 0, 400, Chassis.distToDeg(63), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-45, 100);
		GyroSensor.gyroFollowerDegrees(10, 45, 400, Chassis.distToDeg(60), Direction.FORWARD, false);

		// Check if the run was stopped
		if (!RunHandler.getCurrentRun().isActive()) {
			return;
		}

		RobotStructure.getInstance().rightMotorReg.setSpeed(100);
		RobotStructure.getInstance().leftMotorReg.setSpeed(100);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);

		while (GyroSensor.getCurrentAngle() < -25 && RunHandler.getCurrentRun().isActive()) {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.forward();
		}

		RobotStructure.getInstance().rightMotorReg.flt();
		RobotStructure.getInstance().leftMotorReg.flt();

		// Check if the run was stopped
		if (!RunHandler.getCurrentRun().isActive()) {
			return;
		}

		Wait.waitForSeconds(1);

		GyroSensor.gyroFollowerDegrees(10, 0, 400, Chassis.distToDeg(32), Direction.FORWARD, false);
		Chassis.curveDrive(15, 115, 600, Side.RIGHT, false);
		Chassis.tankDriveMillis(200, 200, 2000, Direction.FORWARD, false);
		Chassis.rotateRightArm(-55, 129.6);
		Chassis.tankDriveMillis(320, 300, 3000, Direction.BACKWARD, false);
		Chassis.tankDriveMillis(300, 800, 1500, Direction.FORWARD, true);
	}
}
