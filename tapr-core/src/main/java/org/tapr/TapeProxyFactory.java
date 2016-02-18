package org.tapr;

public interface TapeProxyFactory {

	/**
	 * Creates a proxy-class for specified class and instance and starts taping.
	 * 
	 * <p>
	 * Any recordings on this class can be retrieved with
	 * {@link #getRecording(Object)}.
	 * 
	 * @param classToTape
	 *            The class name, albeit concrete class or interface
	 * @param instance
	 *            Any concrete instance of specified class
	 * @param settings
	 *            Any tape settings
	 * @return taped proxy class
	 * @throws Exception
	 *             in case anything fails to work with us ;-)
	 */
	<T> T create(Class<T> classToTape, final T instance, TapeSettings settings)
			throws Exception;

	/**
	 * Get the recording for specified taped object.
	 * 
	 * @param tapedProxy
	 *            The object being taped
	 * @return the recording
	 */
	TapeRecording getRecording(Object tapedProxy);
}