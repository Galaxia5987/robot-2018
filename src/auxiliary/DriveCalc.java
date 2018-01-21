package auxiliary;

public class DriveCalc {
	
	private double hight;
	private double disFromLeft;
	private double disFromRight;
	
	public DriveCalc(double hight,double disFromLeft,double disFromRight) {
		this.hight = hight;
		this.disFromLeft = disFromLeft;
		this.disFromRight = disFromRight;
	}
	
	public double getLeftVelo(double pointVelo, double pointAngle) {
		double w = pointVelo * Math.sin(angle2Radians(pointAngle)) / hight;
		double r = hight / Math.tan(angle2Radians(pointAngle));
		System.out.println(w + " " + r);
		return w * (r + disFromLeft);
	}
	
	public double getRightVelo(double pointVelo, double pointAngle) {
		double w = pointVelo * Math.sin(angle2Radians(pointAngle)) / hight;
		double r = hight / Math.tan(angle2Radians(pointAngle));
		System.out.println(w + " " + r);
		return w * (r - disFromRight);
	}
	
	private double angle2Radians(double angle) {
		return angle * Math.PI / 180;
	}
}
