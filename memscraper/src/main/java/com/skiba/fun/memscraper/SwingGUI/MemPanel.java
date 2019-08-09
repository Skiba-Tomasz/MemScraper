package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Arrays;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

public class MemPanel extends JPanel {

	private JLabel titleLabel;
	private JLabel tagLabel;
	private JLabel imageDisplayLabel;

	public MemPanel(MemObject mem) {
		setMinimumSize(new Dimension(650, 650));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		titleLabel = new JLabel(mem.getTitle());
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(titleLabel);
		
		tagLabel = new JLabel(Arrays.toString(mem.getTags()));
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		add(tagLabel);
		
		imageDisplayLabel = new JLabel("");
		imageDisplayLabel.setIcon(mem.getImage());
		add(imageDisplayLabel);
	}

}
