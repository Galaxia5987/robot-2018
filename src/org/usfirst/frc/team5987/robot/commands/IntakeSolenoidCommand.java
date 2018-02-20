package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.subsystems.LiftSubsystem.States;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeSolenoidCommand extends Command {
	public enum opMode {
		TOGGLE,
		OPEN,
		CLOSE
	}
	private opMode chosen;
	private Timer timer = new Timer();
	private double delay = 0;
	
	/**
	 * A command that controls the solenoid moving the intake up.
	 * If the constructor is left blank the intake will just toggle.
	 * 
	 * @author Paulo Khayat
	 */
	public IntakeSolenoidCommand(double delay) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		chosen = opMode.TOGGLE;
		this.delay = delay;
		requires(Robot.intakeSubsystem);
	}
	
	public IntakeSolenoidCommand(boolean state, double delay) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this(state);
		this.delay = delay;
	}
	
	/**
	 * A command that controls the solenoid moving the intake up.
	 * If the constructor is left blank the intake will just toggle.
	 * 
	 * @author Paulo Khayat
	 */
	public IntakeSolenoidCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		chosen = opMode.TOGGLE;
		requires(Robot.intakeSubsystem);
	}
	
	/**
	 * A command that controls the solenoid moving the intake up.
	 * If the constructor is left blank the intake will just toggle.
	 * 
	 * @param toDown - Whether the intake should go down.
	 * @author Paulo Khayat
	 */
	public IntakeSolenoidCommand(boolean state){
		if(state)
			chosen = opMode.OPEN;
		else
			chosen = opMode.CLOSE;
	}
	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(isFinished()){
			switch(chosen){
			case TOGGLE:
				Robot.intakeSubsystem.setSolenoid(!Robot.intakeSubsystem.getSolenoid());
				break;
			case OPEN:
				Robot.intakeSubsystem.setSolenoid(true);
				break;
			case CLOSE:
					Robot.intakeSubsystem.setSolenoid(false);
				break;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() >= delay;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
