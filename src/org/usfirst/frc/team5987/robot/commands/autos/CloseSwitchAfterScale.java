package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants.LiftCommandStates;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CloseSwitchAfterScale extends CommandGroup {

	public CloseSwitchAfterScale(char currentPosition) {
		addSequential(new GetAndEatSwitchCube(currentPosition));
	}
}