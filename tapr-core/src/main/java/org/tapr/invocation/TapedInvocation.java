package org.tapr.invocation;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Method call on a taped object.
 * 
 * @author Ted Vinke
 *
 */
public interface TapedInvocation {

	/**
	 * The invoked method.
	 * 
	 * @return the method, never null
	 */
	public Method getMethod();

	/**
	 * The recorded arguments passed to the method.
	 * 
	 * @return the arguments, never null
	 */
	Object[] getArguments();

	/**
	 * The original recorded result.
	 * 
	 * @return the result
	 */
	Object getReturnedObject();
	
	/**
	 * Returns the type of the method.
	 * @return type
	 */
	Class<?> getReturnType();
}
