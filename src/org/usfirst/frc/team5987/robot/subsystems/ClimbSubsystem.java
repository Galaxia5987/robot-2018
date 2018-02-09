package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;
import auxiliary.SafeVictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climbing subsystem of the robot.
 * 
 * @author Dan Katzuv
 */
public class ClimbSubsystem extends Subsystem {

	public final boolean motor1Reversed = false;
	public final boolean motor2Reversed = false;
	public final boolean motor3Reversed = false;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	// Motors for climbing
	SafeVictorSPX motor1 = new SafeVictorSPX(RobotMap.climbMotor1);
	SafeVictorSPX motor2 = new SafeVictorSPX(RobotMap.climbMotor2);
	SafeVictorSPX motor3 = new SafeVictorSPX(RobotMap.climbMotor3);

	
	public ClimbSubsystem() {
		motor1.setInverted(motor1Reversed);
		motor2.setInverted(motor2Reversed);
		motor3.setInverted(motor3Reversed);
	}

	/**
	 * Set the climbing motors' speed.
	 * 
	 * @param speed
	 *            - speed of the motors
	 */
	public void set(double speed) {
		speed = (speed < 0) ? 0 : speed; // If the speed given to the motors is negative, the 
										 // speed is set to zero (0). This is done because
										 // the robot cannot descend as result of the ratchet mechanism.
		motor1.set(speed);
		motor2.set(speed);
		motor3.set(speed);
	}
	
	}
	
	}
	}
}