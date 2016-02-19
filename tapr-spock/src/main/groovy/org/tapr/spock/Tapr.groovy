package org.tapr.spock

import groovy.transform.CompileStatic;

import org.tapr.Tapr as API

class Tapr {
	
	static <T> T Tape(Class<T> classToTape, final T instance) {
		API.tape(classToTape, instance)
	}
	
	static <T> T Tape(String name, Class<T> classToTape, final T instance) {
		API.tape(name, classToTape, instance)
	}
}
