package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeFilterModeCommand extends Command {
	public enum Modes {
		SWITCH, CUBE, STREAM
	}
	private Modes mode;
	private NetworkTableEntry ntFilterMode = Robot.visionTable.getEntry("Filter Mode");
	/**
	 * Change filter mode for the Raspberry Pi
	 * @param newMode
	 */
    public ChangeFilterModeCommand(Modes newMode) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	mode = newMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	switch(mode){
    	case SWITCH:
    		ntFilterMode.setString("0");
    		break;
    	case CUBE:
    		ntFilterMode.setString("2");
    		break;
		default:
    	case STREAM:
    		ntFilterMode.setString("1");
    		break;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
