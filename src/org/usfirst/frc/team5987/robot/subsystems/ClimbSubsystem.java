package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.SafeSPXVictor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climbing subsystem of the robot.
 * 
 * @author Dan Katzuv
 */
public class ClimbSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public static final double openPosition = -1;
	public static final double closePosition = 1;
	// Make it true if the limit switch is normally on
	public final boolean limitSwitchReverse = true;
	public final boolean motorReversed = false;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Right servo of the right hook on the climb subsystem.
	 */
	Servo rightServo = new Servo(RobotMap.climbRightServo);

	/**
	 * Left servo of the left hook on the climb subsystem.
	 */
	Servo leftServo = new Servo(RobotMap.climbLeftServo);

	/**
	 * Motor for climbing.
	 */
	SafeSPXVictor motor = new SafeSPXVictor(RobotMap.climbMotor);

	/**
	 * Limit switch at the top that is used for affirmation if the robot has
	 * reached the top.
	 */
	DigitalInput limitSwitch = new DigitalInput(RobotMap.climbLimitSwitch);

	public ClimbSubsystem() {
		motor.setInverted(motorReversed);
	}

	/**
	 * Set the servo position.
	 *
	 * <p>
	 * Servo values range from 0.0 to 1.0 corresponding to the range of full
	 * left to full right.
	 *
	 * @param value
	 *            Position from 0.0 to 1.0.
	 */
	public void setHooks(double position) {
		rightServo.set(position);
		leftServo.set(position);
	}

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
	
	public double getClimbSpeed() {
		return motor.getSpeed();
	}
	/**
	 * Get the value from the limit switch to know whether the robot has reached
	 * the top.
	 *
	 * @return The status of the limit switch
	 */
	public boolean hasReachedTop() {
		boolean rawVal = limitSwitch.get();
		return limitSwitchReverse ? !rawVal : rawVal;
	}
}
