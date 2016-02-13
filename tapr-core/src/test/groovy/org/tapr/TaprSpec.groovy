package org.tapr

import spock.lang.Specification
import groovy.transform.ToString
import org.tapr.TapeFactory

/**
 * @author Ted Vinke
 *
 */
class TaprSpec extends Specification {

	def "create and record some methods"() {
		
		given:
		Cat cat = TapeFactory.create(Cat.class, new Cat())
		TapeRecording recording = TapeFactory.getRecording(cat)
		
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