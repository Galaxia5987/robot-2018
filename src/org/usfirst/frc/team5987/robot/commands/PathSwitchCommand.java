package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathSwitchCommand extends PathCommand {

	private final double MOVE_FORWARD_SWITCH_DISTANCE = 3;
	private final double MOVE_SIDEWAYS_SWITCH_DISTANCE = 2;
	
	public PathSwitchCommand() {
		super();
	}
	
	@Override
	public Point[] getPoints() {
		if (!Robot.ntSwitchTarget.getBoolean(false))
    	{
			String gameData = DriverStation.getInstance().getGameSpecificMessage();
			// Positions of the alliance Switch and Scale Plates.
			double switchPosition = gameData.charAt(0) == 'L' ? 1 : -1;
			Point p0 =  new Point(0, 0);
			Point p1 = new Point(MOVE_FORWARD_SWITCH_DISTANCE - 1.5, switchPosition * MOVE_SIDEWAYS_SWITCH_DISTANCE);
			Point p2 = new Point(MOVE_FORWARD_SWITCH_DISTANCE - 0.95, switchPosition * MOVE_SIDEWAYS_SWITCH_DISTANCE);

//			return new Point[] {p0,p1,p2};
			return new Point[] {p0};
    	}
		double switchDistance = Robot.ntSwitchDistance.getDouble(0);
    	double switchAngle = Math.toRadians(Robot.ntSwitchAngle.getDouble(0));
    	switchDistance = switchDistance / Math.cos(switchAngle);
    	double dx = switchDistance * Math.cos(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    	double dy = switchDistance * Math.sin(Robot.driveSubsystem.getAngleRadians() + switchAngle);
//    	SmartDashboard.putNumber("d x", dx);
//		SmartDashboard.putNumber("d y", dy);
//		SmartDashboard.putNumber("c", switchDistance);
//		SmartDashboard.putNumber("a", Robot.driveSubsystem.getAngleRadians() + switchAngle);
		
		Point A = new Point(0, 0);
		Point B = new Point(dx - 1.5, dy);
		Point C = new Point(dx -0.95, dy);
		
		return new Point[] {A,B,C};
	}

}
