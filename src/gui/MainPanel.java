package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import assembler.Program;

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
	private JTextField startField;
	private static DecimalFormat decimalFormat;
	private StartWindow startWindow;

	public MainPanel(final StartWindow startWindow) {
		this.startWindow = startWindow;
		setLayout(new BorderLayout());
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale
				.getDefault());
		decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);

		JToolBar toolBar = new JToolBar();
		toolBar.setLayout(new BorderLayout());
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		addButtons(toolBar, startWindow);
		addStartAddress(toolBar);
		add(toolBar, BorderLayout.PAGE_START);
		add(addCodeArea(), BorderLayout.CENTER);
		add(RegistersTable.getInstance(), BorderLayout.WEST);
	}

	private void addStartAddress(JToolBar toolBar) {
		JPanel tempanel = new JPanel();
		tempanel.add(new JLabel("StartAdress:"));
		startField = getNumberField();
		tempanel.add(startField);
		toolBar.add(tempanel, BorderLayout.WEST);
	}

	private JScrollPane addCodeArea() {
		codeArea = new JTextArea();
		return new JScrollPane(codeArea);
	}

	protected void addButtons(JToolBar toolBar, StartWindow startWindow) {
		JButton button = null;

		button = makeNavigationButton("dukeWaveRed", "Run", "Execute the Code",
				"Run");
		button.addActionListener(this);
		toolBar.add(button, BorderLayout.EAST);

	}

	protected JButton makeNavigationButton(String imageName,
			String actionCommand, String toolTipText, String altText) {
		String imgLocation = "img/" + imageName + ".gif";

		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);

		button.setIcon(new ImageIcon(imgLocation, altText));

		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			final HashMap<Integer, Integer> editedAddr = new HashMap<Integer, Integer>();
			final int startAddress = Integer.parseInt(startField.getText());
			boolean done = false;
			while (!done) {
				int addr = getAddress();
				done = addr == -1;
				if (!done) {
					int value = getValue(addr);
					done = value == -1;
					editedAddr.put(addr, value);
				}
			}
			new Thread(new Runnable() {

				@Override
				public void run() {
					new Program(codeArea.getText(), startAddress,
							startWindow.getMemAccessTime(),
							startWindow.getCaches(), editedAddr);
				}
			}).start();
		} catch (Exception e1) {
		}
	}

	private int getAddress() {
		try {
			String strAddress = JOptionPane
					.showInputDialog("Enter the Address");
			if (strAddress == null || strAddress.isEmpty())
				return -1;
			return Integer.parseInt(strAddress);
		} catch (Exception e) {
			return getAddress();
		}
	}

	private int getValue(int Address) {
		try {
			String strAddress = JOptionPane
					.showInputDialog("Enter the Value of the Address "
							+ Address);
			if (strAddress == null || strAddress.isEmpty())
				return -1;
			return Integer.parseInt(strAddress);
		} catch (Exception e) {
			return getAddress();
		}
	}

	private static JTextField getNumberField() {
		JTextField textField = new JFormattedTextField(decimalFormat);
		textField.setText("0");
		textField.setColumns(8);
		return textField;
	}
}
