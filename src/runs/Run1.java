package runs;

import lejos.hardware.lcd.LCD;
import robotUtils.RobotRun;
import util.GyroSensor;

public class Run1 extends RobotRun{

	@Override
	public void runInstructions() {
		LCD.clear();
		GyroSensor.driveForward(2.5, 500, 2000, true);
	}

}
