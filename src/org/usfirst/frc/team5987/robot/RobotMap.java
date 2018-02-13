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


	/*---------------CLIMB-----------------*/

	public static int climbRightServo = 10; // TODO: CHANGE!
	public static int climbLeftServo = 11; // TODO: CHANGE!
	
	public static int climbMotor1 = 4;
	public static int climbMotor2 = 5;
	public static int climb1PDP = 14;
	public static int climb2PDP = 15;
	
	public static final int[] climbPDPs = new int[] {climb1PDP, climb2PDP}; // TODO: CHANGE!
	/*-------------------------------------*/



	/*----------------DRIVE----------------*/
	public static final int driveRightRearMotor = 1; // TODO: CHANGE!
	public static final int driveRightFrontMotor = 2; // TODO: CHANGE!
	public static final int driveLeftRearMotor = 3; // TODO: CHANGE!
	public static final int driveLeftFrontMotor = 4; // TODO: CHANGE!
	/*-------------------------------------*/

	/*---------------GRIPPER---------------*/
	public static int gripperWheelLeft = 8; // TODO: CHANGE!
	public static int gripperLeftPDP = 10; // TODO: CHANGE!
	public static int gripperWheelRight = 6; // TODO: CHANGE!
	public static int gripperRightPDP = 5; // TODO: CHANGE!
	
	public static final int[] gripperPDPs = new int[] {gripperLeftPDP, gripperRightPDP};
	/*-------------------------------------*/

	/*----------------LIFT-----------------*/

	public static int liftMotorPort = 2;

	/*-------------------------------------*/

	/*----------------INTAKE---------------*/
	public static final int intakeMotorLeft = 7;
	public static int intakeLeftPDP = 6;
	public static final int intakeMotorRight = 5;
	public static int intakeRightPDP = 4;
	
	public static final int[] intakePDPs = new int[] {intakeLeftPDP, intakeRightPDP};
	/*-------------------------------------*/

	/*-----------------------------------SENSORS----------------------------------*/

	/*---------------CLIMB-----------------*/
	public static int climbLimitSwitch = 6; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------DRIVE----------------*/

	public static final int driveRightEncoderChannelA = 2; // TODO: CHANGE!
	public static final int driveRightEncoderChannelB = 3; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelA = 0; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelB = 1; // TODO: CHANGE!

	public static final double driveEncoderDistancePerPulse = 0.00133; // TODO:
																	// CHANGE!

	public static final int backUltrasonic = 1; // TODO: CHANGE!
	public static int bumpSensor = 7; // TODO: CHANGE!
	public static int colorSensor = 2;
	/*-------------------------------------*/

	/*---------------GRIPPER---------------*/
	public static int proximityChannel = 0; // TODO: CHANGE!
	/*-------------------------------------*/

	/*----------------LIFT-----------------*/
	public static int liftEncoderPortA = 4;
	public static int liftEncoderPortB = 5;

	public static int liftHallEffectTop = 8;
	public static int liftHallEffectBottom = 9;
	/*-------------------------------------*/

	
	/*----------------INTAKE---------------*/
	public static final int intakeSolenoid1 = 0;
	public static final int intakeSolenoid2 = 1;
	/*-------------------------------------*/
	
}