package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class MainPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8438576029794021570L;
	/**
	 * Create the panel.
	 * 
	 * @param startWindow
	 */
	private JTextArea codeArea;

	public MainPanel(final StartWindow startWindow) {
		super(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		addButtons(toolBar);
		add(toolBar, BorderLayout.PAGE_START);
		add(addCodeArea(), BorderLayout.CENTER);
	}

	private JScrollPane addCodeArea() {
		codeArea = new JTextArea();
		return new JScrollPane(codeArea);
	}

	protected void addButtons(JToolBar toolBar) {
		JButton button = null;

		// first button
		button = makeNavigationButton("Back24", "Previous",
				"Back to previous something-or-other", "Previous");
		toolBar.add(button, BorderLayout.PAGE_START);

		// second button
		button = makeNavigationButton("Up24", "UP", "Up to something-or-other",
				"Up");
		toolBar.add(button);

		// similar code for creating and adding the third button...
	}

	protected JButton makeNavigationButton(String imageName,
			String actionCommand, String toolTipText, String altText) {
		// Look for the image.
		String imgLocation = "img/" + imageName + ".gif";

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
		button.addActionListener(this);

		button.setIcon(new ImageIcon(imgLocation, altText));

		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
