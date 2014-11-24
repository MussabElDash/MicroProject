package gui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.CacheDetailsHolder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

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

	private static DecimalFormat decimalFormat;

	public CachePanel(int i) {

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale
				.getDefault());
		decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("68px"),
				ColumnSpec.decode("125px"),
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("151px"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				RowSpec.decode("22px"),
				RowSpec.decode("15px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("15px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("24px"),
				FormFactory.NARROW_LINE_GAP_ROWSPEC,
				RowSpec.decode("24px"),}));

		JLabel lblCacheLevel = new JLabel("Cache Level " + i);
		add(lblCacheLevel, "2, 2, 3, 1, center, center");

		JLabel lblSize = new JLabel("Size:");
		add(lblSize, "2, 4, right, center");

		fieldSize = getNumberField();
		fieldSize.setText("1");
		fieldSize.setBounds(211, 47, 114, 19);
		add(fieldSize, "4, 4, left, center");
		fieldSize.setColumns(10);

		JLabel lblLineSize = new JLabel("Line Size:");
		add(lblLineSize, "2, 6, right, center");

		fieldLine = getNumberField();
		fieldLine.setText("1");
		fieldLine.setColumns(10);
		fieldLine.setBounds(211, 74, 114, 19);
		add(fieldLine, "4, 6, left, center");

		JLabel lblAss = new JLabel("Associativity:");
		add(lblAss, "2, 8, right, center");

		fieldAssoc = getNumberField();
		fieldAssoc.setText("1");
		fieldAssoc.setColumns(10);
		fieldAssoc.setBounds(211, 101, 114, 19);
		add(fieldAssoc, "4, 8, left, center");

		JLabel label = new JLabel("Access Time:");
		add(label, "2, 10, right, center");

		fieldAccess = getNumberField();
		fieldAccess.setText("1");
		fieldAccess.setColumns(10);
		fieldAccess.setBounds(211, 128, 114, 19);
		add(fieldAccess, "4, 10, left, center");

		JLabel lblNewLabel = new JLabel("Write hit policy:");
		add(lblNewLabel, "2, 12, right, center");

		comboBack = new JComboBox<String>();
		comboBack.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Write Back", "Write Through" }));
		comboBack.setSelectedIndex(0);
		add(comboBack, "4, 12, left, center");

		JLabel label_1 = new JLabel("Write miss policy:");
		add(label_1, "2, 14, left, center");

		comboAlloc = new JComboBox<String>();
		comboAlloc.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Write Allocate", "No Write Allocate" }));
		comboAlloc.setSelectedIndex(0);
		add(comboAlloc, "4, 14, left, center");

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

	public int getWriteAlloc() {
		return comboAlloc.getSelectedIndex();
	}

	public CacheDetailsHolder getCache() {
		boolean isWriteBack = getWriteBack() == 0;
		boolean isWriteAllocate = getWriteAlloc() == 0;
		CacheDetailsHolder cache = new CacheDetailsHolder(getMemSize(), getLineSize(), getAssoc(),
				isWriteBack, isWriteAllocate, getAccessTime());
		return cache;
	}

	private static JTextField getNumberField() {
		JTextField textField = new JFormattedTextField(decimalFormat);
		textField.setText("0");
		textField.setColumns(8);
		return textField;
	}
}