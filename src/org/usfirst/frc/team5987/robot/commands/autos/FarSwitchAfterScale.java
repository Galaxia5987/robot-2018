package org.usfirst.frc.team5987.robot.commands.autos;

import org.usfirst.frc.team5987.robot.commands.PathPointsCommand;

import auxiliary.Point;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FarSwitchAfterScale extends CommandGroup {

    public FarSwitchAfterScale(char currentPosition) {
    	if(currentPosition == 'R'){
        	addSequential(new PathPointsCommand(new Point[]{
        		    new Point(5.8668654374719615, 0.8121867881548979),
        		    new Point(5.548206631732475, 1.0558997722095675),
        		    new Point(5.660698417201782, 4.317904328018224),
        		    new Point(5.605006827354982, 4.542870159453304),
        		    new Point(5.492202817729595, 4.6366059225512535),
        		    new Point(5.361732576219088, 4.6366059225512535)
        		}, false, false));
    	}else{
    		addSequential(new PathPointsCommand(new Point[]{
    			    new Point(5.81049240382452, 4.842824601366743),
    			    new Point(5.5669309583015005, 4.542870159453304),
    			    new Point(5.735540917812615, 1.337107061503417),
    			    new Point(5.7170215539669655, 1.1308883826879272),
    			    new Point(5.605092176627694, 1.0371526195899774),
    			    new Point(5.417769898421364, 1.0371526195899774)
    			}, false, false));
    	}
    	addSequential(new GetAndEatSwitchCube(currentPosition));
    }

}
