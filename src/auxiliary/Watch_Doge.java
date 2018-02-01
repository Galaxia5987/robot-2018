package auxiliary;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * This class is a good boy. A REALLY good boy. Such a good boy, in fact, he can tell when a 775pro motor is stalling
 * just by sniffing the current that the motor is drawing from the PDP. If the amount of amps passes the maximum
 * that this Watch_Doge was trained with, he'll bork() at the subsystem to shut it off to prevent any further damage.
 * He will then proceed to revive the subsystem with some awesome necromancy().
 */
public class Watch_Doge {

	PowerDistributionPanel PDP; //Reference to PDP for current-sniffing
	Watch_Dogeable subsystem;   //Reference to subsystem that must be monitored (with Watch_Dogeable interface implemented)
	int[] motorPorts;           //The PDP power ports used by the motor controllers in the subsystem
	double maxAmp;              //Maximum number of amperes

    /**Birth and train a new Watch_Doge
     *
     * @param PDP Reference to PowerDistributionPanel object
     * @param subsystem Reference to a subsystem with Watch_Dogeable interface implemented
     * @param motorPorts Array of PDP power ports used by motor controllers in subsystem
     * @param maxAmp Maximum number of amperes allowed before Watch_Dogeable shuts subsystem down (by borking)
     */
	public Watch_Doge(PowerDistributionPanel PDP, Watch_Dogeable subsystem, int[] motorPorts, double maxAmp) {
		this.PDP = PDP;
		this.subsystem = subsystem;
		this.motorPorts = motorPorts;
		this.maxAmp = maxAmp;
	}

    /**
     * Must be run on a regularly-scheduled loop. Every single iteration, Watch_Doge will check if subsystem is enabled via ded(),
     * and if so, will check the current and compare against max. If the maximum current is reached for more than a specified
     * number of seconds (given in the constructor), Watch_Doge will bork() and shut down the subsystem. Watch_Doge will
     * then check if subsystem is ready to re-enable with boolean function wakeMeUp(), and then will proceed to revive
     * the subsystem with necromancy() on the return of a true value.
     */
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