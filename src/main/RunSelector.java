package main;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import robotUtils.RobotRun;
import robotUtils.RobotStructure;
import runs.MotorControl;
import runs.Run1;
import runs.Run2;
import runs.Run3;
import runs.Run4;
import util.Chassis;

/**
 * The master class, in charge of running specific runs.
 * Includes GUI display with an arrow indicator.
 * Arrow is moved using the up and down buttons on the robot, select run with center button.
 * Note that the RobotStructure must be initialized here in order for it to be used in all other programs.
 * 
 * @author John & PetoBlitz #841
 */

public class RunSelector {
		
	private static RobotRun selectedRun;
	
	public static void main(String[] args) {
		
		RobotStructure.init();
		
		int arrowY = 0;
		
		boolean active = true;
		while(active) {
			
			drawScreen(arrowY);	
			
			switch(Button.getButtons()) {
			
			case Button.ID_UP: 
				while(Button.getButtons() == Button.ID_UP);
				arrowY = Math.max(0, arrowY - 1); //Raises the arrow by 1
				break;
			case Button.ID_DOWN:
				while(Button.getButtons() == Button.ID_DOWN);
				arrowY = Math.min(5, arrowY + 1); //Lowers the arrow by 1
				break;
			case Button.ID_ENTER:
				while(Button.getButtons() == Button.ID_ENTER);
				startNewRun(arrowY); //Starts the run  based on the current arrow position
				break;
			case Button.ID_ESCAPE: // timer for 2 seconds or until released
				long startTime = System.currentTimeMillis();
				while (Button.getButtons() == Button.ID_ESCAPE) {
					// set active to false if 2 seconds have passed without release
					if (System.currentTimeMillis() - startTime > 2000) {
						while (Button.getButtons() == Button.ID_ESCAPE);
						active = false;
						return;
					}
				}
				break;
			}
			
			Delay.msDelay(50);
		}
		
	}
	
	/**
	 * Draws everything that should be displayed on the screen (the arrow and run names)
	 *  
	 * @param yCoord The y coordinate the arrow should be displayed at.
	 */
	private static void drawScreen(int yCoord) {
		LCD.clear();
		
		LCD.drawString("->", 0, yCoord);
		
		LCD.drawString("Run 1", 2, 0);
		LCD.drawString("Run 2", 2, 1);
		LCD.drawString("Run 3", 2, 2);
		LCD.drawString("Run 4", 2, 3);
		LCD.drawString("Run 5", 2, 4);	
		LCD.drawString("Motor Ctrl", 2, 5);
	}
	
	/**
	 * Method to start a run depending on selection. Should be called on enter press.

	 * @param runNum
	 */
	private static void startNewRun(int runNum) {
		switch(runNum+1) {
		case 1:
			selectedRun = new Run1();
			break;
		case 2:
			selectedRun = new Run2();
			break;
		case 3:
			selectedRun = new Run3();
			break;
		case 4:
			selectedRun = new Run4();
			break;
		case 5:
			//selectedRun = new Run5();
			break;
		case 6:
			selectedRun = new MotorControl();
			break;
		}
		selectedRun.start();
		while(selectedRun.isAlive());
		Chassis.floatAllMotors();
		
	}
	
}