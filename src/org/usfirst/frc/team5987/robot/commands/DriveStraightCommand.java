package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;

import auxiliary.MiniPID;
import auxiliary.DistanceMotionProfile;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

/**
 *
 */
public class DriveStraightCommand extends Command {
	/**
	 * Error to stop in METER
	 */
	public static final double MIN_DISTANCE_ERROR = 0.05;
	/**
	 * 
	 * @param distance desired distance for driving in METER
	 */
	private DistanceMotionProfile mp;
	private MiniPID anglePID;
	private double initRightDistance;
	private double initLeftDistance;
	private double angleToKeep;
	private double finalDistance;
	private static NetworkTable driveTable = Robot.driveSubsystem.driveTable;
	NetworkTableEntry ntRightDistanceError = driveTable.getEntry("Right Distance Error");
	NetworkTableEntry ntLeftDistanceError = driveTable.getEntry("Left Distance Error");
	private double rightDistanceError;
	private double leftDistanceError;
	
    public DriveStraightCommand(double distance, double angleToKeep) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.angleToKeep = angleToKeep;
    	this.finalDistance = distance;
    	mp = new DistanceMotionProfile(
    			this.finalDistance,
    			DriveSubsystem.MAX_VELOCITY,
    			DriveSubsystem.MIN_VELOCITY,
    			DriveSubsystem.ACCELERATION,
    			DriveSubsystem.DECCELERATION
    			);
		requires(Robot.driveSubsystem);
    }
    
	public DriveStraightCommand(double distance) {
		this(distance, Robot.driveSubsystem.getAngle());
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	initRightDistance = Robot.driveSubsystem.getRightDistance();
    	initLeftDistance = Robot.driveSubsystem.getLeftDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftDistance = Robot.driveSubsystem.getLeftDistance() - initLeftDistance;
    	double rightDistance = Robot.driveSubsystem.getRightDistance() - initRightDistance;
    	double avgDistance = (leftDistance + rightDistance) / 2;
    	
    	// calculate new output based on the MotionProfile and the gyro
    	double speed = mp.getV(avgDistance);
    	double gyroFix = Robot.driveSubsystem.getGyroPID(angleToKeep);
    	
    	Robot.driveSubsystem.setSetpoints(speed - gyroFix, speed + gyroFix);
    	Robot.driveSubsystem.updatePID();
    	
    	rightDistanceError = finalDistance - rightDistance;
    	ntLeftDistanceError.setDouble(rightDistanceError);
    	leftDistanceError = finalDistance - leftDistance;
    	ntRightDistanceError.setDouble(leftDistanceError);
    	
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean rightOk = Math.abs(rightDistanceError) < MIN_DISTANCE_ERROR;
    	boolean leftOk = Math.abs(leftDistanceError) < MIN_DISTANCE_ERROR;
        return rightOk && leftOk;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);
    	Robot.driveSubsystem.setRightSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	this.cancel();
    }
}
