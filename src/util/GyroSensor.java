package util;

import lejos.hardware.lcd.LCD;
import lejos.utility.Stopwatch;
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
	
	/**
	 * Alias for standard forward driving
	 */
	public static void driveForward(double Kp, int speed, int duration, boolean brake) {
		followAngle(Direction.FORWARD, Kp, speed, duration, getCurrentAngle(), brake, 0);
	}
	
	/**
	 * Alias for standard backward driving
	 */
	public static void driveBackward(double Kp, int speed, int duration, boolean brake) {
		followAngle(Direction.BACKWARD, Kp, speed, duration, getCurrentAngle(), brake, 0);
	}
	
	/**
	 * Alias for standard zero driving
	 */
	public static void driveForwardZero(double Kp, int speed, int duration, boolean brake) {
		followAngle(Direction.FORWARD, Kp, speed, duration, 0, brake, 0);
	}
	
	/**
	 * Alias for reverse zero driving
	 */
	public static void driveBackwardZero(double Kp, int speed, int duration, boolean brake) {
		followAngle(Direction.BACKWARD, Kp, speed, duration, 0, brake, 0);
	}
	
	/**
	 * Follows a target angle and corrects for any offset using proportional control
	 * @param d Driving direction
	 * @param Kp Proportional constant
	 * @param speed Target motor speed
	 * @param duration Degrees to drive
	 * @param targetAngle Angle value to follow
	 * @param brake Brake or coast at end
	 * @param msTimeout Timeout in milliseconds for move. Leave 0 to exclude
	 */
	public static void followAngle(Direction d, double Kp, int speed, int duration, int targetAngle, boolean brake, int msTimeout) {

		// reset motor counters
		RobotStructure.getInstance().leftMotorReg.resetTachoCount();
		RobotStructure.getInstance().rightMotorReg.resetTachoCount();

		//init timer if needed
		Stopwatch timer = new Stopwatch();
		timer.reset();
		
		//error var
		int error = 0;

		// start motors
		RobotStructure.getInstance().leftMotorReg.setSpeed(speed);
		RobotStructure.getInstance().rightMotorReg.setSpeed(speed);
		if (d == Direction.FORWARD) {
			RobotStructure.getInstance().leftMotorReg.forward();
			RobotStructure.getInstance().rightMotorReg.forward();
		} else {
			RobotStructure.getInstance().leftMotorReg.backward();
			RobotStructure.getInstance().rightMotorReg.backward();
		}

		// init loop
		boolean active = true;
		while (active && !Thread.currentThread().isInterrupted()) {

			// calculate error
			error = (getCurrentAngle() - targetAngle);

			LCD.drawInt(error, 0, 0);
			LCD.drawInt(getCurrentAngle(), 5, 0);
			
			int powerOutput = (int) (Kp * error);
			
			// set speed accordingly
			if (d == Direction.FORWARD) {
				RobotStructure.getInstance().leftMotorReg.setSpeed(speed - (powerOutput/2));
				RobotStructure.getInstance().rightMotorReg.setSpeed(speed + (powerOutput/2));
			} else {
				RobotStructure.getInstance().leftMotorReg.setSpeed(speed + (powerOutput/2));
				RobotStructure.getInstance().rightMotorReg.setSpeed(speed - (powerOutput/2));
			}

			// stop loop
			if (Math.abs(RobotStructure.getInstance().leftMotorReg.getTachoCount() + RobotStructure.getInstance().rightMotorReg.getTachoCount()) / 2 > duration) {
				active = false;
			}
			//timeout
			if(msTimeout != 0 && timer.elapsed() > msTimeout) {
				active = false;
			}

		}

		// brake if needed
		if (brake) {
			RobotStructure.getInstance().leftMotorReg.stop();
			RobotStructure.getInstance().rightMotorReg.stop();
		} else {
			RobotStructure.getInstance().leftMotorReg.flt();
			RobotStructure.getInstance().rightMotorReg.flt();
		}

	}
	
	public static void binaryFollower(int duration) {
		// reset motor counters
			RobotStructure.getInstance().leftMotorReg.resetTachoCount();
			RobotStructure.getInstance().rightMotorReg.resetTachoCount();

	//init motors
			RobotStructure.getInstance().leftMotorReg.setSpeed(500);
			RobotStructure.getInstance().rightMotorReg.setSpeed(500);
			RobotStructure.getInstance().leftMotorReg.forward();
			RobotStructure.getInstance().rightMotorReg.forward();
			
			while(((RobotStructure.getInstance().leftMotorReg.getTachoCount() + RobotStructure.getInstance().rightMotorReg.getTachoCount() )/ 2 <  duration) && !Thread.currentThread().isInterrupted()){
				if(getCurrentAngle() < 0) {
					RobotStructure.getInstance().leftMotorReg.setSpeed(550);
					RobotStructure.getInstance().rightMotorReg.setSpeed(500);
				}
				else if (getCurrentAngle() > 0) {
					RobotStructure.getInstance().leftMotorReg.setSpeed(500);
					RobotStructure.getInstance().rightMotorReg.setSpeed(550);
				}
				else {
					RobotStructure.getInstance().leftMotorReg.setSpeed(500);
					RobotStructure.getInstance().rightMotorReg.setSpeed(500);
				}
			}
			
			RobotStructure.getInstance().leftMotorReg.stop(true);
			RobotStructure.getInstance().rightMotorReg.stop(true);
		
	}
	
	public static void threadTest() {
		
		RobotStructure.getInstance().armMotorLeftReg.forward();
		while(!Thread.currentThread().isInterrupted());
		
	}

}