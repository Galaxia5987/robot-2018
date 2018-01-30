package auxiliary;

public class Watch_Doge {
	
	Watch_Dogeable subsystem;
	int[] motorPorts;
	double maxAmp;
	public Watch_Doge(Watch_Dogeable subsystem, int[] motorPorts, double maxAmp) {
		this.subsystem = subsystem;
		this.motorPorts = motorPorts;
		this.maxAmp = maxAmp;
	}
	
	public void myHeart(){
		subsystem.bork();
	}
}