package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class liftCommand extends Command {
	double position;
	private boolean isShuffleboard;
	NetworkTableEntry ntSetpoint = Robot.liftSubsystem.LiftTable.getEntry("Setpoint");
	
    public liftCommand(double pos) {
        this.position=pos;
        this.isShuffleboard = false;
    	// Use requires() here to declare subsystem dependencies
        requires(Robot.liftSubsystem);
    }
    
    public liftCommand(){
    	this.isShuffleboard = true;
    	// Use requires() here to declare subsystem dependencies
        requires(Robot.liftSubsystem);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	if(isShuffleboard)
    		this.position = ntSetpoint.getDouble(0);
    	Robot.liftSubsystem.setSetpoint(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.liftSubsystem.setSetpoint(position);
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
