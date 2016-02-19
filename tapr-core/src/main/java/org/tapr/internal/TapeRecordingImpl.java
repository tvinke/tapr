package org.tapr.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.tapr.TapeRecording;
import org.tapr.TapeSettings;
import org.tapr.internal.invocation.TapedInvocationImpl;
import org.tapr.invocation.TapedInvocation;
import org.tapr.invocation.TapedMethod;

public class TapeRecordingImpl implements TapeRecording {

	/** Count the amount of methods called globally. */
	private static final AtomicInteger sequenceCounter = new AtomicInteger();

	private final Object tapedObject;
	private final TapeSettings settings;
	private final List<TapedInvocation> methods = new ArrayList<TapedInvocation>();
	private boolean recording = true;

	TapeRecordingImpl(Object tapedObject, TapeSettings settings) {
		this.tapedObject = tapedObject;
		this.settings = settings;
	}

	void methodCalled(Object tapedObject, final Method method,
			Object[] arguments, Object result) {
		if (recording) {

			TapedMethod tapedMethod = new TapedMethod() {

				@Override
				public String getName() {
					return method.getName();
				}

				@Override
				public Class<?> getReturnType() {
					return method.getReturnType();
				}

				@Override
				public Class<?>[] getParameterTypes() {
					return method.getParameterTypes();
				}

				@Override
				public Class<?>[] getExceptionTypes() {
					return method.getExceptionTypes();
				}

				@Override
				public Method getJavaMethod() {
					return method;
				}

			};

			TapedInvocation invocation = new TapedInvocationImpl(tapedObject,
					tapedMethod, arguments, result,
					sequenceCounter.incrementAndGet());
			methods.add(invocation);
		}
	}

	@Override
	public String getName() {
		if (settings.getName() != null
				&& !settings.getName().trim().equalsIgnoreCase("")) {
			return settings.getName();
		} else {
			return tapedObject.toString();
		}
	}

	@Override
	public void start() {
		this.recording = true;
	}

	@Override
	public void restart() {
		stop();
		this.methods.clear();
		start();
	}

	@Override
	public void stop() {
		this.recording = false;
	}

	@Override
	public boolean isRecording() {
		return recording;
	}

	@Override
	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	@Override
	public List<TapedInvocation> getMethods() {
		return Collections.unmodifiableList(methods);
	}

}