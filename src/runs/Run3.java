package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.ColorSensor;
import util.ColorSensorID;
import util.Direction;
import util.GyroSensor;
import util.Wait;

public class Run3 extends RobotRun {

	int linesPassed = 0;
	
	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(-15, 0, 400, Chassis.distToDeg(120), Direction.BACKWARD, false);
		Wait.waitForSeconds(0.5);
		//Drives forward until HABILITATION PLANT mission
		GyroSensor.turnToAngle(-45, 150);	//Turns 45 degrees towards FOOD GROWTH mission
		RobotStructure.getInstance().leftMotorReg.setSpeed(300);
		RobotStructure.getInstance().rightMotorReg.setSpeed(300);
		Chassis.drive(Direction.BACKWARD);	//Starts driving towards FOOD GROWTH mission
		while(linesPassed < 2 && RunHandler.getCurrentRun().isActive()) {
			if (ColorSensor.isBlack(ColorSensorID.CENTER)) {
				linesPassed++;
			}
		}	//Waits until middle color sensor sees black
		GyroSensor.gyroFollower(-30, GyroSensor.getCurrentAngle(), 300, Chassis.distToDeg(9), Direction.BACKWARD, false);	//Drives back from FOOD GROWTH in order to commit curve drive completing OBSERVATORY
		GyroSensor.turnToAngle(-85, 150);	//Turns perpendicularly to FOOD GROWTH mission
		RobotStructure.getInstance().leftMotorReg.setSpeed(150);
		RobotStructure.getInstance().rightMotorReg.setSpeed(150);
		Chassis.drive(Direction.BACKWARD);
		//Wait.waitForSeconds(2);
		//GyroSensor.gyroFollower(-30, -90, 300, Chassis.distToDeg(5), Direction.FORWARD, false);	//Drives back from FOOD GROWTH in order to commit curve drive completing OBSERVATORY
		
		
	}
}
