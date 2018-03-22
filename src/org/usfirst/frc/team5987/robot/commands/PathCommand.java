package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import auxiliary.surfaceMP;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class PathCommand extends Command {

	private double x = 0, y = 0;
	private double preLeftDistance = 0;
	private double preRightDistance = 0;
	private Point endPoint;
	surfaceMP train;
	private double pointX;
	private double pointY;
	private boolean isRelative;
	
	/**
	 * 
	 * @return an array of items of Point(s) Class 
	 */
	public abstract Point[] getPoints(); 
	
	public PathCommand(boolean isRelative, double timeOut) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    	this.isRelative = isRelative;
    	if (timeOut != 0)
    		setTimeout(timeOut);
    }
	
	public PathCommand(boolean isRelative) {
		this(isRelative,0);
	}
	
	public PathCommand(){
		this(true,0);
     }

    // Called just before this Command runs the first time
    protected void initialize() {
    	preLeftDistance = Robot.driveSubsystem.getLeftDistance();
    	preRightDistance = Robot.driveSubsystem.getRightDistance();
    	
    	double camStartX = Constants.PATH_h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double camStartY = Constants.PATH_h * Math.sin(Robot.driveSubsystem.getAngleRadians());

    	
    	//	Add first point
		Point[] pWithoutFirst = getPoints();
		Point[] p = new Point[pWithoutFirst.length + 1];
		for(int i=1; i<p.length; i++)
			p[i] = pWithoutFirst[i - 1];
		p[0] = isRelative ? new Point(0, 0) : new Point(Robot.robotAbsolutePosition[0], Robot.robotAbsolutePosition[1]);
			
    	x = p[0].get()[0];
    	y = p[0].get()[1];
		
		for (int i = 0; i < p.length; i++) {
			double[] cords = p[i].get();
			p[i].setPoint(cords[0]+ camStartX, cords[1] + camStartY);
		}
				
		train = new surfaceMP(p, Constants.DRIVE_MAX_VELOCITY, Constants.DRIVE_ACCELERATION, Constants.DRIVE_DECCELERATION, Constants.DRIVE_MIN_VELOCITY, 0, Constants.ERROR_ACCELERATION);
		train.setRatio(Constants.PATH_h, 0.375);
		endPoint = p[p.length-1];
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
    	
    	
    	pointX = x + Constants.PATH_h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	pointY = y + Constants.PATH_h * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	
    	Point pos = new Point(pointX, pointY);
    	double[] velocitys = train.getMotorsVelocity(pos, Robot.driveSubsystem.getAngleRadians());
    	Robot.driveSubsystem.setSetpoints(velocitys[0], velocitys[1]);
    	
//    	SmartDashboard.putNumber("x", x);
//    	SmartDashboard.putNumber("y", y);
//    	SmartDashboard.putNumber("point x", pointX);
//    	SmartDashboard.putNumber("point y", pointY);
//    	SmartDashboard.putNumber("velo left", velocitys[0]);
//    	SmartDashboard.putNumber("velo right", velocitys[1]);
    	
    	Robot.driveSubsystem.updatePID(); // TODO: remove comment
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double[] end  = endPoint.get();
    	return Math.abs(end[0] - pointX) < 0.05 && Math.abs(end[1] - pointY) < 0.05 || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);

    	Robot.driveSubsystem.setRightSpeed(0);
    	
		Robot.driveSubsystem.setSetpoints(0, 0);
		if(isRelative){
			Robot.robotAbsolutePosition[0] += x;
			Robot.robotAbsolutePosition[1] += y;
		}else{
			Robot.robotAbsolutePosition[0] = x;
			Robot.robotAbsolutePosition[1] = y;
		}

}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}