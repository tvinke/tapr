package org.tapr;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

import java.lang.reflect.Method;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

public class TapeFactory {

	private TapeFactory() {
		// prevents instantiation
	}

	/**
	 * Creates a proxy-class for specified class and instance and starts taping.
	 * 
	 * <p>
	 * Any recordings on this class can be retrieved with
	 * {@link #getRecording(Object)}.
	 * 
	 * @param clazz
	 *            The class name, albeit concrete class or interface
	 * @param instance
	 *            Any concrete instance of specified class
	 * @return taped proxy class
	 * @throws Exception
	 *             in case anything fails to work with us ;-)
	 */
	public static <T> T create(Class<T> clazz, final T instance) throws Exception {
		ProxyFactory factory = new ProxyFactory();
		if (clazz.isInterface()) {
			factory.setInterfaces(new Class<?>[] { clazz, Tape.class });
		} else {
			factory.setSuperclass(clazz);
			factory.setInterfaces(new Class<?>[] { Tape.class });
		}

		MethodHandler handler = new MethodHandler() {
			private TapeRecording recording = new TapeRecording();
			private final Method getRecording = Tape.class.getMethod("getRecording", new Class<?>[0]);

			@Override
			public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
				if (method.equals(getRecording)) {
					return recording;
				} else {
					List<Object> arguments = Arrays.asList(args);
					method.setAccessible(true);
					Object result = method.invoke(instance, args);
					recording.methodCalled(method, arguments, result);
					return result;
				}
			}
		};

		return clazz.cast(factory.create(new Class<?>[0], new Object[0], handler));
	}

	/**
	 * Get the recording for specified taped object.
	 * 
	 * @param tape
	 *            The object being taped
	 * @return the recording
	 */
	public static TapeRecording getRecording(Object tape) {
		return ((Tape) tape).getRecording();
	}

}
