package runs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robotUtils.RobotRun;
import robotUtils.RobotStructure;

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
		RobotStructure.armMotorLeftReg.setSpeed(800);
		RobotStructure.armMotorRightReg.setSpeed(800);
		RobotStructure.leftMotorReg.setSpeed(800);
		RobotStructure.rightMotorReg.setSpeed(800);

		while (!Thread.currentThread().isInterrupted()) { //Will work while the run has not been stopped

			buttonValues = Button.getButtons();

			// Check each possible situation, which buttons are pressed and acts accordingly
			if ((buttonValues & Button.ID_UP) == Button.ID_UP) {
				if (controllingArms)
					RobotStructure.armMotorLeftReg.forward();
				else
					RobotStructure.leftMotorReg.forward();
			}
			else if ((buttonValues & Button.ID_DOWN) == Button.ID_DOWN) {
				if (controllingArms)
					RobotStructure.armMotorLeftReg.backward();
				else
					RobotStructure.leftMotorReg.backward();
			}
			else {
				if (controllingArms)
					RobotStructure.armMotorLeftReg.stop(true);
				else
					RobotStructure.leftMotorReg.stop(true);
			}
			
			if ((buttonValues & Button.ID_RIGHT) == Button.ID_RIGHT) {
				if (controllingArms)
					RobotStructure.armMotorRightReg.forward();
				else
					RobotStructure.rightMotorReg.forward();
			}
			else if ((buttonValues & Button.ID_LEFT) == Button.ID_LEFT) {
				if (controllingArms)
					RobotStructure.armMotorRightReg.backward();
				else
					RobotStructure.rightMotorReg.backward();
			}
			else {
				if (controllingArms)
					RobotStructure.armMotorRightReg.stop(true);
				else
					RobotStructure.rightMotorReg.stop(true);
			}

			if (buttonValues == Button.ID_ENTER) {
				while (Button.getButtons() == Button.ID_ENTER && !Thread.currentThread().isInterrupted());
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