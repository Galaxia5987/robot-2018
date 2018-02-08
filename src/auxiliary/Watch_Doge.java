package auxiliary;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class is a good boy. A REALLY good boy. Such a good boy, in fact, he can
 * tell when a 775pro motor is stalling just by sniffing the current that the
 * motor is drawing from the PDP. If the amount of amps passes the maximum that took
 * Watch_Doge forty-two treats to learn, he'll {@link #bork()} at the subsystem to shut it
 * off to prevent any further damage. He will then proceed to revive the
 * subsystem with some awesome Refer to {@linkplain #bork the overridden method}.
 */
public class Watch_Doge {

	PowerDistributionPanel PDP; // Reference to PDP for current-sniffing
	Watch_Dogeable subsystem; // Reference to subsystem that must be monitored
								// (with Watch_Dogeable interface implemented)
	int[] motorPorts; // The PDP power ports used by the motor controllers in
						// the subsystem
	double maxAmp; // Maximum number of amperes
	double minTime; // Minumum time the motor can exceed maxAmps before
					// Watch_Doge bork().

	private Timer timer = new Timer();
	private boolean isTimerEnabled = false;

	/**
	 * Birth and train a new Watch_Doge
	 *
	 * @param PDP Reference to PowerDistributionPanel object
	 * @param subsystem Reference to a subsystem with {@link Watch_Dogeable} interface implemented
	 * @param motorPorts Array of PDP power ports used by motor controllers in subsystem
	 * @param maxAmp Maximum number of amperes allowed before Watch_Dogeable shuts 
	 * 		subsystem down by bork().
	 * @param minTime Minimum time [sec] for motor to exceed maxAmps before Watch_Doge bork()s.
	 */
	public Watch_Doge(PowerDistributionPanel PDP, Watch_Dogeable subsystem, int[] motorPorts, double maxAmp,
			double minTime) {
		this.PDP = PDP;
		this.subsystem = subsystem;
		this.motorPorts = motorPorts;
		this.maxAmp = maxAmp;
		this.minTime = minTime;
	}

	/**
	 * Must be run on a regularly-scheduled loop. Every single iteration,
	 * Watch_Doge will check if subsystem is enabled via ded(), and if so, will
	 * check the current and compare against max. If the maximum current is
	 * reached for more than a specified number of seconds (given in the
	 * constructor), Watch_Doge will bork() and shut down the subsystem.
	 * Watch_Doge will then check if subsystem is ready to re-enable with
	 * boolean function wakeMeUp(), and then will proceed to revive the
	 * subsystem with necromancy() on the return of a true value.
	 */
	public void feed() {
		if (!subsystem.ded()) {
			for (int i = 0; i < motorPorts.length; i++) {
				if (PDP.getCurrent(motorPorts[i]) >= maxAmp) {
					if (!isTimerEnabled) {
						timer.reset();
						timer.start();
						isTimerEnabled = true;
					}
					if (timer.hasPeriodPassed(minTime)) { // This also resets the timer					
						subsystem.bork();
						timer.stop();
						isTimerEnabled = false;
					}
				} else {
					timer.stop();
					timer.reset();
					isTimerEnabled = false;
					
				}
			}
		} else if (subsystem.wakeMeUp()) {
			subsystem.necromancy();
		}
	}
}