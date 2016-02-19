package org.tapr.internal.invocation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.tapr.invocation.TapedInvocation;
import org.tapr.invocation.TapedMethod;

/**
 * Recorded method call on a taped object.
 * 
 * @author Ted Vinke
 *
 */
public class TapedInvocationImpl implements TapedInvocation, Serializable {

	private static final long serialVersionUID = 6980099776059264873L;

	private final int sequenceNumber;
	private final Object tapedObject;
	private final TapedMethod method;
	private final Object[] arguments;
	private final Object returnedObject;

	public TapedInvocationImpl(Object tapedObject, TapedMethod tapedMethod,
			Object[] args, Object returnedObject, int sequenceNumber) {
		this.tapedObject = tapedObject;
		this.method = tapedMethod;
		this.arguments = args;
		this.sequenceNumber = sequenceNumber;
		this.returnedObject = returnedObject;
	}

	@Override
	public Method getMethod() {
		return method.getJavaMethod();
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public Object getReturnedObject() {
		return returnedObject;
	}

	@Override
	public Class<?> getReturnType() {
		return method.getReturnType();
	}

	@Override
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !o.getClass().equals(this.getClass())) {
			return false;
		}

		TapedInvocationImpl other = (TapedInvocationImpl) o;

		return this.tapedObject.equals(other.tapedObject)
				&& this.method.equals(other.method)
				&& this.equalArguments(other.arguments);
	}

	private boolean equalArguments(Object[] arguments) {
		return Arrays.equals(arguments, this.arguments);
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("#");
		builder.append(sequenceNumber);
		builder.append(" [method=");
		builder.append(method.getJavaMethod());
		builder.append(", arguments=");
		builder.append(Arrays.asList(arguments));
		builder.append(", returnedObject=");
		builder.append(returnedObject);
		builder.append("]");
		return builder.toString();
	}
}
