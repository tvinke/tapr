package org.tapr;

import java.util.List;

import org.tapr.invocation.TapedInvocation;

/**
 * Represents a tape recording of a taped object.
 * 
 * @author Ted Vinke
 *
 */
public interface TapeRecording {

	/**
	 * Starts recording.
	 * 
	 * <p>
	 * Method calls are recorded.
	 */
	public abstract void start();

	/**
	 * Clears any earlier recording and restarts recording.
	 */
	public abstract void restart();

	/**
	 * Stops recording.
	 * 
	 * <p>
	 * Method calls are no longer recorded.
	 */
	public abstract void stop();

	/**
	 * Checks whether or not tape is recording.
	 * 
	 * @return <code>true</code> if recording, <code>false</code> otherwise
	 */
	public boolean isRecording();

	/**
	 * @param recording
	 *            the recording to set
	 */
	public void setRecording(boolean recording);

	/**
	 * 
	 * Returns an unmodifiable view of the recorded method calls.
	 * 
	 * @return the methods
	 */
	public List<TapedInvocation> getMethods();

}