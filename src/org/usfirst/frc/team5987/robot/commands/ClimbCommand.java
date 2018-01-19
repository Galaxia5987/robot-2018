package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for climbing on the Rung.
 * @author Dan Katzuv
 */
public class ClimbCommand extends Command {
	public final double climbSpeed = 1;
	/**
	 * true if should move motors and go up
	 */
	private boolean powerUp;
    public ClimbCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climbSubsystem);
    }
    
    public ClimbCommand(boolean turnOn) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climbSubsystem);
    	this.powerUp = turnOn;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = powerUp ? climbSpeed : 0;
    	Robot.climbSubsystem.setClimbSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// finish if reached top or supposed to stop
        return Robot.climbSubsystem.hasReachedTop() || !powerUp;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.climbSubsystem.setClimbSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
