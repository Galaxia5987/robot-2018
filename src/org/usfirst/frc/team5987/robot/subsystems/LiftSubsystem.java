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
 *  @author mow, paulo
 */
public class LiftSubsystem extends Subsystem {
	double backupPID[] = {1,0.0002,00.00002};

	NetworkTable LiftTable = NetworkTableInstance.getDefault().getTable("liftTable");
	NetworkTableEntry kP = LiftTable.getEntry("kP");
	NetworkTableEntry kI = LiftTable.getEntry("kI");
	NetworkTableEntry kD = LiftTable.getEntry("kD");
	NetworkTableEntry loc = LiftTable.getEntry("height");
	MiniPID pid;

	Victor liftMotor = new Victor(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	DigitalInput hallEffect1 = new DigitalInput(RobotMap.liftHallEffect1Port);
	DigitalInput hallEffect2 = new DigitalInput(RobotMap.liftHallEffect2Port);
	double target = 0;
	public LiftSubsystem(){
		pid = new MiniPID(kP.getDouble(0), kI.getDouble(0), kD.getDouble(0));
		kP.setDouble(kP.getDouble(backupPID[0]));
		kP.setDouble(kP.getDouble(backupPID[1]));
		kP.setDouble(kP.getDouble(backupPID[2]));
	}
    public void initDefaultCommand() {
    }
    
    public void setSpeed(double speed) {
    	liftMotor.set(speed);
    }
    
    public double getHeight(){
    	return liftEncoder.getDistance() * RobotMap.liftEncoderToHeight; 
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
    
    public void setPoint(double height) {
    	target=height;
    	pid.setSetpoint(height);
    }
    
    public void update() {
    	pid.setP(1);
    	pid.setI(kI.getDouble(0));
    	pid.setD(kD.getDouble(0.3));
    	pid.setOutputLimits(-0.6, 0.6);
    	setSpeed(pid.getOutput(getHeight()));
    	
    	loc.setDouble(getHeight()-target);
    }
}

