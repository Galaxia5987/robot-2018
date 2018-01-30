package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
/**
 *
 */
public class TurnToTargetCommand extends Command {

    private static final double MIN_ERROR_ANGLE = 1;
	private static final double TIMEOUT = 5;
	private NetworkTableEntry ntAngle;
	private double angle;

	public TurnToTargetCommand(NetworkTableEntry angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		ntAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(TIMEOUT);
    	angle = ntAngle.getDouble(0);
    	while(!isFinished()){
        	Command turnCommand = new TurnCommand(angle, true);
        	turnCommand.start();
        	while(turnCommand.isRunning() || isTimedOut()); // wait for turn command to finish
        	turnCommand.cancel();
        	angle = ntAngle.getDouble(0); // check if you're aligned
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(angle) < MIN_ERROR_ANGLE || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
