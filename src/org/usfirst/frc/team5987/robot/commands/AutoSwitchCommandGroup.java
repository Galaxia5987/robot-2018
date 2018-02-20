package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team5987.robot.commands.*;

/**
 *
 */
public class AutoSwitchCommandGroup extends CommandGroup {
	private char startPosition;
	
	public AutoSwitchCommandGroup(char startPosition) {
		this.startPosition = startPosition;
	
		/**
		 * The game specific message which includes data about the Scale and
		 * Switches Plates randomanization.
		 */
		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		// Positions of the alliance Switch and Scale Plates.
		char switchPosition = gameData.charAt(0), scalePosition = gameData.charAt(1);

		NetworkTable autoTable = NetworkTableInstance.getDefault().getTable("Autonomous");

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
		addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.SWITCH));
		addSequential(new IntakeSolenoidCommand(true));
		// Robot is in center, ready to go to one of two Platforms of the
		// Switch.
		if (startPosition == 'C') {
			addSequential(new DriveStraightCommand(Constants.AUTO_SWITCH_STRAIGHT));
			// Switch plate is on the left.
			if (switchPosition == 'L') {
				addSequential(new TurnCommand(Constants.AUTO_SWITCH_TURN, false));
			}
			// Switch plate in on the right.
			if (switchPosition == 'R') {
				addSequential(new TurnCommand(-Constants.AUTO_SWITCH_TURN, false));
			}
			addSequential(new WaitToTargetCommand(Robot.ntVisionTarget, 1));
			addSequential(new LiftCommand(Constants.LiftCommandStates.SWITCH));
			addParallel(new IntakeSolenoidCommand(false));
			addSequential(new PathSwitchCommand());

			addSequential(new ShootCubeCommand(1, true));
			addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.STREAM));
		}

		if (startPosition != scalePosition) {
			addSequential(new DriveStraightCommand(autoLineDistance.getDouble(0)));
		}
//		addSequential(ArriveToScaleGroupCommand()); // TODO: Merge branch of arriving to the Scale and add here.
//		addSequential(PutCubeOnScaleGroupCommand()); // TODO: Merge branch of putting a Power Cube on the Scale and add here.
	}
}
