package org.tapr;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TapeRecording {

	/** Track methods. */
	private final List<TapedMethod> methods = new ArrayList<TapedMethod>();

	/** Are we recording? */
	private boolean recording = false;

	/**
	 * Combination of an invoked method, passed arguments and original result.
	 */
	public static class TapedMethod {
		private final Method method;
		private final List<Object> arguments;
		private final Object result;

		TapedMethod(Method method, List<Object> arguments, Object result) {
			super();
			this.method = method;
			this.arguments = arguments;
			this.result = result;
		}

		/**
		 * The invoked method.
		 * 
		 * @return the method, never null
		 */
		Method getMethod() {
			return method;
		}

		/**
		 * The recorded arguments passed to the method.
		 * 
		 * @return the arguments, never null
		 */
		List<Object> getArguments() {
			return arguments;
		}

		/**
		 * The original recorded result.
		 * 
		 * @return the result
		 */
		Object getResult() {
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TapedMethod [method=");
			builder.append(method);
			builder.append(", arguments=");
			builder.append(arguments);
			builder.append(", result=");
			builder.append(result);
			builder.append("]");
			return builder.toString();
		}

	}

	TapeRecording() {
	}

	void methodCalled(Method method, List<Object> arguments, Object result) {
		if (recording) {
			methods.add(new TapedMethod(method, arguments, result));
		}
	}

	/**
	 * Starts recording.
	 * 
	 * <p>
	 * Method calls are recorded.
	 */
	public void start() {
		this.recording = true;
	}

	/**
	 * Stops recording.
	 * 
	 * <p>
	 * Method calls are no longer recorded.
	 */
	public void stop() {
		this.recording = false;
	}

	/**
	 * Checks whether or not tape is recording.
	 * 
	 * @return <code>true</code> if recording, <code>false</code> otherwise
	 */
	public boolean isRecording() {
		return recording;
	}

	/**
	 * @param recording
	 *            the recording to set
	 */
	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	/**
	 * 
	 * Returns the recorded method calls.
	 * 
	 * @return the methods
	 */
	public List<TapedMethod> getMethods() {
		return Collections.unmodifiableList(methods);
	}

}