package org.tapr.spock

import groovy.json.JsonOutput

import org.tapr.TapeRecording;

class JsonFormatter {

	private TapeRecording recording
	
	JsonFormatter(TapeRecording recording) {
		this.recording = recording
	}
	
	String format() {
		def methods = []
		recording.methods.eachWithIndex { method, index ->
			methods << [ 
				index: index,
				method: method.method.toString(), 
				arguments: addProperties(method.arguments),
				result: addProperties(method.result)
			]
		}
		JsonOutput.toJson(methods)
	}
	
	private List addProperties(Collection collection) {
		List values = []
		collection.eachWithIndex { elem, index ->
			def value = elem.toString()
			values << [
				index: index, 
				value: value, 
				properties: addProperties(elem)]
		}
		return values
	} 
	
	private List addProperties(def object) {
		List values = []
		object.properties.each { k, v ->
			final def value
			if (v == null) {
				value = null
			} else if (k == "class") {
				value = v.getName()
			} else if (isSimpleType(v.class)) {
				value = v
			} else {
				value = v.properties*.toString()
			}
			values << [property: k, type: v.class.getName(), value: value,
				simpleType: isSimpleType(v.class)]
		}
		return values
	}
	
	public static boolean isSimpleType(Class<?> clazz) {
		return clazz == String.class ||
			clazz == Boolean.class ||
			clazz == Integer.class ||
			clazz == Character.class ||
			clazz == Byte.class ||
			clazz == Short.class ||
			clazz == Double.class ||
			clazz == Long.class ||
			clazz == Float.class
	}
}
