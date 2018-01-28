package auxiliary;

public class Misc {
	private static boolean between(double value, double min, double max){
		return value >= min && value <=max;
	}
	/**
	 * 
	 * @param max
	 * @return
	 */
	public static double limit(double value, double maxNegative, double minNegative, double minPositive, double maxPositive){
		if(value < maxNegative)
			return maxNegative;
		else if(between(value, minNegative, 0))
			return minNegative;
		else if(between(value, 0, minPositive))
			return minPositive;
		else if(value > maxPositive)
			return maxPositive;
		else
			return value; // in range
	}
	
	public static double limitAbsMax(double value, double maxAbs){
		return limit(value, -maxAbs, 0, 0, maxAbs);
	}
	
	public static double limitAbsMin(double value, double minAbs){
		return limit(value, Double.NEGATIVE_INFINITY, -minAbs, minAbs, Double.POSITIVE_INFINITY);
	}
	
	public static double limitAbs(double value, double minAbs, double maxAbs){
		return limit(value, -maxAbs, -minAbs, minAbs, maxAbs);
	}
	
	/**
	 * Prevents from exceeding max velocity
	 * @param rightOutput
	 * @param leftOutput
	 * @param maxOutput
	 * @return {normalizedRight, normalizedLeft}
	 */
	public static double[] normalize(double leftOutput, double rightOutput, double maxOutput){
		// normalization
		double scale = 1;
		if((Math.abs(rightOutput) > maxOutput) || (Math.abs(leftOutput) > maxOutput)){
			if(Math.abs(rightOutput) > Math.abs(leftOutput)){
				scale = maxOutput / Math.abs(rightOutput);
			}else{
				scale = maxOutput / Math.abs(leftOutput);
			}
		}

		return new double[]{leftOutput * scale, rightOutput * scale};
	}
	public static double absoluteToRelativeAngle(double absoluteAngle, double startAngle) {
		// TODO Auto-generated method stub
		double currentAngle = startAngle % 360 ; // (angle % 360deg) = angle from 0 to 360
		double oneWay = absoluteAngle - currentAngle; // not passing through 0deg
		double orAnother = absoluteAngle + (360 - currentAngle); // passing through 0deg
		double gonnaCatchYa; // optimal way
		if(Math.abs(oneWay) < Math.abs(orAnother)){
			gonnaCatchYa = oneWay; 
		}else{
			gonnaCatchYa = orAnother;
		}
		return gonnaCatchYa;
	}
}
