package org.tapr.invocation;

import java.lang.reflect.Method;

public interface TapedMethod {

	public String getName();

    public Class<?> getReturnType();

    public Class<?>[] getParameterTypes();

    public Class<?>[] getExceptionTypes();

    public Method getJavaMethod();
}
