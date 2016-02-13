package api;
import org.junit.Test;
import org.tapr.TapeFactory;
import org.tapr.TapeRecording;
import org.tapr.TapeRecording.TapedMethod;

import static org.junit.Assert.*;

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
    	assertEquals("cat should be named Pebbles", "Pebbles", cat2.getName());
    	
    	// given
    	JavaCat cat = TapeFactory.create(JavaCat.class, new JavaCat());
       	
    	TapeRecording recording = TapeFactory.getRecording(cat);
    	
    	// start recording
    	recording.start();
    	assertTrue("we are recording", recording.isRecording());
    	
    	// invoke a method
    	cat.getName();
		cat.setName("Giggles");
		cat.setAge(2);
		String greeting = cat.greeting();
		assertEquals("Hello, Giggles!", greeting);
    	
    	// stop recording
    	recording.stop();
    	assertFalse("we have stopped recording", recording.isRecording());
    	cat.setAge(3); // will not be recorded
    	
    	List<TapedMethod> methods = recording.getMethods();
    	for (TapedMethod method : methods) {
    		System.out.println(method);
    	}
    	
    	assertEquals(4, methods.size());
    	// ugly String comparison here should be removed as soon as possible ;-)
    	assertEquals("TapedMethod [method=public java.lang.String api.JavaCat.getName(), arguments=[], result=null]", methods.get(0).toString());
    	assertEquals("TapedMethod [method=public void api.JavaCat.setName(java.lang.String), arguments=[Giggles], result=null]", methods.get(1).toString());
    	assertEquals("TapedMethod [method=public void api.JavaCat.setAge(int), arguments=[2], result=null]", methods.get(2).toString());
    	assertEquals("TapedMethod [method=public java.lang.String api.JavaCat.greeting(), arguments=[], result=Hello, Giggles!]", methods.get(3).toString());
    }
}