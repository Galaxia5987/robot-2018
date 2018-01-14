package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 *  @author mow
 */
public class LiftSubsystem extends Subsystem {
	
	NetworkTable LiftTable = NetworkTable.getTable("liftTable");
	Victor liftMotor = new Victor(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	DigitalInput hallEffect1 = new DigitalInput(RobotMap.liftHallEffect1Port);
	DigitalInput hallEffect2 = new DigitalInput(RobotMap.liftHallEffect2Port);
	double wantedHeight;

    public void initDefaultCommand() {
    }
    
    public void setSpeed(double speed) {
    	liftMotor.set(speed);
    }
    
    public double getPosition() {
    	return liftEncoder.getDistance();
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
    	wantedHeight = height;
    }
    
    public void Update() {
    	MiniPID pid = new MiniPID(LiftTable.getNumber("liftP", 0), LiftTable.getNumber("liftI", 0), LiftTable.getNumber("liftD", 0));
    	pid.setOutputLimits(0, 1);
    	pid.setSetpoint(wantedHeight);
    	setSpeed(pid.getOutput());
    }
}

