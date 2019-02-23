package robotUtils;

import lejos.hardware.Button;
import util.Chassis;

/**
 * A thread that will stop a given run when the escape button is pressed
 * 
 * @author John & PetroBlitz #841
 */
public class RunStopper extends Thread {

	/**
	 * The run that this stopper will stop
	 */
	private RobotRun target;
	
	/**
	 * Constructor for a run stopper
	 * @param run Target run for this stopper
	 */
	public RunStopper() {
		this.target = RunHandler.getCurrentRun();
	}
	
	/**
	 * Run method for this thread, stops if the target thread isn't running or after it interrupts it.
	 * If the interrupt fails for any reason it will retry 500ms later and the movement is interrupted
	 * anyway.
	 */
	@Override
	public void run() {
		//Loops as long as target is running
		while(target.isActive()) {
			//When escape is pressed, interrupt target and stop motors immediately
			if(Button.getButtons() == Button.ID_ESCAPE) {
				while(Button.getButtons() == Button.ID_ESCAPE);
				try {
					target.deactivate();
					Thread.sleep(200);
					if (RobotStructure.getInstance() != null)
						Chassis.floatAllMotors();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
