package api;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.tapr.TapeFactory;
import org.tapr.TapeRecording;
import org.tapr.TapeRecording.TapedMethod;

import java.util.List;


/**
 * Test the {@link TaprFactory} API from Java.
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
    	JavaCat cat = TapeFactory.create(JavaCat.class, new JavaCat());
       	
    	TapeRecording recording = TapeFactory.getRecording(cat);
    	
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
    	
    	List<TapedMethod> methods = recording.getMethods();
    	for (TapedMethod method : methods) {
    		System.out.println(method);
    	}
    	
    	assertThat(methods.size(), is(4));
    	// ugly String comparison here should be removed as soon as possible ;-)
    	assertThat(methods.get(0).toString(), is("TapedMethod [method=public java.lang.String api.JavaCat.getName(), arguments=[], result=null]"));
    	assertThat(methods.get(1).toString(), is("TapedMethod [method=public void api.JavaCat.setName(java.lang.String), arguments=[Giggles], result=null]"));
    	assertThat(methods.get(2).toString(), is("TapedMethod [method=public void api.JavaCat.setAge(int), arguments=[2], result=null]"));
    	assertThat(methods.get(3).toString(), is("TapedMethod [method=public java.lang.String api.JavaCat.greeting(), arguments=[], result=Hello, Giggles!]"));
    	
    }
}