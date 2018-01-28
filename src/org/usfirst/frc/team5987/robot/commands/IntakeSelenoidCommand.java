package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeSelenoidCommand extends Command {
	boolean down;

	/**
	 * A command that controls the solenoid moving the intake up.
	 * 
	 * @param toDown - Whether the intake should go down.
	 * @author Paulo Khayat
	 */
	public IntakeSelenoidCommand(boolean toDown) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intakeSubsystem);
		down = toDown;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		if (down) {
			Robot.intakeSubsystem.setSolenoid(true);
		} else {
			Robot.intakeSubsystem.setSolenoid(false);
		}

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
