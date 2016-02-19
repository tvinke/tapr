package org.tapr;

import java.io.Serializable;

public interface TapeSettings extends Serializable {

	/**
	 * Gets the name of the taped object.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name of the taped object.
	 * 
	 * @param name
	 *            The name
	 * @return this settings instance
	 */
	public TapeSettings name(String name);

	/**
	 * Enables real-time logging of method invocations on this tape. Can be used
	 * during test debugging in order to find wrong interactions with this mock.
	 * <p>
	 * Invocations are logged as they happen to the standard output stream.
	 * <p>
	 * Calling this method multiple times makes no difference.
	 * <p>
	 * Example:
	 * 
	 * <pre class="code">
	 * <code class="java">
	 * SomeObject tapeWithLogger = tape(SomeObject.class, withSettings().verboseLogging());
	 * </code>
	 * </pre>
	 *
	 * @return this settings instance
	 */
	TapeSettings verboseLogging();
}
