package robotUtils;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

/**
 * This is a class which maps out the hardware connected to the robot for use by
 * other classes. All port and hardware changes should be altered in this class
 * and any calls to motors or sensors should directly reference this class.
 * 
 * @author John
 */
public class RobotStructure {

	private static RobotStructure instance;
	
	public static void init() {
		if (instance == null)
			instance = new RobotStructure();
	}
	
	public static RobotStructure getInstance() {
		if (instance == null)
			init();
		return instance;
	}
	
	public final EV3LargeRegulatedMotor leftMotorReg, rightMotorReg;
	public final EV3MediumRegulatedMotor armMotorLeftReg, armMotorRightReg;
	public final EV3GyroSensor gyro;
	public final EV3ColorSensor colorLeft, colorRight, colorCenter;
	public final SampleProvider gyroAngleSampler, colorLeftIDSampler, colorRightIDSampler, colorCenterIDSampler, colorLeftRedSampler, colorRightRedSampler, colorCenterRedSampler;
	
	public RobotStructure() {
		
		leftMotorReg = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotorReg = new EV3LargeRegulatedMotor(MotorPort.C);
		armMotorLeftReg = new EV3MediumRegulatedMotor(MotorPort.A);
		armMotorRightReg = new EV3MediumRegulatedMotor(MotorPort.D);
		
		gyro = new EV3GyroSensor(SensorPort.S3);
		colorLeft = new EV3ColorSensor(SensorPort.S2);
		colorRight = new EV3ColorSensor(SensorPort.S4);
		colorCenter = new EV3ColorSensor(SensorPort.S1);
		
		gyroAngleSampler = gyro.getAngleMode();
		
		colorLeftRedSampler = colorLeft.getRedMode();
		colorRightRedSampler = colorRight.getRedMode();
		colorCenterRedSampler = colorCenter.getRedMode();
		
		colorLeftIDSampler = colorLeft.getColorIDMode();
		colorRightIDSampler = colorRight.getColorIDMode();
		colorCenterIDSampler = colorCenter.getColorIDMode();
		
		
		
	}
	
	/**
	 * Stops all robot motors
	 */
	public void stopAllMotors() {
		leftMotorReg.stop(true);
		rightMotorReg.stop(true);
		armMotorLeftReg.stop(true);
		armMotorRightReg.stop();	
	}
	
	public void floatAllMotors() {
		leftMotorReg.flt(true);
		rightMotorReg.flt(true);
		armMotorLeftReg.flt(true);
		armMotorRightReg.flt(true);
	}
	
	public void brake(boolean brake) {		
		if(brake) {
		RobotStructure.getInstance().leftMotorReg.startSynchronization();
			leftMotorReg.stop();
			rightMotorReg.stop();
		RobotStructure.getInstance().leftMotorReg.endSynchronization();
		}
		else {
			leftMotorReg.flt(true);
			rightMotorReg.flt();
		}
	}
	
	/**
	 * Returns the average degrees moved by the robot
	 */
	public int getDistance() {
		return (leftMotorReg.getTachoCount() + rightMotorReg.getTachoCount())/2;
	}
}
