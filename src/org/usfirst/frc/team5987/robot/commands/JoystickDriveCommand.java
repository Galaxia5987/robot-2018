package org.usfirst.frc.team5987.robot.commands;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class JoystickDriveCommand extends Command {
	private ArrayList<Double> rightLastN = new ArrayList();
	private ArrayList<Double> leftLastN = new ArrayList();
	private final boolean easyDriving = false;
	public JoystickDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		for(int i=0; i < 5; i++){
			rightLastN.add(0d);
			leftLastN.add(0d);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// TODO: uncomment
		double left = -Robot.m_oi.left.getY();
		double right = -Robot.m_oi.right.getY();
		if(easyDriving){
			left = -Robot.m_oi.left.getY() * Math.abs(Robot.m_oi.left.getY());
			right = -Robot.m_oi.right.getY() * Math.abs(Robot.m_oi.right.getY());
//			rightLastN.add(right);
//			leftLastN.add(left);
//			rightLastN.remove(0);
//			leftLastN.remove(0);
//			double sumR = 0, sumL = 0;
//			for(double inputs : rightLastN){
//				sumR += inputs;
//			}
//			for(double inputs : leftLastN){
//				sumL += inputs;
//			}
//			left = sumL / leftLastN.size();
//			right = sumR / rightLastN.size();
		}
		Robot.driveSubsystem.setLeftSpeed(left);
		Robot.driveSubsystem.setRightSpeed(right);
		
		
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
