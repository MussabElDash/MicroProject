package gui;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class StartWindow {

	private JFrame frmMicro;
	private JComponent currentPanel;
	private int memAccessTime = 0, cacheLvl = 1, memorySize, CacheSizes[];

	public int getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(int memorySize) {
		this.memorySize = memorySize;
	}

	public int[] getCacheSizes() {
		return CacheSizes;
	}

	public void setCacheSizes(int[] cacheSizes) {
		CacheSizes = cacheSizes;
	}

	public JComponent getcurrentPanel() {
		return currentPanel;
	}

	public void setcurrentPanel(JComponent currentPanel) {
		this.currentPanel = currentPanel;
	}

	public int getCacheLvl() {
		return cacheLvl;
	}

	public void setCacheLvl(int cacheLvl) {
		this.cacheLvl = cacheLvl;
	}

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
		frmMicro.setTitle("Micro");
		frmMicro.setBounds(100, 100, 900, 600);
		frmMicro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMicro.setResizable(false);
		frmMicro.setVisible(true);
		currentPanel = new StartPanel(this, frmMicro);
		frmMicro.getContentPane().add(currentPanel);
	}

	public int getMemAccessTime() {
		return memAccessTime;
	}

	public void setMemAccessTime(int memAccessTime) {
		this.memAccessTime = memAccessTime;
	}
}
