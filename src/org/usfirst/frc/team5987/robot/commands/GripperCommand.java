package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripperCommand extends Command {
	
    public GripperCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gripperSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gripperSubsystem.ntProximityVoltage.setDouble(Robot.gripperSubsystem.voltage());
    	Robot.gripperSubsystem.ntSeesCube.setBoolean(Robot.gripperSubsystem.isCubeInside());
    	Robot.gripperSubsystem.setSpeed(0.5, 0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.gripperSubsystem.isCubeInside();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gripperSubsystem.setSpeed(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
