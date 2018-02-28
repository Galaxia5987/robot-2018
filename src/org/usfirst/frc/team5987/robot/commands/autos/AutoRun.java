package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRun extends CommandGroup {

    public AutoRun(boolean isBack) {
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
    	addSequential(new DriveStraightCommand(isBack ? -Constants.AUTO_LINE : Constants.AUTO_LINE));
    }
}
