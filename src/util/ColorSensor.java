package util;

import java.util.MissingResourceException;

import lejos.hardware.Button;
import lejos.robotics.Color;
import lejos.utility.Delay;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;

public class ColorSensor {

	// LEFT, CENTER, RIGHT
	public static double[] blacks, whites = new double[3];

	/**
	 * Calibrates the color sensors
	 */
	public static void calibrateColor() {

		// Place robot on a black surface
		Button.waitForAnyPress();

		// Measure the brightness

		float[] sampleDump = new float[10];
		double sum = 0;

		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorLeftRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		blacks[0] = sum / 10;
		Delay.msDelay(100);

		sum = 0;//

		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorCenterRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		blacks[1] = sum / 10;
		Delay.msDelay(100);

		sum = 0;//

		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorRightRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		blacks[2] = sum / 10;
		Delay.msDelay(100);

		sum = 0;//

		// Place on a white surface

		Button.waitForAnyPress();

		// Measure the brightness again
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorLeftRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		whites[0] = sum / 10;
		Delay.msDelay(100);

		sum = 0;//

		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorCenterRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		whites[1] = sum / 10;
		Delay.msDelay(100);

		sum = 0;//

		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorRightRedSampler.fetchSample(sampleDump, i);
			sum += sampleDump[i];
		}
		whites[2] = sum / 10;
		Delay.msDelay(100);

		// finish calibration
		Button.waitForAnyPress();
	}

	/**
	 * Returns the brightness measured by the robot DEPRECATED
	 * 
	 * @param sensorID The sensor we want to check
	 * @return The brightness on a scale of 0-100
	 */
	public static double getBrightness(ColorSensorID sensorID) {
		switch (sensorID) {
		case LEFT:
			return (blacks[0] + whites[0]) / 2;
		case CENTER:
			return (blacks[1] + whites[1]) / 2;
		case RIGHT:
			return (blacks[2] + whites[2]) / 2;
		default:
			return 50;

		}
	}

	/**
	 * A gyro follower using the PID tuning algorithm. In this case, we use the P
	 * portion of PID, as we found it the most optimal for usage in the FLL The
	 * algorithm uses the following formula: Kp*E(t)+P0 E(t) = The difference
	 * between 50 and the brightness measureby the sensor The robot will travel
	 * until we reach a distance
	 *
	 * @param kP
	 * @param P0
	 * @param degrees
	 * @param direction
	 * @param sensorID
	 */
	public static void lineFollower(double kP, int P0, int degrees, Direction direction, Side blackSide, ColorSensorID sensorID) {

		GyroSensor.initGyroFollower(P0, direction);

		// Wait until a distance is traveled
		if (blackSide == Side.LEFT) {
			while (Chassis.getDistance() < degrees && RunHandler.getCurrentRun().isActive()) {
				RobotStructure.getInstance().rightMotorReg.setSpeed((int) (kP * (50 - getBrightness(sensorID)) + P0) * 8);
			}
		} else {
			while (Chassis.getDistance() < degrees && RunHandler.getCurrentRun().isActive()) {
				RobotStructure.getInstance().leftMotorReg.setSpeed((int) (kP * (50 - getBrightness(sensorID)) + P0) * 8);
			}
		}
	}

	/**
	 * This function return whether the color seen is black
	 * 
	 * @param sensor The sensor we want to check
	 * @return Whether the color seen by the sensor is black or not
	 */
	public static boolean isBlack(ColorSensorID sensor) {

		float[] dump = new float[1];

		switch (sensor) {
		case CENTER:
			RobotStructure.getInstance().colorCenterIDSampler.fetchSample(dump, 0);
			return (dump[0] == Color.BLACK);
		case LEFT:
			RobotStructure.getInstance().colorLeftIDSampler.fetchSample(dump, 0);
			return (dump[0] == Color.BLACK);
		case RIGHT:
			RobotStructure.getInstance().colorRightIDSampler.fetchSample(dump, 0);
			return (dump[0] == Color.BLACK);
		default:
			throw new MissingResourceException("Sensor does not exist!", "Color Sensor ID", "getColor()");
		}
	}
	
	/**
	 * Returns the color a color sensor sees
	 * 
	 * @param sensor The sensor to check
	 * @return The color seen
	 */
	public static Colors getColor(ColorSensorID sensor) {
		float[] dump = new float[5];
		switch (sensor) {
		case LEFT:
			RobotStructure.getInstance().colorLeftIDSampler.fetchSample(dump, 0);
			break;
		case RIGHT:
			RobotStructure.getInstance().colorRightIDSampler.fetchSample(dump, 0);
			break;
		case CENTER:
			RobotStructure.getInstance().colorCenterIDSampler.fetchSample(dump, 0);
			break;
		default:
			throw new MissingResourceException("Sensor does not exist!", "Color Sensor ID", "getColor()");
		}

		if (dump[0] == Color.BLACK)
			return Colors.BLACK;
		else if (dump[0] == Color.RED)
			return Colors.RED;
		else if (dump[0] == Color.GREEN)
			return Colors.GREEN;
		else if (dump[0] == Color.BLUE)
			return Colors.BLUE;
		else if (dump[0] == Color.CYAN)
			return Colors.CYAN;
		else if (dump[0] == Color.WHITE)
			return Colors.WHITE;
		else if (dump[0] == Color.PINK)
			return Colors.PINK;
		else if (dump[0] == Color.YELLOW)
			return Colors.YELLOW;
		else
			return null;

	}
}
