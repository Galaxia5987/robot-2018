package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_ScaleFull extends CommandGroup {

	public CommandGroup_ScaleFull(String gameData, char robotStartingLocation) { // Add
																					// Commands
																					// here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.
		int angleMultiplier;
		if (robotStartingLocation == 'L' || robotStartingLocation == 'l')
			angleMultiplier = 1;
		else {
			angleMultiplier = -1;
		}

		addSequential(new DriveStraightCommand(7.126224)); // drive until the
														// platformzone line
		if(gameData.charAt(1) == robotStartingLocation){ //if the scale is on the same side as the robot
    		addParallel(new LiftCommand(LiftCommand.liftStates.SCALE_TOP)); //lift the arm while it drives to the bump TODO: maybe after the turn
    		
    		//addSequential(new DriveUntilTrigger());//drives until the bump is hit
    		addSequential(new DriveStraightCommand(1));
    		
    		addSequential(new TurnCommand(90 * angleMultiplier,true)); //turn towards the switch
    		addSequential(new ShootCubeCommand(1, true)); //release the box
    		addSequential(new TurnCommand(90 * angleMultiplier,true)); //turn again
    		addParallel(new LiftCommand(LiftCommand.liftStates.BOTTOM)); //lower the arm while going back to the switch
    		addSequential(new DriveStraightCommand(0.889)); //drive back towards the first box
    		addSequential(new TurnCommand(-45 * angleMultiplier , true)); //turn towards it
    	} else {
    		addSequential(new TurnCommand(90  * angleMultiplier, true)); //turn to the inwards of the platform

			addSequential(new DriveStraightCommand(5.495925)); // NOT EXACT
    		addSequential(new TurnCommand(-90 * angleMultiplier,true)); //turn again
    		addParallel(new LiftCommand(LiftCommand.liftStates.SCALE_TOP)); //lift the arm while it drives to the bump TODO: maybe after the turn
										// bump
			//addSequential(new DriveUntilTrigger());
			addSequential(new DriveStraightCommand(1));
    		addSequential(new TurnCommand(-90 * angleMultiplier,true)); //turn again
			addSequential(new DriveStraightCommand(0.7)); // NOT EXACT
    		addSequential(new ShootCubeCommand(1, true)); //release the box
			addSequential(new DriveStraightCommand(-0.35)); // NOT EXACT
    		addSequential(new TurnCommand(-90 * angleMultiplier,true)); //turn again
    		addParallel(new LiftCommand(LiftCommand.liftStates.BOTTOM)); //lower the arm while going back to the switch
										// switch
			addSequential(new DriveStraightCommand(0.88)); // NOT EXACT
														// first box
    		addSequential(new TurnCommand(45 * angleMultiplier,true)); //turn again
		}

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
