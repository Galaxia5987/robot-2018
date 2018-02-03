package auxiliary;

public class Path {
	private Point[] points;
	private Line[] functions;
	private double[] lengths;
	
	public Path(Point[] p){
		this.points = p;
		functions = new Line[points.length - 1];
		lengths = new double[points.length - 1];
		setFunctions();
	}

	public void setFunctions() {
		for (int i = 0; i < points.length - 1; i++) {
			functions[i] = new Line(points[i], points[i+1]);
			lengths[i] = functions[i].distanceTraveled(points[i+1]);
		}
//		functions[0].setLinePose(Line.line.START);
//		functions[functions.length-1].setLinePose(Line.line.END);
	}
	
	public double getError(Line function, Point p) {
		double A = function.getLine()[0];
		double B = -1;
		double C = function.getLine()[1];
		
		return (A * p.get()[0] + B * p.get()[1] + C) / Math.sqrt(A * A + B * B);
	}
	
	public double[] getSmallestError(Point p) {
		double minD = Double.POSITIVE_INFINITY;
		double d;
		double ID=0;
		double anglePerpendiculat = 0;
		double angleParll = 0;
		boolean isr = false;
		for (int i = 0; i < functions.length; i++) {
			d = getError(functions[i],p);
			Point current = getWantedPoint(i,p);
			double r = points[i].disPoint(p);
			if (Math.abs(minD) >= Math.abs(r)) {
				minD = r;
				ID = i + points.length;
				anglePerpendiculat = points[i].getAngle(p);
				angleParll = anglePerpendiculat + (functions[i].isRight(p) ? - Math.PI / 2 : Math.PI / 2);
				isr = true;
			}
			if (functions[i].inLine(current)) {
				if (Math.abs(minD) >= Math.abs(d)) {
					minD = Math.abs(d);
					anglePerpendiculat =  functions[i].getAngle()- Math.PI / 2 + (functions[i].isRight(p) ? Math.PI: 0);
					angleParll = anglePerpendiculat + (functions[i].isRight(p) ? - Math.PI / 2 : Math.PI / 2);
					ID = i;
					isr = false;

				}
			}
		}
		if (Math.abs(minD) >= Math.abs(points[points.length-1].disPoint(p))) {
			minD = points[points.length-1].disPoint(p);
			ID = -1;
			anglePerpendiculat = points[points.length-1].getAngle(p);
			isr = true;
		}
		double[] distance = {ID, minD, anglePerpendiculat, angleParll};
		return distance;
	}
	
	public Point getWantedPoint(int ID, Point p) {
		double x = functions[ID].getLine()[0] == 0 ? p.get()[0] : (p.get()[1] - functions[ID].getLine()[1] + p.get()[0] / functions[ID].getLine()[0]) / (functions[ID].getLine()[0] + 1 / functions[ID].getLine()[0]);
		double y = functions[ID].getLine()[0] * x + functions[ID].getLine()[1];
		Point wanted = new Point(x,y);
		return wanted;
	}
	
	public double getLength(int ID,Point p) {
		if (ID == -1) return -1;
		
		double total = 0;
		if (ID >= points.length) {
			ID -= points.length;
			for (int i = 0; i < ID; i++)
				total += lengths[i];
			return total;
		}
			
		for (int i = 0; i < ID; i++)
			total += lengths[i];
		Point current = getWantedPoint(ID,p);
		total += functions[ID].distanceTraveled(current);
		return total;
	}
	
	public double[] get(Point p) {	
		double[] error =  getSmallestError(p);
		double[] pos = {getLength( (int) error[0],p),error[3], error[1], error[2]};
		return pos;
	}
	
	public void print() {
		for (int i = 0; i < points.length; i++)
			points[i].printPoint();
		for (int i = 0; i < functions.length; i++) {
			functions[i].printLine();
			System.out.println("length: " + lengths[i]);
		}
	}
}
