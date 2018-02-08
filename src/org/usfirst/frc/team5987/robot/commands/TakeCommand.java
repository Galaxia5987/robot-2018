package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TakeCommand extends Command {
	private static final double WHEELSPEED = 0.5;
	private boolean canceled = false;
    public TakeCommand() {
    	requires(Robot.gripperSubsystem);
    	requires(Robot.intakeSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	canceled = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!isFinished()){
			Robot.intakeSubsystem.setSpeed(-WHEELSPEED, -WHEELSPEED); // TODO: check the actual directions
			Robot.gripperSubsystem.setSpeed(-WHEELSPEED , -WHEELSPEED);
    	}
	

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
        return (Robot.gripperSubsystem.isCubeInside() ||!Robot.liftSubsystem.reachedBottom()) && !Robot.m_oi.xbox.getRawButton(Robot.m_oi.TakeCommandButton);
    	
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.gripperSubsystem.setSpeed(0.0, 0.0);
		Robot.intakeSubsystem.setSpeed(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		this.end();
		this.cancel();
    }
}
