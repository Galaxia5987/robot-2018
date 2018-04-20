package org.usfirst.frc.team5987.robot;

import java.util.EnumMap;
import java.util.Map;

import auxiliary.Point;
/**
 * Measurements from right close corners in METERS
 * @author student
 *
 */
public class FieldMeasurements {
	public static Point getRelative(Point robotStartingPosition, Point destination){
		return destination.minus(robotStartingPosition);
	}
	
	public enum Cube {
		/*
		 * From close to far, then right to left (1 is the closest and
		 * rightmost)
		 */
		PLATFORM_1, PLATFORM_2, PLATFORM_3, PLATFORM_4, PLATFORM_5, PLATFORM_6, 
		PILE_1, PILE_2, PILE_3, PILE_4, PILE_5, PILE_6
	}

	public enum Switch {
		TARGET_RIGHT, TARGET_LEFT
	}

	public enum Vault {
		VAULT
	}
	
	public enum Robot {
		LEFT, RIGHT, CENTER
	}

	public static Map<Cube, Point> CUBES = new EnumMap<Cube, Point>(Cube.class);
	static {
		CUBES.put(Cube.PLATFORM_1, new Point(3.55+1.43+0.334/2.0, 2.16+0.334/2));
		CUBES.put(Cube.PLATFORM_2, new Point(0, 0));
		CUBES.put(Cube.PLATFORM_3, new Point(0, 0));
		CUBES.put(Cube.PLATFORM_4, new Point(0, 0));
		CUBES.put(Cube.PLATFORM_5, new Point(0, 0));
		CUBES.put(Cube.PLATFORM_6, new Point(3.55+1.43+0.334/2.0, 2.16+3.9-0.334/2 + 0.05));

		CUBES.put(Cube.PILE_1, new Point(3.55-2.5*0.334, 3.42+0.7));
		CUBES.put(Cube.PILE_2, new Point(0, 0));
		CUBES.put(Cube.PILE_3, new Point(0, 0));
		CUBES.put(Cube.PILE_4, new Point(0, 0));
		CUBES.put(Cube.PILE_5, new Point(0, 0));
		CUBES.put(Cube.PILE_6, new Point(0, 0));

	}
	
	public static Map<Switch, Point> SWITCHES = new EnumMap<Switch, Point>(Switch.class);
	static {
		SWITCHES.put(Switch.TARGET_RIGHT, new Point(0, 0));
		SWITCHES.put(Switch.TARGET_LEFT, new Point(0, 0));
	}
	
	public static Map<Vault, Point> VAULTS = new EnumMap<Vault, Point>(Vault.class);
	static {
		VAULTS.put(Vault.VAULT, new Point(0, 0));
	}
	
	public static Map<Robot, Point> ROBOTS = new EnumMap<Robot, Point>(Robot.class);
	static {
		ROBOTS.put(Robot.RIGHT, new Point(0.55, 0.7+0.53));
		ROBOTS.put(Robot.LEFT, new Point(0, 0));
		ROBOTS.put(Robot.CENTER, new Point(0.34, 0.7+3.72-0.53));
	}
}
