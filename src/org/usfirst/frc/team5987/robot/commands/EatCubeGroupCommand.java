package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class EatCubeGroupCommand extends CommandGroup {
	
	public EatCubeGroupCommand(Point cube) {
    	addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.CUBE));
    	addParallel(new TakeCommand(0.75,4));
    	addSequential(new EatCubeCommand(cube, 2));
    	addSequential(new IntakeSpringSolenoidCommand(true));
    	addSequential(new TurnCommand(15, true,0.5));
    	addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.STREAM));
	}
    public EatCubeGroupCommand() {
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
    	addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.CUBE));
    	addSequential(new WaitToTargetCommand(Robot.ntVisionTarget, 0.02));
    	addParallel(new TakeCommand(0,4));
    	addSequential(new EatCubeCommand(3));
    	addSequential(new IntakeSpringSolenoidCommand(true));
    	addSequential(new TurnCommand(15, true,0.5));
    	addSequential(new ChangeFilterModeCommand(ChangeFilterModeCommand.Modes.STREAM));
    }
}
