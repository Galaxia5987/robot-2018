package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem.PIDTypes;

import auxiliary.DistanceMotionProfile;
import auxiliary.Misc;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

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
	private static final double MIN_DEGREES_ERROR = 1; 
	private static final double TURN_CONTROL_FACTOR = 1;
	private DistanceMotionProfile mp;
	private boolean isRelative;
	private double angle;
	private NetworkTableEntry ntAngle = null;
	private NetworkTableEntry ntIsRelative = null;
	private static NetworkTable driveTable = Robot.driveSubsystem.driveTable;
	NetworkTableEntry ntMPoutput = driveTable.getEntry("MP Output");
	NetworkTableEntry ntRotationDegreesError = driveTable.getEntry("Rotation Degrees Error");
	private PIDTypes priorPIDType;
	
	/**
	 * 
	 * @param angle angle in DEGREES
	 * @param isRelative if true, rotate relative to the starting angle of the robot
	 */
    public TurnCommand(double angle, boolean isRelative) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.isRelative = isRelative;
    	this.angle = angle;
    	requires(Robot.driveSubsystem);
    }
    
    /**
     * 
     * @param ntAngle NetworkTableEntry instance to get the angle from, when the command starts
     * @param isRelative if true, rotate relative to the starting angle of the robot
     */
    public TurnCommand(NetworkTableEntry ntAngle, boolean isRelative){
    	this.ntAngle  = ntAngle;
    	this.isRelative = isRelative;
    }
    
    /**
     * 
     * @param ntAngle NetworkTableEntry instance to get the angle from when the command starts
     * @param isRelative NetworkTableEntry instance to get the isRelative variable from, when the command starts. <br>
     * If the isRelative NetworkTableEntry is set to true the robot will rotate relative to its starting angle.
     */
    public TurnCommand(NetworkTableEntry ntAngle, NetworkTableEntry ntIsRelative){
    	this.ntAngle  = ntAngle;
    	this.ntIsRelative = ntIsRelative;
    	
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	if(isFinished()) {
    		this.end();
    		this.cancel();
    	}
    	priorPIDType = Robot.driveSubsystem.getPIDType();
    	Robot.driveSubsystem.setPIDType(DriveSubsystem.PIDTypes.TURN);
    	if(ntAngle != null)
    		angle = ntAngle.getDouble(0);
    	if(ntIsRelative != null)
    		isRelative = ntIsRelative.getBoolean(true);
    	
    	startAngle = Robot.driveSubsystem.getAngle();
    	
    	if(isRelative){
    		this.desiredAngle = angle;
    	}else{
    		desiredAngle = Misc.absoluteToRelativeAngle(angle, startAngle);
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
    			DriveSubsystem.DECCELERATION
    			);
    }
    
    /**
     * @return how many DEGREES the robot has turned since the begging of the command.
     */
    private double getDeltaAngle(){
    	return Robot.driveSubsystem.getAngleRadians() - startAngle;
    }
    
    /**
     * @return the distance one of the wheels has passed on the perimeter of the rotation circle (circumference) since the begging of the command.
     */
    private double getDeltaDistance(){
    	return getDeltaAngle() * DriveSubsystem.ROTATION_RADIUS;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double out = mp.getV(getDeltaDistance()) * TURN_CONTROL_FACTOR;
    	ntMPoutput.setDouble(out);
    	Robot.driveSubsystem.setSetpoints(-out, out);
    	Robot.driveSubsystem.updatePID();
    	degreesError = Math.toDegrees(desiredAngle - getDeltaAngle());
    	ntRotationDegreesError.setDouble(degreesError);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(degreesError) < MIN_DEGREES_ERROR) && Math.abs(Robot.driveSubsystem.getLeftSpeed()) < DriveSubsystem.MIN_VELOCITY / 2 ;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);
    	Robot.driveSubsystem.setRightSpeed(0);
    	Robot.driveSubsystem.setSetpoints(0, 0);
    	Robot.driveSubsystem.setPIDType(priorPIDType);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	this.cancel();
    }
}
