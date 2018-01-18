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
	
	private static final double topPID[] = {1, 0, 0};
	private static final double bottomPID[] = {0.5, 0, 0};
	/**
	 * Decreasing rate for the output (substructs this from the setpoint every iteration)
	 */
	private static final double ZERO_RATE = 0.005;
	private static final double LIFT_DISTANCE_PER_PULSE = 0.0005;
	private static final double MAX_ZEROING_OUTPUT = 0.3333334;
	private static final double MAX_RUNNING_OUTPUT = 1;
	private static final boolean TOP_HULL_REVERSED = true;
	private static final boolean BOTTOM_HULL_REVERSED = false;
	private double offset = 0;
	private States state = States.MECHANISM_DISABLED;
	
	public NetworkTable LiftTable = NetworkTableInstance.getDefault().getTable("liftTable");
	NetworkTableEntry ntTopKp = LiftTable.getEntry("Top kP");
	NetworkTableEntry ntTopKi = LiftTable.getEntry("Top kI");
	NetworkTableEntry ntTopKd = LiftTable.getEntry("Top kD");
	NetworkTableEntry ntBottomKp = LiftTable.getEntry("Bottom kP");
	NetworkTableEntry ntBottomKi = LiftTable.getEntry("Bottom kI");
	NetworkTableEntry ntBottomKd = LiftTable.getEntry("Bottom kD");
	NetworkTableEntry ntTopHall = LiftTable.getEntry("Top Hall");
	NetworkTableEntry ntBottomHall = LiftTable.getEntry("Bottom Hall");
	NetworkTableEntry ntState = LiftTable.getEntry("State");
	NetworkTableEntry ntError = LiftTable.getEntry("Error");
	
	NetworkTableEntry ntIsEnabled = LiftTable.getEntry("IS ENABLED");
	NetworkTableEntry ntIsExceeding = LiftTable.getEntry("Trying To Exceed Limits");
	
	NetworkTableEntry ntHeight = LiftTable.getEntry("height");
	MiniPID pid;

	Victor liftMotor = new Victor(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	DigitalInput hallEffectTop = new DigitalInput(RobotMap.liftHallEffectTop);
	DigitalInput hallEffectBottom = new DigitalInput(RobotMap.liftHallEffectBottom);
	double target = 0;

	public LiftSubsystem(){
//		updatePIDConstants(1.0);
		pid = new MiniPID(
				ntTopKp.getDouble(topPID[0]),
				ntTopKi.getDouble(topPID[1]),
				ntTopKd.getDouble(topPID[2])
				);
		// Add the NetworkTable entries if they don't exist
		ntIsEnabled.setBoolean(ntIsEnabled.getBoolean(false));
		ntTopKp.setDouble(ntTopKp.getDouble(topPID[0]));
		ntTopKi.setDouble(ntTopKi.getDouble(topPID[1]));
		ntTopKd.setDouble(ntTopKd.getDouble(topPID[2]));
		ntBottomKp.setDouble(ntBottomKp.getDouble(bottomPID[0]));
		ntBottomKi.setDouble(ntBottomKi.getDouble(bottomPID[1]));
		ntBottomKd.setDouble(ntBottomKd.getDouble(bottomPID[2]));
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
    		setSetpoint(getHeight());
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
	    		ntState.setString("MECHANISM_DISABLED");
	    		if(isEnabled)
	    			state = States.ZEROING;
	    		maxOutput = 0.0;
	    		break;
	    		
	    	case ZEROING:
	    		ntState.setString("ZEROING");
	    		setSetpoint(target - ZERO_RATE);
	    		maxOutput = MAX_ZEROING_OUTPUT;
	    		if(bottomHallEffect){
	    			state = States.RUNNING;
	    			offset = height;
	    			setSetpoint(0);
	    		}
	    		break;
	    		
	    	case RUNNING:
	    		ntState.setString("RUNNING");
	    		maxOutput = MAX_RUNNING_OUTPUT;
	    		if(!isEnabled)
	    			state = States.MECHANISM_DISABLED;
	    		break;
	    		
	    	default:
	    		state = States.MECHANISM_DISABLED;
	    		break;
    	}
    	ntError.setDouble(pid.getSetpoint() - (height - offset));
    	ntTopHall.setBoolean(isUp());
    	ntBottomHall.setBoolean(isDown());
    	pid.setOutputLimits(-maxOutput, maxOutput);
    	double out = pid.getOutput(height - offset);
    	updatePIDConstants(out);
    	ntHeight.setDouble(height - offset);
    	return out;
    }
    
    private void updatePIDConstants(double speed){
    	if(speed > 0){
    		pid.setP(ntTopKp.getDouble(topPID[0]));
        	pid.setI(ntTopKi.getDouble(topPID[1]));
        	pid.setD(ntTopKd.getDouble(topPID[2]));
    	}else{
    		pid.setP(ntBottomKp.getDouble(bottomPID[0]));
        	pid.setI(ntBottomKi.getDouble(bottomPID[1]));
        	pid.setD(ntBottomKd.getDouble(bottomPID[2]));
    	}
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
    	boolean rawVal = hallEffectTop.get();
    	return TOP_HULL_REVERSED ? !rawVal : rawVal;
    }
    
    public boolean isDown() {
    	boolean rawVal = hallEffectBottom.get();
    	return BOTTOM_HULL_REVERSED ? !rawVal : rawVal;
    }
    
    public void resetEncoder(){
    	offset = getAbsoluteEncoderHeight();
    }
    
}

