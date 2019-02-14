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
		Chassis.tankDrive(400, 450, Chassis.distToDeg(80));
//		GyroSensor.gyroFollower(30, 0, 200, Chassis.distToDeg(5), Direction.FORWARD, false);
		
		//Make sure that M02 and M03 were complete
		   
//		RobotStructure.getInstance().armMotorRightReg.flt();
//		RobotStructure.getInstance().rightMotorReg.setSpeed(550);
//		Chassis.drive(Direction.BACKWARD);
//		Wait.waitForSeconds(0.55);
//		Chassis.brake(false);
//		Wait.waitForSeconds(2);
//		RobotStructure.getInstance().armMotorRightReg.setSpeed(100);
//		RobotStructure.getInstance().armMotorRightReg.rotate(-180);
//		Chassis.drive(Direction.BACKWARD);
//		Wait.waitForSeconds(0.05);
//		Chassis.brake(false);
//		RobotStructure.getInstance().armMotorRightReg.rotate(180);
//		Wait.waitForSeconds(1);
//		RobotStructure.getInstance().armMotorRightReg.flt();
//		GyroSensor.gyroFollower(-30, 0, 500, Chassis.distToDeg(80), Direction.BACKWARD, false);
	}

}
