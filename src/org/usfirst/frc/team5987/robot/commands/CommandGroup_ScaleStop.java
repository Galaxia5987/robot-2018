package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_ScaleStop extends CommandGroup {
    
    public  CommandGroup_ScaleStop(String gameData, char robotStartingLocation) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	int angleMultiplier;
    	if(robotStartingLocation=='L' || robotStartingLocation=='l')
    		angleMultiplier = 1;
    	else{
    		angleMultiplier = -1;
    	}
    	/*
    	addParallel(new LiftCommand(LiftCommand.liftStates.SWITCH));
    	addSequential(new  DriveStraightCommand(3.755)); //TODO: tweak numbers
    	addSequential(new TurnCommand(90 * angleMultiplier,true));
    	addSequential(new  DriveStraightCommand(1)); //TODO: tweak numbers
    	addSequential(new ShootCubeCommand(1, true)); */
    	
    	
    	addSequential(new  DriveStraightCommand(7.126224)); //drive until the platformzone line
    	if(gameData.charAt(1) == robotStartingLocation){ //if the scale is on the same side as the robot
    		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP)); //lift the arm while it drives to the bump TODO: maybe after the turn
    		
    		//addSequential(new DriveUntilTrigger());//drives until the bump is hit
    		addSequential(new DriveStraightCommand(1));
    		
    		addSequential(new TurnCommand(90 * angleMultiplier,true)); //turn towards the switch
    		addSequential(new ShootCubeCommand(1, true)); //release the box
    		addSequential(new TurnCommand(90 * angleMultiplier,true)); //turn again
    		addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM)); //lower the arm while going back to the switch
    		addSequential(new DriveStraightCommand(0.889)); //drive back towards the first box
    		addSequential(new TurnCommand(-45 * angleMultiplier , true)); //turn towards it
    	}
    	else{
    		addSequential(new TurnCommand(90  * angleMultiplier, true)); //turn to the inwards of the platform
    	}
    	
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
