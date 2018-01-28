package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripperTakeCubeCommand extends Command {

	public GripperTakeCubeCommand() {
		requires(Robot.gripperSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.gripperSubsystem.setSpeed(0.5, 0.5);
		Robot.gripperSubsystem.ntProximityVoltage.setDouble(Robot.gripperSubsystem.voltage());
	}

	protected boolean isFinished() {
		return Robot.gripperSubsystem.isCubeInside();
	}

	protected void end() {
		Robot.gripperSubsystem.setSpeed(0.0, 0.0);
	}

	protected void interrupted() {
	}
}
