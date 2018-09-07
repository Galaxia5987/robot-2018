package auxiliary;

/** Interface that must be implemented for a subsystem to be monitored by Watch_Doge.
 */
public interface Watch_Dogeable {
    /**
     * When Watch_Doge smells something wrong, he/she/zhe (we're inclusive here) needs a way to express "such disgusting wow"
     * (e.g. disable the the subsystem. This method must be overloaded to complete any necessary disabling
     * tasks (disable motors, etc.).
     */
	public void bork();

    /**
     * Method Watch_Doge calls to "revive" (re-enable) a subsystem after being borked once in a previous iteration.
     * Must be overloaded to run any tasks necessary to begin life once again.
     */
	public void necromancy();

    /**
     * Even though Watch_Doge may seem aggressive with a bork capable of disabling subsystems, he/she/zhe
     * will always gently ask the subsystem if it is willing to go back to the realm of the living.
     * Must be overloaded to return true to tell Watch_Doge if he/she/zhe can perform necromancy, false if the subsystem
     * wants to remain dead for another iteration.
     * 
     * This function has sparked many wars, between the 'inside' and 'before_you_go_go' factions.
     * The battle has been a brutal one, and there seems to be no end in sight.
     *
     * @return boolean indicating if subsystem is ready to be re-enabled via necromancy (true is ready)
     */
	public boolean wakeMeUp();

    /**
     * Watch_Doge also must be able to poke the subsystem with a stick to check if it is alive. Overload to return if the
     * subsystem is currently enabled or disabled.
     *
     * @return boolean indicating if subsystem is enabled or disabled (true if disabled)
     */
	public boolean ded();
}