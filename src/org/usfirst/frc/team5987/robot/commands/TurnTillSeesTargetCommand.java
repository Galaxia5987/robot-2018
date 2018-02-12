package org.usfirst.frc.team5987.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;


/**
 * Same as TurnCommand but turns until sees target (Network Table Entry returns true)
 */
public class TurnTillSeesTargetCommand extends TurnCommand {

    private NetworkTableEntry ntSeesTarget;

	public TurnTillSeesTargetCommand(double angle, boolean isRelative, NetworkTableEntry ntSeesTarget) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(angle, isRelative);
    	this.ntSeesTarget = ntSeesTarget;
    }
	
	public TurnTillSeesTargetCommand(NetworkTableEntry ntAngle, boolean isRelative, NetworkTableEntry ntSeesTarget) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(ntAngle, isRelative);
    	this.ntSeesTarget = ntSeesTarget;
    }

	public TurnTillSeesTargetCommand(NetworkTableEntry ntAngle, NetworkTableEntry ntIsRelative, NetworkTableEntry ntSeesTarget) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super(ntAngle, ntIsRelative);
    	this.ntSeesTarget = ntSeesTarget;
    }
	

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return super.isFinished() || ntSeesTarget.getBoolean(false);
    }

}
