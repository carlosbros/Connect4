import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class HighScores extends JFrame {
	private static final long serialVersionUID = 7742101581012456604L;
	
	// text area with the high scores
	private static JTextPane pane;
	
	// constructor
	public HighScores() {
		super("HIGH SCORES");
		addNewTextPane(this);
	}

	public static void addNewTextPane(final JFrame frame) {
		if (frame == null) {
			throw new IllegalArgumentException("Null frame. Can't add Pane.");
		}
		
		pane = new JTextPane();
		pane.setBackground(Color.BLUE);

		// add styles to the JTextPane and center the text
		Instructions.addTitleStyle(pane);
		Instructions.addTextStyle(pane);
		Instructions.centerText(pane);

		// read from the file
		try {
			// FileReader to read the file
			FileReader fileReader = new FileReader("highscores.txt");

			// wrap FileReader in a BufferedReader
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while(bufferedReader.ready()) {
				String next = bufferedReader.readLine();
				append(next);
			}

			bufferedReader.close();
			fileReader.close();

			// change the style of the text
			pane.getStyledDocument().setCharacterAttributes(0,
					12, pane.getStyle("title"), true);
			pane.getStyledDocument().setCharacterAttributes(12,
					pane.getText().length(), pane.getStyle("text"), true);            
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file");                
		}
		catch(IOException e1) {
			System.out.println("Error reading file");      
		}

		pane.setEditable(false);
		frame.add(pane);
	}

	// method to append a String to a JTextPane
	public static void append(String s) {
		if (s == null) {
			throw new IllegalArgumentException("Can't append null String");
		}
		
		try {
			Document doc = pane.getDocument();
			doc.insertString(doc.getLength(), "\n" + s, null);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
	}
}
