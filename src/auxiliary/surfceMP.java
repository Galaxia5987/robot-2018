package auxiliary;

public class surfceMP {
	
	private Path track;
	private double d;
	private double a;
	private double maxVelocity;
	private double topVelocity;
	private double totLength;
	private double startSpeed;
	private double endSpeed;
	private double r = 1;
	private double h = 1;


	public surfceMP(Point[] p,double maxVelocity, double a, double d, double startSpeed, double endSpeed) {
		track = new Path(p);
		this.d = d;
		this.a = a;
		this.maxVelocity = maxVelocity;
		this.topVelocity = maxVelocity;
		this.totLength = track.getLength(p.length-2, p[p.length-1]);
		this.startSpeed = startSpeed;
		this.endSpeed = endSpeed;
	}
	
	public double[] getV(Point p) {
		double[] tmp = track.get(p);
		double[] length = {tmp[0],tmp[1]};
		double[] error = {tmp[2],tmp[3]};
		
		double Verror = velocityByDistance(0,d/2,0,error[0]);
		Verror = Verror > topVelocity ? topVelocity : Verror;
		
		double Vforward = 0;
		
		if (distanceTravled(startSpeed,a,topVelocity) + distanceTravled(topVelocity,d,endSpeed) > totLength)
			topVelocity = Math.sqrt((2 * a * d * totLength + d * startSpeed * startSpeed + a * endSpeed * endSpeed) / (a + d));
		Vforward = mp(length[0]);
		
		double maxVforward = Math.sqrt(maxVelocity * maxVelocity - Verror * Verror);
		Vforward = Vforward > maxVforward ? maxVforward : Vforward;

		double vc = Vforward * Math.cos(length[1]) + Verror * Math.cos(error[1]);
		double vs = Vforward * Math.sin(length[1]) + Verror * Math.sin(error[1]);
		double v = Math.sqrt(vc * vc + vs * vs);
		
		double angle;
		if (vc == 0 )
			angle = vs >= 0 ? Math.PI / 2 : - Math.PI / 2;
		else 
			angle = Math.atan(vs / vc) + (vc < 0 ? Math.PI : 0);
		
		double[] velocity = {v,angle};
		
		return velocity;
	}
	
	private double mp(double x) {
		if (x == -1)
			return 0;
		if (x < distanceTravled(startSpeed,a,topVelocity)) {
			return velocityByDistance(startSpeed,a,0,x);
		}
		if (x < totLength - distanceTravled(topVelocity,d,endSpeed)) {
			return topVelocity;
		}
		if (x <= totLength) {
			return velocityByDistance(topVelocity,-d,totLength - distanceTravled(topVelocity,d,endSpeed),x);
		}
		return 0;
	}

	public double velocityByDistance(double startSpeed, double a, double startPos, double x) {
		return Math.sqrt(startSpeed * startSpeed + 2 * a * (x - startPos));
	}
	
	public double distanceTravled(double startSpeed, double a, double endSpeed) {
		return Math.abs((endSpeed * endSpeed - startSpeed * startSpeed) / (2 * a));
	}
	
	public double[] getMotorsVelocity(Point p, double angle) {
		double[] v = getV(p);
		
		double leftVelocity = v[0] * Math.cos(v[1]-angle) - v[0] * Math.sin(v[1]-angle) * r / h;
		double rightVelocity = v[0] * Math.cos(v[1]-angle) + v[0] * Math.sin(v[1]-angle) * r / h;
		
		double[] velocity = {leftVelocity,rightVelocity};
		
		return velocity;
	}
	
	public void setRatio(double hight, double distanceFromWheel) {
		h = hight;
		r = distanceFromWheel;
	}
}
