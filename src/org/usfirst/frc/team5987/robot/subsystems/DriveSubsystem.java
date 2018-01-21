package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 *@author Dor Brekhman
 */
public class DriveSubsystem extends Subsystem {
	
	// PIDF constants for controlling velocity for wheels
	private static double kP = 0; 
	private static double kI = 0; 
	private static double kD = 0;
	private static double kF = 0;
	/**
	 * Mapping between 0-5V to METER for the analog input
	 */
	private static final double ultransonicMeterFactor = 1.024;
	
	private static final boolean rightInverted = false; // inverts the right motors & right encoder
	private static final boolean leftInverted = true; // inverts the left motors & left encoder
	
	private static final Victor driveRightRearMotor = new Victor(RobotMap.driveRightRearMotor);
	private static final Victor driveRightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor driveLeftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor driveLeftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	
	private static final Encoder driveRightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA, RobotMap.driveRightEncoderChannelB, rightInverted);
	private static final Encoder driveLeftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA, RobotMap.driveLeftEncoderChannelB, leftInverted);
	
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
	// NT error for debugging PIDF constants
	NetworkTableEntry ntRightError = driveTable.getEntry("Right Speed Error");
	NetworkTableEntry ntLeftError = driveTable.getEntry("Left Speed Error");
	
	private static MiniPID rightPID;
	private static MiniPID leftPID;
	
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
		
		// init the MiniPID for each side
		rightPID = new MiniPID(kP, kI, kD, kF);
		leftPID = new MiniPID(kP, kI, kD, kF);
	}
	
	/*TODO ADD DriveJoystickCommand TODO*/
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	/**
	 * Gets the PIDF constants from the NetworkTable
	 */
	private void ntGetPID(){
		kP = ntKp.getDouble(kP);
		kI = ntKi.getDouble(kI);
		kD = ntKd.getDouble(kD);
		kF = ntKf.getDouble(kF);
	}
	
	/**
	 * Updates the PIDF control and moves the motors <br>
	 * Controls the velocity according to <code>setRightSetpoint(..)</code> and <code>setLeftSetpoint(..)</code> <br>
	 * <b>This should be run periodically in order to work!</b>
	 */
	public void updatePID(){
		ntGetPID();
		
		rightPID.setPID(kP, kI, kD, kF);
		leftPID.setPID(kP, kI, kD, kF);
		
		double rightOut = rightPID.getOutput(getRightSpeed());
		double leftOut = leftPID.getOutput(getLeftSpeed());
		
		setRightSpeed(rightOut);
		setLeftSpeed(leftOut);
	}
	
	/**
	 * Set the desired velocity for the right motors (to make it move run updatePID() periodically) 
	 * @param velocity velocity in METERS/SEC
	 */
	public void setRightSetpoint(double velocity){
		double error = velocity - getRightSpeed();
		ntRightError.setDouble(error);
		rightPID.setSetpoint(velocity);
	}
	
	/**
	 * Set the desired velocity for the left motors (to make it move run updatePID() periodically) 
	 * @param velocity velocity in METERS/SEC
	 */
	public void setLeftSetpoint(double velocity){
		double error = velocity - getLeftSpeed();
		ntLeftError.setDouble(error);
		leftPID.setSetpoint(velocity);
	}
	
	/**
	 * Set the speed of the two right motors
	 * @param speed between -1 and 1
	 */
	public void setRightSpeed(double speed) {
		driveRightRearMotor.set(speed);
		driveRightFrontMotor.set(speed);
	}
	
	/**
	 * Set the speed of the two left motors
	 * @param speed between -1 and 1
	 */
	public void setLeftSpeed(double speed) {
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

