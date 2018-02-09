package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickLiftCommand extends Command {
	private final double CHANGE_DOWN_SPEED = 0.02;
	private final double CHANGE_UP_SPEED = 0.08;
    public JoystickLiftCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.liftSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double y = -Robot.m_oi.xbox.getY();
    	double change;
    	if(y > 0)
    		change = y * CHANGE_UP_SPEED;
    	else
    		change = y * CHANGE_DOWN_SPEED;
    	if(Math.abs(y)> 0.1)
    		Robot.liftSubsystem.setSetpoint(Robot.liftSubsystem.getHeight() + change);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	cancel();
    }
}
