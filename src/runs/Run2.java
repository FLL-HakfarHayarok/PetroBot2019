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
		
		//Drive towards the mission chunk
//		GyroSensor.gyroFollower(50, -6, 500, Chassis.distToDeg(80), Direction.FORWARD, false);

		Chassis.tankDrive(500, 750, Chassis.distToDeg(80), Direction.FORWARD);
		
		RobotStructure.getInstance().rightMotorReg.setSpeed(800);
		RobotStructure.getInstance().rightMotorReg.forward();
		Wait.waitForSeconds(2);
		RobotStructure.getInstance().rightMotorReg.flt();
		
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.rotate(130);
		
		RobotStructure.getInstance().armMotorRightReg.setSpeed(150);
		RobotStructure.getInstance().armMotorRightReg.backward();
		Wait.waitForSeconds(3);
		RobotStructure.getInstance().armMotorRightReg.flt();

		Chassis.tankDrive(500, 750, Chassis.distToDeg(2), Direction.BACKWARD);
		Wait.waitForSeconds(2);
		Chassis.tankDrive(500, 750, Chassis.distToDeg(88), Direction.BACKWARD);
	}

}
