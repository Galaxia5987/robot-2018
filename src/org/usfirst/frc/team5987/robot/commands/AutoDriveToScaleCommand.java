package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoDriveToScaleCommand extends PathPointsCommand {

	private static double END_Y = Constants.toMeter(41.88) + Constants.SS - Constants.CENTER_TO_BACK_BUMPER;

	public AutoDriveToScaleCommand(char scalePosition) {
		super(new Point[] { new Point(0, 0), new Point(Constants.toMeter(196) - Constants.CENTER_TO_BACK_BUMPER, 0),
				// If the scale is on the right
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.STD,
						END_Y * scalePosition == 'R' ? 1 : -1),
				new Point(Constants.toMeter(299.65) - Constants.CENTER_TO_BACK_BUMPER - Constants.SED,
						END_Y * scalePosition == 'R' ? 1 : -1) });
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return super.isFinished() || Robot.driveSubsystem.isBump();
	}
}
