package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.net.URL;
import java.util.Arrays;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;
import com.skiba.fun.memscraper.Mem.MemInterface;

public class MemPanel extends JPanel {

	private JLabel titleLabel;
	private JLabel tagLabel;
	private JLabel bottomSeparatorLabel, topSeparatorLabel;
	private JPanel infoPanel;

	public MemPanel(MemInterface mem) {	
		initializeLayout();	
		initializeMemInformationHeader(mem);	
		addMemToView(mem);
		
		setSize(700, getMinimumSize().height);
		validate();
	}
	
	private void initializeLayout() {
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
	}

	private void initializeMemInformationHeader(MemInterface mem) {
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.darkGray);
		addInformationComponents(mem);	
		add(infoPanel, BorderLayout.NORTH);
	}

	private void addInformationComponents(MemInterface mem) {
		addTopSpacingSeparator();	
		addTitle(mem);
		addTags(mem);
		addBottomSeparator();
	}

	private void addTopSpacingSeparator() {
		topSeparatorLabel = new JLabel(" ");
		topSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoPanel.add(topSeparatorLabel);
	}

	private void addTitle(MemInterface mem) {
		titleLabel = new JLabel(" " + mem.getTitle() + " [" + mem.getRating() + "]");
		titleLabel.setForeground(Color.red);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		infoPanel.add(titleLabel);
	}
	
	private void addTags(MemInterface mem) {
		tagLabel = new JLabel(Arrays.toString(mem.getTags()));
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tagLabel.setForeground(Color.red);
		infoPanel.add(tagLabel);
	}

	private void addBottomSeparator() {
		bottomSeparatorLabel = new JLabel(" ");
		bottomSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoPanel.add(bottomSeparatorLabel);
	}

	private void addMemToView(MemInterface mem) {
		switch(mem.getType()) {
		case IMAGE:
			MemPanelImage imageMem = new MemPanelImage(mem);
			add(imageMem, BorderLayout.CENTER);
			break;
		case VIDEO:
			MemPanelVideo videoMem = new MemPanelVideo(mem);
			add(videoMem, BorderLayout.CENTER);
			break;
		case UNDEFINED:
			addErrorMemToView();
			break;
		}		
	}

	private void addErrorMemToView() {
		URL url = getClass().getResource("/img/UndefinedProblem.png");
		System.out.println(url);
		ImageIcon errorIcon = new ImageIcon(url);
		MemPanelImage errorMem = new MemPanelImage(errorIcon);
		add(errorMem, BorderLayout.CENTER);
	}
	
}
