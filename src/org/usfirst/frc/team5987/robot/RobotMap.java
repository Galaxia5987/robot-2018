/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/*--------------------------CLIMB-------------------------------*/
	public static int climbRightServo = 8; // TODO: CHANGE!
	public static int climbLeftServo = 9; // TODO: CHANGE!
	public static int climbMotor = 5; // TODO: CHANGE!
	public static int climbLimitSwitch = 9; // TODO: CHANGE!
	public static int climbBarSolenoid = -1; // TODO: CHANGE!
	public static double climbServoOpenPosition = 1;
	public static double climbServoClosedPosition = 0;
	/*--------------------------------------------------------------*/

	
	/*--------------------------DRIVE-------------------------------*/
	public static final int driveRightRearMotor = 0; // TODO: CHANGE!
	public static final int driveRightFrontMotor = 1; // TODO: CHANGE!
	public static final int driveLeftRearMotor = 2; // TODO: CHANGE!
	public static final int driveLeftFrontMotor = 3; // TODO: CHANGE!
	
	public static final int driveRightEncoderChannelA = 2; // TODO: CHANGE!
	public static final int driveRightEncoderChannelB = 3; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelA = 4; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelB = 5; // TODO: CHANGE!
	
	public static final int driveEncoderDistancePerPulse = 1 * 1; // TODO: CHANGE!
	
	public static final int backUltrasonic = 0; // TODO: CHANGE!
	public static int bumpSensor = 8; // TODO: CHANGE!
	/*--------------------------------------------------------------*/
	
	/*--------------------------INTAKE------------------------------*/
	public static int gripperWheelLeft = 666; // TODO: CHANGE!
	public static int gripperWheelRight = 666; // TODO: CHANGE!
	public static int beamChannel = 666; // TODO: CHANGE!
	/*--------------------------------------------------------------*/

	/*--------------------------LIFT------------------------------*/
	public static int liftMotorPort = 0;
	public static int liftEncoderPortA = 0;
	public static int liftEncoderPortB = 0;
	public static int liftHallEffectUpper = 0;
	public static int liftHallEffectBottom = 0;
	/*--------------------------------------------------------------*/
	
	/*--------------------------INTAKE------------------------------*/
	public static int intakeMotorLeft = 9001; // TODO: CHANGE!
	public static int intakeMotorRight = 9002; // TODO: CHANGE!
	
	public static int intakeSolenoid1 = 9003; // TODO: CHANGE!
	public static int intakeSolenoid2 = 9004; // TODO: CHANGE!
	/*--------------------------------------------------------------*/
	
}
