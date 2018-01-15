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
	public static int servo1 = 8; // TODO: CHANGE!
	public static int servo2 = 9; // TODO: CHANGE!
	public static int motor = 2; // TODO: CHANGE!
	public static int limitSwitch = 9; // TODO: CHANGE!
	public static double climbSpeed = 0.3;
	public static double openPosition = 1;
	public static double closedPosition = 0;
	/*--------------------------------------------------------------*/

	
	/*--------------------------DRIVE-------------------------------*/
	public static final int driveRightRearMotor = 0; // TODO: CHANGE!
	public static final int driveRightFrontMotor = 1; // TODO: CHANGE!
	public static final int driveLeftRearMotor = 2; // TODO: CHANGE!
	public static final int driveLeftFrontMotor = 3; // TODO: CHANGE!
	
	public static final int driveRightEncoderChannelA = 4; // TODO: CHANGE!
	public static final int driveRightEncoderChannelB = 5; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelA = 6; // TODO: CHANGE!
	public static final int driveLeftEncoderChannelB = 7; // TODO: CHANGE!
	
	public static final int driveEncoderDistancePerPulse = 1 * 1; // TODO: CHANGE!
	
	public static final int backUltrasonicPing = 8; // TODO: CHANGE!
	public static final int backUltrasonicEcho = 9; // TODO: CHANGE!
	public static int bumpSensor = 8; // TODO: CHANGE!
	/*--------------------------------------------------------------*/
	
	
	/*--------------------------INTAKE------------------------------*/
	public static int gripperWheelLeft = 666; // TODO: CHANGE!
	public static int gripperWheelRight = 666; // TODO: CHANGE!
	public static int beamChannel = 666; // TODO: CHANGE!
	/*--------------------------------------------------------------*/

	
}
