package org.usfirst.frc.team5987.robot.subsystems;

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
	/***********************CONSTANTS************************/
	// PIDF constants for controlling velocity for wheels
	private static double kP = 0.15; 
	private static double kI = 0.0001; 
	private static double kD = 0.0;
	private static double kF = 0.33;
	private static double TurnKp = 0.15; 
	private static double TurnKi = 0.01; 
	private static double TurnKd = 0.0;
	private static double TurnKf = 0.2;
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
	// Gyro PID
	private static double gyroKp = 0.015;
	private static double gyroKi = 0; 
	private static double gyroKd = 0;
	private static final boolean GYRO_REVERSED = true;
	/**
	 * ABSOLUTE, METER/SEC
	 */
	public static final double MAX_VELOCITY = 1.2;
	/**
	 * ABSOLUTE, METER/SEC
	 */
	public static final double MIN_VELOCITY = 0.4;
	/**
	 * ABSOLUTE, METER/SEC^2
	 */
	public static final double ACCELERATION = 0.6;
	/**
	 * ABSOLUTE, METER/SEC^2
	 */
	public static final double DECCELERATION = 0.5;
	public static final double ROTATION_RADIUS = 0.3325; // test chasiss
	/**
	 * Mapping between 0-5V to METER for the analog input
	 */
	public static final double ultransonicMeterFactor = 1.024;
	private static final boolean rightInverted = true; // inverts the right motor
	private static final boolean leftInverted = false; // inverts the left motors
	private static final boolean rightEncoderInverted = false; // inverts the right encoder
	private static final boolean leftEncoderInverted = false; // inverts the left encoder
	/*******************************************************/
	
	
	private static final Victor driveRightRearMotor = new Victor(RobotMap.driveRightRearMotor);
	private static final Victor driveRightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor driveLeftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor driveLeftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	
	private static final Encoder driveRightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA, RobotMap.driveRightEncoderChannelB, rightEncoderInverted);
	private static final Encoder driveLeftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA, RobotMap.driveLeftEncoderChannelB, leftEncoderInverted);
	
	private static final DigitalInput bumpSensor = new DigitalInput(RobotMap.bumpSensor);
	private static final AnalogInput colorSensor = new AnalogInput(RobotMap.colorSensor);
	/**
	 * HRLV-MaxSonar -EZ ultrasonic sensor
	 */
	private static final AnalogInput backDistanceSensor = new AnalogInput(RobotMap.backUltrasonic);
	
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
	
	private static MiniPID rightPID;
	private static MiniPID leftPID;
	private static MiniPID gyroPID;
	
	/*TODO Set distance per pulse TODO*/
	public DriveSubsystem(){
		// invert the motors if needed
		driveRightRearMotor.setInverted(rightInverted);
		driveRightFrontMotor.setInverted(rightInverted);
		driveLeftRearMotor.setInverted(leftInverted);
		driveLeftFrontMotor.setInverted(leftInverted);

		driveRightFrontMotor.setInverted(rightInverted);
		driveLeftRearMotor.setInverted(leftInverted);
		driveLeftFrontMotor.setInverted(leftInverted);
		// set the distance per pulse for the encoders
		driveRightEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);
		driveLeftEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);
		
		// init the PIDF constants in the NetworkTable
		ntGetPID();
		ntKp.setDouble(kP);
		ntKi.setDouble(kI);
		ntKd.setDouble(kD);
		ntKf.setDouble(kF);
		ntTurnKp.setDouble(TurnKp);
		ntTurnKi.setDouble(TurnKi);
		ntTurnKd.setDouble(TurnKd);
		ntTurnKf.setDouble(TurnKf);
		ntGetGyroPID();
		ntGyroKp.setDouble(gyroKp);
		ntGyroKi.setDouble(gyroKi);
		ntGyroKd.setDouble(gyroKd);
		
		// init the MiniPID for each side
		rightPID = new MiniPID(kP, kI, kD, kF);
		leftPID = new MiniPID(kP, kI, kD, kF);
		gyroPID = new MiniPID(gyroKp, gyroKi, gyroKd);
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
		kP = ntKp.getDouble(kP);
		kI = ntKi.getDouble(kI);
		kD = ntKd.getDouble(kD);
		kF = ntKf.getDouble(kF);
		TurnKp = ntTurnKp.getDouble(TurnKp);
		TurnKi = ntTurnKi.getDouble(TurnKi);
		TurnKd = ntTurnKd.getDouble(TurnKd);
		TurnKf = ntTurnKf.getDouble(TurnKf);
	}
	
	/**
	 * Gets the gyro PID constants from the NetworkTable
	 */
	private void ntGetGyroPID(){
		gyroKp = ntGyroKp.getDouble(gyroKp);
		gyroKi = ntGyroKi.getDouble(gyroKi);
		gyroKd = ntGyroKd.getDouble(gyroKd);
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
			rightPID.setPID(kP, kI, kD, kF);
			leftPID.setPID(kP, kI, kD, kF);
			break;
		case TURN:
			ntPIDType.setString("TURN");
			rightPID.setPID(TurnKp, TurnKi, TurnKd, TurnKf);
			leftPID.setPID(TurnKp, TurnKi, TurnKd, TurnKf);
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
		double outs[] = Misc.normalize(leftVelocity, rightVelocity, MAX_VELOCITY);
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
		return GYRO_REVERSED ? -rawAngle : rawAngle;
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
	/**
	 * 
	 * @return true if the robot's on the cable bump on the center of the arena (in the null territory)
	 */
	public boolean isBump(){
		return bumpSensor.get();
	}
	
	public boolean seesWhite() {
		return colorSensor.getVoltage() >= 4.5;
	}
	
	/**
	 * Get the distance from the back of the robot <br>
	 * <i>Note: Shows ~0.3 M under 0.3 M</i>
	 * @return distance in METER
	 */
	public double getBackDistance(){
		return backDistanceSensor.getVoltage() * ultransonicMeterFactor;
	}
}
