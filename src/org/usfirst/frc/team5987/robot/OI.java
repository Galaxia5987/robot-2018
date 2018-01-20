/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;
import org.usfirst.frc.team5987.robot.commands.ClimbCommand;
import org.usfirst.frc.team5987.robot.commands.GripperCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSelenoidCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeTakeCubeCommand;
import org.usfirst.frc.team5987.robot.commands.OpenHooksCommand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	XboxController xbox = new XboxController(0);
	Button a = new JoystickButton(xbox, 1);
	Button b = new JoystickButton(xbox, 2);
	Button x = new JoystickButton(xbox, 3);
	Button y = new JoystickButton(xbox, 4);
	

	public OI() {
		a.whenPressed(new IntakeTakeCubeCommand());
		y.whenPressed(new IntakeSelenoidCommand(false));
		b.whenPressed(new IntakeSelenoidCommand(true));
	}
}
