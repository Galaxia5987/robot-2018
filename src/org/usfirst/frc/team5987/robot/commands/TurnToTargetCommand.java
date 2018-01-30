package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
/**
 *
 */
public class TurnToTargetCommand extends Command {

    private static final double MIN_ERROR_ANGLE = 2;
	private static final double TIMEOUT = 15;
	private NetworkTableEntry ntAngle;
	private double angle;
	private TurnCommand turnCommand;
	private boolean commandFinished;

	public TurnToTargetCommand(NetworkTableEntry angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		ntAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(TIMEOUT);
    	angle = ntAngle.getDouble(0);
    	turnCommand = new TurnCommand(angle, true);
    	turnCommand.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	commandFinished = turnCommand.isCompleted() || isFinished();
    	SmartDashboard.putBoolean("turn finished", commandFinished);
    	if(commandFinished) {
    		turnCommand.cancel();
    		turnCommand = new TurnCommand(angle, true);
        	turnCommand.start();
    	}else {
    		angle = ntAngle.getDouble(0); // check if you're aligned
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(angle) < MIN_ERROR_ANGLE || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	turnCommand.cancel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
