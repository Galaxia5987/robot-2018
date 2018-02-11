package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickLiftCommand extends Command {
	/**
	 * The Y value area in which the xbox joystick won't make the lift move.
	 */
	private static final double XBOX_JOYSTICK_DEAD_BAND = 0.2;
	/**
	 * The rate at which the lift will goes down with the xbox joystick.
	 */
	private static final double DOWN_SPEED_RATE = 0.02;
	/**
	 * The rate at which the lift will goes up with the xbox joystick.
	 */
	private static final double UP_SPEED_RATE = 0.08;
    public JoystickLiftCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.liftSubsystem);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double y = -Robot.m_oi.xbox.getY(); // invert the input to make up positive and down negative
    	if(Math.abs(y) < XBOX_JOYSTICK_DEAD_BAND)
    		return;
    	// MAPPING (|dead-band to 1| -> |0 to 1|) AND shit (shit's on fire yo)
    	y -= y > 0 ? XBOX_JOYSTICK_DEAD_BAND : -XBOX_JOYSTICK_DEAD_BAND;
    	y *= 1 / (1 - XBOX_JOYSTICK_DEAD_BAND);
    	double change;
    	if(y > 0)
    		change = y * UP_SPEED_RATE;
    	else
    		change = y * DOWN_SPEED_RATE;
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
