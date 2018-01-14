package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *@author Dor Brekhman
 */
public class DriveSubsystem extends Subsystem {
	
	// PIDF constants for controlling velocity for wheels
	private static double kP = 0; 
	private static double kI = 0; 
	private static double kD = 0;
	private static double kF = 0;
	
	private static final boolean rightInverted = false; // inverts the right motors & right encoder
	private static final boolean leftInverted = true; // inverts the left motors & left encoder
	
	private static final Victor driveRightRearMotor = new Victor(RobotMap.driveRightRearMotor);
	private static final Victor driveRightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor driveLeftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor driveLeftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	
	private static final Encoder driveRightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA, RobotMap.driveRightEncoderChannelB, rightInverted);
	private static final Encoder driveLeftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA, RobotMap.driveLeftEncoderChannelB, leftInverted);
	
	private static final DigitalInput bumpSensor = new DigitalInput(RobotMap.bumpSensor);
	private static final Ultrasonic backDistanceSensor = new Ultrasonic(RobotMap.backUltrasonicPing, RobotMap.backUltrasonicEcho, Ultrasonic.Unit.kMillimeters);
	
	private double velocitySetpoint;
	public NetworkTable driveTable;
	
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
		refreshPID();
		driveTable.putNumber("Drive kP", kP);
		driveTable.putNumber("Drive kI", kI);
		driveTable.putNumber("Drive kD", kD);
		driveTable.putNumber("Drive kF", kF);
		
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
	 * Updates the PIDF control and moves the motors <br>
	 * Controls the velocity according to <code>setRightSetpoint(..)</code> and <code>setLeftSetpoint(..)</code> <br>
	 * <b>This should be run periodically in order to work!</b>
	 */
	private void updatePID(){
		refreshPID();
		
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
		
		rightPID.setSetpoint(velocity);
	}
	
	/**
	 * Set the desired velocity for the left motors (to make it move run updatePID() periodically) 
	 * @param velocity velocity in METERS/SEC
	 */
	public void setLeftSetpoint(double velocity){
		
		leftPID.setSetpoint(velocity);
	}
	
	/**
	 * Gets the PIDF constants from the NetworkTable
	 */
	private void refreshPID(){
		kP = driveTable.getNumber("Drive kP", kP);
		kI = driveTable.getNumber("Drive kI", kI);
		kD = driveTable.getNumber("Drive kD", kD);
		kF = driveTable.getNumber("Drive kF", kF);
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
	
	/**
	 * MUST do this in order to <code>getBackDistance()</code> <br>
	 * Starts the ultrasonic's ping
	 */
	public void enableUltrasonic(){
		backDistanceSensor.setEnabled(true);
	}
	
	/**
	 * MUST do this in order to <code>getBackDistance()</code> <br>
	 * Stops the ultrasonic's ping
	 */
	public void disableUltrasonic(){
		backDistanceSensor.setEnabled(false);
	}
	/**
	 * Get the distance from the back of the robot
	 * @return distance in CM
	 */
	public double getBackDistance(){
		return backDistanceSensor.getRangeMM() * 100;
	}
}

