package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem.States;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestPneumaticCommand extends Command {
	Timer timer = new Timer();
	boolean st = false;
	int count = 0;
	private static NetworkTable driveTable = Robot.driveSubsystem.driveTable;

	NetworkTableEntry revs = driveTable.getEntry("Counts");

	public TestPneumaticCommand() {

		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (timer.get() > 0.8) {
			Robot.intakeSubsystem.setSolenoid(st);
			count++;
			revs.setNumber(count);
			st = !st;
			timer.reset();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !(Robot.liftSubsystem.state == States.MECHANISM_DISABLED);

	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
