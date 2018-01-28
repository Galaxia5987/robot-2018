package auxiliary;

/**
 * <b>Motion Profile Implementation</b> <br>
 * A class for calculating speed according to distance for motors <br>
 * <br>
 * <b>Example 1</b> <br>
 * <code>
 *  double[][] points = {{0,0},{0.25,1},{0.75,1},{1,0}}; <br>
	MotionProfile mp = new MotionProfile(points); <br>
	mp.getV(0) // returns 0 <br>
	mp.getV(0.1) // returns 0.4 <br>
	mp.getV(1) // returns 0 <br>
	</code> <br>
 * <b>Example 2</b> <br>
 * <code>
 *  finalDistance = 5; // [METERS] <br>
 *  maxVelocity = 2; // [METERS/SEC] <br>
 *  accelerationDistance = 1; // [METERS] <br>
 *  decelerationDistance = 1.1; // [METERS] <br>
	MotionProfile mp = MotionProfile(finalDistance, maxVelocity, accelerationDistance, decelerationDistance); <br>
	mp.getV(x)
	</code>
 */
public class MotionProfile {
	private double[][] points;
	private double[][] functions;
	private boolean isForward;

	/**
	 * @param points - 2D array of points. a point represents {x, v} (x = distance, v = velocity)<br>
	 */
	public MotionProfile(double[][] points) {
		this.points = points;
		this.functions = pointsToFunctions(points);
	}

	/**
	 * 
	 * @param finalDistance - distance from target [m]
	 * @param maxVelocity - the absolute maximum velocity the robot can drive [m/s]
	 * @param minVelocity - the absolute minimum velocity the robot can drive [m/s]
	 * @param accelerationDistance - the distance the robot drives from velocity 0 to max velocity [m]
	 * @param decelerationDistance - the distance the robot drives from max velocity to 0 [m]
	 */
	public MotionProfile(double finalDistance, double maxVelocity, double minVelocity, double accelerationDistance,
			double decelerationDistance) {
		double Vmax = maxVelocity;
		double Vmin = minVelocity;
		double Xt = finalDistance;
		double Xa = accelerationDistance;
		double Xd = decelerationDistance;
		isForward = finalDistance > 0;
		if (!isForward) {
			Vmax = -Vmax;
			Vmin = -Vmin;
		}
		double[] p1 = { 0, Vmin };
		double[] pLast = { Xt, 0 };
		if (Math.abs(Xa) + Math.abs(Xd) >= Math.abs(Xt)) { // There is collision between acceleration and deceleration
			double Xp2 = (Xa * Xt) / (Xd + Xa);
			double Yp2 = Xp2 * (Vmax / Xa);
			double[] p2 = { Xp2, Yp2 };
			this.points = new double[][] { p1, p2, pLast };
			this.functions = pointsToFunctions(points);
		} else { // No collision between acceleration and deceleration
			double[] p2 = { Xa, Vmax };
			double[] p3 = { Xt - Xd, Vmax };
			this.points = new double[][] { p1, p2, p3, pLast };
			this.functions = pointsToFunctions(points);
		}
	}

	public double[][] getPoints() {
		return points;
	}

	/**
	 * 
	 * @param x - the distance from the target
	 * @return the desired speed in that distance (x) according to the graph of
	 *         the points (from the constructor)
	 */
	public double getV(double x) {
		return getY(x, this.functions, this.points);
	}

	private static double[] pointsToFunction(double[] p1, double[] p2) {
		assert p1.length == 2;
		assert p2.length == 2;
		double x1 = p1[0];
		double y1 = p1[1];
		double x2 = p2[0];
		double y2 = p2[1];
		double m = (y2 - y1) / (x2 - x1);
		double b = -m * x1 + y1;
		double[] function = { m, b };
		return function;
	}

	private static double[][] pointsToFunctions(double[][] points) {
		double[][] functions = new double[points.length - 1][2];
		// from the second point to the last one
		for (int i = 1; i < points.length; i++) {
			// index for appending the function to functions[]
			int functionsI = i - 1;
			functions[functionsI] = pointsToFunction(points[i - 1], points[i]);
		}
		return functions;
	}

	private static double getY(double x, double[][] functions, double[][] points) {
		// choose the last function if x is bigger then the last point's X
		double[] rightFunction = functions[functions.length - 1];
		// from the second point to the last one
		for (int i = 1; i < points.length; i++) {
			if (Math.abs(x) < Math.abs(points[i][0])) {
				// index for getting the function from functions[]
				int functionsI = i - 1;
				rightFunction = functions[functionsI];
				break;
			}
		}
		double m = rightFunction[0];
		double b = rightFunction[1];
		return m * x + b;

	}

	public double[][] getFunctions() {
		// TODO Auto-generated method stub
		return functions;
	}
}
