package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SwitchSide extends CommandGroup {

    public SwitchSide(char robotPosition, boolean isBackwards) {
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
    	// Side Switch Points
    	final int Y_DIRECTION = robotPosition == 'R' ? 1 : -1;
    	addSequential(new PathPointsCommand(new Point[]{
    		    new Point(2.6866389908209403, -0.27514806378132106 * Y_DIRECTION),
    		    new Point(3.3979166722887797, -0.06892938496583123 * Y_DIRECTION),
    		    new Point(3.71519995848316, 0.30601366742596836 * Y_DIRECTION),
    		    new Point(3.7150767485176033, 0.505968109339408 * Y_DIRECTION)
    		}, isBackwards, true));
    }
}
