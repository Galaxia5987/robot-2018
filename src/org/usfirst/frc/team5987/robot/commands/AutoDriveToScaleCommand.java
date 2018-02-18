package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDriveToScaleCommand extends CommandGroup {

	private static double END_Y = Constants.toMeter(41.88) + Constants.AUTO_SCALE_SHIFT_T0_FIELD_CENTER - Constants.CENTER_TO_BACK_BUMPER;

	public AutoDriveToScaleCommand(char scalePosition) {
		addSequential(new IntakeSolenoidCommand(true));
		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_LIFT_DELAY));
		addSequential(new PathPointsCommand(new Point[] { 
				new Point(0, 0),
				new Point(Constants.toMeter(196) - Constants.CENTER_TO_BACK_BUMPER, 0),
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.AUTO_TURN_DISTANCE_BEFORE_SCALE,
						END_Y * ((scalePosition == 'R') ? 1 : -1)),
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.AUTO_END_DISTANCE_BEFORE_SCALE,
						END_Y * ((scalePosition == 'R') ? 1 : -1))
				})
		);
		addSequential(new ShootCubeCommand(0.4, true));
		
	}

}
