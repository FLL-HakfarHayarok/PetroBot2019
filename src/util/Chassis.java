package util;

import robotUtils.RobotStructure;

/**
 * A class full of functions related to the movement of the robot 
 * 
 * @author PetroBlitz #841
 */

public class Chassis {
	private static final double K_perimeter  = 9.42*Math.PI; //The perimeter of the wheels
	private static final double K_distance = 11.5; //The distance between the middle of the wheels
	
	/**
	 * A function that turns a distance value in cm to degrees
	 * 
	 * @param dist The distance in centimeter
	 * @return The distance in motor degrees
	 */
	public static int distToDeg(double dist) {
		return (int) (dist/(K_perimeter))*360;
	}
	
	/**
	 * Stops all robot motors
	 */
	public static void stopAllMotors() {
		RobotStructure.leftMotorReg.stop(true);
		RobotStructure.rightMotorReg.stop(true);
		RobotStructure.armMotorLeftReg.stop(true);
		RobotStructure.armMotorRightReg.stop();	
	}
	
	/**
	 * Stops sending all robot motors power
	 */
	public static void floatAllMotors() {
		RobotStructure.leftMotorReg.flt(true);
		RobotStructure.rightMotorReg.flt(true);
		RobotStructure.armMotorLeftReg.flt(true);
		RobotStructure.armMotorRightReg.flt();
	}
	
	/**
	 * This function stops the wheels 
	 * 
	 * @param brake Whether the robot should stop or just stop sending power to the motors
	 */
	public static void brake(boolean brake) {		
		if(brake) {
		RobotStructure.leftMotorReg.startSynchronization();
			RobotStructure.leftMotorReg.stop();
			RobotStructure.rightMotorReg.stop();
		RobotStructure.leftMotorReg.endSynchronization();
		}
		else {
			RobotStructure.leftMotorReg.startSynchronization();
				RobotStructure.leftMotorReg.flt(true);
				RobotStructure.rightMotorReg.flt();
			RobotStructure.leftMotorReg.endSynchronization();
		}
	}
	
	/**
	 * Returns the average degrees moved by the robot
	 */
	public static int getDistance() {
		return (RobotStructure.leftMotorReg.getTachoCount() + RobotStructure.rightMotorReg.getTachoCount())/2;
	}
	
	/**
	 * A curve drive function, that moves the robot in a part of a circle.
	 * We create two imaginary circles and find the circle the robot will travel using an average of both circles.
	 * This is done by calculating the ratio between both circles (and thus their perimeter and power values)
	 * The distance traveled is calculated using the angle divided by 360 and then multiplied by the perimeter of the big circle
	 * Basically, the distance is the portion of the circle we want to drive 
	 * 
	 * @param radius The radius of the big circle
	 * @param angle The angle of circle that we want to travel (90 degrees would be a quarter of a circle)
	 * @param maxPower The max power output (used by the outer motor)
	 * @param side The side that we want to turn to
	 * @param brake Whether the robot should brake or coast at the end
	 */
	public static void curveDrive(double radius, double angle, int maxPower, Side side, boolean brake) {
		//Resetting the wheel encoders 
		RobotStructure.leftMotorReg.resetTachoCount();
		RobotStructure.rightMotorReg.resetTachoCount();
		
		double ratio = (radius-K_distance)/radius; //Calculating ratio between circles
		double bigPerimeter = 2*radius*Math.PI; //Calculating the perimeter of the big circle
		double dist = distToDeg((bigPerimeter/360)*angle); //The distance that we will travel in the circle
		if (side == Side.RIGHT) {
			RobotStructure.leftMotorReg.setSpeed(maxPower);
			//Multiplying the power of the main motor by the ratio to get the power for the inner motor 
			RobotStructure.rightMotorReg.setSpeed((int) (maxPower*ratio)); 
			RobotStructure.leftMotorReg.forward();
			RobotStructure.rightMotorReg.forward();
			//Waiting until the distance was traveled and confirming using the gyro
			while(RobotStructure.leftMotorReg.getTachoCount() < dist && !Thread.currentThread().isInterrupted() && GyroSensor.getCurrentAngle() < angle) {}
		}
		else {
			RobotStructure.rightMotorReg.setSpeed(maxPower);
			//Multiplying the power of the main motor by the ratio to get the power for the inner motor
			RobotStructure.leftMotorReg.setSpeed((int) (maxPower*ratio));  
			RobotStructure.rightMotorReg.forward();
			RobotStructure.leftMotorReg.forward();
			//Waiting until the distance was traveled and confirming using the gyro
			while(RobotStructure.rightMotorReg.getTachoCount() < dist && !Thread.currentThread().isInterrupted() && GyroSensor.getCurrentAngle() < angle) {}
		}
		brake(brake);
	}
}