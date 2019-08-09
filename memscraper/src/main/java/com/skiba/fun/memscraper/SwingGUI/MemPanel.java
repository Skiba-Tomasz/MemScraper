package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Color;
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
	private JLabel bottomSeparatorLabel, topSeparatorLabel;

	public MemPanel(MemObject mem) {
		setMinimumSize(new Dimension(650, 650));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.DARK_GRAY);
		
		topSeparatorLabel = new JLabel(" ");
		topSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(topSeparatorLabel);
		
		titleLabel = new JLabel(" " + mem.getTitle() + " [" + mem.getRating() + "]");
		titleLabel.setForeground(Color.red);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(titleLabel);
		
		tagLabel = new JLabel(" " + Arrays.toString(mem.getTags()));
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tagLabel.setForeground(Color.red);
		add(tagLabel);
		
		bottomSeparatorLabel = new JLabel(" ");
		bottomSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(bottomSeparatorLabel);
		
		imageDisplayLabel = new JLabel("");
		if(mem.getImage() != null)imageDisplayLabel.setIcon(mem.getImage());
		else imageDisplayLabel.setText("WIDEO");
		add(imageDisplayLabel);

	}

}
