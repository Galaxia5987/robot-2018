package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GripperShootCommand extends Command {
	
	double speed;
	
    public GripperShootCommand(double speed) {
    	this.speed = speed;
    	requires(Robot.gripperSubsystem);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.gripperSubsystem.setSpeed(speed, speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.gripperSubsystem.setSpeed(0, 0);
    }

    protected void interrupted() {
    	Robot.gripperSubsystem.setSpeed(0, 0);
    }
}
