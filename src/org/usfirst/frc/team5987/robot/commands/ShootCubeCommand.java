package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootCubeCommand extends Command {

	double speed;
	double shootTime = 5;
	boolean byTime = false;
	Timer timer = new Timer();

	public ShootCubeCommand(double speed, boolean byTime) {
		this.speed = speed;
		this.byTime = byTime;
		requires(Robot.gripperSubsystem);
		requires(Robot.intakeSubsystem);
	}

	protected void initialize() {
		timer.reset();
	}

	protected void execute() {
		if (speed < 0) {
			if (Robot.liftSubsystem.getHeight() > 0.8)
				Robot.gripperSubsystem.setSpeed(speed, speed);
		} else {
			Robot.gripperSubsystem.setSpeed(speed, speed);
			if (Robot.liftSubsystem.getHeight() < 0.1)
				Robot.intakeSubsystem.setSpeed(speed, speed);
		}
	}

	protected boolean isFinished() {
		return byTime && timer.get() > shootTime;
	}

	protected void end() {
		Robot.gripperSubsystem.setSpeed(0, 0);
		Robot.intakeSubsystem.setSpeed(0, 0);
	}

	protected void interrupted() {
		Robot.gripperSubsystem.setSpeed(0, 0);
		Robot.intakeSubsystem.setSpeed(0, 0);
		this.cancel();
	}
}
