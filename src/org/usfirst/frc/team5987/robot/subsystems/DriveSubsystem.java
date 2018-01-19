package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;
import org.usfirst.frc.team5987.robot.commands.JoystickDriveCommand;

import auxiliary.MiniPID;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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

	// PIDF constants for controlling velocity for wheels
	private static double kP = 0;
	private static double kI = 0;
	private static double kD = 0;
	private static double kF = 0;
	/**
	 * Mapping between 0-5V to METER for the analog input
	 */
	private static final double ultransonicMeterFactor = 1.024;

	private static final boolean rightInverted = false; // inverts the right
														// motors & right
														// encoder
	private static final boolean leftInverted = true; // inverts the left motors
														// & left encoder

	private static final Victor leftFrontMotor = new Victor(RobotMap.driveLeftFrontMotor);
	private static final Victor leftRearMotor = new Victor(RobotMap.driveLeftRearMotor);
	private static final Victor rightFrontMotor = new Victor(RobotMap.driveRightFrontMotor);
	private static final Victor rightRearMotor = new Victor(RobotMap.driveRightRearMotor);

	private static final Encoder rightEncoder = new Encoder(RobotMap.driveRightEncoderChannelA,
			RobotMap.driveRightEncoderChannelB, rightInverted);
	private static final Encoder leftEncoder = new Encoder(RobotMap.driveLeftEncoderChannelA,
			RobotMap.driveLeftEncoderChannelB, leftInverted);

	private static final DigitalInput bumpSensor = new DigitalInput(RobotMap.bumpSensor);
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

	/* TODO Set distance per pulse TODO */
	public DriveSubsystem() {
		// Invert the motors if needed
		leftFrontMotor.setInverted(leftInverted);
		leftRearMotor.setInverted(leftInverted);
		rightFrontMotor.setInverted(rightInverted);
		rightRearMotor.setInverted(rightInverted);
		// Set the distance per pulse for the encoders
		rightEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);
		leftEncoder.setDistancePerPulse(RobotMap.driveEncoderDistancePerPulse);

		// Set DiffrentialDrive
		SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
		SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
		DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
		// Initialize the PIDF constants in the NetworkTable
		/**
		 * Gets the PIDF constants from the NetworkTable
		 */
		private void ntGetPID() {
			kP = ntKp.getDouble(kP);
			kI = ntKi.getDouble(kI);
			kD = ntKd.getDouble(kD);
			kF = ntKf.getDouble(kF);
		}

		ntGetPID();
		ntKp.setDouble(kP);
		ntKi.setDouble(kI);
		ntKd.setDouble(kD);
		ntKf.setDouble(kF);

		// Initialize the MiniPID for each side
		rightPID = new MiniPID(kP, kI, kD, kF);
		leftPID = new MiniPID(kP, kI, kD, kF);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickDriveCommand());
	}

	
	/**
	 * Updates the PIDF control and moves the motors <br>
	 * Controls the velocity according to <code>setRightSetpoint(..)</code> and
	 * <code>setLeftSetpoint(..)</code> <br>
	 * <b>This should be run periodically in order to work!</b>
	 */
	private void updatePID() {
		ntGetPID();

		rightPID.setPID(kP, kI, kD, kF);
		leftPID.setPID(kP, kI, kD, kF);

		double rightOut = rightPID.getOutput(getRightSpeed());
		double leftOut = leftPID.getOutput(getLeftSpeed());

		setRightSpeed(rightOut);
		setLeftSpeed(leftOut);
	}

	/**
	 * Set the desired velocity for the right motors (to make it move run
	 * updatePID() periodically)
	 * 
	 * @param velocity
	 *            velocity in METERS/SEC
	 */
	public void setRightSetpoint(double velocity) {
		double error = velocity - getRightSpeed();
		ntRightError.setDouble(error);
		rightPID.setSetpoint(velocity);
	}

	/**
	 * Set the desired velocity for the left motors (to make it move run
	 * updatePID() periodically)
	 * 
	 * @param velocity
	 *            velocity in METERS/SEC
	 */
	public void setLeftSetpoint(double velocity) {
		double error = velocity - getLeftSpeed();
		ntLeftError.setDouble(error);
		leftPID.setSetpoint(velocity);
	}

	/**
	 * Set the speed of the two left motors
	 * 
	 * @param speed
	 *            between -1 and 1
	 */
	public static void setLeftSpeed(double speed) {
		leftRearMotor.set(speed);
		leftFrontMotor.set(speed);
	}
	
	/**
	 * Set the speed of the two right motors
	 * 
	 * @param speed
	 *            between -1 and 1
	 */
	public static void setRightSpeed(double speed) {
		rightRearMotor.set(speed);
		rightFrontMotor.set(speed);
	}
	
	/**
	 * 
	 * @param leftSpeed - Speed for the left side of the robot
	 * @param rightSpeed - Speed for the right side of the robot
	 */
	public static void setRobotSpeed(double leftSpeed, double rightSpeed)
	{
		setLeftSpeed(leftSpeed);
		setRightSpeed(rightSpeed);
	}

	/**
	 * Get the speed of the right wheels
	 * 
	 * @return speed in METER/SEC
	 */
	public double getRightSpeed() {
		return rightEncoder.getRate();
	}

	/**
	 * Get the speed of the left wheels
	 * 
	 * @return speed in METER/SEC
	 */
	public double getLeftSpeed() {
		return leftEncoder.getRate();
	}

	/**
	 * Get the distance the right wheels have passed since the beginning of the
	 * program
	 * 
	 * @return distance in METER
	 */
	public double getRightDistance() {
		return rightEncoder.getDistance();
	}

	/**
	 * Get the distance the left wheels have passed since the beginning of the
	 * program
	 * 
	 * @return distance in METER
	 */
	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}

	/**
	 * 
	 * @return true if the robot's on the cable bump on the center of the arena
	 *         (in the null territory)
	 */
	public boolean isBump() {
		return bumpSensor.get();
	}

	/**
	 * Get the distance from the back of the robot <br>
	 * <i>Note: Shows ~0.3 M under 0.3 M</i>
	 * 
	 * @return distance in METER
	 */
	public double getBackDistance() {
		return backDistanceSensor.getVoltage() * ultransonicMeterFactor;
	}
}
