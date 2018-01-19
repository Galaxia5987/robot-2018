package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *@author ACoolName
 *@version 1.0
 */
public class intakeSubsystem extends Subsystem {

    //Defining motors and piston
	private Victor intakeVictorLeft=new Victor(RobotMap.intakeMotorLeft);
	private Victor intakeVictorRight=new Victor(RobotMap.intakeMotorRight);
	private DoubleSolenoid solenoid=new DoubleSolenoid(RobotMap.intakeSolenoid1,RobotMap.intakeSolenoid2);
	
	/**
	 * Set the speed for the intake wheels
	 * @param speedLeft - speed between -1 and 1
	 * @param speedRight - speed between -1 and 1
	 */
	public void setSpeed(double speedLeft, double speedRight) {
		// Basic function to set motor speed
		intakeVictorLeft.set(speedLeft);
		intakeVictorRight.set(speedRight);
	}
	
	/**
	 * Open the intake mechanism (expends beyond the frame perimeter) <br>
	 * @param open - true if you want to open the mechanism, false to close
	 */
	public void setSolenoid(boolean open) {
		//open decides weather to open the piston or close it
		if(open) {
			solenoid.set(DoubleSolenoid.Value.kForward);
		}else {
			solenoid.set(DoubleSolenoid.Value.kReverse);
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

