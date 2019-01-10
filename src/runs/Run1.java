package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run1 extends RobotRun{

	@Override
	public void runInstructions() {
		//Drives towards the extraction mission
		GyroSensor.gyroFollower(0, 0, 350, Chassis.distToDeg(60), Direction.BACKWARD, false); 		
		//Lower the arm and grab the cores
		RobotStructure.armMotorLeftReg.setSpeed(800); 
		RobotStructure.armMotorLeftReg.rotate(600);
		//Driving to get away from the mission
		GyroSensor.gyroFollower(0, 0, 400, Chassis.distToDeg(30), Direction.BACKWARD, false);
		//Turning to avoid hitting the mission on the way back
		GyroSensor.turnToAngle(-45, 500);
		//Drive back to the base
		GyroSensor.gyroFollower(20, 0, 400, Chassis.distToDeg(70), Direction.FORWARD, false);
	}

}
