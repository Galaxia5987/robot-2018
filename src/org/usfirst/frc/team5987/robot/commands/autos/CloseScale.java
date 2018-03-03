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
public class CloseScale extends CommandGroup {
	/**
	 * The game specific message which includes data about the Scale and
	 * Switches Plates randomanization.
	 */
	String gameData = DriverStation.getInstance().getGameSpecificMessage();

	// Positions of the alliance Switch and Scale Plates.
	char scalePosition = gameData.charAt(1);
	private static double END_Y = Constants.toMeter(41.88) + Constants.AUTO_SCALE_CLOSE_SHIFT_T0_FIELD_CENTER - Constants.CENTER_TO_BACK_BUMPER;

	public CloseScale(char robotPosition, boolean isBackwards) {
		double intakeDelay = isBackwards ? 0.5 : 0; // delay for opening intake so it won't touch the allience wall
		double forwardAddition = isBackwards ? 0.2 : 0; // forward addition to backwards
		double sideAddition = isBackwards ? 0.30 : 0.28; // side addition to backwards	
		addParallel(new IntakeSolenoidCommand(true, intakeDelay));
		if(isBackwards)
			addParallel(new TakeCommand(1.5, 0.9));
		addParallel(new LiftCommand(0.1, 1.7)); // move the lift up a bit to prevent the cube from touching the floor  
		final int Y_DIRECTION = robotPosition == 'R' ? 1 : -1;
//		/**Close Scale**/
		
		if (isBackwards)
		{
			addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_CLOSE_LIFT_DELAY + 0.85));
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.493986606186959, -0.14026388942251572 * Y_DIRECTION),
				    new Point(4.588839236898502, -0.13759404472058684 * Y_DIRECTION),
				    new Point(4.6827085073407595, -0.12371205313901273 * Y_DIRECTION),
				    new Point(4.77427377226979, -0.09881322019228482 * Y_DIRECTION),
				    new Point(4.862246801457862, -0.06324784715744393 * Y_DIRECTION),
				    new Point(4.94538990378364, -0.01751630269109339 * Y_DIRECTION),
				    new Point(5.02253334028851, 0.037738016849118705 * Y_DIRECTION),
				    new Point(5.077895446118615, 0.08700692249059852 * Y_DIRECTION),
				    new Point(5.605567373724623, 0.6023357201212085 * Y_DIRECTION),
				    new Point(5.67726662057209, 0.6644917144028444 * Y_DIRECTION),
				    new Point(5.755821016025823, 0.7177209974952484 * Y_DIRECTION),
				    new Point(5.840125379569791, 0.7612746874776041 * Y_DIRECTION),
				    new Point(5.928993634480128, 0.7945400281916887 * Y_DIRECTION),
				    new Point(6.021175494719327, 0.8170490100983292 * Y_DIRECTION),
				    new Point(6.115374055190235, 0.8284849546960208 * Y_DIRECTION),
				    new Point(6.137993640668958, 0.8295560355381902 * Y_DIRECTION),
				    new Point(7.334839572192509, 0.8692616234756096 * Y_DIRECTION)}, true, true, 6.5));
		}
		else
		{
			addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, Constants.AUTO_SCALE_CLOSE_LIFT_DELAY));
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.3629363110350345, -0.14868285520377655 * Y_DIRECTION),
				    new Point(4.458846457706386, -0.1461949097600171 * Y_DIRECTION),
				    new Point(4.553769042147027, -0.13224324067848803 * Y_DIRECTION),
				    new Point(4.646338818616149, -0.1070285110248125 * Y_DIRECTION),
				    new Point(4.735224381170721, -0.0709133774045624 * Y_DIRECTION),
				    new Point(4.8191473129154145, -0.02441727397183674 * Y_DIRECTION),
				    new Point(4.896900573125455, 0.03179105845104244 * Y_DIRECTION),
				    new Point(4.911290375053306, 0.043854234347646126 * Y_DIRECTION),
				    new Point(5.545041794163906, 0.5879282998925917 * Y_DIRECTION),
				    new Point(5.588206462506282, 0.6224676333589056 * Y_DIRECTION),
				    new Point(5.668443462607724, 0.6750696287751685 * Y_DIRECTION),
				    new Point(5.754400550202524, 0.7176879956991952 * Y_DIRECTION),
				    new Point(5.844841427855013, 0.7497097657392569 * Y_DIRECTION),
				    new Point(5.9384653089954025, 0.77067437847743 * Y_DIRECTION),
				    new Point(6.03392562677958, 0.7802803055849491 * Y_DIRECTION),
				    new Point(7.150924369747899, 0.8192616234756098 * Y_DIRECTION)}, false, true, 10));
			
		}
			if (isBackwards) {
			addSequential(new ShootCubeCommand(-1, true));
		}
		else {
			addSequential(new ShootCubeCommand(0.5 , true));
		}
		addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM));
		addSequential(new DriveStraightCommand(isBackwards ? 0.2 : -0.2));
	}
	
	public CloseScale(char robotPosition) {
		this(robotPosition, false);
	}
}
