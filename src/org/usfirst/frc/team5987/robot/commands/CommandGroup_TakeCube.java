package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Will lower the intake and elevator, then drives forward and pulls in the block
 * @author Paulo Khayat
 */
public class CommandGroup_TakeCube extends CommandGroup {

    public CommandGroup_TakeCube() {

    	addParallel(new IntakeSelenoidCommand(true)); //Lowers the intake TODO: turn to parallel
    	addSequential(new LiftCommand(LiftCommand.liftStates.BOTTOM)); //lowers the lift
    	addParallel(new GripperTakeCubeCommand()); //turns the intake
    	addSequential(new IntakeTakeCubeCommand()); //turns the gripper
    }
}
