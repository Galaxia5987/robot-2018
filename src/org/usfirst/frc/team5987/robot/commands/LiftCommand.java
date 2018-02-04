package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftCommand extends Command {
	double position;
	private boolean isShuffleboard;
	NetworkTableEntry ntSetpoint = Robot.liftSubsystem.LiftTable.getEntry("Setpoint");

	public enum liftStates {
		BOTTOM, SWITCH, SCALE_DOWN, SCALE_MID, SCALE_TOP, CLIMB

	}

	public LiftCommand(double pos) {
		this.position = pos;
		this.isShuffleboard = false;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.liftSubsystem);
	}

	public LiftCommand(liftStates state) {
		switch (state) {
		case BOTTOM:
			this.position = 0.0;
			break;
		case SWITCH:
			this.position = 0.77;
			break;
		case SCALE_DOWN:
			this.position = 1.8;
			break;
		case SCALE_MID:
			this.position = 2;
			break;
		case SCALE_TOP:
			this.position = 2.10;
			break;
		case CLIMB:
			this.position = 2.2;
			break;
		}
	}

	public LiftCommand() {
		this.isShuffleboard = true;
		// Use requires() here to declare subsystem dependencies
		requires(Robot.liftSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (isShuffleboard)
			this.position = ntSetpoint.getDouble(0);
		Robot.liftSubsystem.setSetpoint(position);
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
