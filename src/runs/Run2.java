package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run2 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(12.5, 0, 450, Chassis.distToDeg(100), Direction.FORWARD, false);
		RobotStructure.leftMotorReg.setSpeed(170);
		RobotStructure.rightMotorReg.setSpeed(700);
		RobotStructure.leftMotorReg.forward();
		RobotStructure.rightMotorReg.forward();
		while(Math.abs(GyroSensor.getCurrentAngle()) < 65 && !Thread.currentThread().isInterrupted()) {}
		Chassis.brake(false);
		//GyroSensor.turnToAngle(60, 500);
		//GyroSensor.gyroFollower(5, 45, 350, GeneralUtils.distToDeg(57.17697), Direction.FORWARD, false);
		//GyroSensor.turnToAngle(60, 500);
		GyroSensor.gyroFollower(25, 90, 150, Chassis.distToDeg(70), Direction.FORWARD, false);
		RobotStructure.armMotorRightReg.setSpeed(800);
		RobotStructure.armMotorRightReg.rotate(-9000, true);
		GyroSensor.gyroFollower(0, 90, 250, Chassis.distToDeg(30), Direction.BACKWARD, false);
	}
}
