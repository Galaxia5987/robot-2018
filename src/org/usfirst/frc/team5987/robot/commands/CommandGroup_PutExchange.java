package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Paulo Khayat
 */
public class CommandGroup_PutExchange extends CommandGroup {

    public CommandGroup_PutExchange() {
    	addSequential(new LiftCommand(Constants.LiftCommandStates.BOTTOM));
    	addParallel(new ShootCubeCommand(0.5, false)); //TODO: change the speed
    	addParallel(new IntakeShootCubeCommand(1.5)); //TODO: optimize the time done
    }
}
