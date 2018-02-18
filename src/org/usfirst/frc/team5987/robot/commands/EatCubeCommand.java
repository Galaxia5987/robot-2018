package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Robot;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.Command;

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
			double distance = Robot.ntVisionDistance.getDouble(0) / Math.cos(angle);
			double navx = Math.toRadians(Robot.navx.getAngle());
			double x = distance * Math.sin(angle + navx);
			double y = distance * Math.cos(angle + navx);
			
			Point[] cube = new Point[] {new Point(x, y)};
			return cube;
		}
		this.cancel();
		return new Point[] {new Point(0,0)};
	}
}
