package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.Constants;

import auxiliary.Point;

public class PathPointsCommand extends PathCommand {

	private Point[] points;
	private boolean isBackwards;

	public PathPointsCommand(Point[] points) {
		this(points, false);
	}

	public PathPointsCommand(Point[] points, boolean isBackwards) {
		super();
		this.isBackwards = isBackwards;
		if(isBackwards) {
			// Invert axes
			for(int i = 0; i < points.length; i++){
				double[] xy = points[i].get();
				points[i].setPoint(-xy[0], -xy[1]);
			}
		}
		this.points = points;
	}

	@Override
	public Point[] getPoints() {
		return points;
	}

	@Override
	public void initialize() {
		if (isBackwards) {
			Constants.PATH_h *= -1;
		}
		super.initialize();
	}

	@Override
	public void end() {
		if (isBackwards) {
			Constants.PATH_h *= -1;
		}
		super.end();
	}
}
