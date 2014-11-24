package gui;

import javax.swing.JPanel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;

public class CacheHitPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8397953474964942021L;

	/**
	 * Create the panel.
	 */
	public CacheHitPanel(int cacheNo, int hit, int req) {

		setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("68px"),
				ColumnSpec.decode("125px"), FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("151px"), }, new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC, RowSpec.decode("15px"),
				RowSpec.decode("22px"), RowSpec.decode("15px"),
				FormFactory.UNRELATED_GAP_ROWSPEC, RowSpec.decode("15px"), }));

		JLabel lblCacheLevel = new JLabel("Cache Level " + cacheNo);
		add(lblCacheLevel, "2, 2, 3, 1, center, center");

		JLabel lblHits = new JLabel("Hits:");
		add(lblHits, "2, 4, right, center");

		JLabel lblHitsNo = new JLabel(hit + "");
		add(lblHitsNo, "4, 4, left, center");

		JLabel lblReq = new JLabel("Requests:");
		add(lblReq, "2, 6, right, center");

		JLabel lblReqNo = new JLabel(req + "");
		add(lblReqNo, "4, 6, left, center");

	}
}
