import static org.junit.Assert.*;
import org.junit.Test;
/**
 * HighScoresTest class
 * 
 * JUnit tests for the HighScores class.
 * This tests the class itself, but
 * because the implementation of I/O for
 * the High Scores is in the Game class,
 * more thorough tests are located in the
 * GameTest class of JUnit tests.
 * 
 * @author cbros
 *
 */
public class TestHighScores {

	@Test public void testAppendNullString() {
		try {
			HighScores.append(null);
			fail("Attempt to append a null String");
		} catch (IllegalArgumentException e) {
			// do nothing, exception expected
		}
	}
	
	@Test public void testAddNullPane() {
		try {
			HighScores.addNewTextPane(null);
			fail("Attempt to add a null Pane");
		} catch (IllegalArgumentException e) {
			// do nothing, exception expected
		}
	}
}
