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
		GyroSensor.gyroFollower(50, -6, 500, Chassis.distToDeg(80), Direction.FORWARD, false);

		RobotStructure.getInstance().rightMotorReg.setSpeed(800);
		RobotStructure.getInstance().rightMotorReg.forward();
		Wait.waitForSeconds(1);
		
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.rotate(130);
		
		RobotStructure.getInstance().armMotorRightReg.setSpeed(150);
		RobotStructure.getInstance().armMotorRightReg.backward();
		Wait.waitForSeconds(3);
		RobotStructure.getInstance().armMotorRightReg.flt();

		GyroSensor.gyroFollower(-50, -2, 400, Chassis.distToDeg(90), Direction.BACKWARD, false);
		Wait.waitForSeconds(5);
	}

}
