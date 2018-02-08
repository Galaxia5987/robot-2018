package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.SafeVictor;
import auxiliary.Watch_Dogeable;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author ACoolName
 * @version 1.0
 */
public class IntakeSubsystem extends Subsystem implements Watch_Dogeable {

	// Defining motors and piston
	private SafeVictor intakeMotorLeft = new SafeVictor(RobotMap.intakeMotorLeft);
	private SafeVictor intakeMotorRight = new SafeVictor(RobotMap.intakeMotorRight);
	private DoubleSolenoid solenoid = new DoubleSolenoid(1,RobotMap.intakeSolenoid1, RobotMap.intakeSolenoid2);
	Timer downTimer = new Timer();

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
		if (solenoid.get() == DoubleSolenoid.Value.kForward) return true;
		return false;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	@Override
	public void bork() {
		intakeMotorLeft.disable();
		intakeMotorRight.disable();
		downTimer.reset();
		downTimer.start();
	}

	@Override
	public void necromancy() {
		intakeMotorLeft.enable();
		intakeMotorRight.enable();
	}

	@Override
	public boolean wakeMeUp() {
		if (downTimer.get() >= 10) {
			downTimer.stop();
			downTimer.reset();
			return true;
		}
		return false;
	}

	@Override
	public boolean ded() {
		return intakeMotorLeft.status() && intakeMotorRight.status();
	}
}