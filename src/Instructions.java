import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Instructions class
 * 
 * Instructions will show the game instructions
 * when the instructions button is pressed during the
 * game
 * 
 * @author cbros
 *
 */
public class Instructions extends JFrame {
	private static final long serialVersionUID = 274292313334079340L;
	
	// text pane in the instructions
	private JTextPane text;

	// text in the instructions text pane
	private static final String title = "\nCONNECT FOUR\n\n";
	private static final String instr = 
			"Also known as Captain's Mistress, Four Up, \nPlot Four, " +
	        "Find Four, Fourplay, Four in a Row, and Four in " +
			"a Line, Connect4 is a two-player connection game in which " + 
	        "the players take turns dropping\n colored discs from the " + 
			"top into\n a seven-column, six-row vertically suspended \n" + 
	        "grid. The pieces fall straight down, occupying\n the next " +
	        "available space \nwithin the column. The objective of the " + 
	        "game is to connect\n four of one's own discs of the same\n " + 
	        "color next to each other vertically, horizontally, or " + 
	        "diagonally before your opponent. \n" +
	        "\nClick on any legal empty space on the board to add a token.\n" +
	        "A legal empty space is a white cell that has no white\n cells" +
	        " below it, or that is located in the bottom row." + 
			"\n\nHave fun!";
	
	// constructor
	public Instructions() {
		super("INSTRUCTIONS");
		
		text = new JTextPane();
		text.setBackground(Color.BLUE);
		
		// add styles to the JTextPane and center the text
		addTitleStyle(text);
		addTextStyle(text);
		centerText(text);
		
		text.setText(title + instr);
		
		text.getStyledDocument().setCharacterAttributes(0,
				title.length(), text.getStyle("title"), true);
		
		text.getStyledDocument().setCharacterAttributes(15,
				instr.length(), text.getStyle("text"), true);
		
		text.setEditable(false);
		this.add(text);
	}
	
	// method that centers the text of a JTextPane
	public static void centerText(JTextPane text) {
		checkNull(text);
		StyledDocument doc = text.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}
	
	// method that adds a style for the text in a JTextPane
	public static void addTitleStyle(JTextPane text) {
		checkNull(text);
		Style style = text.addStyle("title", null);

	    StyleConstants.setForeground(style, Color.RED);
	    StyleConstants.setBackground(style, Color.BLUE);
	    StyleConstants.setFontSize(style, 24);
	    StyleConstants.setBold(style, true);
	}
	
	// method that adds a style for the text in a JTextPane
	public static void addTextStyle(JTextPane text) {
		checkNull(text);
		Style style = text.addStyle("text", null);
		
		StyleConstants.setForeground(style, Color.GREEN);
	    StyleConstants.setBackground(style, Color.BLUE);
	    StyleConstants.setFontSize(style, 16);
	}
	
	// helper method to check for null JTextPanes
	private static void checkNull(JTextPane text) {
		if (text == null) {
			throw new IllegalArgumentException("Null Text Pane.");
		} 
	}
}
