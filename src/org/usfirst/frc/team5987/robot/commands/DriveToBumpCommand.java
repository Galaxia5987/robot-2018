package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToBumpCommand extends Command {

	int choice;
	NetworkTable IRTable = NetworkTableInstance.getDefault().getTable("Image Recogntion");
	NetworkTableEntry ntIRMethod = IRTable.getEntry("Recognition Method");
	NetworkTableEntry ntSeesTarget = IRTable.getEntry("Sees Target");
	
    public DriveToBumpCommand(int choice) {
    	requires(Robot.driveSubsystem);
    	this.choice = choice;
    }

    protected void initialize() {
    	ntIRMethod.setString("White");
    	ntSeesTarget.setBoolean(false);
    	Robot.driveSubsystem.setSetpoints(0.3, 0.3);
	    
    }

    protected void execute() {
		Robot.driveSubsystem.updatePID();
    }

    protected boolean isFinished() {
    	switch (choice) {
    	case 1: 
    		return ntSeesTarget.getBoolean(false);
    	case 2:
    		return Robot.driveSubsystem.seesWhite();
    	case 3:
    		return Robot.driveSubsystem.isBump();
    	case 4:
    		return Robot.navx.getPitch() <= 86;
    	}
    	return false;
    }

    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);
    	Robot.driveSubsystem.setRightSpeed(0);
    }

    protected void interrupted() {
    }
}
