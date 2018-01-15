package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.MiniPID;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;



/**
 *  @author mow
 */
public class LiftSubsystem extends Subsystem {
	
	NetworkTable liftTable = NetworkTableInstance.getDefault().getTable("Lift");
	NetworkTableEntry pLift = liftTable.getEntry("liftP");
	NetworkTableEntry iLift = liftTable.getEntry("liftI");
	NetworkTableEntry dLift = liftTable.getEntry("liftD");
	
	MiniPID pid = new MiniPID(pLift.getDouble(0), iLift.getDouble(0), dLift.getDouble( 0));
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
    	pid.setP(pLift.getDouble(0));
    	pid.setI( iLift.getDouble(0));
    	pid.setD(dLift.getDouble( 0));
    	pid.setOutputLimits(-1, 1);
    	setSpeed(pid.getOutput(getHeight()));
    }
}

