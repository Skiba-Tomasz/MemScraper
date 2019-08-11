package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

public class MemPanelImage extends JPanel{
	private JLabel imageDisplayLabel;
	
	public MemPanelImage(MemObject mem) {
		initializeLayout();
		imageDisplayLabel = setImageToLabelView(mem.getImage());
		add(imageDisplayLabel, BorderLayout.CENTER);
	}

	public MemPanelImage(ImageIcon icon) {
		initializeLayout();
		imageDisplayLabel = setImageToLabelView(icon);
		add(imageDisplayLabel, BorderLayout.CENTER);
	}
	
	private void initializeLayout() {
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
	}
	
	private JLabel setImageToLabelView(ImageIcon image) {
		JLabel label = new JLabel(image, JLabel.CENTER);
		label.setSize(new Dimension(getWidth(), label.getMinimumSize().height));
		return label;
	}
}
