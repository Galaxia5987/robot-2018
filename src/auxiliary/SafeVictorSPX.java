package auxiliary;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class SafeVictorSPX extends VictorSPX {

	private boolean isDisabled;
	
	public SafeVictorSPX(int channel) {
		super(channel);
		this.isDisabled = false;
	}
	
	public double get(){
		return super.getMotorOutputPercent();
	}
	
	/**
	 *
	 * @param speed desired PWM output
	 */
	public void set(double speed) {
		if (isDisabled)
			speed = 0;
		super.set(ControlMode.PercentOutput, speed);
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