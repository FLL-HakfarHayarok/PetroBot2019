package util;

import lejos.hardware.lcd.LCD;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;

/**
 * A bunch of functions related to the gyro sensor
 * 
 * @author PetroBlitz #841
 */

public class GyroSensor {

	/**
	 * Gets the current gyro sensor angle using an average of the last 5 angles
	 * 
	 * @return the angle value in int form
	 */
	public static int getCurrentAngle() {
			float[] angleValues = new float[5];
			int sum = 0;
			for (int i = 0; i < angleValues.length; i++) {
				RobotStructure.getInstance().gyroAngleSampler.fetchSample(angleValues, i);
				sum += angleValues[i];
			}
			return sum/5;
	}
	
	/**
	 * This function resets the gyro sensor
	 */
	public static void resetGyro() {
		RobotStructure.getInstance().gyro.reset();
	}
	
	/**
	 * A basic init function used in all gyro followers
	 * Sets the motor speeds and directions
	 * 
	 * @param power The power values passed to each motor
	 * @param dir The direction the motors should move
	 */
	public static void initGyroFollower(int power, Direction dir) {
		RobotStructure.getInstance().rightMotorReg.resetTachoCount();
		RobotStructure.getInstance().leftMotorReg.resetTachoCount();
		
		RobotStructure.getInstance().rightMotorReg.setSpeed(power);
		RobotStructure.getInstance().leftMotorReg.setSpeed(power);
		
		if(dir == Direction.FORWARD) {
			RobotStructure.getInstance().rightMotorReg.forward();
			RobotStructure.getInstance().leftMotorReg.forward();
		}
		else {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.backward();
		}
	}
		
	/**
	 * A gyro follower using the PID tuning algorithm.
	 * In this case, we use the P portion of PID, as we found it the most optimal for usage in the FLL
	 * The algorithm uses the following formula: Kp*E(t)+P0
	 * E(t) = The difference between the target angle and the current angle
	 * The robot will travel until we reach a distance
	 * 
	 * @param kP A multiplier to the value of E(t)
	 * @param targetAngle The angle that we want to reach/stay at
	 * @param P0 A base power value
	 * @param degrees The degrees that the robot will travel
	 * @param direction The direction the robot will travel 
	 * @param brake Whether the robot will brake or coast
	 */
	public static void gyroFollower(double kP, int targetAngle, int P0, int degrees, Direction direction, boolean brake){
		initGyroFollower(P0, direction);
		LCD.clear();
		//Wait until a distance is traveled
		while(Math.abs(Chassis.getDistance()) < degrees && RunHandler.getCurrentRun().isActive()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) ((kP*(targetAngle-getCurrentAngle()))+P0));
		}
		Chassis.brake(brake);
	}
	
	
	/**
	 * A gyro follower using the PID tuning algorithm.
	 * In this case, we use the P portion of PID, as we found it the most optimal for usage in the FLL
	 * The algorithm uses the following formula: Kp*E(t)+P0
	 * E(t) = The difference between the target angle and the current angle
	 * The robot will travel until a sensor sees black
	 * 
	 * @param kP A multiplier to the value of E(t)
	 * @param targetAngle The angle that we want to reach/stay at
	 * @param P0 A base power value
	 * @param direction The direction the robot will travel 
	 * @param sensorID The sensor we want to wait for to see black
	 * @param brake Whether the robot will brake or coast
	 */
	public static void gyroUntilBlack(double kP, int targetAngle, int P0, Direction direction, ColorSensorID sensorID, boolean brake){
		
		initGyroFollower(P0, direction);
		//Wait until the sensor sees black
		while(!ColorSensor.isBlack(sensorID) && RunHandler.getCurrentRun().isActive()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) ((kP*(targetAngle-getCurrentAngle()))+P0));
		}
		
		Chassis.brake(brake);
	}
	
	/**
	 * Turns the robot until it reaches a certain angle
	 * The direction that the robot turns at is based on whether the angle is positive or negative
	 * 
	 * @param angle The angle we need to reach
	 * @param power The power the motors should move at
	 */
	public static void turnToAngle(double angle, int power) {
		
		RobotStructure.getInstance().rightMotorReg.setSpeed(power);
		RobotStructure.getInstance().leftMotorReg.setSpeed(power);
		
		if (angle > 0) {
			RobotStructure.getInstance().rightMotorReg.forward();
			RobotStructure.getInstance().leftMotorReg.backward();
		}
		else {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.forward();
		}

		//Waits until the angle is reached
		while(Math.abs(getCurrentAngle()) < Math.abs(angle) && RunHandler.getCurrentRun().isActive()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
		}
		
		//Stops the wheels
		Chassis.brake(true);
	}
}