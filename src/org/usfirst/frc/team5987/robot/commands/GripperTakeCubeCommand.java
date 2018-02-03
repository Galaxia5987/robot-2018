package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripperTakeCubeCommand extends Command {
	private boolean canceled = false;
	public GripperTakeCubeCommand() {
		requires(Robot.gripperSubsystem);
	}

	protected void initialize() {
		canceled = false;
	}

	protected void execute() {
		if (Robot.liftSubsystem.isDown() && !Robot.gripperSubsystem.isCubeInside()) {
			Robot.gripperSubsystem.setSpeed(0.3, 0.3);
		}
		else canceled = true;
	}

	protected boolean isFinished() {
		return Robot.gripperSubsystem.isCubeInside() || canceled;
	}

	protected void end() {
		Robot.gripperSubsystem.setSpeed(0.0, 0.0);
	}

	protected void interrupted() {

		Robot.gripperSubsystem.setSpeed(0, 0);
		canceled = true;
	}
}
