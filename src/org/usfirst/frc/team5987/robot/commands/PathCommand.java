package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;

import auxiliary.Point;
import auxiliary.surfceMP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PathCommand extends Command {

	private double x = 0, y = 0;
	private double h = 0.4;
	private double preLeftDistance = 0;
	private double preRightDistance = 0;
	surfceMP train;
	
	
    public PathCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Point A = new Point(h, 0);
		Point B = new Point(h+1, 0);
		Point[] p = {A,B};
		train = new surfceMP(p, DriveSubsystem.MAX_VELOCITY, DriveSubsystem.ACCELERATION, DriveSubsystem.DECCELERATION, DriveSubsystem.MIN_VELOCITY, 0);
		train.setRatio(h, 0.375);
		preLeftDistance = Robot.driveSubsystem.getLeftDistance();
		preRightDistance = Robot.driveSubsystem.getRightDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double dl = Robot.driveSubsystem.getLeftDistance() - preLeftDistance;
    	double dr = Robot.driveSubsystem.getRightDistance() - preRightDistance;
    	double avg = (dl + dr) / 2;
    	preLeftDistance = Robot.driveSubsystem.getLeftDistance();
    	preRightDistance = Robot.driveSubsystem.getRightDistance();
    	x += avg * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	y += avg * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	
    	
    	double pointX = x + h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double pointY = y + h * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	
    	Point pos = new Point(pointX, pointY);
    	double[] velocitys = train.getMotorsVelocity(pos, Robot.driveSubsystem.getAngleRadians());
    	Robot.driveSubsystem.setSetpoints(velocitys[0], velocitys[1]);
    	
    	SmartDashboard.putNumber("x", x);
    	SmartDashboard.putNumber("y", y);
    	SmartDashboard.putNumber("point x", pointX);
    	SmartDashboard.putNumber("point y", pointY);
    	SmartDashboard.putNumber("velo left", velocitys[0]);
    	SmartDashboard.putNumber("velo right", velocitys[1]);
    	
//    	Robot.driveSubsystem.updatePID();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);

    	Robot.driveSubsystem.setRightSpeed(0);
    	
		Robot.driveSubsystem.setSetpoints(0, 0);

}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
