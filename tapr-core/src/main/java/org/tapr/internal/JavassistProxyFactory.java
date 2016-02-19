package org.tapr.internal;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

import org.tapr.Tape;
import org.tapr.TapeProxyFactory;
import org.tapr.TapeRecording;
import org.tapr.TapeSettings;

public class JavassistProxyFactory implements TapeProxyFactory {

	@Override
	public <T> T create(Class<T> classToTape, final T instance,
			final TapeSettings settings) throws Exception {
		ProxyFactory factory = new ProxyFactory();
		if (classToTape.isInterface()) {
			factory.setInterfaces(new Class<?>[] { classToTape, Tape.class });
		} else {
			factory.setSuperclass(classToTape);
			factory.setInterfaces(new Class<?>[] { Tape.class });
		}

		MethodHandler handler = new MethodHandler() {
			private TapeRecordingImpl recording = new TapeRecordingImpl(
					instance, settings);

			private final Method getRecording = Tape.class.getMethod(
					"getRecording", new Class<?>[0]);

			@Override
			public Object invoke(Object self, Method method, Method proceed,
					Object[] args) throws Throwable {
				if (method.equals(getRecording)) {
					return recording;
				} else {
					method.setAccessible(true);
					Object result = method.invoke(instance, args);
					recording.methodCalled(self, method, args, result);
					return result;
				}
			}
		};

		return classToTape.cast(factory.create(new Class<?>[0], new Object[0],
				handler));
	}

	@Override
	public TapeRecording getRecording(Object tape) {
		return ((Tape) tape).getRecording();
	}

}
