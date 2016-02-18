package org.tapr

import org.tapr.internal.TapeRecordingImpl;

import spock.lang.Specification
import groovy.transform.ToString
import static org.tapr.Tapr.*

/**
 * @author Ted Vinke
 *
 */
class TaprSpec extends Specification {

	def "create and record some methods"() {
		
		given:
		Cat cat = tape(Cat.class, new Cat())
		TapeRecordingImpl recording = getRecording(cat)
		
		when:
		recording.start()
		cat.getName()
		cat.name = "Giggles"
		cat.age = 2
		String greeting = cat.greeting()
		recording.stop()
		
		then: "Cat is also a Tape"
		cat instanceof Tape
		println cat
		
		recording
		recording.methods
		recording.methods.each { tapedMethod ->
			println tapedMethod
		}
	}
}
@ToString
class Cat {
	
	String name
	int age
	
	String greeting() {
		"Hello, $name!"
	}
}