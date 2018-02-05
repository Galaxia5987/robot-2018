package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeSelenoidCommand extends Command {
	private enum opMode {
		TOGGLE,
		OPEN,
		CLOSE
	}
	private opMode chosen;
	/**
	 * A command that controls the solenoid moving the intake up.
	 * If the constructor is left blank the intake will just toggle.
	 * 
	 * @author Paulo Khayat
	 */
	public IntakeSelenoidCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		chosen = opMode.TOGGLE;
		requires(Robot.intakeSubsystem);
	}
	
	/**
	 * A command that controls the solenoid moving the intake up.
	 * If the constructor is left blank the intake will just toggle.
	 * 
	 * @param toDown - Whether the intake should go down.
	 * @author Paulo Khayat
	 */
	public IntakeSelenoidCommand(boolean state){
		if(state)
			chosen = opMode.OPEN;
		else
			chosen = opMode.CLOSE;
	}
	// Called just before this Command runs the first time
	protected void initialize() {
		switch(chosen){
		case TOGGLE:
			Robot.intakeSubsystem.setSolenoid(!Robot.intakeSubsystem.getSolenoid());
			break;
		case OPEN:
			Robot.intakeSubsystem.setSolenoid(true);
			break;
		case CLOSE:
			Robot.intakeSubsystem.setSolenoid(false);
			break;
			
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
