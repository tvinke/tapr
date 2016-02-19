package org.tapr.internal;

import java.util.ArrayList;
import java.util.List;

import org.tapr.TapeSettings;
import org.tapr.internal.debugging.VerboseTapeInvocationLogger;
import org.tapr.listeners.InvocationListener;

public class TapeSettingsImpl<T> implements TapeSettings {

	private static final long serialVersionUID = -2861156147776921893L;

	protected String name;
	protected List<InvocationListener> invocationListeners = new ArrayList<InvocationListener>();

	@Override
	public TapeSettings name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public TapeSettings verboseLogging() {
		if (!invocationListenersContainsType(VerboseTapeInvocationLogger.class)) {
			invocationListeners(new VerboseTapeInvocationLogger());
		}
		return this;
	}

	public TapeSettings invocationListeners(InvocationListener... listeners) {
		if (listeners == null || listeners.length == 0) {
			throw new IllegalArgumentException(
					"Invocation listeners need at least one listener.");
		}
		for (InvocationListener listener : listeners) {
			if (listener == null) {
				throw new IllegalArgumentException(
						"Invocation listener can not be null.");
			}
			this.invocationListeners.add(listener);
		}
		return this;
	}

	private boolean invocationListenersContainsType(Class<?> clazz) {
		for (InvocationListener listener : invocationListeners) {
			if (listener.getClass().equals(clazz)) {
				return true;
			}
		}
		return false;
	}

	public List<InvocationListener> getInvocationListeners() {
		return this.invocationListeners;
	}

	public boolean hasInvocationListeners() {
		return !invocationListeners.isEmpty();
	}

}
