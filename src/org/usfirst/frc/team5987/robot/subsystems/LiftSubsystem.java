package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 *  @author mow (awesome docs by Dor Brekhman)
 */
public class LiftSubsystem extends Subsystem {
	
	static private double kP = 0;
	static private double kI = 0;
	static private double kD = 0;
	
	NetworkTable liftTable = NetworkTableInstance.getDefault().getTable("Lift");
	NetworkTableEntry ntKp = liftTable.getEntry("kP");
	NetworkTableEntry ntKi = liftTable.getEntry("kI");
	NetworkTableEntry ntKd = liftTable.getEntry("kD");
	MiniPID pid = new MiniPID(
			ntKp.getDouble(kP),
			ntKi.getDouble(kI),
			ntKd.getDouble(kD)
			);
	Victor liftMotor = new Victor(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	// digital hall effect sensor that returns 1 when the lift is at the top 
	DigitalInput hallEffectUpper = new DigitalInput(RobotMap.liftHallEffectUpper);
	// digital hall effect sensor that returns 1 when the lift is at the bottom
	DigitalInput hallEffectBottom = new DigitalInput(RobotMap.liftHallEffectBottom);
	
    public void initDefaultCommand() {
    }
    
    /**
     * Set the speed for the lift motor (that makes it go up or down)
     * @param speed - speed between -1 and 1
     */
    public void setSpeed(double speed) {
    	liftMotor.set(speed);
    }
    
    /**
     * Get the position of the lift from it's bottom
     * @return distance in METER
     */
    public double getPosition() {
    	return liftEncoder.getDistance();
    }
    
    /**
     * Get the speed the lift moves
     * @return speed in METER/SEC
     */
    public double getSpeed() {
    	return liftEncoder.getRate();
    }
    
    /**
     * @return true if the elevator is at its top (and can't go further)
     */
    public boolean isUp() {
    	return hallEffectUpper.get();
    }
    
    /**
     * @return true if the elevator is at its bottom (and can't go further)
     */
    public boolean isDown() {
    	return hallEffectBottom.get();
    }
    
    /**
     * Changes the desired height for the PID control. <br>
     * <i>Note:  In order for the PID control to work you have to <br>
     * call <code>updatePID()</code> periodically</i>
     * @param height - desired height in METER from the bottom of the lift
     */
    public void setSetpoint(double height) {
    	pid.setSetpoint(height);
    }
    
    /**
     * The PID control - moves the motor and makes the lift reach the <br>
     * desired height defined by <code>setSetpoint()</code> <br>
     * <b>You must call this periodically!</b>
     */
    public void updatePID() {
    	pid.setP(ntKp.getDouble(kP));
    	pid.setI(ntKi.getDouble(kI));
    	pid.setD(ntKd.getDouble(kD));
    	pid.setOutputLimits(-1, 1);
    	setSpeed(pid.getOutput(getPosition()));
    }
}

