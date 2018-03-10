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
		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_FAR_LIFT_DELAY + 0.6));
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
				    new Point(4.94155545630881, 0.04677081825609575 * Y_DIRECTION),
				    new Point(5.036221126831934, 0.05329465516699988 * Y_DIRECTION),
				    new Point(5.129448424284338, 0.07098139125237352 * Y_DIRECTION),
				    new Point(5.2199257353296655, 0.09958219210898814 * Y_DIRECTION),
				    new Point(5.3063801361457354, 0.13869467353329856 * Y_DIRECTION),
				    new Point(5.387595301169158, 0.18776856265827993 * Y_DIRECTION),
				    new Point(5.462428615559966, 0.24611343973347152 * Y_DIRECTION),
				    new Point(5.529827250631272, 0.3129084516292522 * Y_DIRECTION),
				    new Point(5.5888429760789995, 0.38721386040621264 * Y_DIRECTION),
				    new Point(5.638645500618649, 0.4679842644729889 * Y_DIRECTION),
				    new Point(5.678534153339924, 0.5540833063243022 * Y_DIRECTION),
				    new Point(5.707947741434408, 0.6442996599362762 * Y_DIRECTION),
				    new Point(5.726472445608082, 0.7373640728926287 * Y_DIRECTION),
				    new Point(5.733847642098271, 0.8319672234763371 * Y_DIRECTION),
				    new Point(5.733981621704474, 0.8486683958854928 * Y_DIRECTION),
				    new Point(5.7254524344572, 4.377840334251446 * Y_DIRECTION),
				    new Point(5.730851088337228, 4.472576832814124 * Y_DIRECTION),
				    new Point(5.747428942803585, 4.56600768620274 * Y_DIRECTION),
				    new Point(5.774952764290562, 4.65681841725577 * Y_DIRECTION),
				    new Point(5.813035320599906, 4.74373141127035 * Y_DIRECTION),
				    new Point(5.861140828864334, 4.825523890742815 * Y_DIRECTION),
				    new Point(5.918592493468014, 4.901045118605576 * Y_DIRECTION),
				    new Point(5.9845820278731034, 4.969232587928262 * Y_DIRECTION),
				    new Point(6.058181026389951, 5.029126970310501 * Y_DIRECTION),
				    new Point(6.1383540259016875, 5.079885612657629 * Y_DIRECTION),
				    new Point(6.223973073778036, 5.120794392453418 * Y_DIRECTION),
				    new Point(6.31383359702257, 5.151277764738178 * Y_DIRECTION),
				    new Point(6.4066713493906775, 5.170906859441464 * Y_DIRECTION),
				    new Point(6.501180198049478, 5.179405515148145 * Y_DIRECTION),
				    new Point(6.596030499539469, 5.17665416440887 * Y_DIRECTION),
				    new Point(6.5990652697891, 5.176379546136036 * Y_DIRECTION),
				    new Point(7.320286478227653, 5.109730743437007 * Y_DIRECTION)}, false, true, 8));
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
