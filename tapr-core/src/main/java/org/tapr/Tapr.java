package org.tapr;

import org.tapr.internal.JavassistProxyFactory;
import org.tapr.internal.TapeSettingsImpl;

public class Tapr {

	private static final TapeProxyFactory proxyFactory = new JavassistProxyFactory();

	private Tapr() {
		// prevents instantiation
	}

	/**
	 * Creates a taped proxy-class for specified class or interface and
	 * instance.
	 * 
	 * @param classToTape
	 *            Class or interface to tape
	 * @param instance
	 *            Any concrete instance of specified class
	 * @return taped proxy class
	 * @throws Exception
	 *             in case anything fails to work with us ;-)
	 */
	public static <T> T tape(Class<T> classToTape, final T instance)
			throws Exception {
		return proxyFactory.create(classToTape, instance, withSettings());
	}

	/**
	 * Creates a taped proxy-class for specified class or interface and
	 * instance.
	 * 
	 * @param classToTape
	 *            Class or interface to tape
	 * @param instance
	 *            Any concrete instance of specified class
	 * @return taped proxy class
	 * @throws Exception
	 *             in case anything fails to work with us ;-)
	 */
	public static <T> T tape(Class<T> classToTape, final T instance,
			TapeSettings settings) throws Exception {
		return proxyFactory.create(classToTape, instance, settings);
	}

	/**
	 * Get the recording for specified taped object.
	 * 
	 * @param tapedObject
	 *            The object being taped
	 * @return the recording
	 */
	public static TapeRecording getRecording(Object tapedObject) {
		return proxyFactory.getRecording(tapedObject);
	}

	/**
	 * 
	 * Allows tape creation with additional settings.
	 * 
	 * @return tape settings instance with defaults
	 */
	@SuppressWarnings("rawtypes")
	public static TapeSettings withSettings() {
		return new TapeSettingsImpl();
	}
}
