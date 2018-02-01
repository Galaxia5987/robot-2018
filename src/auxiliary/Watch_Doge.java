package auxiliary;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Watch_Doge {
	
	PowerDistributionPanel PDP;
	Watch_Dogeable subsystem;
	int[] motorPorts;
	double maxAmp;
	
	public Watch_Doge(PowerDistributionPanel PDP, Watch_Dogeable subsystem, int[] motorPorts, double maxAmp) {
		this.PDP = PDP;
		this.subsystem = subsystem;
		this.motorPorts = motorPorts;
		this.maxAmp = maxAmp;
	}
	
	public void feed() {
		if (!subsystem.ded()) {
			for (int i = 0; i < motorPorts.length; i++) {
				if (PDP.getCurrent(motorPorts[i]) >= maxAmp)
					subsystem.bork();
			}
		}
		else {
			if (subsystem.wakeMeUp())
				subsystem.necromancy();
		}
	}
}