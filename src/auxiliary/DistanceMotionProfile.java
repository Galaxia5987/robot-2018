package auxiliary;



/**
 * @author Dor Brekhman
 * Velocity(distance) Motion Profile that is based on TimeMotionProfile
 */
public class DistanceMotionProfile {
	private static enum MPTypes{
		/**
		 * Cannot reach max velocity
		 *      - 
		 *    -   - 
		 *  -       - 
		 * -          - 
		 */
		TRIANGLE,
		/**
		 * Regular motion profile
		 *      --------
		 *    -          -
		 *  -              -
		 * -                 -
		 */
		TRAPEZOID
	};
	private MPTypes type;
	private double VmaxTime;
	private double a;
	private double d;
	private boolean isForward;
	private double Vmax;
	private double finalDistance;
	private double minVelocity;

	/**
	 * 
	 * @param finalDistance distance from target [METERS] - positive if going forward, otherwise negative
	 * @param maxVelocity the maximum velocity the robot can drive [METERS/SEC] - ABSOLUTE
	 * @param minVelocity the minimum velocity the robot can move [METERS/SEC] - ABSOLUTE
	 * @param acceleration [METERS/SEC^2] - ABSOLUTE
	 * @param deceleration [METERS/SEC^2] - ABSOLUTE
	 */
	public DistanceMotionProfile(double finalDistance, double maxVelocity, double minVelocity, double acceleration, double deceleration){
		this.finalDistance = finalDistance;
		isForward = finalDistance > 0;
		this.minVelocity = minVelocity * (isForward? 1 : -1);
		Vmax = maxVelocity * (isForward? 1 : -1);
		a = acceleration * (isForward? 1 : -1);
		d = deceleration * (isForward? -1 : 1);
		double x = finalDistance;
		double Ta = Vmax / a;
		double Td = - Vmax / d;
		double Xa = 0.5 * a * Ta * Ta;
		double Xd = - 0.5 * d * Td * Td;
		
		
		if(Math.abs(Xa) + Math.abs(Xd) >= Math.abs(finalDistance))
			type = MPTypes.TRIANGLE;
		else
			type = MPTypes.TRAPEZOID;
		
		switch(type){
		
		case TRIANGLE:
			double bDecceleration = (isForward? 1 : -1) * Math.sqrt(
					-2 * d * finalDistance * (a - d)
					/
					a
					); // m of mx+b of deceleration(time) function
			Vmax = (a * bDecceleration) / (a - d);
			VmaxTime = bDecceleration / (a - d);
			break;
			
		case TRAPEZOID:
			double maxVelocityDistance = x - (Xa + Xd);
			VmaxTime = maxVelocityDistance / Vmax;
			break;
			
		default:
			throw new Error("You're fucked up mate. How is it not TRIANGLE or TRAPEZOID?!?!?!?!?!?!");
		}
	}
	
	/**
	 * 
	 * @param x distance after start [METER] - can be positive or negative
	 * @return the desired speed in that distance (x)
	 */
	public double getV(double x){
		// if the distance is before the starting distance (0), return the minimum velocity
		if(finalDistance > 0){
			if(x < 0){
				return minVelocity;
			}
		}else{
			if(x > 0){
				return minVelocity;
			}
		}
		
		double outV;
		double dir = isForward ? 1 : -1;
		double absTotalDist = Math.abs(finalDistance);
		double A = Math.abs(a);
		double D = Math.abs(d);
		double absX = Math.abs(x);
		Vmax = Math.abs(Vmax);
		
		boolean inDecceleration = false;
		switch(type){
		case TRIANGLE:
			// distance when going from acceleration to deceleration, absolute
			double vertexDist = Math.abs((VmaxTime * Vmax) / 2);
			// acceleration function
			if(absX < vertexDist){
				outV = Math.sqrt(2 * A * absX);
			// deceleration function
			}else{
				inDecceleration = true;
				outV = Math.sqrt(Math.abs((Vmax*Vmax) + D * (VmaxTime * Vmax) - 2 * D * absX));
				 // in case of overshoot, change direction (go back)
				if(absX > absTotalDist)
					outV = -outV;
			}
			// return minus velocity if going backwards
			outV *= dir;
			break;
			
		case TRAPEZOID:
			double accelerationEndDist = Math.abs((Vmax * Vmax) / (2 * A)); // absolute
			double decelerationStartDist = Math.abs(absTotalDist) - Math.abs((Vmax * Vmax) / (2 * D)); // absolute
			// acceleration function
			if(absX < accelerationEndDist){
				outV = Math.sqrt(2 * A * absX);
			// constant velocity (max velocity) function
			}else if(absX < decelerationStartDist){
				outV = Vmax; 
			// deceleration function
			}else{
				inDecceleration = true;
				outV = Math.sqrt(Math.abs(2 * D * absTotalDist - 2 * D * absX));
				// in case of overshoot, change direction (go back)
				if(absX > absTotalDist)
					outV = -outV;
			}
			// return minus velocity if going backwards
			outV *= dir;
			break;
			
		default:
			throw new Error("You're fucked up mate. How is it not a TRIANGLE or TRAPEZOID?!?!?!?!?!?!");
		}
		
		if(!inDecceleration){
			// If |outV| is less than minVelocity return +- minVelocity 
			if (Math.abs(outV) < Math.abs(minVelocity))
				return minVelocity;
		} return outV;

	}
	public MPTypes getType() {
		// TODO Auto-generated method stub
		return type;
	}
	

}
