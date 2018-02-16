package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.DistanceMotionProfile;
import auxiliary.Misc;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * TODO: REMOVE (-) minus in ntDistance!!!
 * TODO: fix absolute angle
 */
public class DriveStraightCommand extends Command {
	private DistanceMotionProfile mp;
	private double initRightDistance;
	private double initLeftDistance;
	private double angleToKeep;
	private double finalDistance;
	private boolean keepStartingAngle = false;
	private static NetworkTable driveTable = Robot.driveSubsystem.driveTable;
	NetworkTableEntry ntAngleToKeep;
	NetworkTableEntry ntFinalDistance;
	NetworkTableEntry ntDistanceError = driveTable.getEntry("Distance Error");
	NetworkTableEntry ntMPoutput = driveTable.getEntry("MP Output");
	private double DistanceError;
	NetworkTableEntry ntShowAngleToKeep = driveTable.getEntry("Show Angle To Keep");
	private double absDistanceAddition = 0;
	
	

	/**
	 * 
	 * @param distance
	 *            desired distance, distance from target
	 * @param angleToKeep
	 *            The fixed angle the robot will be heading to
	 */
	public DriveStraightCommand(double distance, double angleToKeep) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.angleToKeep = angleToKeep;
		this.finalDistance = distance;
		keepStartingAngle = false;
		constructMotionProfile();
		requires(Robot.driveSubsystem);
	}
	
	/**
	 * Uses values from the Shuffleboard.
	 * @param distance
	 *            desired distance, distance from target
	 * @param angleToKeep
	 *            The fixed angle the robot will be heading to
	 */
	public DriveStraightCommand(NetworkTableEntry distance, NetworkTableEntry angleToKeep) {
		this.ntAngleToKeep = angleToKeep;
		this.ntFinalDistance = distance;
		keepStartingAngle = false;
		requires(Robot.driveSubsystem);
	}

	/**
	 * The robot will keep (heading to) its starting angle
	 * 
	 * @param distance
	 *            desired distance, distance from target
	 */
	public DriveStraightCommand(double distance) {
		this(distance, 0);
		keepStartingAngle = true;
		requires(Robot.driveSubsystem);
	}
	
	/**
	 * The robot will keep (heading to) its starting angle. Uses values from the Shuffleboard.
	 * 
	 * @param ntDistance
	 *            desired distance, distance from target
	 * @param angleToKeep
	 * 			  the angle to keep while driving           
	 * @param absDistanceAddition
	 * 			  the distance to add to the distance from the network table (or subtract if the distance from NT is negative)           
	 */
	public DriveStraightCommand(NetworkTableEntry ntDistance, double angleToKeep, double absDistanceAddition) {
		this.ntFinalDistance = ntDistance;
		this.absDistanceAddition  = absDistanceAddition;
		this.angleToKeep = angleToKeep;
		keepStartingAngle = false;
		requires(Robot.driveSubsystem);
	}
	
	/**
	 * The robot will keep (heading to) its starting angle. Uses values from the Shuffleboard.
	 * 
	 * @param distance
	 *            desired distance, distance from target
	 * @param absDistanceAddition
	 * 			  the distance to add to the distance from the network table (or subtract if the distance from NT is negative)           
	 */
	public DriveStraightCommand(NetworkTableEntry ntDistance, double absDistanceAddition) {
		this.ntFinalDistance = ntDistance;
		this.absDistanceAddition  = absDistanceAddition;
		keepStartingAngle = true;
		requires(Robot.driveSubsystem);
	}
	
	/**
	 * The robot will keep (heading to) its starting angle. Uses values from the Shuffleboard.
	 * 
	 * @param distance
	 *            desired distance, distance from target
	 */
	public DriveStraightCommand(NetworkTableEntry ntDistance) {
		this.ntFinalDistance = ntDistance;
		keepStartingAngle = true;
		requires(Robot.driveSubsystem);
	}

	/**
	 * Constructs the motion profiler for the driving command.
	 */
	public void constructMotionProfile() {
		mp = new DistanceMotionProfile(
				finalDistance,
				Constants.DRIVE_MAX_VELOCITY, Constants.DRIVE_MIN_VELOCITY,
				Constants.DRIVE_ACCELERATION, Constants.DRIVE_DECCELERATION);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (ntAngleToKeep != null) {
			angleToKeep = ntAngleToKeep.getDouble(0);
		}
		if (ntFinalDistance != null) {
			finalDistance = ntFinalDistance.getDouble(0);
			// TODO: remove!!!!
			finalDistance *= -1;
		}
		if(keepStartingAngle){
			angleToKeep = Robot.driveSubsystem.getAngle();
		}else{
			double start = Robot.driveSubsystem.getAngle();
			angleToKeep = Misc.absoluteToRelativeAngle(angleToKeep, start) + start;
		}
		
		if(finalDistance > 0)
			finalDistance += absDistanceAddition;
		else
			finalDistance -= absDistanceAddition;
		
		ntShowAngleToKeep.setDouble(angleToKeep);
		initRightDistance = Robot.driveSubsystem.getRightDistance();
		initLeftDistance = Robot.driveSubsystem.getLeftDistance();

		constructMotionProfile();
//		double minV = finalDistance > 0 ? 0.4 : -0.4;
//		Robot.driveSubsystem.setRightSpeed(minV);
//		Robot.driveSubsystem.setLeftSpeed(minV);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double leftDistance = Robot.driveSubsystem.getLeftDistance() - initLeftDistance;
		double rightDistance = Robot.driveSubsystem.getRightDistance() - initRightDistance;
		double avgDistance = (leftDistance + rightDistance) / 2;

		// calculate new output based on the MotionProfile and the gyro
		double speed = mp.getV(avgDistance);
		double gyroFix = Robot.driveSubsystem.getGyroPID(angleToKeep);
//		gyroFix = 0; // TODO: remove (exterminate)
		
		Robot.driveSubsystem.setSetpoints(speed - gyroFix, speed + gyroFix);
		Robot.driveSubsystem.updatePID();

		// debug distance error in NetworkTables
		ntMPoutput.setDouble(speed);
		
		DistanceError = finalDistance - avgDistance;
		ntDistanceError.setDouble(DistanceError);

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// finish when both sides have reached targets
		return Math.abs(DistanceError) < Constants.DRIVE_STRAIGHT_MIN_DISTANCE_ERROR;

	}

	// Called once after isFinished returns true
	protected void end() {
		// stop at the end
		Robot.driveSubsystem.setLeftSpeed(0);
		Robot.driveSubsystem.setRightSpeed(0);
		Robot.driveSubsystem.setSetpoints(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		// allow other commands to interrupt this one
//		end();
//		this.cancel();
	}
}
