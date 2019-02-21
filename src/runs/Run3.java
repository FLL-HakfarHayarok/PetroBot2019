package runs;

import robotUtils.RobotRun;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run3 extends RobotRun {

	int linesPassed = 2;
	
	@Override
	public void runInstructions() {
		
		GyroSensor.resetGyro();
		
		GyroSensor.gyroFollower(15, 0, 400, Chassis.distToDeg(115), Direction.FORWARD, false);
		Wait.waitForSeconds(0.3);
		//Drives forward until reaches HABITATION HUB mission
		GyroSensor.turnToAngle(-25, 150);	//Turns 45 degrees towards FOOD GROWTH mission
//		Chassis.tankDrive (200, 200, Chassis.distToDeg(30));
//		GyroSensor.turnToAngle(-90, 150);	//Turns 45 degrees towards FOOD GROWTH mission		
//		GyroSensor.gyroFollower(15, -90, 300, Chassis.distToDeg(10), Direction.FORWARD, false);
		
	}
}
