/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.ClimbCommand;
import org.usfirst.frc.team5987.robot.commands.CommandGroup_CenterSwitch;
import org.usfirst.frc.team5987.robot.commands.DriveToBumpCommand;
import org.usfirst.frc.team5987.robot.commands.IntakeSolenoidCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand;
import org.usfirst.frc.team5987.robot.commands.LiftCommand.liftStates;
import org.usfirst.frc.team5987.robot.commands.PathCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCubeCommand;
import org.usfirst.frc.team5987.robot.commands.TakeCommand;

import auxiliary.DPadButton;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public static final int TakeCommandButton = 1;
	public Joystick left = new Joystick(0);
	public Joystick right = new Joystick(1);
	public XboxController xbox = new XboxController(2);
	
	Button a = new JoystickButton(xbox, TakeCommandButton);
	Button b = new JoystickButton(xbox, 2);
	Button x = new JoystickButton(xbox, 3);
	Button y = new JoystickButton(xbox, 4);
	Button select = new JoystickButton(xbox, 7);
	Button start = new JoystickButton(xbox, 8);

	DPadButton d_up = new DPadButton(xbox,0);
	DPadButton d_down = new DPadButton(xbox,4);
	DPadButton d_dl = new DPadButton(xbox,5);
	DPadButton d_left = new DPadButton(xbox,6);
	DPadButton d_lu = new DPadButton(xbox,7);

	public OI() {
		d_down.whenPressed(new LiftCommand(liftStates.BOTTOM));
		d_dl.whenPressed(new LiftCommand(liftStates.SWITCH));
		d_left.whenPressed(new LiftCommand(liftStates.SCALE_DOWN));
		d_lu.whenPressed(new LiftCommand(liftStates.SCALE_MID));
		d_up.whenPressed(new LiftCommand(liftStates.SCALE_TOP));
		b.whileHeld(new ShootCubeCommand(0.75, false));
		x.whileHeld(new ShootCubeCommand(-0.75, false));
		y.whenPressed(new IntakeSolenoidCommand());
		a.whenPressed(new TakeCommand());
		start.toggleWhenActive(new PathCommand());
	}
}
