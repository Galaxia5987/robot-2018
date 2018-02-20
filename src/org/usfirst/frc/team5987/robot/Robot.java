/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.autos.*;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;

import org.usfirst.frc.team5987.robot.commands.EatCubeGroupCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;

import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.PathSwitchCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TestAbsPath;
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
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
	public static double[] robotAbsolutePosition = new double[]{0, 0};
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
	NetworkTable driveTable = NetworkTableInstance.getDefault().getTable("Drive");
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
    SendableChooser <Character> initPositionChooser = new SendableChooser <>();
    SendableChooser <String> scaleChooser = new SendableChooser <>();
    SendableChooser <String> switchChooser = new SendableChooser <>();
    SendableChooser <Command> m_chooser = new SendableChooser <>();
    char initPosition;
    String scaleChoice, switchChoice;
	public static NetworkTableEntry ntVisionAngle = visionTable.getEntry("Angle");
	public static NetworkTableEntry ntVisionTarget = visionTable.getEntry("Sees Target");
	public static NetworkTableEntry ntVisionDistance = visionTable.getEntry("Distance");
	public static NetworkTableEntry ntVisionFilterMode = visionTable.getEntry("Filter Mode");

	Compressor compressor = new Compressor(1);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("Match number", DriverStation.getInstance().getMatchNumber());
		navx.reset();
		m_oi = new OI();
        
		// Autonomous options
		initPositionChooser.addObject("Right", 'R');
		initPositionChooser.addDefault("Center", 'C');
		initPositionChooser.addObject("Left", 'L');

		scaleChooser.addDefault("Nothing", "nothing");
		scaleChooser.addObject("Close", "close");
		scaleChooser.addObject("Close and far", "both");
		
		switchChooser.addDefault("Nothing", "nothing");
		switchChooser.addObject("Close", "close");
		switchChooser.addObject("Close and far", "both");
		switchChooser.addObject("Side", "side");
        
        SmartDashboard.putData("Auto mode", initPositionChooser);
		m_chooser.addDefault("Default Auto", new Switch());
		m_chooser.addObject("Line", new DriveStraightCommand(1.5));
		m_chooser.addObject("Scale (Robot Right) backwards", new CloseScale('R', true));
		m_chooser.addObject("Scale (Robot Left) forward", new CloseScale('L', false));
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putData("Robot Position", initPositionChooser);
		SmartDashboard.putData("Scale Options", scaleChooser);
		SmartDashboard.putData("Switch Options", switchChooser);

		SmartDashboard.putData(new TurnCommand(30, true));
		SmartDashboard.putData(new TurnToTargetGroupCommand());
		SmartDashboard.putData("Drive forward", new DriveStraightCommand(3));
		SmartDashboard.putData("Drive 0.2", new DriveStraightCommand(0.2));
		SmartDashboard.putData("Drive 4", new DriveStraightCommand(4));
		SmartDashboard.putData("Drive backward", new DriveStraightCommand(-3));
		SmartDashboard.putData(new Switch());
		SmartDashboard.putData("0.5M lift 3s", new LiftCommand(0.5, 3));
		SmartDashboard.putData("0M lift", new LiftCommand(0));
		SmartDashboard.putData(new PathSwitchCommand());
		SmartDashboard.putData(new PathPointsCommand(new Point[]{
				new Point(0,0),
				new Point(1, 0),
				new Point (2, 2)
				})
				);

        SmartDashboard.putData(new CloseScale('R'));

		SmartDashboard.putData(new ShootCubeCommand(1, true));
		SmartDashboard.putData(new TurnTillSeesTargetCommand(-90, true, ntVisionTarget));
		SmartDashboard.putData(new Switch());
		SmartDashboard.putData(new EatCubeGroupCommand());
		SmartDashboard.putData(new TestAbsPath());
		SmartDashboard.putData(new IntakeSolenoidCommand());
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
		SmartDashboard.putBoolean("Robot Enabled", false);
		liftSubsystem.setSetpoint(0);
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
		SmartDashboard.putBoolean("Robot Enabled", true);
		navx.reset();
//		m_chooser.addDefault("Default Auto", new AutoSwitchCommandGroup('C'));
//		m_chooser.addObject("Line", new DriveStraightCommand(1.5));
//		m_chooser.addObject("Scale (Robot Right)", new CloseScale('R', true));
//		m_chooser.addObject("Scale (Robot Left)", new CloseScale('L', false));
//		m_autonomousCommand = m_chooser.getSelected();
		while(DriverStation.getInstance().getGameSpecificMessage().length() != 3){} // wait for game data
		char switchPosition = DriverStation.getInstance().getGameSpecificMessage().charAt(0), 
			 scalePosition = DriverStation.getInstance().getGameSpecificMessage().charAt(1);

//		if (m_autonomousCommand != null) {
//			m_autonomousCommand.start();
//		}
		boolean isBack = true;
        initPosition = initPositionChooser.getSelected();
        scaleChoice = scaleChooser.getSelected();
        switchChoice = switchChooser.getSelected();
		(new MainAuto(initPosition, scaleChoice, switchChoice, isBack)).start();
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
		SmartDashboard.putBoolean("Robot Enabled", true);
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
		SmartDashboard.putNumber("Drive Setpoint", 0);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
//		driveSubsystem.setSetpoints(SmartDashboard.getNumber("Drive Setpoint", 0), SmartDashboard.getNumber("Drive Setpoint", 0));
//		driveSubsystem.updatePID();
//		SmartDashboard.putNumber("right V", driveSubsystem.getRightSpeed());
//		SmartDashboard.putNumber("left V", driveSubsystem.getLeftSpeed());
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
//
//		driveSubsystem.ntRightDistance.setDouble(driveSubsystem.getRightDistance());
//		driveSubsystem.ntLeftDistance.setDouble(driveSubsystem.getLeftDistance());
		// driveSubsystem.setSetpoints(ntLeftSP.getDouble(-0.1),
		// ntRightSP.getDouble(-0.1));
		// driveSubsystem.setSetpoints(-0.3, -1);

		SmartDashboard.putBoolean("inTake", intakeSubsystem.getSolenoid());
		
		// Make the Xbox controller rumble if a Power Cube is recognized by the camera.
		if (ntVisionTarget.getBoolean(false))
		{
			m_oi.xbox.setRumble(RumbleType.kLeftRumble, 1);		
			m_oi.xbox.setRumble(RumbleType.kRightRumble, 1);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
