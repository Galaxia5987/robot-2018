package org.usfirst.frc.team5987.robot.subsystems;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * this is an ultimate subsystem (other known ultimates are: "Nerf this!" and "ryu ga waga teki wo kurau")
 * we use this subsystem instead of using the gripper and intake subsystems so that they dont start working individually
 */
public class WheelSubsystem extends Subsystem {
	GripperSubsystem gripperSubsystem;
	IntakeSubsystem intakeSubsystem;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public WheelSubsystem(GripperSubsystem gripperSubsystem, IntakeSubsystem intakeSubsystem ){
		this.gripperSubsystem = gripperSubsystem;
		this.intakeSubsystem = intakeSubsystem;
	}
	
    public void initDefaultCommand() {
    	
    }
    
    public void setSpeedIntake(double speedLeft, double speedRight) {
		intakeSubsystem.setSpeed(speedLeft, speedRight);
	}
    
    public void setSpeedGripper(double speedLeft, double speedRight){
    	gripperSubsystem.setSpeed(speedLeft, speedRight);
    }
    
    public double voltage() {
    	return gripperSubsystem.voltage();
    }
    
    public boolean isCubeInside(){
    	return gripperSubsystem.isCubeInside();
    }
    
    public void setSolenoid(boolean open) {
    	intakeSubsystem.setSolenoid(open);
    }
    
    public boolean getSolenoid() {
    	return intakeSubsystem.getSolenoid();
    }
    
    
}

