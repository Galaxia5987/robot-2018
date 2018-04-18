package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CubeCommand extends CommandGroup {

    public CubeCommand(Point cube, double timeout) {
    	requires(Robot.gripperSubsystem);
    	requires(Robot.intakeSubsystem);
    	requires(Robot.driveSubsystem);

    	addSequential(new IntakeSpringSolenoidCommand(false));
    	addParallel(new TakeCommand(0,timeout));
    	addSequential(new PathPointsCommand(new Point[]{cube}, false, false, timeout));
    	addSequential(new IntakeSpringSolenoidCommand(true));
    }
}
