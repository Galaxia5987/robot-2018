package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;

import auxiliary.Point;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TakeCommand;

/**
 *
 */
public class FarScale extends CommandGroup {
	/**
	 * The game specific message which includes data about the Scale and
	 * Switches Plates randomanization.
	 */
	String gameData = DriverStation.getInstance().getGameSpecificMessage();

	// Positions of the alliance Switch and Scale Plates.
	char scalePosition = gameData.charAt(1);
	private static double END_Y = Constants.toMeter(41.88) + Constants.AUTO_SCALE_CLOSE_SHIFT_T0_FIELD_CENTER - Constants.CENTER_TO_BACK_BUMPER;

	public FarScale(char robotPosition, boolean isBackwards) {
		double intakeDelay = isBackwards ? 2 : 0; // delay for opening intake so it won't touch the allience wall
		double forwardAddition = isBackwards ? 0.29 : 0; // forward addition to backwards
		double sideAddition = isBackwards ? 0.17 : 0; // side addition to backwards
		addParallel(new IntakeSolenoidCommand(true, intakeDelay));
//		if(isBackwards)
			addParallel(new TakeCommand(3.5, 0.7));
		addParallel(new LiftCommand(0.1, 1.7)); // move the lift up a bit to prevent the cube from touching the floor  
		final int Y_DIRECTION = robotPosition == 'R' ? 1 : -1;
		/**Far Scale**/
		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_FAR_LIFT_DELAY));
		addSequential(new PathPointsCommand(new Point[] { 
				new Point(4.5, -0.15 * Y_DIRECTION),
				new Point(5.3, 0.4 * Y_DIRECTION),
				new Point(5.30001, 4.0 * Y_DIRECTION),
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.AUTO_TURN_DISTANCE_BEFORE_SCALE,
						(4.55 - Constants.AUTO_SCALE_FAR_SHIFT_T0_FIELD_CENTER + sideAddition) * Y_DIRECTION),
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.AUTO_END_DISTANCE_BEFORE_SCALE + forwardAddition,
						(4.55 - Constants.AUTO_SCALE_FAR_SHIFT_T0_FIELD_CENTER + sideAddition) * Y_DIRECTION)
				}, isBackwards, true)
		);
		if (isBackwards) {
			addSequential(new ShootCubeCommand(-0.4, true));
		}
		else {
			addSequential(new ShootCubeCommand(0.4, true));
		}
	}
	
	public FarScale(char robotPosition) {
		this(robotPosition, false);
	}
}
