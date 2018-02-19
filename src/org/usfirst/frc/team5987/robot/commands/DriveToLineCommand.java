package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToLineCommand extends Command {

	double startingAngle;
	double speed;
	Timer timer = new Timer();
	NetworkTable IRTable = NetworkTableInstance.getDefault().getTable("Image Recogntion");
	NetworkTableEntry ntIRMethod = IRTable.getEntry("Filter Method");
	NetworkTableEntry ntSeesTarget = IRTable.getEntry("Sees Target");
	NetworkTableEntry ntIRError = IRTable.getEntry("IR Error");
	
    public DriveToLineCommand(double speed) {
    	requires(Robot.driveSubsystem);
    	this.speed = speed;
    }

    protected void initialize() {
    	startingAngle = Robot.navx.getAngle();
    	ntIRMethod.setString("1");
    	ntSeesTarget.setBoolean(false);
    	timer.reset();
    	timer.start();
    	Robot.driveSubsystem.setSetpoints(speed, speed);
    }

    protected void execute() {
    	double error = (Robot.navx.getAngle() - startingAngle) / 180.0;
    	ntIRError.setDouble(error);
    	Robot.driveSubsystem.setLeftSpeed(speed - error);
    	Robot.driveSubsystem.setRightSpeed(speed + error);
    }

    protected boolean isFinished() {
    	return ntSeesTarget.getBoolean(false);
    }

    protected void end() {
    	Robot.driveSubsystem.setLeftSpeed(0);
    	Robot.driveSubsystem.setRightSpeed(0);
    }

    protected void interrupted() {
    }
}
