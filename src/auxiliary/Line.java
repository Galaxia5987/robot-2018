package auxiliary;

public class Line {
	
	public enum line {
		START,
		MIDLE,
		END
	}
	
	private Point A;
	private Point B;
	
	private double m;
	private double n;
	private line linePos;
	
	public Line(Point a, Point b) {
		this(a, b, line.MIDLE);
	}
	
	public Line(Point a, Point b, line l) {
		A = a;
		B = b;
		linePos = l;
		
		calcFunction(a,b);
	}
	
	public void setLinePose(line l) {
		linePos = l;
	}

	public void calcFunction(Point a, Point b) {
		double[] A = a.get();
		double[] B = b.get();
		
		m = (B[1] - A[1]) / (B[0] - A[0]);
		n = A[1] - m * A[0];
	}
	
	public double[] getLine() {
		double[] f = {m,n};
		return f;
	}
	
	public void printLine() {
		System.out.println("f(x) = " + m + " * x + " + n);
	}
	
	public double[] getPointA() {
		return A.get();
	}
	
	public double[] getPointB() {
		return B.get();
	}
	
	public double distanceTraveled(Point p) {
		double dx = p.get()[0] - A.get()[0];
		double dy = p.get()[1] - A.get()[1];
		double distance = Math.sqrt(dx * dx + dy * dy);
		if (A.get()[0] <= B.get()[0]) 
			return distance * (A.get()[0] <= p.get()[0] ? 1 : -1);
		else
			return distance * (A.get()[0] < p.get()[0] ? -1 : 1);
			
			
	}
	
	public boolean inLine(Point p) {
		switch(linePos) {
		default:
		case MIDLE:
			if (A.get()[0] < B.get()[0])
				return (A.get()[0] <= p.get()[0]) && (p.get()[0] <= B.get()[0]);
			return (B.get()[0] <= p.get()[0]) && (p.get()[0] <= A.get()[0]);
		case START: 
			if (A.get()[0] < B.get()[0])
				return (p.get()[0] <= B.get()[0]);
			return (B.get()[0] <= p.get()[0]);
		case END: 
			if (A.get()[0] < B.get()[0])
				return (A.get()[0] <= p.get()[0]);
			return (p.get()[0] <= A.get()[0]);
		}
	}
	
	public boolean isRight(Point p) {
		double x = p.get()[0];
		double y = p.get()[1];
		double dy = m * x + n - y;
		dy *= (A.get()[0] <= B.get()[0]) ? 1: -1;
		return dy >= 0;
	}
	
	public double getAngle() {
		return Math.atan((B.get()[1]-A.get()[1])/(B.get()[0]-A.get()[0])) + (A.get()[0] <= B.get()[0] ? 0: Math.PI);
	}
}
