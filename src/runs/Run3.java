package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run3 extends RobotRun {
	@Override
	public void runInstructions() {
		//Reset the gyro sensor
		GyroSensor.resetGyro();
		
		//Drive towards the Habitation hub
		GyroSensor.gyroFollowerDegrees(20, 0, 400, Chassis.distToDeg(30), Direction.FORWARD, false);
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		//Move the arm so that it doesn't hit the Habitation hub
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);
		Chassis.rotateRightArm(40, 9);
		
		//Drive all the way to the Habitation hub
		GyroSensor.gyroFollowerMillis(15, 0, 400, 5200, Direction.FORWARD, false);
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		//Take out the cone module
		Chassis.rotateRightArm(-90, 9);
		Chassis.tankDriveDegrees(800, 800, Chassis.distToDeg(1.5), Direction.BACKWARD, false);
		Chassis.tankDriveMillis(800, 800, 1000, Direction.FORWARD, false);
		Wait.waitForSeconds(0.5);
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		Chassis.tankDriveDegrees(800, 800, Chassis.distToDeg(1.5), Direction.BACKWARD, false);
		Chassis.tankDriveMillis(800, 800, 1000, Direction.FORWARD, false);
		Chassis.rotateRightArm(120, 9);
		
		
		//Return to the base
		Chassis.tankDriveDegrees(100, 100, Chassis.distToDeg(15), Direction.BACKWARD, false);
		Chassis.tankDriveMillis(500, 800, 7000, Direction.BACKWARD, true);
	}
}
