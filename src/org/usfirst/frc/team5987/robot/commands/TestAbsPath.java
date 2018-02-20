package org.usfirst.frc.team5987.robot.commands;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestAbsPath extends CommandGroup {

    public TestAbsPath() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new PathPointsCommand(new Point[]{
    			new Point(1, 0.0001)
    	}, false, false));
    	addSequential(new PathPointsCommand(new Point[]{
    			new Point(2, 0)
    	}, false, false));
    }
}
