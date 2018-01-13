package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_ScaleFull extends CommandGroup {
    
    public  CommandGroup_ScaleFull(String gameData, char robotStartingLocation) {    	        // Add Commands here:
    	        // e.g. addSequential(new Command1());
    	        //      addSequential(new Command2());
    	        // these will run in order.
    	    	int angleMultiplier;
    	    	if(robotStartingLocation=='L' || robotStartingLocation=='l')
    	    		angleMultiplier = 1;
    	    	else{
    	    		angleMultiplier = -1;
    	    	}
    	    	
    	    	
    	    	addSequential(new DriveDistanceCM(712.6224)); //drive until the platformzone line
    	    	if(gameData.charAt(1) == robotStartingLocation){ //if the scale is on the same side as the robot
    	    		addParallel(new Lift(2)); //lift the arm while it drives to the bump
    	    		addSequential(new DriveUntilTrigger());//drives until the bump is hit
    	    		
    	    		addSequential(new TurnAngle(90 * angleMultiplier)); //turn towards the switch
    	    		//driveabit
    	    		addSequential(new DropBox()); //release the box
    	    		addSequential(new TurnAngle(90 * angleMultiplier)); //turn again
    	    		addParallel(new Lift(0)); //lower the arm while going back to the switch
    	    		addSequential(new DriveDistanceCM(88.9)); //drive back towards the first box
    	    		addSequential(new TurnAngle(-45 * angleMultiplier)); //turn towards it
    	    	}
    	    	else{
    	    		addSequential(new TurnAngle(90  * angleMultiplier)); //turn to the inwards of the platform
    	    		addSequential(new DriveDistanceCM(549.5925)); // NOT EXACT
    	    		addSequential(new TurnAngle(-90 * angleMultiplier));
    	    		addParallel(new Lift(2)); //lift the arm while it drives to the bump
    	    		addSequential(new DriveUntilTrigger());
    	    		addSequential(new TurnAngle(-90 * angleMultiplier));
    	    		addSequential(new DriveDistanceCM(70));//NOT EXACT
    	    		addSequential(new DropBox()); //release the box
    	    		addSequential(new DriveDistanceCM(-35));//NOT EXACT
    	    		addSequential(new TurnAngle(-90 * angleMultiplier)); //turn again
    	    		addParallel(new Lift(0)); //lower the arm while going back to the switch
    	    		addSequential(new DriveDistanceCM(88.9)); //drive back towards the first box
    	    		addSequential(new TurnAngle(45 * angleMultiplier));
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
}
