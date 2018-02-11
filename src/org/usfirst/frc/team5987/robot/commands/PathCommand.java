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
	private double h = 0.2;
	private double preLeftDistance = 0;
	private double preRightDistance = 0;
	private Point endPoint;
	surfceMP train;
	private double pointX;
	private double pointY;
	
	
    public PathCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!Robot.ntSwitchTarget.getBoolean(false))
    	{
    		this.cancel();
    	}
    	x = 0; y = 0;
    	preLeftDistance = Robot.driveSubsystem.getLeftDistance();
    	preRightDistance = Robot.driveSubsystem.getRightDistance();
    	double switchDistance = Robot.ntSwitchDistance.getDouble(0);
    	double switchAngle = Robot.ntSwitchAngle.getDouble(0) * Math.PI / 180;
    	switchDistance = switchDistance / Math.cos(switchAngle);
    	double dx = switchDistance * Math.cos(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    	double dy = switchDistance * Math.sin(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    	SmartDashboard.putNumber("d x", dx);
		SmartDashboard.putNumber("d y", dy);
		SmartDashboard.putNumber("c", switchDistance);
		SmartDashboard.putNumber("a", Robot.driveSubsystem.getAngleRadians() + switchAngle);
    	double startX = h * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double startY = h * Math.sin(Robot.driveSubsystem.getAngleRadians());
    	double cameraX = (0.2) * Math.cos(Robot.driveSubsystem.getAngleRadians());
    	double cameraY = (0.2) * Math.sin(Robot.driveSubsystem.getAngleRadians());

    	Point A = new Point(startX, startY);
		Point B = new Point(cameraX + dx - 1.5, cameraY + dy);
		Point C = new Point(cameraX + dx-0.95, cameraY + dy);
		
		SmartDashboard.putNumber("sx", startX);
		SmartDashboard.putNumber("sy", startY);
		SmartDashboard.putNumber("s2x", cameraX + dx - 1);
		SmartDashboard.putNumber("s2y", cameraY + dy);
		SmartDashboard.putNumber("s3x", cameraX + dx);
		SmartDashboard.putNumber("s3y", cameraY + dy);
		
		Point[] p = {A,B,C};
		train = new surfceMP(p, DriveSubsystem.MAX_VELOCITY, DriveSubsystem.ACCELERATION, DriveSubsystem.DECCELERATION, DriveSubsystem.MIN_VELOCITY, 0);
		train.setRatio(h, 0.375);
		preLeftDistance = Robot.driveSubsystem.getLeftDistance();
		preRightDistance = Robot.driveSubsystem.getRightDistance();
		endPoint = C;
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
    	
    	SmartDashboard.putNumber("x", x);
    	SmartDashboard.putNumber("y", y);
    	SmartDashboard.putNumber("point x", pointX);
    	SmartDashboard.putNumber("point y", pointY);
    	SmartDashboard.putNumber("velo left", velocitys[0]);
    	SmartDashboard.putNumber("velo right", velocitys[1]);
    	
    	Robot.driveSubsystem.updatePID(); // TODO: remove comment
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double[] end  = endPoint.get();
    	return Math.abs(end[0] - pointX) < 0.05 && Math.abs(end[1] - pointY) < 0.05;
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