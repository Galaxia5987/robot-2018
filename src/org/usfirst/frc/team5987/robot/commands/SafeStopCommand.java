package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SafeStopCommand extends Command {

	double startSpeed;
	Timer t = new Timer();
	
    public SafeStopCommand(double speed, double timeout) {
        // Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
		setTimeout(timeout);
		startSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t.reset();
    	t.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double delta = t.get() * Constants.DRIVE_DECCELERATION * 0.4; //times constant 0.4
     	Robot.driveSubsystem.setSetpoints(startSpeed - delta,startSpeed - delta);
		Robot.driveSubsystem.updatePID();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
     	Robot.driveSubsystem.setSetpoints(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
