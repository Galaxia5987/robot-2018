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
				    new Point(4.6430485592704285, 0.11446123501720451 * Y_DIRECTION),
				    new Point(4.737604273718151, 0.12242154210383638 * Y_DIRECTION),
				    new Point(4.830552304811114, 0.14152162429146448 * Y_DIRECTION),
				    new Point(4.920584968206846, 0.1714927628334621 * Y_DIRECTION),
				    new Point(5.006435595823737, 0.2119132942676063 * Y_DIRECTION),
				    new Point(5.086896356573748, 0.2622145427924911 * Y_DIRECTION),
				    new Point(5.160835249318403, 0.3216888209581578 * Y_DIRECTION),
				    new Point(5.227212028974454, 0.3894993861093545 * Y_DIRECTION),
				    new Point(5.285092841705093, 0.4646922125043865 * Y_DIRECTION),
				    new Point(5.333663363294437, 0.5462094134878093 * Y_DIRECTION),
				    new Point(5.3722402558617235, 0.6329041248806313 * Y_DIRECTION),
				    new Point(5.400280781730874, 0.7235566401938545 * Y_DIRECTION),
				    new Point(5.417390439198058, 0.8168915706592934 * Y_DIRECTION),
				    new Point(5.4233285127697695, 0.9115957886534759 * Y_DIRECTION),
				    new Point(5.423330262105102, 0.9162377586767967 * Y_DIRECTION),
				    new Point(5.414755701650212, 4.312925220445417 * Y_DIRECTION),
				    new Point(5.420144159856089, 4.407662299468344 * Y_DIRECTION),
				    new Point(5.436711959098479, 4.501094936442244 * Y_DIRECTION),
				    new Point(5.464226007278455, 4.591908629112361 * Y_DIRECTION),
				    new Point(5.502299209698263, 4.678825721108891 * Y_DIRECTION),
				    new Point(5.55039591509034, 4.760623377273724 * Y_DIRECTION),
				    new Point(5.607839451686322, 4.836150787708472 * Y_DIRECTION),
				    new Point(5.6738216473011756, 4.904345358500258 * Y_DIRECTION),
				    new Point(5.74741419949457, 4.9642476613382565 * Y_DIRECTION),
				    new Point(5.827581735842857, 5.015014931695041 * Y_DIRECTION),
				    new Point(5.913196380576915, 5.0559329256670535 * Y_DIRECTION),
				    new Point(6.003053622648058, 5.08642596866051 * Y_DIRECTION),
				    new Point(6.095889261974448, 5.10606505454792 * Y_DIRECTION),
				    new Point(6.190397195451554, 5.1145738813483455 * Y_DIRECTION),
				    new Point(6.214753152646472, 5.1149447252083755 * Y_DIRECTION),
				    new Point(7.334839572192509, 5.114944725208376 * Y_DIRECTION)}, true, true,8));
		}
		else {
			addSequential(new PathPointsCommand(new Point[]{
				    new Point(4.847916538588957, 0.1187128932668246 * Y_DIRECTION),
				    new Point(4.9424735625689875, 0.12665762986866752 * Y_DIRECTION),
				    new Point(5.035424737589552, 0.14574240615577777 * Y_DIRECTION),
				    new Point(5.12546233507631, 0.17569871871999457 * Y_DIRECTION),
				    new Point(5.211319617530009, 0.21610511268553614 * Y_DIRECTION),
				    new Point(5.291788660235817, 0.26639311115081293 * Y_DIRECTION),
				    new Point(5.365737345536554, 0.3258552130807403 * Y_DIRECTION),
				    new Point(5.432125290577663, 0.3936548471273292 * Y_DIRECTION),
				    new Point(5.490018484436712, 0.46883814133841883 * Y_DIRECTION),
				    new Point(5.538602428707925, 0.5503473431667056 * Y_DIRECTION),
				    new Point(5.577193596667045, 0.6370357009731822 * Y_DIRECTION),
				    new Point(5.605249049797736, 0.7276835976573539 * Y_DIRECTION),
				    new Point(5.6223740763847685, 0.8210157094304522 * Y_DIRECTION),
				    new Point(5.628327744706739, 0.9157189483251418 * Y_DIRECTION),
				    new Point(5.62832993672657, 0.9204926526521482 * Y_DIRECTION),
				    new Point(5.619755701650212, 4.317051220445417 * Y_DIRECTION),
				    new Point(5.625144159856089, 4.411788299468343 * Y_DIRECTION),
				    new Point(5.641711959098479, 4.505220936442243 * Y_DIRECTION),
				    new Point(5.669226007278455, 4.596034629112361 * Y_DIRECTION),
				    new Point(5.707299209698263, 4.68295172110889 * Y_DIRECTION),
				    new Point(5.75539591509034, 4.7647493772737235 * Y_DIRECTION),
				    new Point(5.812839451686322, 4.840276787708471 * Y_DIRECTION),
				    new Point(5.878821647301176, 4.9084713585002575 * Y_DIRECTION),
				    new Point(5.95241419949457, 4.968373661338256 * Y_DIRECTION),
				    new Point(6.032581735842857, 5.01914093169504 * Y_DIRECTION),
				    new Point(6.118196380576915, 5.060058925667053 * Y_DIRECTION),
				    new Point(6.208053622648058, 5.09055196866051 * Y_DIRECTION),
				    new Point(6.300889261974448, 5.110191054547919 * Y_DIRECTION),
				    new Point(6.3953971954515545, 5.118699881348345 * Y_DIRECTION),
				    new Point(6.419753152646472, 5.119070725208375 * Y_DIRECTION),
				    new Point(7.286478227654698, 5.119070725208376 * Y_DIRECTION)}, false, true, 8));
			}
		if (isBackwards) {
			addSequential(new ShootCubeCommand(-0.75, true));
		}
		else {
			addSequential(new ShootCubeCommand(0.75, true));
		}
		addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM));
		addSequential(new DriveStraightCommand(isBackwards ? 0.4 : -0.4));
	}
	
	public FarScale(char robotPosition) {
		this(robotPosition, false);
	}
}
