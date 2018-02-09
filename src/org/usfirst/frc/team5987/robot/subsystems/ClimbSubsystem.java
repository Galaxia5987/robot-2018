package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climbing subsystem of the robot.
 * 
 * @author Dan Katzuv
 */
public class ClimbSubsystem extends Subsystem {

	public final boolean motorReversed = false;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}


	
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
	
		}
	}
}