package org.tapr.spock

import static org.tapr.spock.Tapr.*
import org.tapr.invocation.TapedInvocation;

import spock.lang.IgnoreRest;
import spock.lang.Specification

class SpockFormatterSpec extends Specification {
	
	Publisher publisher = new Publisher()
		
	void "should send messages to all subscribers"() {
		
		given:
		Subscriber subscriber = Mock()
		Subscriber subscriber2 = Mock()
		
		and:
		publisher.subscribers << subscriber
        publisher.subscribers << subscriber2
		
		when:
		publisher.send("hello")
		
		then:
		1 * subscriber.receive("hello")
		1 * subscriber2.receive("hello")	
	}
	
	void "should format simple interaction"() {
		
		given:
		Subscriber subscriber = Tape("subscriber", Subscriber, new SubscriberImpl())
		Subscriber subscriber2 = Tape("subscriber2", Subscriber, new SubscriberImpl())
		
		and:
		publisher.subscribers << subscriber
		publisher.subscribers << subscriber2
		
		when:
		publisher.send("hello")
		
		then:
		def recording = subscriber.recording
		def methods = recording.methods
		println "${methods.size()} methods"
		for (TapedInvocation method : methods) {
			println(method);
		}
		
		and:
		def f = new SpockFormatter()
		f.format(subscriber.recording) == '1 * subscriber.receive("hello")'
		f.format(subscriber2.recording) == '1 * subscriber2.receive("hello")'
	}
	
	void "should format combined simple interactions"() {
		
		given:
		Subscriber subscriber = Tape("subscriber", Subscriber, new SubscriberImpl())
		Subscriber subscriber2 = Tape("subscriber2", Subscriber, new SubscriberImpl())
		
		when: "invoke some methods on multiple objects"
		subscriber2.receive("first")
		subscriber.receive("second")
		subscriber2.receive("third")
				
		then:
		def expected = new StringBuilder()
		expected << '1 * subscriber2.receive("first")\n'
		expected << '1 * subscriber.receive("second")\n'
		expected << '1 * subscriber2.receive("third")'
		
		def f = new SpockFormatter()
		f.format(subscriber.recording, subscriber2.recording) == expected.toString()
		
	}
}
class Publisher {
	List<Subscriber> subscribers = []
	void send(String message) { subscribers*.receive(message) }
}

interface Subscriber {
	void receive(String message)
}
class SubscriberImpl implements Subscriber {

	@Override
	public void receive(String message) {
		println "${this} received $message"
	}
	
}