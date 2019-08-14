package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.Mem.MemObjectImage;

public class MemPanelImage extends JPanel{
	private JLabel imageDisplayLabel;
	
	public MemPanelImage(MemObjectImage mem) {
		initializeLayout();
		if(SharedData.getInstance().isDebugMode())setBackground(Color.CYAN);
		imageDisplayLabel = setImageToLabelView(mem.getMemImage());
		add(imageDisplayLabel, BorderLayout.CENTER);
		setMaximumSize(getMinimumSize());
		revalidate();
		repaint();
	}

	public MemPanelImage(ImageIcon icon) {
		initializeLayout();
		imageDisplayLabel = setImageToLabelView(icon);
		if(SharedData.getInstance().isDebugMode()) {
			imageDisplayLabel.setOpaque(true);
			imageDisplayLabel.setBackground(Color.RED);
		}
		imageDisplayLabel.setMaximumSize(imageDisplayLabel.getMinimumSize());
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
