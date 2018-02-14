package org.usfirst.frc.team5987.robot.commands;

import auxiliary.Point;

public class PathPointsCommand extends PathCommand{
	
	private Point[] points;
	
	public PathPointsCommand(Point[] points) { 
		super();
		this.points = points;
	}

	@Override
	public Point[] getPoints() {
		return points;
	}
}
