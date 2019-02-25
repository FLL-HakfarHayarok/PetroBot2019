package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run5 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollowerDegrees(10, 0, 400, Chassis.distToDeg(65), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-45, 100);
		GyroSensor.gyroFollowerDegrees(10, 45, 400, Chassis.distToDeg(55), Direction.FORWARD, false);

		// Check if the run was stopped
		if (!RunHandler.getCurrentRun().isActive()) {
			return;
		}

		RobotStructure.getInstance().rightMotorReg.setSpeed(100);
		RobotStructure.getInstance().leftMotorReg.setSpeed(100);

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

		GyroSensor.gyroFollowerDegrees(10, 0, 400, Chassis.distToDeg(65), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-40, 100);
		GyroSensor.gyroFollowerDegrees(10, 40, 400, Chassis.distToDeg(32), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-85, 100);
		Wait.waitForSeconds(1);
		Chassis.tankDriveMillis(500, 500, 1000, Direction.FORWARD, false);
		Wait.waitForSeconds(1);
		Chassis.tankDriveMillis(300, 300, 1500, Direction.BACKWARD, false);
		while (GyroSensor.getCurrentAngle() < 0 && RunHandler.getCurrentRun().isActive()) {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.forward();
			}
			Chassis.tankDriveDegrees(100, 100, Chassis.distToDeg(2), Direction.FORWARD, false);
		}
	}
