package gui;

import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MemoryPanel extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3809395347824126693L;
	private JTextField textField;
	private DecimalFormat decimalFormat;

	/**
	 * Create the panel.
	 * 
	 * @param frmMicro
	 * @param startWindow
	 */
	public MemoryPanel(final StartWindow startWindow, final JFrame frmMicro) {

		JPanel MainPanel = new JPanel();
		setViewportView(MainPanel);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale
				.getDefault());
		decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		MainPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		JLabel lblMemorySize = new JLabel("Memory Size");
		panel.add(lblMemorySize);
		textField = getNumberField();
		panel.add(textField);

		final JTextField numbers[] = new JTextField[startWindow.getCacheLvl()];
		for (int i = 1; i <= startWindow.getCacheLvl(); i++) {
			JPanel x = new JPanel();
			MainPanel.add(x);
			x.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
			JLabel lbl = new JLabel("Cache Level " + i + " Size");
			x.add(lbl);
			JTextField numfield = numbers[i - 1] = getNumberField();
			x.add(numfield);
		}

		JPanel panel_1 = new JPanel();
		MainPanel.add(panel_1);

		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cache[] = new int[startWindow.getCacheLvl()];
				for (int i = 0; i < cache.length; i++)
					cache[i] = Integer.parseInt(numbers[i].getText());
				startWindow.setCacheSizes(cache);
				startWindow.setMemorySize(Integer.parseInt(textField.getText()));
				frmMicro.remove(startWindow.getcurrentPanel());
//				startWindow.setcurrentPanel(new MemoryPanel(startWindow,
//						frmMicro));
				frmMicro.getContentPane().add(startWindow.getcurrentPanel());
				frmMicro.repaint();
				frmMicro.revalidate();
			}
		});
		panel_1.add(btnNewButton);
	}

	private JTextField getNumberField() {
		JTextField textField = new JFormattedTextField(decimalFormat);
		textField.setText("0");
		textField.setColumns(8);
		return textField;
	}

}
