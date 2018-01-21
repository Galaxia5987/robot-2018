package auxiliary;


/**
 * Velocity(distance) Motion Profile that is based on TimeMotionProfile
 */
public class DistanceMotionProfile {
	private static enum MPTypes{
		TRIANGLE,
		TRAPEZOID
	};
	private MPTypes type;
	private double[][] points;
	private double[][] functions;
	private double tFinal;
	private double VmaxTime;
	private double a;
	private double d;
	private boolean isForward;
	private double Vmax;
	private double finalDistance;
	/**
	 * @param points 2D array of points. a point represents {x, v} (x=distance, v=velocity)<br>
	 */
	public DistanceMotionProfile(double[][] points){
		this.points = points;
		this.functions = pointsToFunctions(points);
	}
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
		
		double[] p1 = {0, 0};
		double[] pLast;
		double[] p2;
		switch(type){
		
		case TRIANGLE:
			double bDecceleration = (isForward? 1 : -1) * Math.sqrt(
					-2 * d * finalDistance * (a - d)
					/
					a
					);
			Vmax = (a * bDecceleration) / (a - d);
			VmaxTime = bDecceleration / (a - d);
			tFinal = - bDecceleration / d;
			p2 = new double[]{VmaxTime, Vmax};
			pLast = new double[]{tFinal, 0};
			this.points = new double[][]{p1, p2, pLast};
			this.functions = pointsToFunctions(points);
			
		case TRAPEZOID:
			double maxVelocityDistance = x - (Xa + Xd);
			VmaxTime = maxVelocityDistance / Vmax;
			p2 = new double[]{Ta, Vmax};
			double[] p3 = {Ta + VmaxTime, Vmax};
			tFinal = Ta + Td + VmaxTime;
			pLast = new double[]{tFinal, 0};
			this.points = new double[][]{p1, p2, p3, pLast};
			this.functions = pointsToFunctions(points);
			
		default:
			throw new Error("You're fucked up mate. How is it not TRIANGLE or TRAPEZOID?!?!?!?!?!?!");
		}
	}
	public double[][] getPoints() {
		return points;
	}
	
	public double[][] getFunctions() {
		return functions;
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
		switch(type){
		
		case TRIANGLE:
			double vertexDist = (VmaxTime * Vmax) / 2;
			if(absDist < vertexDist){
				outV = 2 * a * absDist;
			}else{
				outV = Math.sqrt((Vmax*Vmax) + D * (VmaxTime * Vmax) - 2 * D * absDist);
			}
			outV *= dir;
			return outV;
		case TRAPEZOID:
			double accelerationEndDist = (Vmax * Vmax) / 2 * A;
			double decelerationStartDist = finalDistance - (Vmax * Vmax) / 2 * D;
			if(absDist < accelerationEndDist){
				outV = Math.sqrt(2 * A * absDist);
			}else if(absDist < decelerationStartDist){
				outV = Vmax; 
			}else{
				outV = Math.sqrt(2 * D * finalDistance - 2 * D * absDist);
			}
			outV *= dir;
			return outV;
		default:
			throw new Error("You're fucked up mate. How is it not TRIANGLE or TRAPEZOID?!?!?!?!?!?!");
		}
	}

	private static double[] pointsToFunction(double[] p1, double[] p2){
		assert p1.length == 2;
		assert p2.length == 2;
		double x1 = p1[0];
		double y1 = p1[1];
		double x2 = p2[0];
		double y2 = p2[1];
		double m = (y2 - y1) / (x2 - x1);
		double b = -m*x1 + y1;
		double[] function = {m, b};
		return function;
	}
	private static double[][] pointsToFunctions(double[][] points){
		double[][] functions = new double[points.length-1][2];
		// from the second point to the last one
		for(int i=1; i < points.length; i ++){
			// index for appending the function to functions[]
			int functionsI = i - 1;
			functions[functionsI] = pointsToFunction(points[i-1], points[i]);
		}
		return functions;
	}
	private static double getY(double x, double[][] functions, double[][] points){
		// choose the last function if x is bigger then the last point's X
		double[] rightFunction = functions[functions.length-1];
		// from the second point to the last one
		for(int i=1; i < points.length; i ++){
			if(Math.abs(x) < Math.abs(points[i][0])){
				// index for getting the function from functions[]
				int functionsI = i - 1;
				rightFunction = functions[functionsI];
				break;
			}
		}
		double m = rightFunction[0];
		double b = rightFunction[1];
		return m*x + b;
		
	}
}
