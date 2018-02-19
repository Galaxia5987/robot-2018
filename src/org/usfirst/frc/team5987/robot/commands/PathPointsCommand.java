package org.usfirst.frc.team5987.robot.commands;

import auxiliary.Point;

public class PathPointsCommand extends PathCommand{
	
	private Point[] points;
	private boolean isRelative;
	
	public PathPointsCommand(Point[] points, boolean isRelative) { 
		super(isRelative);
		this.points = points;
	}
	
	public PathPointsCommand(Point[] points){
		this(points, true);
	}

	@Override
	public Point[] getPoints() {
		return points;
	}
}
