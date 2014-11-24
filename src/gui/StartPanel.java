package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242215161741533193L;
	private JTextField textField, time;

	/**
	 * Create the panel.
	 * 
	 * @param startWindow
	 * @param frmMicro
	 */
	public StartPanel(final StartWindow startWindow) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale
				.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);

		JPanel panelCacheTechnique = new JPanel();
		add(panelCacheTechnique);

		JLabel lblAccessTime = new JLabel("Access Time");
		panelCacheTechnique.add(lblAccessTime);

		time = new JFormattedTextField(decimalFormat);
		time.setText("1");
		time.setColumns(8);
		panelCacheTechnique.add(time);

		JPanel panelCacheLevels = new JPanel();
		add(panelCacheLevels);

		JLabel label_1 = new JLabel("Number of Caching Levels");
		panelCacheLevels.add(label_1);
		textField = new JFormattedTextField(decimalFormat);
		textField.setText("1");
		textField.setColumns(8); // whatever size you wish to set
		panelCacheLevels.add(textField);

		JPanel panel = new JPanel();
		add(panel);

		JButton button = new JButton("Next");
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startWindow.setCacheLvl(Integer.parseInt(textField.getText()));
				startWindow.setMemAccessTime(Integer.parseInt(time.getText()));
				startWindow.changeCurrentPanel(new MemoryPanel(startWindow));
			}
		});

	}
}
