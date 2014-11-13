package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JTable;

public class StartWindow {

	private JFrame frmMicro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMicro = new JFrame();
		frmMicro.setTitle("Micro\n");
		frmMicro.setBounds(100, 100, 700, 900);
		frmMicro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMicro.getContentPane().setLayout(null);
	}
}
