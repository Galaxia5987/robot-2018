package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.FieldMeasurements;
import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;

/**
 *
 */
public class EatCubeCommand extends PathCommand {
	private Point cubeTarget = null;

    public EatCubeCommand(double timeout) {
    	super(true,timeout);
    }
    
    public EatCubeCommand(Point cube, double timeout) {
    	super(true,timeout);
    	cubeTarget = cube;
    }
    
	@Override
	public Point[] getPoints() {
		if (Robot.ntVisionTarget.getBoolean(false)) {
			double angle = Math.toRadians(Robot.ntVisionAngle.getDouble(0));
			double distance = Robot.ntVisionDistance.getDouble(0) / Math.cos(angle) - 0.5;
			double navx = Robot.driveSubsystem.getAngleRadians();
			double y = distance * Math.sin(angle + navx);
			double x = distance * Math.cos(angle + navx);
//			double y = distance * Math.sin(angle);
//			double x = distance * Math.cos(angle);
					
			Point[] cube = new Point[] {new Point(x, y)};
			return cube;
		}
		
		if(cubeTarget != null) {
			return new Point[] {FieldMeasurements.getRelative(Robot.robotStartingPositionPoint, cubeTarget)};
		}
//		this.cancel();
		return new Point[] {new Point(1.5,0)};
	}
}
