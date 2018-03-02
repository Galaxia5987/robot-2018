package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.EatCubeGroupCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TakeCommand;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GetAndEatSwitchCube extends CommandGroup {

    public GetAndEatSwitchCube(char currentPosition) {
		if (Constants.isCubeVision) {
			addSequential(new WaitCommand(2));
			addSequential(new EatCubeGroupCommand());
		} 
		else {
			if (currentPosition == 'R') {
				addSequential(new PathPointsCommand(new Point[] { new Point(4.781846902310962, 1.0371526195899774) },
						false, false));
			} else {
				addSequential(new PathPointsCommand(new Point[] { new Point(4.819487594119858, 4.6366059225512535) },
						false, false));
			}
		}
		addParallel(new IntakeSolenoidCommand());
		addParallel(new DriveStraightCommand(0.3, 0.5));
		addSequential(new LiftCommand(Constants.LiftCommandStates.SWITCH));
		addSequential(new WaitCommand(0.75));
		addSequential(new ShootCubeCommand(1, true));
    }
}
