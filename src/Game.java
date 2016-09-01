import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
/**
 * Game class
 * 
 * In this class is where the game runs.
 * This class has most of the logic behind the
 * game, and has all the data structures that store
 * the different components of the game and board
 * 
 * @author cbros
 *
 */
public class Game implements Runnable {
	// cols of the board
	static final int COLUMNS = 7;
	static final int ROWS = 6;

	// width and height of the board
	private static final int WIDTH = 100 * COLUMNS;
	private static final int HEIGHT = 100 * ROWS;

	// game board JPanel
	private static JPanel board;
	// JButton 2D array for the grid
	private static JButton[][] container;

	// says what player is playing
	private static boolean isP1 = true;
	
	// names of the two players
	private static String p1 = "Player 1";
	private static String p2 = "Player 2";

	// boolean that is true when the game is still going on
	private static boolean hasMove;

	// current number of moves in the game
	public static int numMoves;
	
	// label for the state of the game
	public static final JLabel state = new JLabel("PLAY!" + "  Moves: " + 
			numMoves);

	// high scores window
	private static HighScores scores;

	@Override
	public void run() {
		numMoves = 0;
		hasMove = true;
		
		// main frame
		JFrame frame = new JFrame("CONNECT 4");
		frame.setLocation(300, 50);
		frame.setSize(WIDTH, HEIGHT + 100);

		// nickname asker frames
		final NicknameAsker asker1 = new NicknameAsker("Player 1's nickname");
		asker1.setLocation(350, 200);
		asker1.setSize(300, 150);
		final NicknameAsker asker2 = new NicknameAsker("Player 2's nickname");
		asker2.setLocation(650, 200);
		asker2.setSize(300, 150);
		
		// instructions frame
		final Instructions instr = new Instructions();
		instr.setSize(500, 500);
		instr.setLocation(420, 110);
		
		// high scores frame
		scores = new HighScores();
		scores.setSize(400, 300);
		scores.setLocation(460, 110);

		
		// create the top panel that will go into the main frame
		JPanel topPanel = new JPanel();

		// create the components of the top panel
		
		// instructions button
		JButton instructions = new JButton("Instructions");
		// instructions button functionality
		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instr.setVisible(true);
			}
		});
		instructions.setToolTipText("Show how to play this game");
		
		// high scores button
		JButton highScores = new JButton("High Scores");
		// high scores button functionality
		highScores.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// update the high scores window and show it
				HighScores.addNewTextPane(scores);
				scores.setVisible(true);
			}
		});
		highScores.setToolTipText("Show all-time high scores");
		
		// reset button
		JButton reset = new JButton("Reset");
		// reset button functionality
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		reset.setToolTipText("Start a new game");
		
		// add components to the top panel
		topPanel.add(instructions);
		topPanel.add(state);
		topPanel.add(highScores);
		topPanel.add(reset);
		// add top panel to frame
		frame.add(topPanel, BorderLayout.NORTH);
		
		
		// bottom panel that will contain buttons to change nicknames
		JPanel bottomPanel = new JPanel();
		
		// nickname buttons
		JButton changeNick1 = new JButton("Change " + p1 + "'s nickname");
		JButton changeNick2 = new JButton("Change " + p2 + "'s nickname");
		
		// action listeners for the bottom panel buttons
		ActionListener action1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asker1.setVisible(true);
			}
		};
		ActionListener action2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				asker2.setVisible(true);
			}
		};
		// add the action listeners
		changeNick1.addActionListener(action1);
		changeNick2.addActionListener(action2);
		
		// add the buttons to the bottom panel
		bottomPanel.add(changeNick1);
		bottomPanel.add(changeNick2);
		// add the bottom panel to the SOUTH of the JFrame
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		// board panel
		board = new JPanel(new GridLayout(ROWS, COLUMNS));
		board.setBackground(Color.BLUE);
		container = new TokenContainer[ROWS][COLUMNS];

		// fill in the grid
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[i].length; j++) {
				container[i][j] = new TokenContainer(i, j, Color.WHITE);
				board.add(container[i][j]);
			}
		}

		// add board to frame
		frame.add(board, BorderLayout.CENTER);

		// make the frame close when we click the red X
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// make NicknameAskers visible
		asker1.setVisible(true);
		asker2.setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

	// getter for the boolean that indicates whether p1 is playing
	public static boolean isP1() {
		return isP1;
	}

	// method that changes the player boolean, used by TokenContainer
	public static void changePlayer() {
		isP1 = !isP1;
	}
	
	// reset method for the reset button
	public static void reset() {
		hasMove = true;
		isP1 = true;
		numMoves = 0;
		
		// reset the grid
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[i].length; j++) {
				((TokenContainer)container[i][j]).reset();
			}
		}
		state.setText("PLAY!" + "  Moves: " + numMoves);
	}

	// method that checks if a player has won horizonally
	public static void checkHorizontalWin() {
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < 4; j++) {
				TokenContainer curr = (TokenContainer) container[i][j];
				TokenContainer next1 = (TokenContainer) container[i][j + 1];
				TokenContainer next2 = (TokenContainer) container[i][j + 2];
				TokenContainer next3 = (TokenContainer) container[i][j + 3];
				if (!curr.getColor().equals(Color.WHITE) && 
						curr.getColor().equals(next1.getColor()) &&
						next1.getColor().equals(next2.getColor()) &&
						next2.getColor().equals(next3.getColor())) {
					String winner = p1;
					if (curr.getColor().equals(Color.GREEN)) {winner = p2;}
					state.setText(winner + " won in " + numMoves + 
							" moves!");
					checkHighScores(numMoves, winner, "highscores.txt");
					hasMove = !hasMove;
					break;
				}
			}
		}
	}
	
	// method that checks if a player has won vertically
	public static void checkVerticalWin() {
		for (int j = 0; j < container.length; j++) {
			for (int i = 0; i < 3; i++) {
				TokenContainer curr = (TokenContainer) container[i][j];
				TokenContainer next1 = (TokenContainer) container[i + 1][j];
				TokenContainer next2 = (TokenContainer) container[i + 2][j];
				TokenContainer next3 = (TokenContainer) container[i + 3][j];
				if (!curr.getColor().equals(Color.WHITE) && 
						curr.getColor().equals(next1.getColor()) &&
						next1.getColor().equals(next2.getColor()) &&
						next2.getColor().equals(next3.getColor())) {
					String winner = p1;
					if (curr.getColor().equals(Color.GREEN)) {winner = p2;}
					state.setText(winner + " won in " + numMoves + 
							" moves!");
					checkHighScores(numMoves, winner, "highscores.txt");
					hasMove = !hasMove;
					break;
				}
			}
		}
	}

	// method that checks if a player has won diagonally
	public static void checkDiagonalWin() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < container[i].length; j++) {
				if (j < 3) {
					TokenContainer curr = (TokenContainer) 
							container[i][j];
					TokenContainer next1 = (TokenContainer) 
							container[i + 1][j + 1];
					TokenContainer next2 = (TokenContainer) 
							container[i + 2][j + 2];
					TokenContainer next3 = (TokenContainer) 
							container[i + 3][j + 3];
					Color currColor = curr.getColor();
					if (!currColor.equals(Color.WHITE) && 
							currColor.equals(next1.getColor()) &&
							next1.getColor().equals(next2.getColor()) &&
							next2.getColor().equals(next3.getColor())) {
						String winner = p1;
						if (currColor.equals(Color.GREEN)) {winner = p2;}
						state.setText(winner + " won in " + numMoves + 
								" moves!");
						checkHighScores(numMoves, winner, "highscores.txt");
						hasMove = !hasMove;
						break;
					}
				}
				else if (j == 3) {
					TokenContainer curr = (TokenContainer) 
							container[i][j];
					TokenContainer next1 = (TokenContainer) 
							container[i + 1][j + 1];
					TokenContainer next2 = (TokenContainer) 
							container[i + 2][j + 2];
					TokenContainer next3 = (TokenContainer) 
							container[i + 3][j + 3];
					TokenContainer prev1 = (TokenContainer) 
							container[i + 1][j - 1];
					TokenContainer prev2 = (TokenContainer) 
							container[i + 2][j - 2];
					TokenContainer prev3 = (TokenContainer) 
							container[i + 3][j - 3];
					Color currColor = curr.getColor();
					if (!currColor.equals(Color.WHITE) && 
							((currColor.equals(next1.getColor()) &&
							  next1.getColor().equals(next2.getColor()) &&
							  next2.getColor().equals(next3.getColor())) || 
						      (currColor.equals(prev1.getColor()) &&
							  prev1.getColor().equals(prev2.getColor()) &&
							  prev2.getColor().equals(prev3.getColor())))) {
						String winner = p1;
						if (currColor.equals(Color.GREEN)) {winner = p2;}
						state.setText(winner + " won in " + numMoves + 
								" moves!");
						checkHighScores(numMoves, winner, "highscores.txt");
					    hasMove = !hasMove;
						break;
					}
				}
				else if (j > 3) {
					TokenContainer curr = (TokenContainer) 
							container[i][j];
					TokenContainer prev1 = (TokenContainer) 
							container[i + 1][j - 1];
					TokenContainer prev2 = (TokenContainer) 
							container[i + 2][j - 2];
					TokenContainer prev3 = (TokenContainer) 
							container[i + 3][j - 3];
					Color currColor = curr.getColor();
					if (!currColor.equals(Color.WHITE) &&  
							currColor.equals(prev1.getColor()) &&
							prev1.getColor().equals(prev2.getColor()) &&
							prev2.getColor().equals(prev3.getColor())) {
						String winner = p1;
						if (currColor.equals(Color.GREEN)) {winner = p2;}
						state.setText(winner + " won in " + numMoves + 
								" moves!");
						checkHighScores(numMoves, winner, "highscores.txt");
						hasMove = !hasMove;
						break;
					}
				}
			}
		}
	}
	
	public static void checkTie() {
		for (int i = 0; i < container.length; i++) {
			for (int j = 0; j < container[i].length; j++) {
				TokenContainer next = ((TokenContainer)container[i][j]);
				if (next.getColor().equals(Color.WHITE)) {return;}
			}
		}
		state.setText("It's a tie! Press Reset to play again");
	}

	static void checkHighScores(int moves, String winner, 
			String filename) {
		FileReader fileReader = null;
		
		// output that we will write to the high scores txt file
		String output = "";
		
		try {
			fileReader = new FileReader(filename);
			BufferedReader reader = new BufferedReader(fileReader);
			
			// store the title of the file
		    String title = "";
			if (reader.ready()) {
				title = reader.readLine();
				output += title + "\n";
			}

			// read the rest and check for a high score
			while (reader.ready()) {
				String next = reader.readLine();
				
				Pattern p = Pattern.compile("(\\d+)(.*) won in (\\d+) moves");
				Matcher m = p.matcher(next);

				if (m.find()) {
					int match = Integer.parseInt(m.group(3));
					if (match == 0 || match >= moves) {
						int recordNum = Integer.parseInt(m.group(1));
						output += recordNum + ". " + winner + " won in " +
								moves + " moves\n";
						
						// update high score number
						recordNum++;
						if (recordNum > 10) {break;}
						
						String[] split = next.split("\\.");
						output += recordNum + "." + split[1] + "\n";
						
						// if we have found a high score, we're done, so
						// read the rest of the file and break
						while (reader.ready()) {
							recordNum++;
							if (recordNum > 10) {break;}
							
							String nextLine = reader.readLine();
							String[] trim = nextLine.split("\\.");

							output += recordNum + "." + trim[1] + "\n";
						}
						reader.close();
						fileReader.close();
						break;
					}
				}
				output += next + "\n";
			}
			reader.close();
			fileReader.close();
			
			// write the output to the high scores txt file
			FileOutputStream f = new FileOutputStream(filename);
			OutputStreamWriter w = new OutputStreamWriter(f);
			BufferedWriter out = new BufferedWriter(w);
			out.write(output);
			out.close();
			w.close();
			f.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// getter for hasMove, indicates whether there is a possible next move
	public static boolean hasMove() {
		return hasMove;
	}

	// getter for the board
	public static JButton[][] getBoard() {
		JButton[][] boardCopy = new JButton[ROWS][COLUMNS];
		for (int i = 0; i < boardCopy.length; i++) {
			for (int j = 0; j < boardCopy[i].length; j++) {
				boardCopy[i][j] = container[i][j];
			}
		}
		return boardCopy;
	}

	// setter for player 1 nickname
	public static void setP1(String newP1) {
		if (newP1 == null) throw new IllegalArgumentException("Illegal name");
		p1 = newP1;
	}
	
	// setter for player 2 nickname
	public static void setP2(String newP2) {
		if (newP2 == null) throw new IllegalArgumentException("Illegal name");
		p2 = newP2;
	}

	// getter for player 1 nickname
	public static String getP1() {
		return p1;
	}

	// getter for player 2 nickname
	public static String getP2() {
		return p2;
	}
}
