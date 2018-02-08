package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;

import auxiliary.Point;
import auxiliary.surfceMP;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathCommand extends Command {

	private static final double MIN_DISTANCE_ERROR = 0.05;
	private double x = 0, y = 0;
	private double h = 0.2;
	private double preLeftDistance = 0;
	private double preRightDistance = 0;
	private Point endPoint;
	surfceMP train;
	private double pointX;
	private double pointY;
	
	private Point[] points;
	
	
    public PathCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	points = null;
    }
    
    public PathCommand(Point[] points) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.points = points;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Point[] p;
    	
    	x = 0; y = 0;
    	preLeftDistance = Robot.driveSubsystem.getLeftDistance();
    	preRightDistance = Robot.driveSubsystem.getRightDistance();
    	
    	double startX = h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double startY = h * Math.sin(Robot.driveSubsystem.getAngleRadians());

    	Point A = new Point(startX, startY);
    	
    	if (points == null) {
    		if (!Robot.ntSwitchTarget.getBoolean(false))
    			this.cancel();
    		double switchDistance = Robot.ntSwitchDistance.getDouble(0);
    		double switchAngle = Robot.ntSwitchAngle.getDouble(0) * Math.PI / 180;
    		switchDistance = switchDistance / Math.cos(switchAngle);
    		double dx = switchDistance * Math.cos(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    		double dy = switchDistance * Math.sin(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    		Point B = new Point(startX + dx - 1.5, startY + dy);
    		Point C = new Point(startX + dx - 0.95, startY + dy);
    		endPoint = C;
    		p = new Point[]{A,B,C};
    	}else {
    		endPoint = points[points.length-1];
    		p = points;
    	}
		
		train = new surfceMP(p, DriveSubsystem.MAX_VELOCITY, DriveSubsystem.ACCELERATION, DriveSubsystem.DECCELERATION, DriveSubsystem.MIN_VELOCITY, 0);
		train.setRatio(h, 0.375);
		
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
    	
    	
    	pointX = x + h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	pointY = y + h * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	
    	Point pos = new Point(pointX, pointY);
    	double[] velocitys = train.getMotorsVelocity(pos, Robot.driveSubsystem.getAngleRadians());
    	Robot.driveSubsystem.setSetpoints(velocitys[0], velocitys[1]);
    	
    	Robot.driveSubsystem.updatePID();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double[] end  = endPoint.get();
    	return Math.abs(end[0] - pointX) < MIN_DISTANCE_ERROR && Math.abs(end[1] - pointY) < MIN_DISTANCE_ERROR;
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
