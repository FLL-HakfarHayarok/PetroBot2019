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
	public final EV3ColorSensor colorLeft, colorRight;
	public final SampleProvider gyroAngleSampler, color1RedSampler;
	
	public RobotStructure() {
		
		leftMotorReg = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotorReg = new EV3LargeRegulatedMotor(MotorPort.C);
		armMotorLeftReg = new EV3MediumRegulatedMotor(MotorPort.A);
		armMotorRightReg = new EV3MediumRegulatedMotor(MotorPort.D);
		
		gyro = new EV3GyroSensor(SensorPort.S2);
		colorLeft = new EV3ColorSensor(SensorPort.S3);
		colorRight = new EV3ColorSensor(SensorPort.S1);
		
		//TODO: Create proper samples
		gyroAngleSampler = gyro.getAngleMode();
		color1RedSampler = colorLeft.getRedMode();
		
	}
	
	/**
	 * Stops all robot motors
	 */
	public void stopAllMotors() {
		RobotStructure.getInstance().leftMotorReg.stop(true);
		RobotStructure.getInstance().rightMotorReg.stop(true);
		RobotStructure.getInstance().armMotorLeftReg.stop(true);
		RobotStructure.getInstance().armMotorRightReg.stop(true);	
	}
	
}
