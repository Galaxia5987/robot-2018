package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACoolName
 * @version 1.0
 */
public class IntakeSubsystem extends Subsystem {

	// Defining motors and piston
	private Victor intakeMotorLeft = new Victor(RobotMap.intakeMotorLeft);
	private Victor intakeMotorRight = new Victor(RobotMap.intakeMotorRight);
	private DoubleSolenoid solenoid = new DoubleSolenoid(RobotMap.intakeSolenoid1, RobotMap.intakeSolenoid2);

	/**
	 * Set the speed for the intake wheels
	 * 
	 * @param speedLeft - speed between -1 and 1.
	 * @param speedRight - speed between -1 and 1.
	 */
	public void setSpeed(double speedLeft, double speedRight) {
		// Basic function to set motor speed
		intakeMotorLeft.set(speedLeft);
		intakeMotorRight.set(-speedRight);
	}

	/**
	 * Open the intake mechanism (expends beyond the frame perimeter).
	 * 
	 * @param open - whether you want to open the mechanism.
	 */
	public void setSolenoid(boolean open) {
		// open decides weather to open the piston or close it
		if (open) {
			solenoid.set(DoubleSolenoid.Value.kForward);
		} else {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
	public boolean getSolenoid() {
		return (solenoid.get() == DoubleSolenoid.Value.kForward);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
