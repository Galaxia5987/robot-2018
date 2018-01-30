/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.ArriveToSwitchGroupCommand;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.ExampleCommand;
import org.usfirst.frc.team5987.robot.commands.TurnCommand;
import org.usfirst.frc.team5987.robot.commands.TurnToTargetGroupCommand;
import org.usfirst.frc.team5987.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.GripperSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
	public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static final GripperSubsystem gripperSubsystem = new GripperSubsystem();
	public static OI m_oi;
	public static final LiftSubsystem liftSubsystem = new LiftSubsystem();
	NetworkTable liftTable = NetworkTableInstance.getDefault().getTable("liftTable");
	NetworkTable driveTable = NetworkTableInstance.getDefault().getTable("Drive");
	public static NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("Vision");
	NetworkTableEntry ntLeftSP = driveTable.getEntry("Left SP");
	NetworkTableEntry ntRightSP = driveTable.getEntry("Right SP");
	NetworkTableEntry ntAngle = driveTable.getEntry("Angle");
	NetworkTableEntry ntSetpoint = liftTable.getEntry("Setpoint");
	
	public static AHRS navx = new AHRS(SPI.Port.kMXP);
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	

	public static NetworkTableEntry ntSwitchAngle = visionTable.getEntry("Switch Angle");

	public static NetworkTableEntry ntSwitchDistance = visionTable.getEntry("Switch Distance");


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		navx.reset();
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		ntSwitchAngle.setDouble(ntSwitchAngle.getDouble(0));
		ntSwitchDistance.setDouble(ntSwitchDistance.getDouble(0));
		SmartDashboard.putData(new TurnCommand(ntSwitchAngle, true));
		SmartDashboard.putData(new TurnToTargetGroupCommand());
		SmartDashboard.putData(new DriveStraightCommand(ntSwitchDistance));
		SmartDashboard.putData(new ArriveToSwitchGroupCommand());
		ntSetpoint.setDouble(0);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveSubsystem.setSetpoints(0, 0);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		navx.reset();
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		navx.reset(); // TODO: remove 
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
//		driveSubsystem.setSetpoints(1, 1);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		liftSubsystem.updateMotors();
		ntAngle.setDouble(driveSubsystem.getAngle());
//		driveSubsystem.setSetpoints(ntLeftSP.getDouble(-0.1), ntRightSP.getDouble(-0.1));
//		driveSubsystem.setSetpoints(-0.3, -1);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
