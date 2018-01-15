package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climbing subsystem of the robot.
 * @author Dan Katzuv
 */
public class ClimbSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	
	/**
	 * Motor for climbing.
	 */
	Victor motor = new Victor(RobotMap.motor);
	
	/**
	 * Limit switch at the top that is used for affirmation if the robot has
	 * reached the top.
	 */
	DigitalInput limitSwitch = new DigitalInput(RobotMap.limitSwitch);

	
	/**
	 * Set the PWM value.
	 *
	 * <p>
	 * The PWM value is set using a range of -1.0 to 1.0, appropriately scaling
	 * the value for the FPGA.
	 *
	 * @param speed
	 *            The speed value between -1.0 and 1.0 to set.
	 */
	public void setClimbSpeed(double speed) {
		motor.set(speed);
	}
	
	/**
	   * Get the value from the limit switch to know whether the robot has reached the top.
	   *
	   * @return the status of the limit switch
	   */
	public boolean hasReachedTop()
	{
		return limitSwitch.get();
	}
}
