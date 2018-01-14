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
	
	public void setSpeed(double speedLeft, double speedRight) {
		// Basic function to set motor speed
		intakeVictorLeft.set(speedLeft);
		intakeVictorRight.set(speedRight);
	}
	
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

