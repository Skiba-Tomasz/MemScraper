package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

public class MemPanelImage extends JPanel{
	
	public MemPanelImage(MemObject mem) {
		//JPanel imagePanel = new JPanel();
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		JLabel imageDisplayLabel = new JLabel(mem.getImage(), JLabel.CENTER);
		imageDisplayLabel.setBackground(Color.white);
		imageDisplayLabel.setSize(new Dimension(getWidth(), imageDisplayLabel.getMinimumSize().height));
		add(imageDisplayLabel, BorderLayout.CENTER);
		//add(imagePanel, BorderLayout.CENTER);
	}

}
