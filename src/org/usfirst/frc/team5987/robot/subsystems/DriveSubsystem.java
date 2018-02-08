package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.Robot;

import org.usfirst.frc.team5987.robot.RobotMap;
import org.usfirst.frc.team5987.robot.commands.JoystickDriveCommand;

import auxiliary.MiniPID; 	
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * @author Dor Brekhman
 */
public class DriveSubsystem extends Subsystem {
	/*********************** CONSTANTS ************************/
	// Gyro PID
	private static double gyroKp = 0.007;
	private static double gyroKi = 0;
	// PIDF constants for controlling velocity for wheels
	private static double kP = 0.15; 
	private static double kI = 0.002; 
	private static double kD = 0.04;
	private static double kF = 0.4; 
	private static double gyroKd = 0;
	private static final boolean GYRO_REVERSED = true;
	/**
	 * ABSOLUTE, METER/SEC
	 */
	public static final double MAX_VELOCITY = 1;
	/**
	 * Absolute max velocity [m/s]
	 */
	public static final double MIN_VELOCITY = 0.2;
	/**
	 * Absolute acceleration [m/s^2]
	 */
	public static final double ACCELERATION = 0.4;
	/**
	 * Absolute deccleration [m/s^2]
	 */
	public static final double DECCELERATION = 0.4;
	public static final double ROTATION_RADIUS = 0.3325; // test chasiss
	/**
	 * Mapping between 0-5V to METER for the analog input
	 */
	public static final double ultransonicMeterFactor = 1.024;
	private static final boolean rightInverted = true; // inverts the right motors & right encoder
	private static final boolean leftInverted = false; // inverts the left motors & left encoder
	/*******************************************************/

	private static final Victor driveRightRearMotor = new Victor(RobotMap.driveRightRearMotor);
	private static final Victor driveRightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor driveLeftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor driveLeftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	
	private static SpeedControllerGroup leftMotors = new SpeedControllerGroup(driveLeftFrontMotor, driveLeftRearMotor);
	private static SpeedControllerGroup rightMotors = new SpeedControllerGroup(driveRightFrontMotor, driveRightRearMotor);
	private static DifferentialDrive mainDrive = new DifferentialDrive(leftMotors, rightMotors);

