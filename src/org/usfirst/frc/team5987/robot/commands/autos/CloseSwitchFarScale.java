package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.Constants;
import org.usfirst.frc.team5987.robot.commands.DriveStraightCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CloseSwitchFarScale extends CommandGroup {

    public CloseSwitchFarScale(char robotPosition) {
    	if (robotPosition == 'R') {
        	addSequential(new PathPointsCommand(new Point[]{
        		    new Point(4.50069467746868, -0.312642369020501),
        		    new Point(5.622982897696609, 0.3810022779043283),
        		    new Point(6.203156994770904, 0.4184965831435081),
        		    new Point(5.847799876084287, 0.9809111617312074)
        		}, true, false));
        	addSequential(new GetAndEatSwitchCube('R'));
        	addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, 5.5));
        	addSequential(new PathPointsCommand(new Point[]{
        		    new Point(5.343150358837866, 1.0746469248291577),
        		    new Point(5.454637114347899, 4.5241230068337135),
        		    new Point(5.94120756493277, 4.824077448747153),
        		    new Point(7.082323192536945, 4.880318906605924)
        		}, true, false));
    	}
    	else {
    		addSequential(new PathPointsCommand(new Point[]{
    			    new Point(3.0044694564451984, 5.8739179954441925),
    			    new Point(4.968764066916065, 5.8739179954441925),
    			    new Point(5.529403306008857, 5.517722095671982),
    			    new Point(5.697974658574964, 4.880318906605924),
    			    new Point(5.436572176659369, 4.711594533029613)
    			}, true, true));
    		addSequential(new GetAndEatSwitchCube('L'));
    		addParallel(new LiftCommand(Constants.LiftCommandStates.SCALE_TOP, 5.5));
    		addSequential(new PathPointsCommand(new Point[]{
    			    new Point(5.286659060576783, 4.317904328018224),
    			    new Point(5.455196765898279, 1.655808656036447),
    			    new Point(5.791523892630391, 0.7934396355353077),
    			    new Point(6.93235089436422, 0.7934396355353077)
    			}, true, false));
    	}
    	addSequential(new ShootCubeCommand(-0.75, true));
    	addParallel(new LiftCommand(Constants.LiftCommandStates.BOTTOM));
		addSequential(new DriveStraightCommand(0.2));
    }
}