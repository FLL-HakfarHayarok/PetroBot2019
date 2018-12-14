package runs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import robotUtils.RobotRun;
import robotUtils.RobotStructure;

public class MotorControl extends RobotRun {

	public void runInstructions() {

		boolean controllingArms = false;
		int buttonValues;

		LCD.clear();
		LCD.drawString("wheels", 0, 0);

		RobotStructure.getInstance().armMotorLeftReg.setSpeed(800);
		RobotStructure.getInstance().armMotorRightReg.setSpeed(800);
		RobotStructure.getInstance().leftMotorReg.setSpeed(800);
		RobotStructure.getInstance().rightMotorReg.setSpeed(800);

		while (!Thread.currentThread().isInterrupted()) {

			buttonValues = Button.getButtons();

			// check for each motor
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
