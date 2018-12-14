package robotUtils;

import lejos.robotics.RegulatedMotor;

/**
 * Abstraction for a robot run. Starts both itself and a stopper thread which
 * interrupts the run if the escape button is pressed.
 * 
 * @author John & Wifi
 */
public abstract class RobotRun extends Thread {
	
	/** 
	 * Run method which is called by the Thread's "start"
	 */
	@Override
	public void run() {
		
		//create a stopper for this run
		 new RunStopper(this).start();
		
		 //start chassis synchronization
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
	
}
