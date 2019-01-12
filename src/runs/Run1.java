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
		GyroSensor.resetGyro();
		GyroSensor.gyroFollower(30, 0, 500, Chassis.distToDeg(44), Direction.FORWARD, false);
		
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		RobotStructure.getInstance().armMotorLeftReg.resetTachoCount();
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.backward();		
		Wait.waitForSeconds(2);
		RobotStructure.getInstance().armMotorLeftReg.flt();
		
		GyroSensor.gyroFollower(30, 0, 500, Chassis.distToDeg(20), Direction.FORWARD, false);
		GyroSensor.turnToAngle(120, 600);
		
		if(!RunHandler.getCurrentRun().isActive()) {
			return;
		}
		
		Chassis.drive(Direction.FORWARD);
		Wait.waitForSeconds(5);
	}

}
