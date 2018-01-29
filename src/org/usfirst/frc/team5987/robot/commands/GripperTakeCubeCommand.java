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
	}

	protected void execute() {
		if (Robot.liftSubsystem.isDown() && !Robot.gripperSubsystem.isCubeInside()) {
			Robot.gripperSubsystem.setSpeed(0.5, 0.5);
			Robot.gripperSubsystem.ntProximityVoltage.setDouble(Robot.gripperSubsystem.voltage());
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
	}
}
