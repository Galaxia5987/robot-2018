package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;



/**
 *  @author mow, paulo, Dor
 */
public class LiftSubsystem extends Subsystem {
	enum States {
		MECHANISM_DISABLED,
		ZEROING,
		RUNNING
	}
	
	double backupPID[] = {1, 0.0002, 00.00002};
	/**
	 * Decreasing rate for the output (substructs this from the setpoint every iteration)
	 */
	private static final double ZERO_RATE = 0.005;
	private static final double LIFT_DISTANCE_PER_PULSE = 0.0005;
	private static final double MAX_ZEROING_OUTPUT = 0.3333334;
	private double offset = 0;
	
	
	private States state = States.MECHANISM_DISABLED;
	NetworkTable LiftTable = NetworkTableInstance.getDefault().getTable("liftTable");
	NetworkTableEntry ntKp = LiftTable.getEntry("kP");
	NetworkTableEntry ntKi = LiftTable.getEntry("kI");
	NetworkTableEntry ntKd = LiftTable.getEntry("kD");
	NetworkTableEntry ntIsEnabled = LiftTable.getEntry("IS ENABLED");
	NetworkTableEntry ntIsExceeding = LiftTable.getEntry("Trying To Exceed Limits");
	
	NetworkTableEntry ntHeight = LiftTable.getEntry("height");
	MiniPID pid;

	Victor liftMotor = new Victor(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	DigitalInput hallEffect1 = new DigitalInput(RobotMap.liftHallEffect1Port);
	DigitalInput hallEffect2 = new DigitalInput(RobotMap.liftHallEffect2Port);
	double target = 0;

	public LiftSubsystem(){
		ntKp.setDouble(ntKp.getDouble(backupPID[0]));
		ntKp.setDouble(ntKp.getDouble(backupPID[1]));
		ntKp.setDouble(ntKp.getDouble(backupPID[2]));
		pid = new MiniPID(
				ntKp.getDouble(backupPID[0]),
				ntKi.getDouble(backupPID[1]),
				ntKd.getDouble(backupPID[2])
				);
		ntIsEnabled.setBoolean(ntIsEnabled.getBoolean(false));
		liftEncoder.setDistancePerPulse(LIFT_DISTANCE_PER_PULSE);
	}
	
    public void initDefaultCommand() {
    }
    
    public void setSpeed(double speed) {
    	boolean notExceedingBottom = speed < 0 && !isDown();
    	boolean notExceedingTop = speed > 0 && !isUp();
    	if(notExceedingBottom || notExceedingTop || speed == 0){
    		liftMotor.set(speed);
    		ntIsExceeding.setBoolean(false);
    	}else{
    		ntIsExceeding.setBoolean(true);
    	}
    }
    
    public void setSetpoint(double height) {
    	target = height;
    	pid.setSetpoint(height);
    }
    
    public void updateMotors(){
    	setSpeed(update());
    }
    
    public double update(){
    	return _update(getAbsoluteEncoderHeight(), isDown(), ntIsEnabled.getBoolean(false));
    }
    
    public double _update(double height, boolean bottomHallEffect, boolean isEnabled) {
    	double maxOutput = 0;
    	switch(state){
	    	case MECHANISM_DISABLED:
	    		if(isEnabled)
	    			state = States.RUNNING;
	    		maxOutput = 0.0;
	    		break;
	    	case ZEROING:
	    		setSetpoint(target - ZERO_RATE);
	    		maxOutput = MAX_ZEROING_OUTPUT;
	    		if(bottomHallEffect){
	    			state = States.RUNNING;
	    			offset = height;
	    		}
	    		break;
	    	case RUNNING:
	    		maxOutput = 1.0;
	    		break;
	    	default:
	    		state = States.MECHANISM_DISABLED;
	    		break;
    	}
    	pid.setP(ntKp.getDouble(backupPID[0]));
    	pid.setI(ntKi.getDouble(backupPID[1]));
    	pid.setD(ntKd.getDouble(backupPID[2]));
    	pid.setOutputLimits(-maxOutput, maxOutput);
    	ntHeight.setDouble(height - offset);
    	return pid.getOutput(height - offset);
    }
    
    public double getAbsoluteEncoderHeight(){
    	return liftEncoder.getDistance(); 
    }
     public double getHeight(){
    	 return getAbsoluteEncoderHeight() - offset;
     }
    public double getSpeed() {
    	return liftEncoder.getRate();
    }
    
    public boolean isUp() {
    	return hallEffect1.get();
    }
    
    public boolean isDown() {
    	return hallEffect2.get();
    }
    
    public void resetEncoder(){
    	offset = getAbsoluteEncoderHeight();
    }
    
}

