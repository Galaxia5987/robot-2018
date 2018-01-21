package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Will lower the intake and elevator, then drives forward and pulls in  the block
 * @author Paulo Khayat
 */
public class CommandGroup_TakeCube extends CommandGroup {

    public CommandGroup_TakeCube() {
    	
    	addParallel(new IntakeSelenoidCommand(true)); //Lowers the intake
    	addSequential(new LiftCommand(LiftCommand.liftStates.BOTTOM)); //lowers the lift
    	addParallel(new IntakeTakeCubeCommand()); //turns the intake
    	addParallel(new GripperTakeCubeCommand()); //turns the gripper
    	addParallel(new DriveStraightCommand(0.5)); //TODO: change to camera OR a simple drive
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
