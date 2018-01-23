package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;

import auxiliary.DistanceMotionProfile;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnCommand extends Command {
	/**
	 * Desired rotation angle relative to the starting angle in RADIANS
	 */
	private double desiredAngle;
	/**
	 * Starting angle in RADIANS
	 */
	private double startAngle;
	/**
	 * The delta between the current angle to the desired in DEGREES
	 */
	private double degreesError;
	/**
	 * If the absolute angle error is less than that, the command will stop
	 */
	private static final double MIN_DEGREES_ERROR = 3; 
	
	private DistanceMotionProfile mp;
	
	/**
	 * 
	 * @param angle angle in DEGREES
	 * @param isRelative if true, rotate relative to the starting angle of the robot
	 */
    public TurnCommand(double angle, boolean isRelative) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	startAngle = Robot.driveSubsystem.getAngle();
    	
    	if(isRelative){
    		this.desiredAngle = angle;
    	}else{
    		double currentAngle = startAngle % 360 ; // (angle % 360deg) = angle from 0 to 360
    		double oneWay = angle - currentAngle; // not passing through 0deg
    		double orAnother = angle + (360 - currentAngle); // passing through 0deg
    		double gonnaGetYa; // optimal way
    		if(Math.abs(oneWay) < Math.abs(orAnother)){
    			gonnaGetYa = oneWay; 
    		}else{
    			gonnaGetYa = orAnother;
    		}
    		desiredAngle = gonnaGetYa;
    	}
		// convert to radians
		this.desiredAngle = Math.toRadians(this.desiredAngle);
		this.startAngle = Math.toRadians(this.startAngle);
		
		// motion profile that has input of circumference (distance)
    	mp = new DistanceMotionProfile(
    			desiredAngle * DriveSubsystem.ROTATION_RADIUS,
    			DriveSubsystem.MAX_VELOCITY,
    			DriveSubsystem.MIN_VELOCITY,
    			DriveSubsystem.ACCELERATION,
    			DriveSubsystem.DECCELERATION);
    	
    	requires(Robot.driveSubsystem);
    }
    
    /**
     * @return how many DEGREES the robot has turned.
     */
    private double getDeltaAngle(){
    	return Robot.driveSubsystem.getAngleRadians() - startAngle;
    }
    
    /**
     * @return the distance one of the wheels has passed on the perimeter of the rotation circle (circumference).
     */
    private double getDeltaDistance(){
    	return getDeltaAngle() * DriveSubsystem.ROTATION_RADIUS;
    } 
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double out = mp.getV(getDeltaDistance());
    	Robot.driveSubsystem.setSetpoints(out, -out);
    	Robot.driveSubsystem.updatePID();
    	degreesError = Math.toDegrees(desiredAngle - getDeltaAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(degreesError) < MIN_DEGREES_ERROR;
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
