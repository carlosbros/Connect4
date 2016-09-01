import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NicknameAsker extends JFrame {
	private static final long serialVersionUID = 4966039422616255140L;
	
	public NicknameAsker(final String title) {
		super(title);
        
		// text where the user will enter his/her nickname
		final JTextField text = new JTextField();
		
		JButton submit = new JButton("Submit " + title);
		// add action listeners for the submit button
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = text.getText();
				if (title.contains("1")) {
					if (!newname.equals("")) {
						Game.setP1(text.getText());
					}
				}
				else if (title.contains("2")) {
					if (!newname.equals("")) {
						Game.setP2(text.getText());
					}
				}
				delete();
			}
		});

		// add components to the NicknameAsker
		this.add(text, BorderLayout.SOUTH);
		this.add(submit, BorderLayout.CENTER);
	}

	private void delete() {
		this.dispose();
	}
}
