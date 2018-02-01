package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.Robot;
import org.usfirst.frc.team5987.robot.RobotMap;

import auxiliary.SafeVictor;
import auxiliary.Watch_Dogeable;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * a gripper subsystem (duh) Can define the speed of the two wheels Can get a
 * boolean if the power cube is in.
 * 
 * @author Paulo Khayat
 */
public class GripperSubsystem extends Subsystem implements Watch_Dogeable {

	SafeVictor leftWheel = new SafeVictor(RobotMap.gripperWheelLeft);
	SafeVictor rightWheel = new SafeVictor(RobotMap.gripperWheelRight);
	AnalogInput proximitySensor = new AnalogInput(RobotMap.proximityChannel);
	NetworkTable GripperTable = NetworkTableInstance.getDefault().getTable("GripperTable");
	public NetworkTableEntry ntProximityVoltage = GripperTable.getEntry("Proximity Voltage");
	public NetworkTableEntry ntSeesCube = GripperTable.getEntry("Sees Cube");
	Timer downTimer = new Timer();

	public void initDefaultCommand() {

	}

	/**
	 * 
	 * @param speedL - speed of the left wheels on the gripper.
	 * @param speedR - speed of the right wheels on the gripper.
	 */
	public void setSpeed(double speedL, double speedR) {
		leftWheel.set(speedL);
		rightWheel.set(speedR);
	}
	
	/**
	 * 
	 * @return the hall affect voltage.
	 */
	public double voltage() {
		return proximitySensor.getVoltage();
	}

	/**
	 * 
	 * @return whether there is a cube in the gripper
	 */
	public boolean isCubeInside() {
		ntProximityVoltage.setDouble(Robot.gripperSubsystem.voltage());
		return voltage() >= 2.5;
	}
	
	@Override
	public void bork() {
		leftWheel.disable();
		rightWheel.disable();
		downTimer.reset();
		downTimer.start();
	}

	@Override
	public void necromancy() {
		leftWheel.enable();
		rightWheel.enable();
	}

	@Override
	public boolean wakeMeUp() {
		if (downTimer.get() >= 3) {
			downTimer.stop();
			downTimer.reset();
			return true;
		}
		return false;
	}

	@Override
	public boolean ded() {
		return leftWheel.status() && rightWheel.status();
	}
}
