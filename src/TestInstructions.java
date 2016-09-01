import org.junit.Test;
import static org.junit.Assert.*;
/**
 * InstructionsTest class
 * 
 * JUnit tests for the Instructions class
 * 
 * @author cbros
 *
 */
public class TestInstructions {
	
	@Test public void testCenterNullText() {
		try {
			Instructions.centerText(null);
			fail("Null text - center");
		} catch (IllegalArgumentException e) {
			// do nothing, expected exception
		}
	}
	
	@Test public void testAddTitleStyleNullText() {
		try {
			Instructions.addTitleStyle(null);
			fail("Null text - title style");
		} catch (IllegalArgumentException e) {
			// do nothing, expected exception
		}
	}
	
	@Test public void testAddTextStyleNullText() {
		try {
			Instructions.addTextStyle(null);
			fail("Null text - text style");
		} catch (IllegalArgumentException e) {
			// do nothing, expected exception
		}
	}
}
