package org.usfirst.frc.team5987.robot.commands;

import java.util.HashMap;

import org.usfirst.frc.team5987.robot.Constants;
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

	public LiftCommand(double pos, boolean isShuffleboard){
		this.position = pos;
		this.isShuffleboard = isShuffleboard;
		ntSetpoint.setDouble(0);
		Constants.LIFT_COMMAND_POSITIONS.put(Constants.LiftCommandStates.BOTTOM, 5d);
		double x = Constants.LIFT_COMMAND_POSITIONS.get(Constants.LiftCommandStates.BOTTOM);
		requires(Robot.liftSubsystem);
	}
	
	public LiftCommand(double pos) {
		this(pos, false);
	}

	public LiftCommand(Constants.LiftCommandStates state) {
		this(0, false);
		this.position = Constants.LIFT_COMMAND_POSITIONS.get(state);
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