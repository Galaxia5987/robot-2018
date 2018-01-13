package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CommandGroup_CenterSwitch extends CommandGroup {
    
    public  CommandGroup_CenterSwitch(String gameData) {    	        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	double angleMultiplier = gameData.charAt(0)=='L' ? 1.2 : -1;
    	if(gameData.charAt(0)=='L' || gameData.charAt(0)=='l')
    		angleMultiplier = 1.3;
    	else{
    		angleMultiplier = -1;
    	}
    	addSequential(new DriveDistanceCM(177.8));
    	addSequential(new TurnAngle(-45*angleMultiplier));
    	addSequential(new DriveDistanceCM(150)); //NOT EXACT
    	addSequential(new TurnAngle(45*angleMultiplier));
    	//DRIVE TO WALL BY CAMERA
    	
    	
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
    }
}
