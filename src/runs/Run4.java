package runs;

import robotUtils.RobotRun;
import util.Chassis;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run4 extends RobotRun {

	int linesPassed = 2;
	
	@Override
	public void runInstructions() {
		//Reset the gyro sensor
		GyroSensor.resetGyro();
		
		GyroSensor.gyroFollowerDegrees(15, 0, 400, Chassis.distToDeg(115), Direction.FORWARD, false);
		Wait.waitForSeconds(0.5);
		//Drives forward until reaches HABITATION HUB mission
		
		GyroSensor.turnToAngle(-45, 150);	//Turns 45 degrees towards FOOD GROWTH mission
		Wait.waitForSeconds(1);
//		
//		GyroSensor.gyroFollowerUntilBlack(15, GyroSensor.getCurrentAngle(), 400, 1, ColorSensorID.CENTER, Direction.FORWARD, false);
//		Wait.waitForSeconds(1);
//		GyroSensor.gyroFollowerDegrees(15, GyroSensor.getCurrentAngle(), 400, Chassis.distToDeg(27), Direction.FORWARD, false);
//		Wait.waitForSeconds(1);
//		GyroSensor.turnToAngle(-80, 100);
//		GyroSensor.gyroFollowerUntilBlack(15, -80, 200, 1, ColorSensorID.LEFT, Direction.FORWARD, false);
//		GyroSensor.turnToAngle(90, 200);
//		Chassis.tankDriveMillis(400, 400, Chassis.distToDeg(20), Direction.BACKWARD, false);
		
	}
}
