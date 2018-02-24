package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	public LiftCommand(double pos, boolean isShuffleboard){
		this.position = pos;
		this.isShuffleboard = isShuffleboard;
		ntSetpoint.setDouble(0);
		requires(Robot.liftSubsystem);
	}
	
	public LiftCommand(double pos) {
		this(pos, false);
	}

	public LiftCommand(liftStates state) {
		this(0, false);
		switch (state) {
		default:
		case BOTTOM:
			this.position = 0.0;
			break;
		case SWITCH:
			this.position = 0.85;
			break;
		case SCALE_DOWN:
			this.position = 1.5;
			break;
		case SCALE_MID:
			this.position = 1.75;
			break;
		case SCALE_TOP:
			this.position = 2;
			break;
		case CLIMB:
			this.position = 1.65;
			break;
		}
		SmartDashboard.putNumber("LiftCommand.position", this.position);
	
	}

	public LiftCommand() {
		this(0, true);
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