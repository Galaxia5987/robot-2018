package auxiliary;

import edu.wpi.first.wpilibj.Victor;

/**
 * Extension of Victor class to provide easy enable/disable functionality
 */
public class SafeVictor extends Victor {

	private boolean isDisabled;
	
	public SafeVictor(int channel) {
		super(channel);
		this.isDisabled = false;
	}

	/**
	 *
	 * @param speed desired PWM output
	 */
	@Override
	public void set(double speed) {
		if (isDisabled)
			speed = 0;
		super.set(speed);
	}

	/**
	 * Disables motor
	 */
	public void disable() {
		this.isDisabled = true;
	}

	/**
	 *  Enables motor
	 */
	public void enable() {
		this.isDisabled = false;
	}

	/**
	 *
	 * @return true if enabled, false if disabled
	 */
	public boolean status() {
		return this.isDisabled;
	}
}
