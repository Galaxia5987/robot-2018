package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class JoystickDriveCommand extends Command {

	public JoystickDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// TODO: uncomment
		Robot.driveSubsystem.setLeftSpeed(-Robot.m_oi.left.getY());
		Robot.driveSubsystem.setRightSpeed(-Robot.m_oi.right.getY());
		double rightOut = -Robot.m_oi.right.getY();
		double leftOut = -Robot.m_oi.left.getY();
		double rightV = Robot.driveSubsystem.getRightSpeed();
		double leftV = Robot.driveSubsystem.getLeftSpeed();

		if(leftOut != 0)
			SmartDashboard.putNumber("kF Left", leftV / leftOut);
		else
			SmartDashboard.putNumber("kF Left", 0);
		if(rightOut != 0)
			SmartDashboard.putNumber("kF Right", rightV / rightOut);
		else
			SmartDashboard.putNumber("kF Right", 0);
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
	}
}
