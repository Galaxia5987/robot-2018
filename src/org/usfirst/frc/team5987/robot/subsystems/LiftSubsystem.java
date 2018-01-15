package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 *  @author mow
 */
public class LiftSubsystem extends Subsystem {
	
	NetworkTable LiftTable = NetworkTable.getTable("liftTable");
	MiniPID pid = new MiniPID(LiftTable.getNumber("liftP", 0), LiftTable.getNumber("liftI", 0), LiftTable.getNumber("liftD", 0));
	Spark liftMotor = new Spark(RobotMap.liftMotorPort);
	Encoder liftEncoder = new Encoder(RobotMap.liftEncoderPortA, RobotMap.liftEncoderPortB);
	DigitalInput hallEffect1 = new DigitalInput(RobotMap.liftHallEffect1Port);
	DigitalInput hallEffect2 = new DigitalInput(RobotMap.liftHallEffect2Port);
	double wantedHeight;

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
    	pid.setSetpoint(height);
    }
    
    public void update() {
    	pid.setP(LiftTable.getNumber("liftP", 0));
    	pid.setI(LiftTable.getNumber("liftI", 0));
    	pid.setD(LiftTable.getNumber("liftD", 0));
    	pid.setOutputLimits(-1, 1);
    	setSpeed(pid.getOutput(getHeight()));
    }
}

