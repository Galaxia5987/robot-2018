package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Will lower the intake and elevator, then drives forward and pulls in the block
 * @author Paulo Khayat
 */
public class CommandGroup_TakeCube extends CommandGroup {

    public CommandGroup_TakeCube() {
    	
    	addSequential(new IntakeSelenoidCommand(true)); //Lowers the intake TODO: turn to parallel
    	//addSequential(new LiftCommand(LiftCommand.liftStates.BOTTOM)); //lowers the lift
    	addParallel(new IntakeTakeCubeCommand()); //turns the intake
    	addSequential(new GripperTakeCubeCommand()); //turns the gripper
    	// addParallel(new DriveStraightCommand(0.5)); //TODO: change to camera OR a simple drive
    	
    }
}