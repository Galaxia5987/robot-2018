package auxiliary;


/**
 * @author Dor
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

	/**
	 * 
	 * @param finalDistance distance from target [METERS]
	 * @param maxVelocity the maximum velocity the robot can drive [METERS/SEC] - ABSOLUTE
	 * @param minVelocity the minimum velocity the robot can move [METERS/SEC] - ABSOLUTE
	 * @param acceleration [METERS/SEC^2] - ABSOLUTE
	 * @param deceleration [METERS/SEC^2] - ABSOLUTE
	 */
	public DistanceMotionProfile(double finalDistance, double maxVelocity, double minVelocity, double acceleration, double deceleration){
		this.finalDistance = finalDistance;
		isForward = finalDistance > 0;
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
	 * @param x distance after start [METER]
	 * @return the desired speed in that distance (x)
	 */
	public double getV(double x){
		double outV;
		double dir = isForward ? 1:-1;
		double absDist = Math.abs(finalDistance);
		double A = Math.abs(a);
		double D = Math.abs(d);
		x = Math.abs(x); 
		
		assert VmaxTime > 0;
		Vmax = Math.abs(Vmax);
		switch(type){
		case TRIANGLE:
			double vertexDist = Math.abs((VmaxTime * Vmax) / 2); // absolute
			if(absDist < vertexDist){
				outV = Math.sqrt(2 * a * x);
			}else{
				outV = Math.sqrt((Vmax*Vmax) + D * (VmaxTime * Vmax) - 2 * D * x);
			}
			outV *= dir;
			break;
			
		case TRAPEZOID:
			double accelerationEndDist = Math.abs((Vmax * Vmax) / (2 * A)); // absolute
			double decelerationStartDist = Math.abs(finalDistance) - Math.abs((Vmax * Vmax) / (2 * D)); // absolute
			if(absDist < accelerationEndDist){
				outV = Math.sqrt(2 * A * x);
			}else if(absDist < decelerationStartDist){
				outV = Vmax; 
			}else{
				outV = Math.sqrt(2 * D * finalDistance - 2 * D * x);
			}
			outV *= dir;
			break;
			
		default:
			throw new Error("You're fucked up mate. How is it not TRIANGLE or TRAPEZOID?!?!?!?!?!?!");
		}
		return outV;
	}
	

}
