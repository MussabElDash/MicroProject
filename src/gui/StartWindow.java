package gui;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;

import utilities.CacheDetailsHolder;

public class StartWindow {

	private JFrame frmMicro;

	private JComponent currentPanel;
	private int memAccessTime = 0, cacheLvl = 1, startAddress = 0;
	private CacheDetailsHolder[] caches;

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
					new StartWindow();
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
		currentPanel = new StartPanel(this);
		frmMicro.getContentPane().add(currentPanel);
		frmMicro.repaint();
		frmMicro.revalidate();
	}

	public int getMemAccessTime() {
		return memAccessTime;
	}

	public void setMemAccessTime(int memAccessTime) {
		this.memAccessTime = memAccessTime;
	}

	public void changeCurrentPanel(JComponent panel) {
		frmMicro.remove(currentPanel);
		currentPanel = panel;
		frmMicro.getContentPane().add(currentPanel);
		frmMicro.repaint();
		frmMicro.revalidate();
	}

	public CacheDetailsHolder[] getCaches() {
		return caches;
	}

	public void setCaches(CacheDetailsHolder[] caches) {
		this.caches = caches;
	}

	public int getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}
}
