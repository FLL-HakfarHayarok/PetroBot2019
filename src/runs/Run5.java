package runs;

import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;
import util.Chassis;
import util.Direction;
import util.GyroSensor;

public class Run5 extends RobotRun {

	@Override
	public void runInstructions() {
		GyroSensor.resetGyro();
		GyroSensor.gyroFollowerDegrees(15, 0, 400, Chassis.distToDeg(65), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-45, 100);
		GyroSensor.gyroFollowerDegrees(15, 45, 400, Chassis.distToDeg(55), Direction.FORWARD, false);

		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		RobotStructure.getInstance().rightMotorReg.setSpeed(100);
		RobotStructure.getInstance().leftMotorReg.setSpeed(100);

		while (GyroSensor.getCurrentAngle() < -15 && RunHandler.getCurrentRun().isActive()) {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.forward();
		}
		
		RobotStructure.getInstance().rightMotorReg.flt();
		RobotStructure.getInstance().leftMotorReg.flt();
		
		//Check if the run was stopped
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		GyroSensor.gyroFollowerDegrees(15, 0, 400, Chassis.distToDeg(65), Direction.FORWARD, false);
		GyroSensor.turnToAngle(-45, 100);
		GyroSensor.gyroFollowerDegrees(15, 45, 400, Chassis.distToDeg(35), Direction.FORWARD, false);
	}

}
