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

	/*--------------------------------MOTORS----------------------------------*/

	/*---------------CLIMB-----------------*/
	public static int climbRightServo = 8; // TODO: CHANGE!
	public static int climbLeftServo = 9; // TODO: CHANGE!
	public static int climbMotor = 10; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------DRIVE----------------*/
	public static final int driveRightRearMotor = 3; // TODO: CHANGE!
	public static final int driveRightFrontMotor = 2; // TODO: CHANGE!
	public static final int driveLeftRearMotor = 7; // TODO: CHANGE!
	public static final int driveLeftFrontMotor = 6; // TODO: CHANGE!
	/*-------------------------------------*/

	/*---------------GRIPPER---------------*/
	public static int gripperWheelLeft = 5; // TODO: CHANGE!
	public static int gripperWheelRight = 4; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------LIFT-----------------*/
	public static int liftMotorPort = 11;
	/*-------------------------------------*/

	/*----------------INTAKE---------------*/
	public static final int intakeMotorLeft = 0;
	public static final int intakeMotorRight = 1;
	/*-------------------------------------*/

	/*-----------------------------------SENSORS----------------------------------*/

	/*---------------CLIMB-----------------*/
	public static int climbLimitSwitch = 6; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------DRIVE----------------*/
	public static final int driveRightEncoderChannelA = 3; // TODO: CHANGE!
	public static final int driveRightEncoderChannelB = 2; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelA = 1; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelB = 0; // TODO: CHANGE!

	public static final double driveEncoderDistancePerPulse = 0.00133; // TODO:
																	// CHANGE!

	public static final int backUltrasonic = 0; // TODO: CHANGE!
	public static int bumpSensor = 7; // TODO: CHANGE!
	public static int colorSensor = 2;
	/*-------------------------------------*/

	/*---------------GRIPPER---------------*/
	public static int proximityChannel = 1; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------LIFT-----------------*/
	public static int liftEncoderPortA = 4;
	public static int liftEncoderPortB = 5;

	public static int liftHallEffectTop = 8;
	public static int liftHallEffectBottom = 9;
	/*-------------------------------------*/

	/*-----------------------------------OTHERS----------------------------------*/
	/*---------------CLIMB-----------------*/
	
	/*-------------------------------------*/
	
	/*----------------INTAKE---------------*/
	public static final int intakeSolenoid1 = -1;
	public static final int intakeSolenoid2 = -1;
	/*-------------------------------------*/
	
}