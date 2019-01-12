package robotUtils;

import lejos.hardware.lcd.LCD;
import lejos.robotics.RegulatedMotor;

/**
 * Abstraction for a robot run. Starts both itself and a stopper thread which
 * interrupts the run if the escape button is pressed.
 * 
 * @author John & Wifi
 */
public abstract class RobotRun extends Thread {
	
	protected boolean active = false;
	
	/** 
	 * Run method which is called by the Thread's "start"
	 */
	@Override
	public void run() {
		
		this.active = true;
		
		LCD.clear();
		
		//set current run
		RunHandler.setCurrentRun(this);
		
		//create a stopper for this run
		new RunStopper().start();
		
		//start chassis synchronization
		//TODO: input your motors here
     	RobotStructure.getInstance().leftMotorReg.synchronizeWith(new RegulatedMotor[] {RobotStructure.getInstance().rightMotorReg});
		 
		//run the implemented contents method
		runInstructions();
	}
	
	/**
	 * The contents of the run which should be implemented on a per run basis.
	 * Note that the instruction should be able to handle interrupts and
	 * stop the run accordingly.
	 */
	public abstract void runInstructions();
	
	public boolean isActive() {
		return this.active;
	}
	
	public void deactivate(){
		this.active = false;
	}
	
}
