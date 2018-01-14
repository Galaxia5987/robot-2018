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
