import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * PieceContainer class, extends JButton class
 * 
 * A PieceContainer is a single cell in a 
 * Connect4 game board. It can either be
 * empty, or contain a token
 * 
 * @author cbros
 *
 */
public class TokenContainer extends JButton {
	private static final long serialVersionUID = 2534969550384148345L;
	
	// width and height of the space inside the container that holds a token
	private static final int CIRCLE_WIDTH = 80;
	private static final int CIRCLE_HEIGHT = 80;
	
	// color of the token inside, white if it's empty
	private Color color;

	public TokenContainer(final int row, final int column, Color color) {
		super();
		
		// error checking
		if (row >= Game.ROWS || column >= Game.COLUMNS) {
			throw new IndexOutOfBoundsException("Invalid Row/Column index.");
		}
		checkInvalidColor(color);
		
		this.color = color;

		// when a TokenContainer is clicked, this action listener will put a 
		// token of the appropriate color in the appropriate container
		ActionListener actionlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton[][] cont = Game.getBoard();
				if (Game.hasMove()) {
					for (int i = row; i < cont.length - 1; i++) {
						TokenContainer current = (TokenContainer) 
								cont[i][column];
						TokenContainer next = (TokenContainer) 
								cont[i + 1][column];
						// check if next cell is not white, change color if so
						if (current.getColor().equals(Color.WHITE) && 
								!next.getColor().equals(Color.WHITE)) {
							if (Game.isP1()) {
								current.setColor(Color.RED);
							}
							else {current.setColor(Color.GREEN);}
							Graphics g = current.getGraphics();
							current.paintComponent(g);
							Game.changePlayer();
						    Game.numMoves++;
							Game.state.setText("PLAY!" + "  Moves: " + 
									Game.numMoves);
						}
					}
					// the above loop doesn't check clicks at the bottom row
					if (row == 5) {
						TokenContainer curr = 
								(TokenContainer)cont[row][column];
						if (curr.getColor().equals(Color.WHITE)) {
							if (Game.isP1()) {
								curr.setColor(Color.RED);
							}
							else {curr.setColor(Color.GREEN);}
							Graphics g = curr.getGraphics();
							curr.paintComponent(g);
							Game.changePlayer();
							Game.numMoves++;
							Game.state.setText("PLAY!" + "  Moves: " + 
									Game.numMoves);
						}
					}
					// on every move, we have to check for a win / tie
					Game.checkHorizontalWin();
					if (Game.hasMove()) {
						Game.checkVerticalWin();
					}
					if (Game.hasMove()) {
						Game.checkDiagonalWin();
					}
					if (Game.hasMove()) {
						Game.checkTie();
					}
				}
			}
		};
		this.addActionListener(actionlistener);
	}

	// getter for the color of the TokenContainer
	public Color getColor() {
		return color;
	}
	
	// setter for the color of the TokenContainer
	public void setColor(Color color) {
		checkInvalidColor(color);
		this.color = color;
	}
	
	// helper method to check for invalid colors
	private void checkInvalidColor(Color color) {
		if (color == null) {
			throw new IllegalArgumentException("Null color");
		}
		if (!color.equals(Color.RED) && 
			!color.equals(Color.GREEN) && 
		    !color.equals(Color.WHITE)) {
			throw new IllegalArgumentException("Invalid color");
		}
	}

	// make the current TokenContainer empty
	public void reset() {
		this.color = Color.WHITE;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(color);
		g.fillOval(10, 13, CIRCLE_WIDTH, CIRCLE_HEIGHT);
	}
}
