package util;

import java.util.MissingResourceException;

import lejos.hardware.Button;
import lejos.robotics.Color;
import lejos.utility.Delay;
import robotUtils.RobotStructure;

public class ColorSensor {
	
	//LEFT, CENTER, RIGHT
	public static double[] blacks, whites = new double[3];
			
	public static void calibrateColor() {
		
		//place robot on black
		Button.waitForAnyPress();
		
		//measure black
		
		float[] sampleDump = new float[10];
		double sum = 0;
		
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorLeftRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		blacks[0] = sum/10;
		Delay.msDelay(100);
		
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorCenterRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		blacks[1] = sum/10;
		Delay.msDelay(100);
		
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorRightRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		blacks[2] = sum/10;
		Delay.msDelay(100);
		
		//place on white
		
		Button.waitForAnyPress();
		
		//measure whites
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorLeftRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		whites[0] = sum/10;
		Delay.msDelay(100);
		
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorCenterRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		whites[1] = sum/10;
		Delay.msDelay(100);
		
		for (int i = 0; i < sampleDump.length; i++) {
			RobotStructure.getInstance().colorRightRedSampler.fetchSample(sampleDump, i);
			sum+=sampleDump[i];
		}
		whites[2] = sum/10;
		Delay.msDelay(100);
		
		//finish cal
		Button.waitForAnyPress();	
	}
	
	public static double getBrightness(ColorSensorID sensorID) {
		switch(sensorID) {
		case LEFT:
			return (blacks[0]+whites[0])/2;
		case CENTER:
			return (blacks[1]+whites[1])/2;
		case RIGHT:
			return (blacks[2]+whites[2])/2;
		default:
			return 50;
		}
	}
	
	public static void lineFollower(double kP, int P0, int degrees, Direction direction, ColorSensorID sensorID) {
		RobotStructure.getInstance().rightMotorReg.resetTachoCount();
		RobotStructure.getInstance().leftMotorReg.resetTachoCount();
		
		RobotStructure.getInstance().rightMotorReg.setSpeed(P0);
		RobotStructure.getInstance().leftMotorReg.setSpeed(P0);
				
		if(direction == Direction.FORWARD) {
			RobotStructure.getInstance().rightMotorReg.forward();
			RobotStructure.getInstance().leftMotorReg.forward();
		}
		else {
			RobotStructure.getInstance().rightMotorReg.backward();
			RobotStructure.getInstance().leftMotorReg.backward();
		}
		
		while(RobotStructure.getInstance().getDistance() < degrees && !Thread.currentThread().isInterrupted()) {
			RobotStructure.getInstance().rightMotorReg.setSpeed((int) (kP*(50-getBrightness(sensorID))+P0));
		}
	}
	
	public static boolean isBlack(ColorSensorID sensor) {
		
		float[] dump = new float[1];
		
		switch(sensor) {
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
			throw new MissingResourceException("wtf", "A run", "lul");		
		}
	}
}
