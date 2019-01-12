package runs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import robotUtils.RunHandler;

/**
 * A class that replicates the motor control function used in EV3
 * Has minimal UI, that displays whether the arms or wheels are being controlled
 * Switching between arms and wheels is done using the enter button {@link lejos.hardware.Button}  
 * 
 * @author PetroBlitz #841
 */

public class MotorControl extends RobotRun {

	public void runInstructions() {

		boolean controllingArms = false;
		int buttonValues;

		LCD.clear();
		LCD.drawString("wheels", 0, 0);

		//Setting the motor speed
		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);
		RobotStructure.getInstance().leftMotorReg.setSpeed(800);
		RobotStructure.getInstance().rightMotorReg.setSpeed(800);

		while (RunHandler.getCurrentRun().isActive()) { //Will work while the run has not been stopped

			buttonValues = Button.getButtons();

			// Check each possible situation, which buttons are pressed and acts accordingly
			if ((buttonValues & Button.ID_UP) == Button.ID_UP) {
				if (controllingArms)
					RobotStructure.getInstance().armMotorLeftReg.forward();
				else
					RobotStructure.getInstance().leftMotorReg.forward();
			}
			else if ((buttonValues & Button.ID_DOWN) == Button.ID_DOWN) {
				if (controllingArms)
					RobotStructure.getInstance().armMotorLeftReg.backward();
				else
					RobotStructure.getInstance().leftMotorReg.backward();
			}
			else {
				if (controllingArms)
					RobotStructure.getInstance().armMotorLeftReg.stop(true);
				else
					RobotStructure.getInstance().leftMotorReg.stop(true);
			}
			
			if ((buttonValues & Button.ID_RIGHT) == Button.ID_RIGHT) {
				if (controllingArms)
					RobotStructure.getInstance().armMotorRightReg.forward();
				else
					RobotStructure.getInstance().rightMotorReg.forward();
			}
			else if ((buttonValues & Button.ID_LEFT) == Button.ID_LEFT) {
				if (controllingArms)
					RobotStructure.getInstance().armMotorRightReg.backward();
				else
					RobotStructure.getInstance().rightMotorReg.backward();
			}
			else {
				if (controllingArms)
					RobotStructure.getInstance().armMotorRightReg.stop(true);
				else
					RobotStructure.getInstance().rightMotorReg.stop(true);
			}

			if (buttonValues == Button.ID_ENTER) {
				while (Button.getButtons() == Button.ID_ENTER && RunHandler.getCurrentRun().isActive());
				controllingArms = !controllingArms;
				LCD.clear();
				if (controllingArms)
					LCD.drawString("arms", 0, 0);
				else
					LCD.drawString("wheels", 0, 0);
			}

		}

	}

}