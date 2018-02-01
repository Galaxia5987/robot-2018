package auxiliary;

import edu.wpi.first.wpilibj.Victor;

public class SafeVictor extends Victor {

	private boolean isDisabled;
	
	public SafeVictor(int channel) {
		super(channel);
		this.isDisabled = false;
	}
	
	@Override
	public void set(double speed) {
		if (isDisabled)
			speed = 0;
		super.set(speed);
	}
	
	public void disable() {
		this.isDisabled = true;
	}
	
	public void enable() {
		this.isDisabled = false;
	}
	
	public boolean status() {
		return this.isDisabled;
	}
}
