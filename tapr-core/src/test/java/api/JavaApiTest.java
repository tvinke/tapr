package api;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.tapr.*;
import org.tapr.invocation.TapedInvocation;


/**
 * Test the {@link Tapr} API from Java.
 * 
 * <p>This is a jUnit test verifying the proper API is public and usable. That's why this test class
 * is in some "api" package and not in "org.tapr".
 * 
 * @author Ted Vinke
 *
 */
public class JavaApiTest {
	
    @Test 
    public void createAndRecordSomeMethods() throws Exception {
    	
    	JavaCat cat2 = new JavaCat();
    	cat2.setName("Pebbles");
    	assertThat(cat2.getName(), is("Pebbles"));
    	
    	// given
    	JavaCat cat = Tapr.tape(JavaCat.class, new JavaCat("Pebbles"));
       	
    	TapeRecording recording = Tapr.getRecording(cat);
    	
    	// start recording
    	recording.start();
    	assertThat("we are recording", recording.isRecording(), is(true));
    	
    	// invoke a method
    	cat.getName();
		cat.setName("Giggles");
		cat.setAge(2);
		String greeting = cat.greeting();
		assertThat(greeting, is("Hello, Giggles!"));
    	
    	// stop recording
    	recording.stop();
    	assertThat("we stopped recording", recording.isRecording(), is(false));
    	cat.setAge(3); // will not be recorded
    	
    	List<TapedInvocation> methods = recording.getMethods();
    	for (TapedInvocation method : methods) {
    		System.out.println(method);
    	}
    	
    	assertThat(methods.size(), is(4));
    	// ugly String comparison here should be removed as soon as possible ;-)
    	assertThat(methods.get(0).toString(), is("#1 [method=public java.lang.String api.JavaCat.getName(), arguments=[], returnedObject=Pebbles]"));
    	assertThat(methods.get(1).toString(), is("#2 [method=public void api.JavaCat.setName(java.lang.String), arguments=[Giggles], returnedObject=null]"));
    	assertThat(methods.get(2).toString(), is("#3 [method=public void api.JavaCat.setAge(int), arguments=[2], returnedObject=null]"));
    	assertThat(methods.get(3).toString(), is("#4 [method=public java.lang.String api.JavaCat.greeting(), arguments=[], returnedObject=Hello, Giggles!]"));
    	
    }
}