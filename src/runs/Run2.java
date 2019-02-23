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
		//Reset the gyro sensor
		GyroSensor.resetGyro();
		
		//Drive towards the mission chunk
		Chassis.tankDriveDegrees(500, 750, Chassis.distToDeg(80), Direction.FORWARD, false);
		
		//Ensure the solar panels were pushed
		RobotStructure.getInstance().rightMotorReg.setSpeed(800);
		RobotStructure.getInstance().rightMotorReg.forward();
		Wait.waitForSeconds(2);
		RobotStructure.getInstance().rightMotorReg.flt();
		
		//Move the first payload down the track
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.rotate(130);
		
		//Lower the other payloads on to the track
		RobotStructure.getInstance().armMotorRightReg.setSpeed(150);
		RobotStructure.getInstance().armMotorRightReg.backward();
		Wait.waitForSeconds(3);
		RobotStructure.getInstance().armMotorRightReg.flt();

		//Drive back to the base
		Chassis.tankDriveDegrees(500, 750, Chassis.distToDeg(2), Direction.BACKWARD, true);
		Wait.waitForSeconds(2);
		Chassis.tankDriveDegrees(500, 750, Chassis.distToDeg(88), Direction.BACKWARD, false);
	}

}
