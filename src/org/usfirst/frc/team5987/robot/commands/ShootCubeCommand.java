package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class ShootCubeCommand extends Command {
	double speed;
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
		timer.start();
	}

	protected void execute() {
		if (speed < 0) {
			if (Robot.liftSubsystem.getHeight() > Constants.SHOOT_BACKWARDS_MIN_HEIGHT)
				Robot.gripperSubsystem.setSpeed(speed, speed);
		} else {
			Robot.gripperSubsystem.setSpeed(speed, speed);
			if (Robot.liftSubsystem.getHeight() < Constants.SHOOT_FORWARD_INTAKE_MAX_HEIGHT)
				Robot.intakeSubsystem.setSpeed(speed, speed);
		}
	}

	protected boolean isFinished() {
		return byTime && timer.get() > Constants.SHOOT_TIME;
	}

	protected void end() {
		Robot.gripperSubsystem.setSpeed(0, 0);
		Robot.intakeSubsystem.setSpeed(0, 0);
	}

	protected void interrupted() {
		end();
		this.cancel();
	}
}
