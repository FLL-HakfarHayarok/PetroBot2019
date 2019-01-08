package util;

import robotUtils.RobotStructure;

public class Chassis {
	private static final double K_perimiter  = 9.42*Math.PI;
	private static final double K_distance = 11.5;
	
	/**
	 * Stops all robot motors
	 */
	public static void stopAllMotors() {
		RobotStructure.leftMotorReg.stop(true);
		RobotStructure.rightMotorReg.stop(true);
		RobotStructure.armMotorLeftReg.stop(true);
		RobotStructure.armMotorRightReg.stop();	
	}
	
	public static void floatAllMotors() {
		RobotStructure.leftMotorReg.flt(true);
		RobotStructure.rightMotorReg.flt(true);
		RobotStructure.armMotorLeftReg.flt(true);
		RobotStructure.armMotorRightReg.flt();
	}
	
	public static void brake(boolean brake) {		
		if(brake) {
		RobotStructure.getInstance().leftMotorReg.startSynchronization();
			RobotStructure.leftMotorReg.stop();
			RobotStructure.rightMotorReg.stop();
		RobotStructure.getInstance().leftMotorReg.endSynchronization();
		}
		else {
			RobotStructure.leftMotorReg.flt(true);
			RobotStructure.rightMotorReg.flt();
		}
	}
	
	/**
	 * Returns the average degrees moved by the robot
	 */
	public static int getDistance() {
		return (RobotStructure.leftMotorReg.getTachoCount() + RobotStructure.rightMotorReg.getTachoCount())/2;
	}
	
}
