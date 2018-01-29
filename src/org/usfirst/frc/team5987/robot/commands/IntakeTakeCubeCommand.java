package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeTakeCubeCommand extends Command {
	private double wheelSpeed = 0.5;
	private boolean canceled = false;
	public IntakeTakeCubeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (Robot.liftSubsystem.isDown() && !Robot.gripperSubsystem.isCubeInside())
			Robot.intakeSubsystem.setSpeed(wheelSpeed, -wheelSpeed); // TODO: check the actual directions
		else canceled = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gripperSubsystem.isCubeInside() || canceled;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.intakeSubsystem.setSpeed(0, 0);
	}
}
