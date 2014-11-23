package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CachePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384259585612057338L;

	/**
	 * Create the panel.
	 */
	private JTextField fieldSize, fieldLine, fieldAssoc, fieldAccess;
	private JComboBox<String> comboBack, comboAlloc;

	public CachePanel() {

	}

	public int getMemSize() {
		return Integer.parseInt(fieldSize.getText());
	}

	public int getLineSize() {
		return Integer.parseInt(fieldLine.getText());
	}

	public int getAssoc() {
		return Integer.parseInt(fieldAssoc.getText());
	}

	public int getAccessTime() {
		return Integer.parseInt(fieldAccess.getText());
	}

	public int getWriteBack() {
		return comboBack.getSelectedIndex();
	}
	
	public int getWriteAlloc(){
		return comboAlloc.getSelectedIndex();
	}

}
