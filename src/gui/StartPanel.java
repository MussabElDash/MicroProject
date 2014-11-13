package gui;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTextField;

public class StartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242215161741533193L;
	private JTextField textField;

	/**
	 * Create the panel.
	 * 
	 * @param startWindow
	 * @param frmMicro
	 */
	public StartPanel(final StartWindow startWindow, final JFrame frmMicro) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panelCacheTechnique = new JPanel();
		add(panelCacheTechnique);

		JLabel label = new JLabel("Caching Technique");
		panelCacheTechnique.add(label);

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Write Back", "Write Allocate", "Write Through",
						"Write Around" }));
		comboBox.setSelectedIndex(0);
		panelCacheTechnique.add(comboBox);

		JPanel panelCacheLevels = new JPanel();
		add(panelCacheLevels);

		JLabel label_1 = new JLabel("Number of Caching Levels");
		panelCacheLevels.add(label_1);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		textField = new JFormattedTextField(decimalFormat);
		textField.setText("1");
		textField.setColumns(8); //whatever size you wish to set
		panelCacheLevels.add(textField);
		
		JPanel panel = new JPanel();
		add(panel);
		
				JButton button = new JButton("Next");
				panel.add(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						startWindow.setCacheLvl(Integer.parseInt(textField.getText()));
						startWindow.setCacheTech(comboBox.getSelectedIndex());
						frmMicro.remove(startWindow.getcurrentPanel());
						startWindow.setcurrentPanel(new MemoryPanel(startWindow,
								frmMicro));
						frmMicro.getContentPane().add(startWindow.getcurrentPanel());
						frmMicro.repaint();
						frmMicro.revalidate();
					}
				});

	}
}