	private static final Encoder driveRightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA,
			RobotMap.driveRightEncoderChannelB, rightInverted);
	private static final Encoder driveLeftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA,
			RobotMap.driveLeftEncoderChannelB, leftInverted);

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
	// NetworkTable error for debugging PIDF constants
	NetworkTableEntry ntRightError = driveTable.getEntry("Right Speed Error");
	NetworkTableEntry ntLeftError = driveTable.getEntry("Left Speed Error");
	// Gyro NetworkTable constants

	NetworkTableEntry ntGyroKp = driveTable.getEntry("Gyro kP");
	NetworkTableEntry ntGyroKi = driveTable.getEntry("Gyro kI");
	NetworkTableEntry ntGyroKd = driveTable.getEntry("Gyro kD");
	// NetworkTable error for debugging gyro PID
	NetworkTableEntry ntGyroError = driveTable.getEntry("Gyro Error");
	NetworkTableEntry ntGyroPIDOut = driveTable.getEntry("Gyro PID Out");
	NetworkTableEntry ntRightSpeed = driveTable.getEntry("Right Velocity");
	NetworkTableEntry ntLeftSpeed = driveTable.getEntry("Left Velocity");
	
	private static MiniPID rightPID;
	private static MiniPID leftPID;
	private static MiniPID gyroPID;

	/* TODO Set distance per pulse TODO */
	public DriveSubsystem() {
		// Inverts the motors if needed
		driveRightRearMotor.setInverted(rightInverted);
		driveRightFrontMotor.setInverted(rightInverted);
		driveLeftRearMotor.setInverted(leftInverted);
		driveLeftFrontMotor.setInverted(leftInverted);

		driveRightFrontMotor.setInverted(rightInverted);
		driveLeftRearMotor.setInverted(leftInverted);
		driveLeftFrontMotor.setInverted(leftInverted);
		// Sets the distance per pulse for the encoders
		driveRightEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);
		driveLeftEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);

		// Initialize the PIDF constants in the NetworkTable
		ntGetPID();
		ntKp.setDouble(kP);
		ntKi.setDouble(kI);
		ntKd.setDouble(kD);
		ntKf.setDouble(kF);
		ntGetGyroPID();
		ntGyroKp.setDouble(gyroKp);
		ntGyroKi.setDouble(gyroKi);
		ntGyroKd.setDouble(gyroKd);

		// Initialize the MiniPID for each side
		rightPID = new MiniPID(kP, kI, kD, kF);
		leftPID = new MiniPID(kP, kI, kD, kF);
		gyroPID = new MiniPID(gyroKp, gyroKi, gyroKd);
	}

	/**
	   * Tank drive method for differential drive platform.
	   * The calculated values will be squared to decrease sensitivity at low speeds.
	   *
	   * @param leftSpeed  The robot's left side speed along the X axis [-1.0..1.0]. Forward is
	   *                   positive.
	   * @param rightSpeed The robot's right side speed along the X axis [-1.0..1.0]. Forward is
	   *                   positive.
	   * @param squaredInputs If set, decreases the input sensitivity at low speeds.
	   */
	public void tankDrive(double leftSpeed, double rightSpeed, boolean squaredInputs)
	{
		mainDrive.tankDrive(leftSpeed, rightSpeed, squaredInputs);
	}
	
	/* TODO ADD DriveJoystickCommand TODO */
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDriveCommand());
	}

	/**
	 * Gets the PIDF constants from the NetworkTable
	 */
	private void ntGetPID() {
		kP = ntKp.getDouble(kP);
		kI = ntKi.getDouble(kI);
		kD = ntKd.getDouble(kD);
		kF = ntKf.getDouble(kF);
	}

	/**
	 * Gets the gyro PID constants from the NetworkTable
	 */
	private void ntGetGyroPID() {
		gyroKp = ntGyroKp.getDouble(gyroKp);
		gyroKi = ntGyroKi.getDouble(gyroKi);
		gyroKd = ntGyroKd.getDouble(gyroKd);
	}

	/**
	 * Updates the PIDF control and moves the motors <br>
	 * Controls the velocity according to {@link #setRightSetpoint()} and
	 * {@link #setLeftSetpoint()}. <br>
	 * <b>This should be run periodically in order to work!</b>
	 */
	public void updatePID() {
		ntGetPID();

		rightPID.setPID(kP, kI, kD, kF);
		leftPID.setPID(kP, kI, kD, kF);

		double rightOut = rightPID.getOutput(getRightSpeed());
		double leftOut = leftPID.getOutput(getLeftSpeed());

		setRightSpeed(rightOut);
		setLeftSpeed(leftOut);
	}

	/**
	 * Get the output from the gyro PID.
	 * 
	 * @param desiredAngle - the setpoint for the PID control
	 * @return output for adding rotation to the robot
	 */
	public double getGyroPID(double desiredAngle) {
		ntGetGyroPID();
		double out = gyroPID.getOutput(getAngle(), desiredAngle);
		ntGyroPIDOut.setDouble(out);
		return out;
	}

	/**
	 * Set the desired velocity for the both motors (to make it move use
	 * {@link #updatePID()} and set speed methods periodically)
	 * 
	 * @param rightVelocity - desired velocity for the right motors METERS/SEC
	 * @param leftVelocity - desired velocity for the left motors METERS/SEC
	 */
	public void setSetpoints(double leftVelocity, double rightVelocity) {
		double rightOut; // Normalized output [METERS/SEC]
		double leftOut; // Normalized output [METERS/SEC]
		// NORMALIZATION
		if ((Math.abs(rightVelocity) > MAX_VELOCITY) || (Math.abs(leftVelocity) > MAX_VELOCITY)) {
			if (Math.abs(rightVelocity) > Math.abs(leftVelocity)) {
				rightOut = (rightVelocity / rightVelocity) * MAX_VELOCITY;
				leftOut = (leftVelocity / rightVelocity) * MAX_VELOCITY;
			} else {
				leftOut = (leftVelocity / leftVelocity) * MAX_VELOCITY;
				rightOut = (rightVelocity / leftVelocity) * MAX_VELOCITY;
			}
		} else {
			// No normalization needed
			rightOut = rightVelocity;
			leftOut = leftVelocity;
		}
		}

	/**
	 * Set the speed of the two right motors
	 * 
	 * @param speed - between -1 and 1
	 */
	public void setRightSpeed(double speed) {
		driveRightRearMotor.set(speed);
		driveRightFrontMotor.set(speed);
	}

	/**
	 * Set the speed of the two left motors
	 * 
	 * @param speed - between -1 and 1
	 */
	public void setLeftSpeed(double speed) {
		driveLeftRearMotor.set(speed);
		driveLeftFrontMotor.set(speed);
	}

	/**
	 * Get the speed of the right wheels
	 * 
	 * @return speed [m/s]
	 */
	public double getRightSpeed() {
		return driveRightEncoder.getRate();
	}

	/**
	 * Get the speed of the left wheels
	 * 
	 * @return speed [m/s]
	 */
	public double getLeftSpeed() {
		return driveLeftEncoder.getRate();
	}

	/**
	 * Get the distance the right wheels have passed since the beginning of the
	 * program
	 * 
	 * @return distance [m]
	 */
	public double getRightDistance() {
		return driveRightEncoder.getDistance();
	}

	/**
	 * Get the distance the left wheels have passed since the beginning of the
	 * program
	 * 
	 * @return distance in [m]
	 */
	public double getLeftDistance() {
		return driveLeftEncoder.getDistance();
	}

	/**
	 * Get the angle of the navX
	 * 
	 * @return angle [degrees]
	 */

    public double getAngle() {
		double rawAngle = Robot.navx.getAngle();
		return GYRO_REVERSED ? -rawAngle : rawAngle;
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
	 * @return whether the robot's on the cable bump on the center of the arena
	 *         (in the null territory)
	 */
	public boolean isBump() {
		return bumpSensor.get();
	}

	public boolean seesWhite() {
		return colorSensor.getVoltage() >= 4.5;
	}

	/**
	 * Get the distance from the back of the robot <br>
	 * <i>Note: Shows ~0.3 M under 0.3 M</i>
	 * 
	 * @return distance [m]
	 */
	public double getBackDistance() {
		return backDistanceSensor.getVoltage() * ultransonicMeterFactor;
	}
}
