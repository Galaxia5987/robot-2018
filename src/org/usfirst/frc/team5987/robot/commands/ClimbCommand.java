package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for climbing on the Rung.
 * 
 * @author Dan Katzuv
 */
public class ClimbCommand extends Command {

	/**
	 * Whether the climbing mechanism should work.
	 */
	private boolean doesGoUp;

	/**
	 * Speed climbing of the robot.
	 */
	private double climbSpeed;

	public ClimbCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climbSubsystem);
	}

	/**
	 * Constructor
	 * @param turnOn - Whether the climbing mechanism should work.
	 */
	public ClimbCommand(boolean turnOn) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climbSubsystem);
		this.doesGoUp = turnOn;
	}

	/**
	 * Constructs the climb command.
	 * @param turnOn - Whether the climbing mechanism should work.
	 * @param climbSpeed - Climbing speed of the robot.
	 */
	public ClimbCommand(boolean turnOn, double climbSpeed) {
		requires(Robot.climbSubsystem);
		this.doesGoUp = turnOn;
		this.climbSpeed = climbSpeed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double speed = doesGoUp ? climbSpeed : 0;
		Robot.climbSubsystem.setClimbSpeed(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// finish if reached top or supposed to stop
		return Robot.climbSubsystem.hasReachedTop() || !doesGoUp;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.climbSubsystem.setClimbSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
