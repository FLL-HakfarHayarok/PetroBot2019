package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run3 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(12.5, 0, 450, Chassis.distToDeg(120), Direction.FORWARD, false);
		RobotStructure.getInstance().leftMotorReg.setSpeed(170);
		RobotStructure.getInstance().rightMotorReg.setSpeed(700);
		RobotStructure.getInstance().leftMotorReg.forward();
		RobotStructure.getInstance().rightMotorReg.forward();
		while(Math.abs(GyroSensor.getCurrentAngle()) < 65 && RunHandler.getCurrentRun().isActive()) {}
		Chassis.brake(false);
		//GyroSensor.turnToAngle(60, 500);
		//GyroSensor.gyroFollower(5, 45, 350, GeneralUtils.distToDeg(57.17697), Direction.FORWARD, false);
		//GyroSensor.turnToAngle(60, 500);
		GyroSensor.gyroFollower(25, 90, 150, Chassis.distToDeg(70), Direction.FORWARD, false);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);
		RobotStructure.getInstance().armMotorRightReg.rotate(-9000, true);
		GyroSensor.gyroFollower(0, 90, 250, Chassis.distToDeg(30), Direction.BACKWARD, false);
	}
}
