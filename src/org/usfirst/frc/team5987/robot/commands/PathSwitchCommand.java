package org.usfirst.frc.team5987.robot.commands;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathSwitchCommand extends PathCommand {
	Date d = new Date();
	public PathSwitchCommand() {
		super();
	}
	public PathSwitchCommand(double timeOut) {
		super(true,timeOut);
	}
	
	@Override
	public Point[] getPoints() {
		if (!Robot.ntVisionTarget.getBoolean(false))
    	{
			String gameData = DriverStation.getInstance().getGameSpecificMessage();
			// Positions of the alliance Switch and Scale Plates.
			double switchPosition = gameData.charAt(0) == 'L' ? 1 : -1;
			Point p0 =  new Point(0, 0);
			Point p1 = new Point(1.85, gameData.charAt(0) == 'L' ? 1.4 : -1.3);
			Point p2 = new Point(2.85, gameData.charAt(0) == 'L' ? 1.4 : -1.3);
//			System.out.println("switch not found");
			
			
			SmartDashboard.putString("Switch - "+d.toString(), "switch not found");
			return new Point[] {p1,p2};
//			return new Point[] {p0}; // TODO: Change to above!
    	}
		double switchDistance = Robot.ntVisionDistance.getDouble(0);
    	double switchAngle = Math.toRadians(Robot.ntVisionAngle.getDouble(0));
    	switchDistance = switchDistance / Math.cos(switchAngle);
    	double dx = switchDistance * Math.cos(Robot.driveSubsystem.getAngleRadians() + switchAngle);
    	double dy = switchDistance * Math.sin(Robot.driveSubsystem.getAngleRadians() + switchAngle);
//    	SmartDashboard.putNumber("d x", dx);
//		SmartDashboard.putNumber("d y", dy);
//		SmartDashboard.putNumber("c", switchDistance);
//		SmartDashboard.putNumber("a", Robot.driveSubsystem.getAngleRadians() + switchAngle);
		double yShift = dy >= 0 ? Constants.AUTO_SWITCH_Y_SIDE_SHIFT : - Constants.AUTO_SWITCH_Y_SIDE_SHIFT;
		Point B = new Point(dx - Constants.PATH_TURN_DISTANCE_BEFORE_SWITCH + Constants.AUTO_SWITCH_X_SHIFT, dy + yShift);
		Point C = new Point(dx - Constants.PATH_END_DISTANCE_BEFORE_SWITCH + Constants.AUTO_SWITCH_X_SHIFT, dy + yShift);
//		System.out.println("switch found B(: " +B.get()[0]+", "+B.get()[1]);
//		System.out.println("switch found C(: " +C.get()[0]+", "+C.get()[1]);
		SmartDashboard.putString("Switch B - "+d.toString(), "switch-B( " +B.get()[0]+", "+B.get()[1]);
		SmartDashboard.putString("Switch C - "+d.toString(), "switch-C( " +C.get()[0]+", "+C.get()[1]);
		return new Point[] {B,C};
	}

}
