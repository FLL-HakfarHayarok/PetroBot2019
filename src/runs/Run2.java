package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run2 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(30, 0, 500, Chassis.distToDeg(70), Direction.FORWARD, false);
		GyroSensor.gyroFollower(30, 0, 200, Chassis.distToDeg(10), Direction.FORWARD, false);
		RobotStructure.getInstance().leftMotorReg.setSpeed(800);
		RobotStructure.getInstance().leftMotorReg.forward();
		Wait.waitForSeconds(1);
		RobotStructure.getInstance().rightMotorReg.setSpeed(800);
		Chassis.drive(Direction.FORWARD);
		Wait.waitForSeconds(2);
		Chassis.drive(Direction.BACKWARD);
		Wait.waitForSeconds(0.75);
		Chassis.drive(Direction.FORWARD);
		Wait.waitForSeconds(0.75);
		
	}

}
