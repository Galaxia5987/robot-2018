	/**
	 * Servo number one (1) for hook number two (2) on the climb subsystem.
	 */
	Servo servo1 = new Servo(RobotMap.servo1);
	
	/**
	 * Servo number two (2) for hook number two (2) on the climb subsystem.
	 */
	Servo servo2 = new Servo(RobotMap.servo2);
	
	/**
	 * Motor for climbing.
	 */
	Victor motor = new Victor(RobotMap.motor);
	
	/**
	 * Limit switch at the top that is used for affirmation if the robot has
	 * reached the top.
	 */
	DigitalInput limitSwitch = new DigitalInput(RobotMap.limitSwitch);

	/**
	 * Set the servo position.
	 *
	 * <p>
	 * Servo values range from 0.0 to 1.0 corresponding to the range of full
	 * left to full right.
	 *
	 * @param value
	 *            Position from 0.0 to 1.0.
	 */
	public void setServos(double position) {
		servo1.set(position);
		servo1.set(position);
	}

	/**
	 * Set the PWM value.
	 *
	 * <p>
	 * The PWM value is set using a range of -1.0 to 1.0, appropriately scaling
	 * the value for the FPGA.
	 *
	 * @param speed
	 *            The speed value between -1.0 and 1.0 to set.
	 */
	public void setClimbSpeed(double speed) {
		motor.set(speed);
	}
	
	/**
	   * Get the value from the limit switch to know whether the robot has reached the top.
	   *
	   * @return the status of the limit switch
	   */
	public boolean hasReachedTop()
	{
		return limitSwitch.get();
	}
