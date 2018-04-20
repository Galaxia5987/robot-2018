package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.IllegalUseOfCommandException;

/**
 *
 */
public class TakeCommand extends Command {
	private double intakeSpeed = Constants.TAKE_INTAKE_SPEED * Constants.TAKE_INTAKE_DIRECTION;
	private double gripperSpeed = Constants.TAKE_GRIPPER_SPEED * Constants.TAKE_GRIPPER_DIRECTION;
	private double delay = 0;
	private double operationTime = 100000000;
	private Timer timer = new Timer();
	private boolean overrideProximitySensor = false;
	private boolean cancel = false;
    public TakeCommand() {
    	requires(Robot.gripperSubsystem);
    	requires(Robot.intakeSubsystem);
    }
    
    public TakeCommand(double delay, double operationTime){
    	this();
    	this.delay = delay;
    	this.operationTime = operationTime;
    	overrideProximitySensor = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(delay + operationTime);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!isFinished() && timer.get() > delay){
			Robot.intakeSubsystem.setSpeed(intakeSpeed, intakeSpeed); // TODO: check the actual directions
			Robot.gripperSubsystem.setSpeed(gripperSpeed , gripperSpeed);
    	}
	

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(timer.get() < delay)
    		return false;
    	boolean isCubeInside = overrideProximitySensor ? false : Robot.gripperSubsystem.isCubeInside();
        return cancel || (isTimedOut() || isCubeInside ||!Robot.liftSubsystem.reachedBottom()) && !Robot.m_oi.xbox.getRawButton(OI.TakeCommandButton);
    	
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
		try {
			this.cancel();
		} catch (IllegalUseOfCommandException e) {
			cancel = true;
		}
    }
}
