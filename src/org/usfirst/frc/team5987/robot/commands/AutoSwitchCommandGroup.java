package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.NetworkTable;
import org.usfirst.frc.team5987.robot.subsystems.NetworkTableEntry;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSwitchCommandGroup extends CommandGroup {

	public AutoSwitchCommandGroup() {
		/**
		 * The game specific message which includes data about the Scale and
		 * Switches Plates randomanization.
		 */
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		/**
		 * Determines where the Robot was placed in the beginning of the game.
		 */
		char robotInitPosition = Robot.driveSubsystem.driveTable.getEntry("Robot init position");
		
		// Positions of the alliance Switch and Scale Plates.
		char switchPosition = gameData.charAt(0), scalePosition = gameData.charAt(1);
		
		public NetworkTable autoTable = NetworkTableInstance.getDefault().getTable("Autonomous");

		/**
		 * Distance from the alliance wall to the auto line. REMEMBER TO ADD A FACTOR.
		 */
		NetworkTableEntry autoLineDistance = autoTable.getEntry("auto line distance");
		
		/************************* SWITCH ********************/
		/**
		 * Distance the robot drives forward when heading to the switch, starting at the aliance wall.
		 */
		NetworkTableEntry beginningSwitchDist = autoTable.getEntry("switch distance 1");
		
		/**
		 * Angle the robot rotates to when heading to the switch (between
		 * {@link #switchDist1} and {@link #distFromSwitch}).
		 */
		NetworkTableEntry switchAngle = autoTable.getEntry("switch angle");

		/**
		 * Final distance from the Switch after rotating the second time.
		 */
		NetworkTableEntry distanceFromSwitch = autoTable.getEntry("switch distance 2");
		
		/**
		 * Angle relative to the robot the robot should nullify to get to the switch.
		 */
		NetworkTableEntry finalAngleFromSwitch = autoTable.getEntry("angle from switch");
		
		// Robot is in center, ready to go to one of two Platforms of the
		// Switch.
		if (robotInitPosition == 'C') {
			// Switch plate is on the left.
			if (switchPosition == 'L') {
				addSequential(DriveStraightCommand(beginningSwitchDist));
				addSequential(TurnCommand(switchAngle));
				addSequential(DriveStraightCommand(distanceFromSwitch, finalAngleFromSwitch));
			}
			// Switch plate in on the right.
			if (switchPosition == 'R') {
				addSequential(DriveStraightCommand(beginningSwitchDist));
				addSequential(TurnCommand(-switchAngle));
				addSequential(DriveStraightCommand(distanceFromSwitch, finalAngleFromSwitch));
			}
		}
		
		if (robotInitPosition != scalePosition)
		{
			addSequential(DriveStraightCommand(autoLineDistance));
		}
		
		if (scalePosition == 'L')
		{
			// TODO: Merge branch of arriving to the scale, then add this GroupCommand here.
		}
		
		// Robot is on one of the sides of the field, ready to go to one of 
		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
