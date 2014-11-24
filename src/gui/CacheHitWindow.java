package gui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import utilities.Pair;

public class CacheHitWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8295209798709734582L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public CacheHitWindow(ArrayList<Pair<Integer, Integer>> cachesHits) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		int x = cachesHits.size();
		for (int i = 1; i <= x; i++) {
			Pair<Integer, Integer> pair = cachesHits.get(i - 1);
			contentPane.add(new CacheHitPanel(i, pair.first, pair.second));
		}

		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);
		setVisible(true);
	}

}
