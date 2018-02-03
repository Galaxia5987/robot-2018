package org.usfirst.frc.team5987.robot.commands;

import org.omg.CORBA.Request;
import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;

import auxiliary.Point;
import auxiliary.surfceMP;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathCommand extends Command {

	private double x = 0, y = 0;
	private double h = 1;
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
    	Point A = new Point(0, 0);
		Point B = new Point(6, 8);
		Point C = new Point(14, 8);
		Point D = new Point(16, 14);
		Point[] p = {A,B,C,D};
		train = new surfceMP(p, DriveSubsystem.MAX_VELOCITY, DriveSubsystem.ACCELERATION, DriveSubsystem.DECCELERATION, DriveSubsystem.MIN_VELOCITY, 0);
		train.setRatio(h, 0.5);
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
    	
    	double PointX = x + h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double PointY = y + h * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	
    	Point pos = new Point(PointX, PointY);
    	double[] velocitys = train.getMotorsVelocity(pos, Robot.driveSubsystem.getAngleRadians());
    	Robot.driveSubsystem.setSetpoints(velocitys[0], velocitys[1]);
    	
    	Robot.driveSubsystem.updatePID();
    	
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
