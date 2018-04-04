package org.usfirst.frc.team5987.robot.commands.autos;


import org.usfirst.frc.team5987.robot.commands.DriveSeconds;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class MainAuto extends CommandGroup {
	private void ntAppendCommand(String command){
		String current = SmartDashboard.getString("Auto Commands", "");
		SmartDashboard.putString("Auto Commands", current + ", \n" + command);
	}
    public MainAuto(char robotPosition, String scaleChoice, String switchChoice, String stupidAuto, boolean isBackwards) {
        final char switchSide = DriverStation.getInstance().getGameSpecificMessage().charAt(0),
        	 scaleSide = DriverStation.getInstance().getGameSpecificMessage().charAt(1);
		char currentPosition = robotPosition;
        
		SmartDashboard.putString("Auto Commands", "");
		
		// Stupid autos
		if(stupidAuto == "nothing"){
			ntAppendCommand("IntakeSolenoidCommand(true)");
			addSequential(new IntakeSolenoidCommand(true));
			return;
		}else if (stupidAuto == "line"){
			double direction = isBackwards ? -1 : 1;
			ntAppendCommand("DriveSeconds(" + 0.5 * direction + ", 1.5)");
			addSequential(new DriveSeconds(0.5 * direction, 1.5));
			return;
		}
		
        // Going to the Switch side if the robot starts on the Switch position.
        if (switchChoice == "side" && robotPosition == switchSide) {
        	ntAppendCommand("SwitchSide");
			addSequential(new SwitchSide(robotPosition, isBackwards));
			return;
        }
        
        // Going to the Switch from the center.
		if (currentPosition == 'C') {
			ntAppendCommand("Switch");
			addSequential(new Switch());
        	return;
		} else {
			if(scaleChoice == "nothing"){
				ntAppendCommand("AutoRun");
				addSequential(new AutoRun(isBackwards));
				return;
			}
		}
		
		// Special case: If we choose to go to both Switches and both Scales and the robot starts
		// on the Switch side and on the opposite of the Scale side.
		if (scaleChoice == "both" &&( switchChoice != "nothing" && switchChoice != "another scale") && robotPosition == switchSide && robotPosition != scaleSide)
		{
			ntAppendCommand("CloseSwitchFarScale(" + robotPosition + ")");
			addSequential(new CloseSwitchFarScale(robotPosition));
			return;
		}
		
		// If the robot starts on the Scale side.
        if (currentPosition == scaleSide) {
        	ntAppendCommand("CloseScale(" + robotPosition + ", " + isBackwards + ")");
    		addSequential(new CloseScale(robotPosition, isBackwards));
    	} 
//         If the robot starts on the opposite side of the Scale.
        else {	
        	if (scaleChoice == "both") {
        		ntAppendCommand("FarScale(" + robotPosition + ", " + isBackwards + ")");
    			addSequential(new FarScale(robotPosition, isBackwards));
    			currentPosition = scaleSide;
    		}
    		else {
    			ntAppendCommand("AutoRun");
    			addSequential(new AutoRun(isBackwards));
    			return;
    		}
    	}
        
    	switch (switchChoice) {
    	case "both":
    		if(currentPosition == switchSide){
    			ntAppendCommand("CloseSwitchAfterScale(" + currentPosition + ")");
    			addSequential(new CloseSwitchAfterScale(currentPosition));
    		}
    		else {
    			ntAppendCommand("FarSwitchAfterScale(" + currentPosition + ")");
    			addSequential(new FarSwitchAfterScale(currentPosition));
    		}
    		break;
    	case "close":	
    		if (currentPosition == switchSide){
    			ntAppendCommand("CloseSwitchAfterScale(" + currentPosition + ")");
    			addSequential(new CloseSwitchAfterScale(currentPosition));
    		}
    		break;
    	case "another scale":
			ntAppendCommand("ScaleAfterScale(" + robotPosition + ", " + currentPosition + ", " + (robotPosition == scaleSide) +")");
			addSequential(new ScaleAfterScale(robotPosition, currentPosition, robotPosition == scaleSide));
    		break;
    	}
  
    }
}