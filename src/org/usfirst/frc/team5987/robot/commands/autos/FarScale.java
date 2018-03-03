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
				    new Point(5.515469096203671, 4.279685179745962 * Y_DIRECTION),
				    new Point(5.5208628050681225, 4.374421959975809 * Y_DIRECTION),
				    new Point(5.537435782655087, 4.467853678559674 * Y_DIRECTION),
				    new Point(5.564954864011591, 4.558665846163619 * Y_DIRECTION),
				    new Point(5.6030328836282, 4.645580827873965 * Y_DIRECTION),
				    new Point(5.6511341224642875, 4.727375818222191 * Y_DIRECTION),
				    new Point(5.708581844970539, 4.802900044815234 * Y_DIRECTION),
				    new Point(5.774567820070419, 4.871090958533638 * Y_DIRECTION),
				    new Point(5.848163692150082, 4.930989182517882 * Y_DIRECTION),
				    new Point(5.928334042078453, 4.9817520096258665 * Y_DIRECTION),
				    new Point(6.0139509545022, 5.022665258466031 * Y_DIRECTION),
				    new Point(6.103809886468546, 5.053153321203771 * Y_DIRECTION),
				    new Point(6.196646614120545, 5.072787261778737 * Y_DIRECTION),
				    new Point(6.29115501904203, 5.081290850599305 * Y_DIRECTION),
				    new Point(6.378273859329013, 5.079191069620799 * Y_DIRECTION),
				    new Point(7.260286478227652, 5.009730743437007 * Y_DIRECTION)}, true, true, 9));
		}
		else {
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.941454470302601, 0.046769862444066945 * Y_DIRECTION),
				    new Point(5.036120140825725, 0.05329369935497108 * Y_DIRECTION),
				    new Point(5.129347438278129, 0.07098043544034488 * Y_DIRECTION),
				    new Point(5.219824749323456, 0.09958123629695947 * Y_DIRECTION),
				    new Point(5.306279150139526, 0.13869371772126998 * Y_DIRECTION),
				    new Point(5.387494315162948, 0.18776760684625143 * Y_DIRECTION),
				    new Point(5.462327629553756, 0.24611248392144316 * Y_DIRECTION),
				    new Point(5.5297262646250624, 0.3129074958172238 * Y_DIRECTION),
				    new Point(5.58874199007279, 0.3872129045941844 * Y_DIRECTION),
				    new Point(5.638544514612438, 0.4679833086609607 * Y_DIRECTION),
				    new Point(5.678433167333714, 0.5540823505122741 * Y_DIRECTION),
				    new Point(5.707846755428197, 0.6442987041242481 * Y_DIRECTION),
				    new Point(5.7263714596018715, 0.7373631170806008 * Y_DIRECTION),
				    new Point(5.733746656092061, 0.8319662676643089 * Y_DIRECTION),
				    new Point(5.733880383459992, 0.8487691356213194 * Y_DIRECTION),
				    new Point(5.725550602291856, 4.123198494114019 * Y_DIRECTION),
				    new Point(5.7309372132410905, 4.217935678187192 * Y_DIRECTION),
				    new Point(5.7475031906597165, 4.311368638194881 * Y_DIRECTION),
				    new Point(5.775015468080017, 4.402182867337572 * Y_DIRECTION),
				    new Point(5.813086975717024, 4.489100701697835 * Y_DIRECTION),
				    new Point(5.861182086147049, 4.570899295673259 * Y_DIRECTION),
				    new Point(5.9186241500412216, 4.646427826171413 * Y_DIRECTION),
				    new Point(5.984605015933926, 4.714623683521267 * Y_DIRECTION),
				    new Point(6.0581964000926725, 4.774527421311429 * Y_DIRECTION),
				    new Point(6.138362946526872, 4.8252962548261555 * Y_DIRECTION),
				    new Point(6.223976793394489, 4.8662159181698454 * Y_DIRECTION),
				    new Point(6.313833440872007, 4.89671071326235 * Y_DIRECTION),
				    new Point(6.4066686972429165, 4.916351609325927 * Y_DIRECTION),
				    new Point(6.50117646479048, 4.924862278912297 * Y_DIRECTION),
				    new Point(6.5942129934355656, 4.9222813508375465 * Y_DIRECTION),
				    new Point(7.320286478227653, 4.859730743437007 * Y_DIRECTION)}, false, true, 8));
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
