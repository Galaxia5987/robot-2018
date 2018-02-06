package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeTakeCubeCommand extends Command {
	private static final double WHEELSPEED = 0.5;
	private boolean canceled = false;
	public IntakeTakeCubeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.wheelSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		canceled = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if (Robot.liftSubsystem.isDown() && !Robot.wheelSubsystem.isCubeInside())
			Robot.wheelSubsystem.setSpeedIntake(-WHEELSPEED, -WHEELSPEED); // TODO: check the actual directions
		else canceled = true;

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gripperSubsystem.isCubeInside() || canceled ||!Robot.liftSubsystem.isDown();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0, 0);
	}
	
	protected void interrupted() {
		this.end();
		this.cancel();
	}
}
