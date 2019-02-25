package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run1 extends RobotRun{

	@Override
	public void runInstructions() {
		//Resets the gyro sensor
		GyroSensor.resetGyro();
		
		//Driving straight to the extraction mission
		GyroSensor.gyroFollowerDegrees(30, 0, 500, Chassis.distToDeg(45), Direction.FORWARD, false);
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		//Lower the arm
		RobotStructure.getInstance().armMotorLeftReg.resetTachoCount();
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.backward();		
		Wait.waitForSeconds(2);
		RobotStructure.getInstance().armMotorLeftReg.flt();
		
		//Check if the run was stopped
				if(!RunHandler.getCurrentRun().isActive()) {
					return;
				}

		//Drive and turn away from the extraction mission
		GyroSensor.gyroFollowerDegrees(30, 0, 400, Chassis.distToDeg(12), Direction.FORWARD, false);
		GyroSensor.turnToAngle(90, 600);
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		//Drive to the base
		Chassis.drive(Direction.FORWARD);
		Wait.waitForSeconds(5);
	}

}
