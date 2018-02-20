package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;

/**
 *
 */
public class EatCubeCommand extends PathCommand {

    public EatCubeCommand() {
    	super();
    }
    
    
    
	@Override
	public Point[] getPoints() {
		if (Robot.ntVisionTarget.getBoolean(false)) {
			double angle = Math.toRadians(Robot.ntVisionAngle.getDouble(0));
			double distance = Robot.ntVisionDistance.getDouble(0) / Math.cos(angle) - 0.33;
			double navx = Math.toRadians(Robot.navx.getAngle());
			double y = distance * Math.sin(angle + navx);
			double x = distance * Math.cos(angle + navx);
//			double y = distance * Math.sin(angle);
//			double x = distance * Math.cos(angle);
					
			Point[] cube = new Point[] {new Point(x, y)};
			return cube;
		}
//		this.cancel();
		return new Point[] {new Point(0,0)};
	}
}
