package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnToTargetGroupCommand extends CommandGroup {
	private final double MAX_ROTATIONS = 10;
    public TurnToTargetGroupCommand(NetworkTableEntry ntAngle) {
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
    	for(int i=0; i < MAX_ROTATIONS; i++)
    		addSequential(new TurnCommand(ntAngle, true));
    }
}
