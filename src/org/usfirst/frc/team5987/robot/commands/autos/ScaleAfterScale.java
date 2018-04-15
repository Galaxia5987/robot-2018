package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.EatCubeGroupCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ScaleAfterScale extends CommandGroup {

    public ScaleAfterScale(char startingRobotPosition, char currentPosition, boolean isClose) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
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
//		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, 0.5));
//		final int Y_DIRECTION = startingRobotPosition == 'R' ? 1 : -1;
//		if(isClose){
//			addSequential(new PathPointsCommand(new Point[]{
//				    new Point(6.137993640668958, 0.8295560355381902 * Y_DIRECTION),
//				    new Point(7.334839572192509, 0.8692616234756096 * Y_DIRECTION)}, true, false, 4));
//		}else{
//			addSequential(new PathPointsCommand(new Point[]{
//				    new Point(6.378173615800014, 5.32919896400697 * Y_DIRECTION),
//				    new Point(7.260286478227652, 5.259730743437007 * Y_DIRECTION)}, true, false, 4));
//		}
//		addSequential(new ShootCubeCommand(-1, true));
//		addSequential(new DriveStraightCommand(0.2));

    }
}
