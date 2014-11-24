package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import utilities.CacheDetailsHolder;

public class MemoryPanel extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3809395347824126693L;

	/**
	 * Create the panel.
	 * 
	 * @param frmMicro
	 * @param startWindow
	 */
	public MemoryPanel(final StartWindow startWindow) {

		JPanel MainPanel = new JPanel();
		setViewportView(MainPanel);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

		final CachePanel cachePanels[] = new CachePanel[startWindow
				.getCacheLvl()];
		for (int i = 1; i <= startWindow.getCacheLvl(); i++) {
			CachePanel cachePanel = cachePanels[i - 1] = new CachePanel(i);
			JPanel temp = new JPanel();
			temp.add(cachePanel);
			MainPanel.add(temp);
		}

		JPanel panel_1 = new JPanel();
		MainPanel.add(panel_1);

		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CacheDetailsHolder caches[] = new CacheDetailsHolder[startWindow
						.getCacheLvl()];
				for (int i = 0; i < caches.length; i++)
					caches[i] = cachePanels[i].getCache();
				startWindow.setCaches(caches);
//				startWindow.changeCurrentPanel(new MemoryPanel(startWindow));
			}
		});
		panel_1.add(btnNewButton);
	}

}
