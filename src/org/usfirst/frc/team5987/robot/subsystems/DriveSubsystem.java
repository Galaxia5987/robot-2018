package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.RobotMap;
import org.usfirst.frc.team5987.robot.commands.JoystickDriveCommand;

import auxiliary.MiniPID;
import auxiliary.Misc;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author Dor Brekhman
 */
public class DriveSubsystem extends Subsystem {
	public enum PIDTypes{
		/**
		 * Normal PID constants
		 */
		STRAIGHT,
		/**
		 * Turning in place PID constants
		 */
		TURN
	}
	private PIDTypes pidType = PIDTypes.STRAIGHT;
	/*******************************************************/
	
	
	private static final Victor driveRightRearMotor = new Victor(RobotMap.driveRightRearMotor);
	private static final Victor driveRightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor driveLeftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor driveLeftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	
	private static final Encoder driveRightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA, RobotMap.driveRightEncoderChannelB, Constants.DRIVE_rightEncoderInverted);
	private static final Encoder driveLeftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA, RobotMap.driveLeftEncoderChannelB, Constants.DRIVE_leftEncoderInverted);
	
	// Creates a new NetworkTable
	public NetworkTable driveTable = NetworkTableInstance.getDefault().getTable("Drive");
	// NT PIDF constants
	NetworkTableEntry ntKp = driveTable.getEntry("kP");
	NetworkTableEntry ntKi = driveTable.getEntry("kI");
	NetworkTableEntry ntKd = driveTable.getEntry("kD");
	NetworkTableEntry ntKf = driveTable.getEntry("kF");
	// NT Turn PIDF constants
	NetworkTableEntry ntTurnKp = driveTable.getEntry("Turn kP");
	NetworkTableEntry ntTurnKi = driveTable.getEntry("Turn kI");
	NetworkTableEntry ntTurnKd = driveTable.getEntry("Turn kD");
	NetworkTableEntry ntTurnKf = driveTable.getEntry("Turn kF");
	// NT error for debugging PIDF constants
	NetworkTableEntry ntRightError = driveTable.getEntry("Right Speed Error");
	NetworkTableEntry ntLeftError = driveTable.getEntry("Left Speed Error");

	// Gyro NT constants
	NetworkTableEntry ntGyroKp = driveTable.getEntry("Gyro kP");
	NetworkTableEntry ntGyroKi = driveTable.getEntry("Gyro kI");
	NetworkTableEntry ntGyroKd = driveTable.getEntry("Gyro kD");
	// NT error for debugging gyro PID
	NetworkTableEntry ntGyroError = driveTable.getEntry("Gyro Error");
	NetworkTableEntry ntGyroPIDOut = driveTable.getEntry("Gyro PID Out");
	NetworkTableEntry ntRightSpeed = driveTable.getEntry("Right Velocity");
	NetworkTableEntry ntLeftSpeed = driveTable.getEntry("Left Velocity");
	
	public NetworkTableEntry ntLeftDistance = driveTable.getEntry("Left Distance");
	public NetworkTableEntry ntRightDistance = driveTable.getEntry("Right Distance");
	
	private NetworkTableEntry ntPIDType = driveTable.getEntry("PID Type");
	public static MiniPID gyroPID;
	
	private static MiniPID rightPID;
	private static MiniPID leftPID;
	/*TODO Set distance per pulse TODO*/
	public DriveSubsystem(){
		// invert the motors if needed
		driveRightRearMotor.setInverted(Constants.DRIVE_rightInverted);
		driveRightFrontMotor.setInverted(Constants.DRIVE_rightInverted);
		driveLeftRearMotor.setInverted(Constants.DRIVE_leftInverted);
		driveLeftFrontMotor.setInverted(Constants.DRIVE_leftInverted);
		
		// set the distance per pulse for the encoders
		driveLeftEncoder.setDistancePerPulse(Constants.DRIVE_LEFT_DISTANCE_PER_PULSE);
		driveRightEncoder.setDistancePerPulse(Constants.DRIVE_RIGHT_DISTANCE_PER_PULSE);
		
		driveRightFrontMotor.setInverted(Constants.DRIVE_rightInverted);
		driveLeftRearMotor.setInverted(Constants.DRIVE_leftInverted);
		driveLeftFrontMotor.setInverted(Constants.DRIVE_leftInverted);
		
		
		// init the PIDF constants in the NetworkTable
		ntGetPID();
		ntKp.setDouble(Constants.DRIVE_kP);
		ntKi.setDouble(Constants.DRIVE_kI);
		ntKd.setDouble(Constants.DRIVE_kD);
		ntKf.setDouble(Constants.DRIVE_kF);
		ntTurnKp.setDouble(Constants.DRIVE_TurnKp);
		ntTurnKi.setDouble(Constants.DRIVE_TurnKi);
		ntTurnKd.setDouble(Constants.DRIVE_TurnKd);
		ntTurnKf.setDouble(Constants.DRIVE_TurnKf);
		ntGetGyroPID();
		ntGyroKp.setDouble(Constants.DRIVE_gyroKp);
		ntGyroKi.setDouble(Constants.DRIVE_gyroKi);
		ntGyroKd.setDouble(Constants.DRIVE_gyroKd);
		
		// init the MiniPID for each side
		rightPID = new MiniPID(Constants.DRIVE_kP, Constants.DRIVE_kI, Constants.DRIVE_kD, Constants.DRIVE_kF);
		leftPID = new MiniPID(Constants.DRIVE_kP, Constants.DRIVE_kI, Constants.DRIVE_kD, Constants.DRIVE_kF);
		DriveSubsystem.gyroPID = new MiniPID(Constants.DRIVE_gyroKp, Constants.DRIVE_gyroKi, Constants.DRIVE_gyroKd);
	}
	
	/*TODO ADD DriveJoystickCommand TODO*/
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDriveCommand());
    }
	
	/**
	 * Gets the PIDF constants from the NetworkTable
	 */
	private void ntGetPID(){
		Constants.DRIVE_kP = ntKp.getDouble(Constants.DRIVE_kP);
		Constants.DRIVE_kI = ntKi.getDouble(Constants.DRIVE_kI);
		Constants.DRIVE_kD = ntKd.getDouble(Constants.DRIVE_kD);
		Constants.DRIVE_kF = ntKf.getDouble(Constants.DRIVE_kF);
		Constants.DRIVE_TurnKp = ntTurnKp.getDouble(Constants.DRIVE_TurnKp);
		Constants.DRIVE_TurnKi = ntTurnKi.getDouble(Constants.DRIVE_TurnKi);
		Constants.DRIVE_TurnKd = ntTurnKd.getDouble(Constants.DRIVE_TurnKd);
		Constants.DRIVE_TurnKf = ntTurnKf.getDouble(Constants.DRIVE_TurnKf);
	}
	
	/**
	 * Gets the gyro PID constants from the NetworkTable
	 */
	private void ntGetGyroPID(){
		Constants.DRIVE_gyroKp = ntGyroKp.getDouble(Constants.DRIVE_gyroKp);
		Constants.DRIVE_gyroKi = ntGyroKi.getDouble(Constants.DRIVE_gyroKi);
		Constants.DRIVE_gyroKd = ntGyroKd.getDouble(Constants.DRIVE_gyroKd);
	}
	
	public PIDTypes getPIDType(){
		return pidType;
	}
	
	public void setPIDType(PIDTypes newType){
		pidType = newType;
	}
	
	/**
	 * Updates the PIDF control and moves the motors <br>
	 * Controls the velocity according to <code>setRightSetpoint(..)</code> and <code>setLeftSetpoint(..)</code> <br>
	 * <b>This should be run periodically in order to work!</b>
	 */
	public void updatePID(){
		ntGetPID();
		switch(pidType){
		default:
		case STRAIGHT:
			ntPIDType.setString("STRAIGHT");
			rightPID.setPID(Constants.DRIVE_kP, Constants.DRIVE_kI, Constants.DRIVE_kD, Constants.DRIVE_kF);
			leftPID.setPID(Constants.DRIVE_kP, Constants.DRIVE_kI, Constants.DRIVE_kD, Constants.DRIVE_kF);
			break;
		case TURN:
			ntPIDType.setString("TURN");
			rightPID.setPID(Constants.DRIVE_TurnKp, Constants.DRIVE_TurnKi, Constants.DRIVE_TurnKd, Constants.DRIVE_TurnKf);
			leftPID.setPID(Constants.DRIVE_TurnKp, Constants.DRIVE_TurnKi, Constants.DRIVE_TurnKd, Constants.DRIVE_TurnKf);
			break;
		}
		
		double rightCurrent = getRightSpeed();
		double leftCurrent = getLeftSpeed();
		double rightOut = rightPID.getOutput(getRightSpeed());
		double leftOut = leftPID.getOutput(getLeftSpeed());
		SmartDashboard.putString("updatePID Right Current", ""+rightCurrent);
		SmartDashboard.putString("updatePID Left Current", ""+leftCurrent);
		SmartDashboard.putString("updatePID Right Out", ""+rightOut);
		SmartDashboard.putString("updatePID Left Out", ""+leftOut);
		setRightSpeed(rightOut);
		setLeftSpeed(leftOut);
	}
	
	/**
	 * Get the output from the gyro PID.
	 * @param desiredAngle - the setpoint for the PID control
	 * @return output for adding rotation to the robot
	 */
	public double getGyroPID(double desiredAngle){
		ntGetGyroPID();
		double out = gyroPID.getOutput(getAngle(), desiredAngle);
		ntGyroPIDOut.setDouble(out);
		return out;
	}
	
	
	/**
	 * Set the desired velocity for the both motors (to make it move use {@link #updatePID()}  and set speed methods periodically) 
	 * @param rightVelocity desired velocity for the right motors METERS/SEC
	 * @param leftVelocity desired velocity for the left motors METERS/SEC
	 */
	public void setSetpoints(double leftVelocity, double rightVelocity){
		double outs[] = Misc.normalize(leftVelocity, rightVelocity, Constants.DRIVE_MAX_VELOCITY);
		double leftOut = outs[0];
		double rightOut = outs[1];
		double leftError = leftOut - getLeftSpeed();
		ntLeftError.setDouble(leftError);
		ntLeftSpeed.setDouble(getLeftSpeed());
		leftPID.setSetpoint(leftOut);
		
		double rightError = rightOut - getRightSpeed();
		ntRightError.setDouble(rightError);
		ntRightSpeed.setDouble(getRightSpeed());
		ntRightDistance.setDouble(getRightDistance());
		ntLeftDistance.setDouble(getLeftDistance());
		rightPID.setSetpoint(rightOut);
		SmartDashboard.putString("setSetpoints Left Out", ""+leftOut);
		SmartDashboard.putString("setSetpoints Left Out", ""+leftOut);
	}
	
	
	
	
	/**
	 * Set the speed of the two right motors
	 * @param speed between -1 and 1
	 */
	public void setRightSpeed(double speed) {
		if(speed<-1) speed =-1;
		if(speed>1) speed=1;
		driveRightRearMotor.set(speed);
		driveRightFrontMotor.set(speed);
	}
	
	/**
	 * Set the speed of the two left motors
	 * @param speed between -1 and 1
	 */
	public void setLeftSpeed(double speed) {
		if(speed<-1) speed =-1;
		if(speed>1) speed=1;
		driveLeftRearMotor.set(speed);
		driveLeftFrontMotor.set(speed);
	}
	
	/**
	 * Get the speed of the right wheels
	 * @return speed in METER/SEC
	 */
	public double getRightSpeed() {
		return driveRightEncoder.getRate();
		
	}
	
	/**
	 * Get the speed of the left wheels
	 * @return speed in METER/SEC
	 */
	public double getLeftSpeed() {
		return driveLeftEncoder.getRate();
	}
	
	/**
	 * Get the distance the right wheels have passed since the beginning of the program
	 * @return distance in METER
	 */
	public double getRightDistance() {
		return driveRightEncoder.getDistance();
	}
	
	/**
	 * Get the distance the left wheels have passed since the beginning of the program
	 * @return distance in METER
	 */
	public double getLeftDistance() {
		return driveLeftEncoder.getDistance();
	}
	
	/**
	 * Get the angle of the navX
	 * @return angle in DEGREES
	 */
    public double getAngle() {
		double rawAngle = Robot.navx.getAngle();
		return Constants.GYRO_REVERSED ? -rawAngle : rawAngle;
	}
    
    public double getPitch(){
    	return Robot.navx.getPitch();
    }
    
    public double getYaw(){
    	return Robot.navx.getYaw();
    }
    
    
	/**
	 * Get the angle of the navX
	 * @return angle in RADIANS
	 */
    public double getAngleRadians(){
    	return Math.toRadians(getAngle());
    }
    
	public void resetEncoders() {
		// TODO Auto-generated method stub
		driveLeftEncoder.reset();
		driveRightEncoder.reset();
	}
}
