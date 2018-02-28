package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.usfirst.frc.team5987.robot.commands.*;
import org.usfirst.frc.team5987.robot.commands.ChangeFilterModeCommand.Modes;

/**
 *
 */
public class Switch extends CommandGroup {
	
	public Switch() {
	
		// Positions of the alliance Switch and Scale Plates.
		char switchPosition = DriverStation.getInstance().getGameSpecificMessage().charAt(0);

		addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.SWITCH));
		addSequential(new IntakeSolenoidCommand(true));
		// Robot is in center, ready to go to one of two Platforms of the
		// Switch.
			addSequential(new DriveStraightCommand(Constants.AUTO_SWITCH_STRAIGHT,0,1.5));
			// Switch plate is on the left.
			if (switchPosition == 'L') {
				addSequential(new TurnCommand(Constants.AUTO_SWITCH_TURN, false,1.5));
			}
			// Switch plate in on the right.
			if (switchPosition == 'R') {
				addSequential(new TurnCommand(-Constants.AUTO_SWITCH_TURN, false,1.5));
			}
			addSequential(new WaitToTargetCommand(Robot.ntVisionTarget, 1));
			addSequential(new LiftCommand(Constants.LiftCommandStates.SWITCH));
			addParallel(new IntakeSolenoidCommand(false));
			addSequential(new PathSwitchCommand(4));

			addSequential(new ShootCubeCommand(1, true));
			addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.STREAM));
	}
}
