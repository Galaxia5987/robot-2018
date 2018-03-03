package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;

import auxiliary.Point;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TakeCommand;

/**
 *
 */
public class FarScale extends CommandGroup {
	/**
	 * The game specific message which includes data about the Scale and
	 * Switches Plates randomanization.
	 */
	String gameData = DriverStation.getInstance().getGameSpecificMessage();

	// Positions of the alliance Switch and Scale Plates.
	char scalePosition = gameData.charAt(1);
	private static double END_Y = Constants.toMeter(41.88) + Constants.AUTO_SCALE_CLOSE_SHIFT_T0_FIELD_CENTER - Constants.CENTER_TO_BACK_BUMPER;

	public FarScale(char robotPosition, boolean isBackwards) {
		double intakeDelay = isBackwards ? 2 : 0; // delay for opening intake so it won't touch the allience wall
		double forwardAddition = isBackwards ? 0.24 : 0; // forward addition to backwards
		double sideAddition = isBackwards ? 0.37 : 0; // side addition to backwards
		addParallel(new IntakeSolenoidCommand(true, intakeDelay));
//		if(isBackwards)
			addParallel(new TakeCommand(3.5, 0.7));
		addParallel(new LiftCommand(0.2, 1.7)); // move the lift up a bit to prevent the cube from touching the floor  
		final int Y_DIRECTION = robotPosition == 'R' ? 1 : -1;
		/**Far Scale**/
		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_FAR_LIFT_DELAY));
		if (isBackwards) {
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.731802407897101, 0.046487526716335326 * Y_DIRECTION),
				    new Point(4.826465725961964, 0.05304541026084225 * Y_DIRECTION),
				    new Point(4.91968665624793, 0.07076567493865737 * Y_DIRECTION),
				    new Point(5.010153674998203, 0.09939901463332837 * Y_DIRECTION),
				    new Point(5.096594003192735, 0.13854258735292543 * Y_DIRECTION),
				    new Point(5.1777915132556185, 0.1876456828074941 * Y_DIRECTION),
				    new Point(5.252603838734243, 0.24601747034319624 * Y_DIRECTION),
				    new Point(5.319978446234384, 0.31283671822757825 * Y_DIRECTION),
				    new Point(5.378967443495475, 0.38716334754549986 * Y_DIRECTION),
				    new Point(5.428740915271441, 0.4679516581541507 * Y_DIRECTION),
				    new Point(5.468598599394847, 0.5540650406214043 * Y_DIRECTION),
				    new Point(5.497979738753976, 0.6442919671654916 * Y_DIRECTION),
				    new Point(5.5164709705755435, 0.7373630366197195 * Y_DIRECTION),
				    new Point(5.523812142018851, 0.8319688336169049 * Y_DIRECTION),
				    new Point(5.523940760036492, 0.8484240873480531 * Y_DIRECTION),
				    new Point(5.5154526549539185, 4.286344346315591 * Y_DIRECTION),
				    new Point(5.520846363818371, 4.381081126545438 * Y_DIRECTION),
				    new Point(5.537419341405335, 4.474512845129304 * Y_DIRECTION),
				    new Point(5.564938422761839, 4.565325012733248 * Y_DIRECTION),
				    new Point(5.603016442378447, 4.652239994443595 * Y_DIRECTION),
				    new Point(5.651117681214536, 4.73403498479182 * Y_DIRECTION),
				    new Point(5.708565403720788, 4.8095592113848635 * Y_DIRECTION),
				    new Point(5.774551378820667, 4.877750125103267 * Y_DIRECTION),
				    new Point(5.848147250900329, 4.937648349087512 * Y_DIRECTION),
				    new Point(5.928317600828701, 4.988411176195496 * Y_DIRECTION),
				    new Point(6.013934513252448, 5.02932442503566 * Y_DIRECTION),
				    new Point(6.103793445218794, 5.0598124877734 * Y_DIRECTION),
				    new Point(6.196630172870792, 5.0794464283483665 * Y_DIRECTION),
				    new Point(6.291138577792278, 5.087950017168934 * Y_DIRECTION),
				    new Point(6.372129138637975, 5.086309173826331 * Y_DIRECTION),
				    new Point(7.450286478227648, 5.009730743437007 * Y_DIRECTION)}, true, true, 12));
		}
		else {
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.941486657785115, 0.046770167092048565 * Y_DIRECTION),
				    new Point(5.036152328308238, 0.053294004002952755 * Y_DIRECTION),
				    new Point(5.129379625760642, 0.07098074008832644 * Y_DIRECTION),
				    new Point(5.21985693680597, 0.09958154094494101 * Y_DIRECTION),
				    new Point(5.30631133762204, 0.13869402236925166 * Y_DIRECTION),
				    new Point(5.3875265026454615, 0.18776791149423305 * Y_DIRECTION),
				    new Point(5.462359817036269, 0.2461127885694247 * Y_DIRECTION),
				    new Point(5.529758452107576, 0.31290780046520544 * Y_DIRECTION),
				    new Point(5.588774177555303, 0.38721320924216596 * Y_DIRECTION),
				    new Point(5.638576702094952, 0.4679836133089423 * Y_DIRECTION),
				    new Point(5.678465354816228, 0.5540826551602556 * Y_DIRECTION),
				    new Point(5.707878942910711, 0.6442990087722296 * Y_DIRECTION),
				    new Point(5.7264036470843855, 0.737363421728582 * Y_DIRECTION),
				    new Point(5.733778843574575, 0.8319665723122904 * Y_DIRECTION),
				    new Point(5.733912652739005, 0.8487370280303501 * Y_DIRECTION),
				    new Point(5.72551509838958, 4.203233957936973 * Y_DIRECTION),
				    new Point(5.730905547651925, 4.297970923691545 * Y_DIRECTION),
				    new Point(5.74747531053329, 4.391403212444888 * Y_DIRECTION),
				    new Point(5.774991267308344, 4.482216326841356 * Y_DIRECTION),
				    new Point(5.813066296427338, 4.569132618646866 * Y_DIRECTION),
				    new Point(5.8611647209229085, 4.650929263961185 * Y_DIRECTION),
				    new Point(5.918609844840651, 4.726455467107563 * Y_DIRECTION),
				    new Point(5.98459347366353, 4.794648651158443 * Y_DIRECTION),
				    new Point(6.058187284787433, 4.854549407313037 * Y_DIRECTION),
				    new Point(6.138355888076839, 4.905314992804179 * Y_DIRECTION),
				    new Point(6.22397139275185, 4.946231187432661 * Y_DIRECTION),
				    new Point(6.313829275665284, 4.976722341919634 * Y_DIRECTION),
				    new Point(6.406665327719303, 4.9963594767069734 * Y_DIRECTION),
				    new Point(6.5011734400027175, 5.004866317263736 * Y_DIRECTION),
				    new Point(6.594177571329789, 5.002284402421551 * Y_DIRECTION),
				    new Point(7.320286478227653, 4.939730743437007 * Y_DIRECTION)}, false, true, 8));
			}
		if (isBackwards) {
			addSequential(new ShootCubeCommand(-0.65, true));
		}
		else {
			addSequential(new ShootCubeCommand(0.65, true));
		}
		addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM, 1));
		addSequential(new DriveStraightCommand(isBackwards ? 0.6 : -0.6));
	}
	
	public FarScale(char robotPosition) {
		this(robotPosition, false);
	}
}
