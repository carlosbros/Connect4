import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.junit.Test;
/**
 * GameTest class
 * 
 * JUnit tests for the Game class
 * 
 * @author cbros
 *
 */
public class TestGame {
	
	@Test public void testChangePlayer() {
		assertTrue(Game.isP1());
		Game.changePlayer();
		assertFalse(Game.isP1());
		Game.changePlayer();
		assertTrue(Game.isP1());
	}
	
	@Test public void testCheckHighScoresOnEmptyFile() {
		String filename = "HighScoresTest.txt";
		try {
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);
			
			// initial state of the text file
			if (reader.ready()) {
				assertEquals("HIGH SCORES", reader.readLine());
			}
			int i = 1;
			while (reader.ready()) {
				assertEquals(i + ". ??? won in 0 moves", reader.readLine());
				i++;
			}
			reader.close();
			
			// check new high score, should change the file
			String newWinner = "Me";
			Game.checkHighScores(25, newWinner, filename);
			file = new FileReader(filename);
			reader = new BufferedReader(file);
			
			// new state of the file
			if (reader.ready()) {
				assertEquals("HIGH SCORES", reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("1. " + newWinner + " won in 25 moves", 
						     reader.readLine());
			}
			int j = 2;
			if (reader.ready()) {
				assertEquals(j + ". ??? won in 0 moves", reader.readLine());
				j++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test public void testCheckHighScoreBetterThanPreviousOne() {
		String filename = "HighScoresTest.txt";
		try {
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);
			
			// check new high score, should change the file
			String newWinner = "Hello";
			Game.checkHighScores(10, newWinner, filename);
			
			// new state of the file
			if (reader.ready()) {
				assertEquals("HIGH SCORES", reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("1. " + newWinner + " won in 10 moves", 
						     reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("2. Me won in 25 moves", reader.readLine());
			}
			int j = 3;
			if (reader.ready()) {
				assertEquals(j + ". ??? won in 0 moves", reader.readLine());
				j++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test public void testCheckHighScoresAddToEndOfList() {
		String filename = "HighScoresTest.txt";
		try {
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);
			
			// check new high score, should change the file
			String newWinner = "HighScorer";
			Game.checkHighScores(27, newWinner, filename);
			
			// new state of the file
			if (reader.ready()) {
				assertEquals("HIGH SCORES", reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("1. Hello won in 10 moves", 
						     reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("2. Me won in 25 moves", reader.readLine());
			}
			if (reader.ready()) {
				assertEquals("3. " + newWinner + " won in 27 moves", 
						     reader.readLine());
			}
			int j = 4;
			if (reader.ready()) {
				assertEquals(j + ". ??? won in 0 moves", reader.readLine());
				j++;
			}
			reader.close();
			
			// reset the file to what it was at the beginning, to 
			// be able to run tests more than once without having to 
			// change the file manually
			FileOutputStream stream = new FileOutputStream(filename);
			OutputStreamWriter writer = new OutputStreamWriter(stream);
			BufferedWriter out = new BufferedWriter(writer);
			
			String output = "HIGH SCORES\n";
			for (int k = 1; k < 11; k++) {
				output += k + ". ??? won in 0 moves\n";
			}
			
			out.write(output);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test public void testSetP1ToNull() {
		try {
			Game.setP1(null);
			fail("Set P1's nickname to null");
		} catch (IllegalArgumentException e) {
			// Do nothing, this is supposed to happen
		}
	}
	
	@Test public void testSetP2ToNull() {
		try {
			Game.setP2(null);
			fail("Set P2's nickname to null");
		} catch (IllegalArgumentException e) {
			// Do nothing, this is supposed to happen
		}
	}
}
