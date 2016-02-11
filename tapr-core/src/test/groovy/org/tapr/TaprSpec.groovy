package org.tapr

import spock.lang.Specification

/**
 * @author Ted Vinke
 *
 */
class TaprSpec extends Specification {

	def "should work"() {
		expect:
		new Tape()
	}
}
