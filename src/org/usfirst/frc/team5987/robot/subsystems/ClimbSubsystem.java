package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;
import auxiliary.SafeVictorSPX;
import auxiliary.Watch_Dogeable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climbing subsystem of the robot.
 * 
 * @author Dan Katzuv
 */
public class ClimbSubsystem extends Subsystem implements Watch_Dogeable {

	public final boolean motor1Reversed = false;
	public final boolean motor2Reversed = false;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// Motors for climbing
	SafeVictorSPX motor1 = new SafeVictorSPX(RobotMap.climbMotor1);
	SafeVictorSPX motor2 = new SafeVictorSPX(RobotMap.climbMotor2);

	Timer downTimer = new Timer();
	
	public ClimbSubsystem() {
		motor1.setInverted(motor1Reversed);
		motor2.setInverted(motor2Reversed);
	}

	/**
	 * Set the climbing motors' speed.
	 * 
	 * @param speed
	 *            - speed of the motors
	 */
	public void set(double speed) {
		speed = (speed > 0) ? 0 : speed; // If the speed given to the motors is negative, the 
										 // speed is set to zero (0). This is done because
										 // the robot cannot descend as result of the ratchet mechanism.
		motor1.set(speed);
		motor2.set(speed);
	}
	
	@Override
	public void bork()
	{
		motor1.disable();
		motor2.disable();
		downTimer.reset();
		downTimer.start();
	}
	
	@Override
	public void necromancy() {
		motor1.enable();
		motor2.enable();
	}
	
	@Override
	public boolean wakeMeUp() {
		if (downTimer.get() >= 10) {
			downTimer.stop();
			downTimer.reset();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean ded() {
		return motor1.status() && motor2.status();
	}
}