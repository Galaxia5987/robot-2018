package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Dan Katzuv
 */
public class IntakeSpringSolenoidCommand extends Command {
	public enum opMode {
		TOGGLE, LOCK, UNLOCK
	}

	private opMode chosen;
	private Timer timer = new Timer();
	private double delay = 0;

	/**
	 * A command that controls the spring solenoid locking the Intake. If the constructor
	 * is left blank the Intake will just toggle.
	 * 
	 */
	public IntakeSpringSolenoidCommand(double delay) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		chosen = opMode.TOGGLE;
		this.delay = delay;
//		requires(Robot.intakeSubsystem);
	}

	public IntakeSpringSolenoidCommand(boolean state, double delay) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this(state);
		this.delay = delay;
	}

	/**
	 * A command that controls the solenoid moving the intake up. If the constructor
	 * is left blank the intake will just toggle.
	 * 
	 * @author Paulo Khayat
	 */
	public IntakeSpringSolenoidCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		chosen = opMode.TOGGLE;
//		requires(Robot.intakeSubsystem);
	}

	/**
	 * A command that controls the spring solenoid locking the Intake.
	 * 
	 * @param toLock - Whether theIintake should be locked.
	 */
	public IntakeSpringSolenoidCommand(boolean toLock) {
		if (toLock)
			chosen = opMode.LOCK;
		else
			chosen = opMode.UNLOCK;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (isFinished()) {
			switch (chosen) {
			case TOGGLE:
				Robot.intakeSubsystem.setSpringSolenoid(!Robot.intakeSubsystem.getSpringSolenoid());
				break;
			case LOCK:
				Robot.intakeSubsystem.setSpringSolenoid(true);
				break;
			case UNLOCK:
				Robot.intakeSubsystem.setSpringSolenoid(false);
				break;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() >= delay;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
