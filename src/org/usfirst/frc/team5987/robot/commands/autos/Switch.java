package org.usfirst.frc.team5987.robot.commands.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.TurnCommand;

/**
 *
 */
public class Switch extends CommandGroup {
	private static final double SWITCH_DISTANCE_ADDITION = -0.1;
    public Switch() {
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
    	
//    	addSequential(new TurnToTargetGroupCommand());
//    	addSequential(new DriveStraightCommand(Robot.ntSwitchDistance, SWITCH_DISTANCE_ADDITION));
//    	addSequential(new DriveStraightCommand(0.1));
    	addSequential(new TurnCommand(15, true));
    	addSequential(new DriveStraightCommand(0.1));
    	addSequential(new TurnCommand(15, true));
    }
}
