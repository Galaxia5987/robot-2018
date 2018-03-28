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


	/*done---------------CLIMB-----------------*/

	
	public static int climbMotor1 = 10;
	public static int climbMotor2 = 9;
	public static int climb1PDP = 12;
	public static int climb2PDP = 13;
	
	public static final int[] climbPDPs = new int[] {climb1PDP, climb2PDP}; // TODO: CHANGE!
	/*-------------------------------------*/



	/*done----------------DRIVE----------------*/
	public static final int driveRightRearMotor = 8; // TODO: CHANGE!
	public static final int driveRightFrontMotor = 9; // TODO: CHANGE!
	public static final int driveLeftRearMotor = 6; // TODO: CHANGE!
	public static final int driveLeftFrontMotor = 7; // TODO: CHANGE!
	/*-------------------------------------*/

	/*done---------------GRIPPER---------------*/
	public static int gripperWheelLeft = 2; // TODO: CHANGE!
	public static int gripperLeftPDP = 6; // TODO: CHANGE!
	public static int gripperWheelRight = 3; // TODO: CHANGE!
	public static int gripperRightPDP = 5; // TODO: CHANGE!
	
	public static final int[] gripperPDPs = new int[] {gripperLeftPDP, gripperRightPDP};
	/*-------------------------------------*/

	/*done----------------LIFT-----------------*/

	public static int liftMotorPort = 2;

	/*-------------------------------------*/

	/*done----------------INTAKE---------------*/
	public static final int intakeMotorLeft = 4;
	public static int intakeLeftPDP = 4;
	public static final int intakeMotorRight = 5;
	public static int intakeRightPDP = 11;
	
	public static final int[] intakePDPs = new int[] {intakeLeftPDP, intakeRightPDP};
	/*-------------------------------------*/

	/*-----------------------------------SENSORS----------------------------------*/

	/*done----------------DRIVE----------------*/

	public static final int driveRightEncoderChannelA = 0; // TODO: CHANGE!
	public static final int driveRightEncoderChannelB = 1; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelA = 2; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelB = 3; // TODO: CHANGE!
	/*-------------------------------------*/

	/*done---------------GRIPPER---------------*/
	public static int proximityChannel = 0; // TODO: CHANGE!
	/*-------------------------------------*/
	
	/*done----------------INTAKE---------------*/
	public static final int intakeSolenoid1 = 0;
	public static final int intakeSolenoid2 = 1;
	public static final int intakeSolenoid3 = 2;
	public static final int intakeSolenoid4 = 3;
	/*-------------------------------------*/
	
}