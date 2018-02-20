package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WaitToTargetCommand extends Command {
	private NetworkTableEntry ntTarget;
    public WaitToTargetCommand(NetworkTableEntry ntTarget, double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.ntTarget= ntTarget;
    	setTimeout(timeout);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	ntTarget.setBoolean(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (ntTarget.getBoolean(false) && Robot.ntVisionDistance.getDouble(99) < 4) || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
