package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Font;
import java.net.URL;
import java.util.Arrays;

import com.skiba.fun.memscraper.Mem.MemInterface;
import com.skiba.fun.memscraper.Mem.MemObject;
import com.skiba.fun.memscraper.Mem.MemObjectImage;
import com.skiba.fun.memscraper.Mem.MemObjectVideo;

public class MemPanel extends JPanel {
	private int panelWidth = 700;

	private JTextArea titleTextArea;
	private JLabel tagLabel;
	private JLabel bottomSeparatorLabel, topSeparatorLabel;
	private JPanel infoPanel;

	public MemPanel(MemObject mem) {	
		initializeLayout();	
		initializeMemInformationHeader(mem);	
		addMemToView(mem);
		setSize(panelWidth, getMinimumSize().height);
		validate();
	}
	
	private void initializeLayout() {
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
	}

	private void initializeMemInformationHeader(MemObject mem) {
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		if(SharedData.getInstance().isDebugMode())infoPanel.setBackground(Color.ORANGE);
		else infoPanel.setBackground(Color.DARK_GRAY);
		addInformationComponents(mem);	
		infoPanel.setMaximumSize(infoPanel.getMinimumSize());
		add(infoPanel, BorderLayout.NORTH);
	}

	private void addInformationComponents(MemObject mem) {
		addTopSpacingSeparator();	
		addTitle(mem);
		addTags(mem);
		addBottomSeparator();
	}

	private void addTopSpacingSeparator() {
		topSeparatorLabel = new JLabel(" ");
		topSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		topSeparatorLabel.setOpaque(true);
		if(SharedData.getInstance().isDebugMode())topSeparatorLabel.setBackground(Color.GREEN);
		else topSeparatorLabel.setBackground(Color.DARK_GRAY);
		infoPanel.add(topSeparatorLabel);
	}

	private void addTitle(MemObject mem) {
		String title =  mem.getTitle() + " [" + mem.getRating() + "]";//"<html>" + mem.getTitle() + " [" + mem.getRating() + "]" +"</html>";
		titleTextArea = new JTextArea(title);
		titleTextArea.setAlignmentX(LEFT_ALIGNMENT);
		titleTextArea.setLineWrap(true);
		titleTextArea.setWrapStyleWord(true);
		titleTextArea.setForeground(Color.red);
		titleTextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		titleTextArea.setOpaque(true);
		if(SharedData.getInstance().isDebugMode())titleTextArea.setBackground(Color.BLUE);
		else titleTextArea.setBackground(Color.DARK_GRAY);
		titleTextArea.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
		infoPanel.add(titleTextArea);
	}
	
	private void addTags(MemObject mem) {
		tagLabel = new JLabel(Arrays.toString(mem.getTags()));
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		if(SharedData.getInstance().isDebugMode()) {
			tagLabel.setOpaque(true);
			tagLabel.setBackground(Color.PINK);
		}else tagLabel.setBackground(Color.DARK_GRAY);
		tagLabel.setForeground(Color.red);
		infoPanel.add(tagLabel);
	}

	private void addBottomSeparator() {
		bottomSeparatorLabel = new JLabel(" ");
		bottomSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		if(SharedData.getInstance().isDebugMode()) {
			bottomSeparatorLabel.setOpaque(true);
			bottomSeparatorLabel.setBackground(Color.GREEN);
		} else bottomSeparatorLabel.setBackground(Color.DARK_GRAY);
		infoPanel.add(bottomSeparatorLabel);
	}

	private void addMemToView(MemObject mem) {
		switch(mem.getType()) {
		case IMAGE:
			MemPanelImage imageMem = new MemPanelImage((MemObjectImage)mem);	
			add(imageMem, BorderLayout.CENTER);
			break;
		case VIDEO:
			MemPanelVideo videoMem = new MemPanelVideo((MemObjectVideo)mem);
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
