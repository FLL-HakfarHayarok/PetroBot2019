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
		GyroSensor.gyroFollower(30, 0, 500, Chassis.distToDeg(70), Direction.FORWARD, false);
		GyroSensor.gyroFollower(30, 0, 200, Chassis.distToDeg(10), Direction.FORWARD, false);
		
		//Make sure that M02 and M03 were complete
		RobotStructure.getInstance().leftMotorReg.setSpeed(800);
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().leftMotorReg.forward();
		RobotStructure.getInstance().armMotorLeftReg.forward(); 
		Wait.waitForSeconds(1.5);
		RobotStructure.getInstance().armMotorLeftReg.flt();
		RobotStructure.getInstance().rightMotorReg.setSpeed(550);
		Chassis.drive(Direction.BACKWARD);
		Wait.waitForSeconds(0.55);
		Chassis.brake(false);
		Wait.waitForSeconds(2);
//		RobotStructure.getInstance().armMotorRightReg.setSpeed(100);
//		RobotStructure.getInstance().armMotorRightReg.rotate(-180);
//		Chassis.drive(Direction.BACKWARD);
//		Wait.waitForSeconds(0.05);
//		Chassis.brake(false);
//		RobotStructure.getInstance().armMotorRightReg.rotate(180);
//		Wait.waitForSeconds(1);
//		RobotStructure.getInstance().armMotorRightReg.flt();
		GyroSensor.gyroFollower(-30, 0, 500, Chassis.distToDeg(80), Direction.BACKWARD, false);
	}

}
