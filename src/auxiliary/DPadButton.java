package auxiliary;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {
	private GenericHID m_joystick;
	private int m_dPadNumber;

	/**
	 * 
	 * @param dPadNumber
	 *            the direction of the button used (0-7) - 0 is up, and then so
	 *            on clockwise.
	 */
	public DPadButton(XboxController joystick, int dPadNumber) {
		m_joystick = joystick;
		m_dPadNumber = dPadNumber;
	}

	public boolean get() {
		return (m_joystick.getPOV() % 360) / 45  == m_dPadNumber;
	}
}
