/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.ArriveToSwitchGroupCommand;
import org.usfirst.frc.team5987.robot.commands.AutoCommandGroup;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.ExampleCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;

import org.usfirst.frc.team5987.robot.commands.PathCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.PathSwitchCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TurnCommand;
import org.usfirst.frc.team5987.robot.commands.TurnTillSeesTargetCommand;
import org.usfirst.frc.team5987.robot.commands.TurnToTargetGroupCommand;
import org.usfirst.frc.team5987.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.GripperSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem;

import com.kauailabs.navx.frc.AHRS;

import auxiliary.Point;
import auxiliary.Watch_Doge;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.CameraServer;
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

	public static final PowerDistributionPanel PDP = new PowerDistributionPanel();

	public static final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final ClimbSubsystem climbSubsystem = new ClimbSubsystem();
	public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static final GripperSubsystem gripperSubsystem = new GripperSubsystem();
	public static final LiftSubsystem liftSubsystem = new LiftSubsystem();

	public static OI m_oi;

	public static final Watch_Doge clingyShiba = new Watch_Doge(PDP, gripperSubsystem, RobotMap.gripperPDPs, 30, 0.5);
	public static final Watch_Doge inTakeCanine = new Watch_Doge(PDP, intakeSubsystem, RobotMap.intakePDPs, 27, 0.5);
	public static final Watch_Doge climbingCatahoulaLeopard = new Watch_Doge(PDP, climbSubsystem, RobotMap.climbPDPs,
			30, 2);
	
	NetworkTable LiftTable = liftSubsystem.LiftTable;
	public static NetworkTable driveTable = NetworkTableInstance.getDefault().getTable("Drive");
	public static NetworkTable visionTable = NetworkTableInstance.getDefault().getTable("Vision");
	NetworkTableEntry ntLeftSP = driveTable.getEntry("Left SP");
	NetworkTableEntry ntRightSP = driveTable.getEntry("Right SP");
	NetworkTableEntry ntAngle = driveTable.getEntry("Angle");
	NetworkTableEntry ntPitch = driveTable.getEntry("Pitch");
	NetworkTableEntry ntYaw = driveTable.getEntry("Yaw");

	NetworkTableEntry ntAcs1 = driveTable.getEntry("Acs1");
	NetworkTableEntry ntAcs2 = driveTable.getEntry("Acs2");
	NetworkTableEntry ntAcs3 = driveTable.getEntry("Acs3");

	NetworkTableEntry ntGyroX = driveTable.getEntry("GyroX");
	NetworkTableEntry ntGyroY = driveTable.getEntry("GyroY");
	NetworkTableEntry ntGyroZ = driveTable.getEntry("GyroZ");

	NetworkTableEntry ntSetpoint = LiftTable.getEntry("Setpoint");
	CameraServer cs;

	public static AHRS navx = new AHRS(SPI.Port.kMXP);

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	public static NetworkTableEntry ntSwitchAngle = visionTable.getEntry("Angle");
	public static NetworkTableEntry ntSwitchTarget = visionTable.getEntry("Sees Target");
	public static NetworkTableEntry ntSwitchDistance = visionTable.getEntry("Distance");

	Compressor compressor = new Compressor(1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		navx.reset();
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new AutoCommandGroup('C'));
		m_chooser.addObject("Line", new DriveStraightCommand(1.5));
		SmartDashboard.putData("Auto mode", m_chooser);
		ntSwitchAngle.setDouble(ntSwitchAngle.getDouble(0));
		ntSwitchDistance.setDouble(ntSwitchDistance.getDouble(0));
		SmartDashboard.putData(new TurnCommand(30, true));
		SmartDashboard.putData(new TurnToTargetGroupCommand());
		SmartDashboard.putData(new DriveStraightCommand(ntSwitchDistance));
		SmartDashboard.putData(new ArriveToSwitchGroupCommand());
		SmartDashboard.putData(new LiftCommand());
		SmartDashboard.putData(new PathSwitchCommand());
		SmartDashboard.putData(new PathPointsCommand(new Point[]{
				new Point(0,0),
				new Point(1, 0),
				new Point (2, 2)
				})
				);
		SmartDashboard.putData(new AutoCommandGroup('C'));
		SmartDashboard.putData(new ShootCubeCommand(1, true));
		SmartDashboard.putData(new TurnTillSeesTargetCommand(-90, true, ntSwitchTarget));
		SmartDashboard.putData(new ArriveToSwitchGroupCommand());
		ntSetpoint.setDouble(0);
		liftSubsystem.setState(LiftSubsystem.States.ZEROING);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		driveSubsystem.setSetpoints(0, 0);
		driveSubsystem.setLeftSpeed(0);
		driveSubsystem.setRightSpeed(0);
		driveSubsystem.resetEncoders();

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		liftSubsystem.update();
		// liftSubsystem.setSetpoint(0);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		navx.reset();
		m_chooser.addDefault("Default Auto", new AutoCommandGroup('C'));
		m_chooser.addObject("Line", new DriveStraightCommand(1.5));
		m_autonomousCommand = m_chooser.getSelected();

//		String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
//		switch (autoSelected) {
//		case "Default Auto":
//			m_autonomousCommand = new MyAutoCommand();
//			break;
//		case "Default Auto":
//		default:
//			m_autonomousCommand = null;
//			break;
//		}

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
		liftSubsystem.displaySensorValues();
		liftSubsystem.update();
		clingyShiba.feed();
		inTakeCanine.feed();
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
		// driveSubsystem.setSetpoints(1, 1);
		compressor.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// liftSubsystem.update();
		liftSubsystem.displaySensorValues();
		// double joyY = m_oi.rightStick.getY();
		// SmartDashboard.putNumber("Joy Y", joyY);
		// liftSubsystem.setPrecentSpeed(joyY);
		ntPitch.setDouble(driveSubsystem.getPitch());
		ntYaw.setDouble(driveSubsystem.getYaw());
		ntAngle.setDouble(driveSubsystem.getAngle());

		
		ntAcs1.setDouble(navx.getRawAccelX());
		ntAcs2.setDouble(navx.getRawAccelY());
		ntAcs3.setDouble(navx.getRawAccelZ());

		ntGyroX.setDouble(navx.getRawGyroX());
		ntGyroY.setDouble(navx.getRawGyroY());
		ntGyroZ.setDouble(navx.getRawGyroZ());

		liftSubsystem.update();
		clingyShiba.feed();
		inTakeCanine.feed();
		gripperSubsystem.ntProximityVoltage.setDouble(gripperSubsystem.voltage());
		gripperSubsystem.ntSeesCube.setBoolean(gripperSubsystem.isCubeInside());
		// driveSubsystem.setLeftSpeed(-m_oi.left.getY());
		// driveSubsystem.setRightSpeed(-m_oi.right.getY());
		SmartDashboard.putNumber("left Dis", driveSubsystem.getLeftDistance());
		SmartDashboard.putNumber("right Dis", driveSubsystem.getRightDistance());

		driveSubsystem.ntRightDistance.setDouble(driveSubsystem.getRightDistance());
		driveSubsystem.ntLeftDistance.setDouble(driveSubsystem.getLeftDistance());
		// driveSubsystem.setSetpoints(ntLeftSP.getDouble(-0.1),
		// ntRightSP.getDouble(-0.1));
		// driveSubsystem.setSetpoints(-0.3, -1);

		SmartDashboard.putBoolean("inTake", intakeSubsystem.getSolenoid());

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
