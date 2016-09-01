import static org.junit.Assert.*;
import java.awt.Color;
import org.junit.Test;
/**
 * TokenContainerTest class
 * 
 * JUnit tests for the TokenContainer class
 * 
 * @author cbros
 *
 */
public class TestTokenContainer {

	@Test public void testRowOutOfBounds() {
		try {
			@SuppressWarnings("unused")
			TokenContainer test = new TokenContainer(6, 4, Color.BLACK);
			fail("Illegal row");
		} catch (IndexOutOfBoundsException e) {
			// do nothing, it's supposed to throw the exception
		}
	}
	
	@Test public void testColumnOutOfBounds() {
		try {
			@SuppressWarnings("unused")
			TokenContainer test = new TokenContainer(2, 8, Color.BLACK);
			fail("Illegal column");
		} catch (IndexOutOfBoundsException e) {
			// do nothing, it's supposed to throw the exception
		}
	}

	@Test public void testConstructorValidColor() {
		TokenContainer test = new TokenContainer(5, 4, Color.RED);
		assertEquals(test.getColor(), Color.RED);
	}

	@Test public void testConstructorNullColor() {
		try {
			@SuppressWarnings("unused")
			TokenContainer test = new TokenContainer(5, 4, null);
			fail("Null color");
		} catch (IllegalArgumentException e) {
			// do nothing, it's supposed to throw the exception
		}
	}
	
	@Test public void testConstructorInvalidColor() {
		try {
			@SuppressWarnings("unused")
			TokenContainer test = new TokenContainer(5, 4, Color.BLACK);
			fail("Invalid color");
		} catch (IllegalArgumentException e) {
			// do nothing, it's supposed to throw the exception
		}
	}

	@Test public void testSetValidColor() {
		TokenContainer test = new TokenContainer(5, 4, Color.WHITE);
		assertEquals(test.getColor(), Color.WHITE);
		test.setColor(Color.RED);
		assertEquals(test.getColor(), Color.RED);
	}

	@Test public void testSetNullColor() {
		try {
			TokenContainer test = new TokenContainer(5, 4, Color.WHITE);
			test.setColor(null);
			fail("Null color");
		} catch (IllegalArgumentException e) {
			// do nothing, it's supposed to throw the exception
		}
	}
	
	@Test public void testSetInvalidColor() {
		try {
			TokenContainer test = new TokenContainer(5, 4, Color.GREEN);
			assertEquals(test.getColor(), Color.GREEN);
			test.setColor(Color.CYAN);
			fail("Invalid color");
		} catch (IllegalArgumentException e) {
			// do nothing, it's supposed to throw the exception
		}
	}
}
