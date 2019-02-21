package util;

import robotUtils.RobotStructure;
import robotUtils.RunHandler;

/**
 * A class full of functions related to the movement of the robot 
 * 
 * @author PetroBlitz #841
 */

public class Chassis {
	private static final double K_perimeter  = 6.24*Math.PI; //The perimeter of the wheels
	private static final double K_ratio = 0.6;
	private static final double K_drive = K_perimeter * K_ratio;
	private static final double K_distance = 17.2; //The distance between the middle of the wheels
	
	/**
	 * A function that turns a distance value in centimeters to wheel revolutions in degrees
	 * 
	 * @param dist The distance in centimeter
	 * @return The distance in motor degrees
	 */
	public static int distToDeg(double dist) {
		return (int) (dist/K_perimeter*360);
	}
	
	/**
	 * Stops all robot motors
	 */
	public static void stopAllMotors() {
		RobotStructure.getInstance().leftMotorReg.stop(true);
		RobotStructure.getInstance().rightMotorReg.stop(true);
		RobotStructure.getInstance().armMotorLeftReg.stop(true);
		RobotStructure.getInstance().armMotorRightReg.stop();	
	}
	
	/**
	 * Stops sending all robot motors power
	 */
	public static void floatAllMotors() {
		RobotStructure.getInstance().leftMotorReg.flt(true);
		RobotStructure.getInstance().rightMotorReg.flt(true);
		RobotStructure.getInstance().armMotorLeftReg.flt(true);
		RobotStructure.getInstance().armMotorRightReg.flt();
	}
	
    public static void rotateLeftArm(int angle, double powerTransmissionRelation)
    {
		RobotStructure.getInstance().armMotorLeftReg.rotate((int) (angle * powerTransmissionRelation));
    }

    public static void rotateRightArm(int angle, double powerTransmissionRelation)
    {
		RobotStructure.getInstance().armMotorRightReg.rotate((int) (angle * powerTransmissionRelation));
    }

    public static void rotateLeftArm(int angle)
    {
		RobotStructure.getInstance().armMotorLeftReg.rotate((int) (angle));
    }

    public static void rotateRightArm(int angle)
    {
		RobotStructure.getInstance().armMotorRightReg.rotate((int) (angle));
    }

    
	/**
	 * This function stops the wheels 
	 * 
	 * @param brake whether the robot should stop or just stop sending power to the motors
	 */
	public static void brake(boolean brake) {		
		RobotStructure.getInstance().leftMotorReg.startSynchronization();
		if(brake) {
			RobotStructure.getInstance().leftMotorReg.stop();
			RobotStructure.getInstance().rightMotorReg.stop();
		}
		else {
			RobotStructure.getInstance().leftMotorReg.flt(true);
			RobotStructure.getInstance().rightMotorReg.flt();
		}
		RobotStructure.getInstance().leftMotorReg.endSynchronization();
	}
	
	/**
	 * This function drives
	 * 
	 * @param d direction to drive
	 */
	public static void drive(Direction d) {		
		RobotStructure.getInstance().leftMotorReg.startSynchronization();
		if(d == Direction.FORWARD) {
			RobotStructure.getInstance().leftMotorReg.forward();
			RobotStructure.getInstance().rightMotorReg.forward();
		}
		else if (d == Direction.BACKWARD){
			RobotStructure.getInstance().leftMotorReg.backward();
			RobotStructure.getInstance().rightMotorReg.backward();
		}
		RobotStructure.getInstance().leftMotorReg.endSynchronization();
	}
	
	public static void tankDrive(double leftSpeed, double rightSpeed, double revolutions) {
		RobotStructure.getInstance().leftMotorReg.setSpeed((int) leftSpeed);
		RobotStructure.getInstance().rightMotorReg.setSpeed((int) rightSpeed);
		
		RobotStructure.getInstance().leftMotorReg.resetTachoCount();
		RobotStructure.getInstance().rightMotorReg.resetTachoCount();
		
		drive(Direction.FORWARD);
		while(Math.abs(getDistance()) < revolutions);
		brake(false);
	}
	

	/**
	 * Returns the average degrees moved by the robot
	 */
	public static int getDistance() {
		return (Math.abs(RobotStructure.getInstance().leftMotorReg.getTachoCount() + RobotStructure.getInstance().rightMotorReg.getTachoCount()) / 2);
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
		RobotStructure.getInstance().leftMotorReg.resetTachoCount();
		RobotStructure.getInstance().rightMotorReg.resetTachoCount();
		
		double ratio = (radius-K_distance)/radius; //Calculating ratio between circles
		double bigPerimeter = 2*radius*Math.PI; //Calculating the perimeter of the big circle
		double dist = distToDeg((bigPerimeter/360)*angle); //The distance that we will travel in the circle
		if (side == Side.RIGHT) {
			RobotStructure.getInstance().leftMotorReg.setSpeed(maxPower);
			//Multiplying the power of the main motor by the ratio to get the power for the inner motor 
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) (maxPower*ratio)); 
			RobotStructure.getInstance().leftMotorReg.forward();
			RobotStructure.getInstance().rightMotorReg.forward();
			//Waiting until the distance was traveled and confirming using the gyro
			while(RobotStructure.getInstance().leftMotorReg.getTachoCount() < dist && RunHandler.getCurrentRun().isActive()) {}
		}
		else {
			RobotStructure.getInstance().rightMotorReg.setSpeed(maxPower);
			//Multiplying the power of the main motor by the ratio to get the power for the inner motor
			RobotStructure.getInstance().leftMotorReg.setSpeed((int) (maxPower*ratio));  
			RobotStructure.getInstance().rightMotorReg.forward();
			RobotStructure.getInstance().leftMotorReg.forward();
			//Waiting until the distance was traveled and confirming using the gyro
			while(RobotStructure.getInstance().rightMotorReg.getTachoCount() < dist && RunHandler.getCurrentRun().isActive()) {}
		}
		brake(brake);
	}
}