package runs;

import lejos.hardware.lcd.LCD;
import robotUtils.RobotRun;
import robotUtils.RobotStructure;

public class Run1 extends RobotRun{

	@Override
	public void runInstructions() {
		LCD.clear();
		RobotStructure.getInstance().leftMotorReg.setSpeed(600);
		RobotStructure.getInstance().rightMotorReg.setSpeed(600);
		RobotStructure.getInstance().leftMotorReg.forward();
		RobotStructure.getInstance().rightMotorReg.forward();
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < 5000 && !Thread.currentThread().isInterrupted());
		RobotStructure.getInstance().leftMotorReg.setSpeed(0);
		RobotStructure.getInstance().rightMotorReg.setSpeed(0);
		
		System.out.println("slepping");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			return;
		}
	}

}
