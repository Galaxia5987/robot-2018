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
		addParallel(new LiftCommand(0.1, 1.7)); // move the lift up a bit to prevent the cube from touching the floor  
		final int Y_DIRECTION = robotPosition == 'R' ? 1 : -1;
		/**Far Scale**/
		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_FAR_LIFT_DELAY + 0.6));
		if (isBackwards) {
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.731894141254905, 0.04648842794942881 * Y_DIRECTION),
				    new Point(4.826557459319768, 0.05304631149393568 * Y_DIRECTION),
				    new Point(4.919778389605733, 0.0707665761717508 * Y_DIRECTION),
				    new Point(5.010245408356007, 0.09939991586642188 * Y_DIRECTION),
				    new Point(5.096685736550539, 0.13854348858601895 * Y_DIRECTION),
				    new Point(5.177883246613423, 0.1876465840405876 * Y_DIRECTION),
				    new Point(5.252695572092047, 0.24601837157628978 * Y_DIRECTION),
				    new Point(5.3200701795921885, 0.3128376194606717 * Y_DIRECTION),
				    new Point(5.379059176853279, 0.38716424877859335 * Y_DIRECTION),
				    new Point(5.428832648629245, 0.4679525593872442 * Y_DIRECTION),
				    new Point(5.468690332752651, 0.5540659418544979 * Y_DIRECTION),
				    new Point(5.49807147211178, 0.6442928683985851 * Y_DIRECTION),
				    new Point(5.516562703933348, 0.7373639378528131 * Y_DIRECTION),
				    new Point(5.523903875376655, 0.8319697348499984 * Y_DIRECTION),
				    new Point(5.524032716231848, 0.848332571021865 * Y_DIRECTION),
				    new Point(5.51536862983712, 4.529785491691415 * Y_DIRECTION),
				    new Point(5.520773282874881, 4.6245216481955405 * Y_DIRECTION),
				    new Point(5.537357053796855, 4.717951451607728 * Y_DIRECTION),
				    new Point(5.564886625798815, 4.808760439538559 * Y_DIRECTION),
				    new Point(5.602974685778604, 4.895671021809124 * Y_DIRECTION),
				    new Point(5.651085373437891, 4.977460454846548 * Y_DIRECTION),
				    new Point(5.70854182029202, 5.052978044443485 * Y_DIRECTION),
				    new Point(5.774535672521745, 5.121161334856268 * Y_DIRECTION),
				    new Point(5.8481384636900104, 5.18105105647754 * Y_DIRECTION),
				    new Point(5.928314677321196, 5.231804621784808 * Y_DIRECTION),
				    new Point(6.013936315565629, 5.272707979690637 * Y_DIRECTION),
				    new Point(6.103798768983037, 5.3031856615158155 * Y_DIRECTION),
				    new Point(6.1966377641732375, 5.322808877248885 * Y_DIRECTION),
				    new Point(6.291147150818105, 5.331301548185849 * Y_DIRECTION),
				    new Point(6.378173615800014, 5.32919896400697 * Y_DIRECTION),
				    new Point(7.260286478227652, 5.259730743437007 * Y_DIRECTION)}, true, true, 8));
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
			addSequential(new ShootCubeCommand(-1, true));
		}
		else {
			addSequential(new ShootCubeCommand(0.3, true)); // less (was 0.65) [untested]
		}
		addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM, 1)); // was 1 sec delay
		addSequential(new DriveStraightCommand(isBackwards ? 0.2 : -0.6));
	}
	
	public FarScale(char robotPosition) {
		this(robotPosition, false);
	}
}
