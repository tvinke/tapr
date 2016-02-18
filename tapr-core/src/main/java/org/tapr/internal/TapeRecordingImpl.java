package org.tapr.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.tapr.TapeRecording;
import org.tapr.internal.invocation.TapedInvocationImpl;
import org.tapr.invocation.TapedInvocation;
import org.tapr.invocation.TapedMethod;

public class TapeRecordingImpl implements TapeRecording {

	/** Count the amount of methods called globally. */
	private static final AtomicInteger sequenceCounter = new AtomicInteger();

	/** Track methods. */
	private final List<TapedInvocation> methods = new ArrayList<TapedInvocation>();

	/** Are we recording? */
	private boolean recording = false;

	TapeRecordingImpl() {
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

			TapedInvocation invocation = new TapedInvocationImpl(
					tapedObject, tapedMethod, arguments, result,
					sequenceCounter.incrementAndGet());
			methods.add(invocation);
		}
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#start()
	 */
	@Override
	public void start() {
		this.recording = true;
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#restart()
	 */
	@Override
	public void restart() {
		stop();
		this.methods.clear();
		start();
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#stop()
	 */
	@Override
	public void stop() {
		this.recording = false;
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#isRecording()
	 */
	@Override
	public boolean isRecording() {
		return recording;
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#setRecording(boolean)
	 */
	@Override
	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	/* (non-Javadoc)
	 * @see org.tapr.TapeRecording#getMethods()
	 */
	@Override
	public List<TapedInvocation> getMethods() {
		return Collections.unmodifiableList(methods);
	}

}