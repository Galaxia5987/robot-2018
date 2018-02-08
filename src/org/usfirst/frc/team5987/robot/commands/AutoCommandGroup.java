package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.NetworkTable;
import org.usfirst.frc.team5987.robot.subsystems.NetworkTableEntry;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCommandGroup extends CommandGroup {

	public AutoCommandGroup() {
		/**
		 * The game specific message which includes data about the Scale and
		 * Switches Plates randomanization.
		 */
		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		/**
		 * Determines where the Robot was placed in the beginning of the game.
		 */
		NetworkTableEntry robotInitPosition = Robot.driveSubsystem.driveTable.getEntry("Robot init position");

		// Positions of the alliance Switch and Scale Plates.
		char switchPosition = gameData.charAt(0), scalePosition = gameData.charAt(1);

		public NetworkTable autoTable = NetworkTableInstance.getDefault().getTable("Autonomous");

		/**
		 * Distance from the alliance wall to the auto line. REMEMBER TO ADD A
		 * FACTOR.
		 */
		NetworkTableEntry autoLineDistance = autoTable.getEntry("auto line distance");

		/************************* SWITCH ********************/
		/**
		 * Distance the robot drives forward when heading to the switch,
		 * starting at the aliance wall.
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
		 * Angle relative to the robot the robot should nullify to get to the
		 * switch.
		 */
		NetworkTableEntry finalAngleFromSwitch = autoTable.getEntry("angle from switch");

		/**
		 * Second distance the robot drives (between
		 * {@link #beginningSwitchDist} and {@link #distanceFromSwitch})
		 */
		NetworkTableEntry secondSwitchDist = autoTable.getEntry("second switch distance");

		// Robot is in center, ready to go to one of two Platforms of the
		// Switch.
		addSequential(DriveStraightCommand(beginningSwitchDist));
		if (robotInitPosition == 'C') {
			// Switch plate is on the left.
			if (switchPosition == 'L') {
				addSequential(TurnCommand(switchAngle));
				addSequential(DriveStraightCommand(secondSwitchDist));
				addSequential(TurnCommand(-switchAngle));
			}
			// Switch plate in on the right.
			if (switchPosition == 'R') {
				addSequential(TurnCommand(-switchAngle));
				addSequential(DriveStraightCommand(secondSwitchDist));
				addSequential(TurnCommand(switchAngle));
				addSequential(DriveStraightCommand(distanceFromSwitch, finalAngleFromSwitch));

			}
			addSequential(ArriveToSwitchGroupCommand()); // TODO: Merge branches of arriving to the Switch and add here.
			addSequential(PutCubeOnSwitchGroupCommand()); // TODO: Merge branches of putting a Power Cube on the Switch and add here.
		}

		if (robotInitPosition != scalePosition) {
			addSequential(DriveStraightCommand(autoLineDistance));
		}
		addSequential(ArriveToScaleGroupCommand()); // TODO: Merge branch of arriving to the Scale and add here.
		addSequential(PutCubeOnScaleGroupCommand()); // TODO: Merge branch of putting a Power Cube on the Scale and add here.
	}
}
