package org.usfirst.frc.team5987.robot.commands;

import java.util.HashMap;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LiftCommand extends Command {
	double position;
	private boolean isShuffleboard;
	private double delay = 0;
	private Timer timer = new Timer();
	NetworkTableEntry ntSetpoint = Robot.liftSubsystem.LiftTable.getEntry("Setpoint");

	public LiftCommand(double pos, boolean isShuffleboard){
		this.position = pos;
		this.isShuffleboard = isShuffleboard;
		ntSetpoint.setDouble(0);
		requires(Robot.liftSubsystem);
	}
	
	public LiftCommand(double pos) {
		this(pos, false);
	}
	
	public LiftCommand(double pos, double delayBeforeStart) {
		this(pos, false);
		this.delay = delayBeforeStart;
	}

	public LiftCommand(Constants.LiftCommandStates state) {
		this(0, false);
		this.position = Constants.LIFT_COMMAND_POSITIONS.get(state);
		SmartDashboard.putNumber("LiftCommand.position", this.position);
	
	}
	
	public LiftCommand(Constants.LiftCommandStates state, double delayBeforeStart) {
		this(state);
		this.delay = delayBeforeStart;
	}

	public LiftCommand() {
		this(0, true);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
//		if (isShuffleboard)
//			this.position = ntSetpoint.getDouble(0);
//		Robot.liftSubsystem.setSetpoint(position);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() { 
		if(isFinished()) {
			if (isShuffleboard)
				this.position = ntSetpoint.getDouble(0);
			Robot.liftSubsystem.setSetpoint(position);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() >= delay;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
