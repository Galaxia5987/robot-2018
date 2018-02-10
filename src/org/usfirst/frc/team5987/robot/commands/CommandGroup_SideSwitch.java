package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_SideSwitch extends CommandGroup {
    
    public  CommandGroup_SideSwitch(String gameData, char robotStartingLocation) {
        // Add Commands here:
    	int angleMultiplier;
    	if(robotStartingLocation=='L' || robotStartingLocation=='l')
    		angleMultiplier = 1;
    	else{
    		angleMultiplier = -1;
    	}
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());

    	addParallel(new LiftCommand(LiftCommand.liftStates.SWITCH));
    	addSequential(new  DriveStraightCommand(3.755)); //TODO: tweak numbers
    	addSequential(new TurnCommand(90 * angleMultiplier,true));
    	addSequential(new  DriveStraightCommand(1)); //TODO: tweak numbers
    	addSequential(new ShootCubeCommand(1, true));
    	
    	
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
    }
}
