package util;

import lejos.hardware.lcd.LCD;
import robotUtils.RobotStructure;

public class GyroSensor {

	/**
	 * Gets the current gyro sensor angle
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
	
	public static void resetGyro() {
		RobotStructure.getInstance().gyro.reset();
	}
	
	private static void initGyroFollower(int power, Direction dir) {
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
		
	public static void gyroFollower(double kP, int targetAngle, int P0, int degrees, Direction direction, boolean brake){
		initGyroFollower(P0, direction);
		LCD.clear();
		while(Chassis.getDistance() < degrees && !Thread.currentThread().isInterrupted()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) ((kP*(targetAngle+getCurrentAngle()))+P0));
		}
		Chassis.brake(brake);
	}
	
	
	
	public static void gyroUntilBlack(double kP, int targetAngle, int P0, Direction direction, ColorSensorID sensorID, boolean brake){
		
		initGyroFollower(P0, direction);
		
		while(!ColorSensor.isBlack(sensorID) && !Thread.currentThread().isInterrupted()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) ((kP*(targetAngle+getCurrentAngle()))+P0));
		}
		
		Chassis.brake(brake);
	}
	
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
		
		while(Math.abs(getCurrentAngle()) < Math.abs(angle) && !Thread.currentThread().isInterrupted()) {
			LCD.drawInt(getCurrentAngle(), 0, 0);
		}
		Chassis.brake(true);
	}
}