package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import memory.Memory;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class RegistersTable extends JPanel {

	private static RegistersTable TABLE = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6551495407661797920L;
	/**
	 * Create the panel.
	 */
	private static JLabel[] fields = new JLabel[8];

	public static RegistersTable getInstance() {
		if (TABLE == null)
			TABLE = new RegistersTable();
		return TABLE;
	}

	public RegistersTable() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:20px"), ColumnSpec.decode("20px"),
				ColumnSpec.decode("left:80px"), }, new RowSpec[] {
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), RowSpec.decode("10px"),
				RowSpec.decode("20px"), }));

		JLabel lblR = new JLabel("R0");
		add(lblR, "1, 1");

		JLabel lblR_7 = new JLabel("R1");
		add(lblR_7, "1, 3");

		JLabel lblR_1 = new JLabel("R2");
		add(lblR_1, "1, 5");

		JLabel lblR_2 = new JLabel("R3");
		add(lblR_2, "1, 7");

		JLabel lblR_3 = new JLabel("R4");
		add(lblR_3, "1, 9");

		JLabel lblR_4 = new JLabel("R5");
		add(lblR_4, "1, 11");

		JLabel lblR_5 = new JLabel("R6");
		add(lblR_5, "1, 13");

		JLabel lblR_6 = new JLabel("R7");
		add(lblR_6, "1, 15");

		for (int i = 0; i < 8; i++) {
			fields[i] = new JLabel("0");
			add(fields[i], "3, " + (i * 2 + 1));
		}
	}

	public static void updateRegisters() {
		Memory mem = Memory.getInstance();
		for (int i = 0; i < 7; i++)
			fields[i].setText(mem.getRegisterValue("R" + i) + "");
		fields[7].setText(mem.getRegisterValue("PC") + "");
	}
}
