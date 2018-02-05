package auxiliary;

/**
 * <b>Motion Profile Implementation</b> <br>
 * A class for calculating speed according to time for motors <br>
 * <br>
 * <b>Example 1</b> <br>
 * <code>
 *  double[][] points = {{0,0},{0.25,1},{0.75,1},{1,0}}; <br>
	MotionProfile mp = new MotionProfile(points); <br>
	mp.getV(0) // returns 0 <br>
	mp.getV(0.1) // returns 0.4 <br>
	mp.getV(1) // returns 0 <br>
	</code> <br>
 */
public class TimeMotionProfile {
	private double[][] points;
	private double[][] functions;
	private double tFinal;

	/**
	 * @param points
	 *            2D array of points. a point represents {x, v} (x=distance,
	 *            v=velocity)<br>
	 */
	public TimeMotionProfile(double[][] points) {
		this.points = points;
		this.functions = pointsToFunctions(points);
	}

	/**
	 * 
	 * @param finalDistance
	 *            distance from target [METERS]
	 * @param maxVelocity
	 *            the maximum velocity the robot can drive [METERS/SEC] -
	 *            ABSOLUTE
	 * @param acceleration
	 *            [METERS/SEC^2] - ABSOLUTE
	 * @param deceleration
	 *            [METERS/SEC^2] - ABSOLUTE
	 */
	public TimeMotionProfile(double finalDistance, double maxVelocity, double acceleration, double deceleration) {
		boolean isForward = finalDistance > 0;
		double Vmax = maxVelocity * (isForward ? 1 : -1);
		double a = acceleration * (isForward ? 1 : -1);
		double d = deceleration * (isForward ? -1 : 1);
		double x = finalDistance;
		double Ta = Vmax / a;
		double Td = -Vmax / d;
		double Xa = 0.5 * a * Ta * Ta;
		double Xd = -0.5 * d * Td * Td;

		double[] p1 = { 0, 0 };
		if (Math.abs(Xa) + Math.abs(Xd) >= Math.abs(finalDistance)) { // Triangle
																		// Motion
																		// Profile
			double bDecceleration = (isForward ? 1 : -1) * Math.sqrt(-2 * d * finalDistance * (a - d) / a);
			Vmax = (a * bDecceleration) / (a - d);
			double VmaxTime = bDecceleration / (a - d);
			tFinal = -bDecceleration / d;
			double[] p2 = { VmaxTime, Vmax };
			double[] pLast = { tFinal, 0 };
			this.points = new double[][] { p1, p2, pLast };
			this.functions = pointsToFunctions(points);
		} else { // Trapezoid Motion Profile
			double maxVelocityDistance = x - (Xa + Xd);
			double maxVelocityTime = maxVelocityDistance / Vmax;
			double[] p2 = { Ta, Vmax };
			double[] p3 = { Ta + maxVelocityTime, Vmax };
			tFinal = Ta + Td + maxVelocityTime;
			double[] pLast = { tFinal, 0 };
			this.points = new double[][] { p1, p2, p3, pLast };
			this.functions = pointsToFunctions(points);
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
	 * @param t
	 *            time after start [SEC]
	 * @return the desired speed in that distance (x) according to the graph of
	 *         the points (from the constructor)
	 */
	public double getV(double t) {
		if (t > tFinal) // finished the motion profile
			return 0;
		return getY(t, this.functions, this.points);
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
}
