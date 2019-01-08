package util;

public class GeneralUtils {
	public static int distToDeg(double dist) {
		return (int) (dist/(9.42*Math.PI))*360;
	}
}
