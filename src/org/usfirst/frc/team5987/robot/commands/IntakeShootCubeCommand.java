package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeShootCubeCommand extends Command {
	private double wheelSpeed = 0.5;
	private double time;
	private Timer Timer = new Timer();

	public IntakeShootCubeCommand(double timer) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.wheelSubsystem);
		time = timer;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Timer.start();
		Robot.wheelSubsystem.setSpeedIntake(-wheelSpeed, wheelSpeed); // TODO: check the actual directions

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Timer.get() > time;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intakeSubsystem.setSpeed(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
		this.cancel();

	}
}
