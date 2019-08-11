package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Arrays;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


public class MemPanel extends JPanel {

	private JLabel titleLabel;
	private JLabel tagLabel;
	private JLabel imageDisplayLabel;
	private JLabel bottomSeparatorLabel, topSeparatorLabel;

	public MemPanel(MemObject mem) {
		
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.darkGray);
		
		topSeparatorLabel = new JLabel(" ");
		topSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoPanel.add(topSeparatorLabel);
		
		titleLabel = new JLabel(" " + mem.getTitle() + " [" + mem.getRating() + "]");
		titleLabel.setForeground(Color.red);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		infoPanel.add(titleLabel);
		
		tagLabel = new JLabel(" " + Arrays.toString(mem.getTags()));
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		tagLabel.setForeground(Color.red);
		infoPanel.add(tagLabel);
		
		bottomSeparatorLabel = new JLabel(" ");
		bottomSeparatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoPanel.add(bottomSeparatorLabel);
		
		add(infoPanel, BorderLayout.NORTH);
		
		
		switch(mem.getType()) {
		case IMAGE:
			MemPanelImage imageMem = new MemPanelImage(mem);
			add(imageMem, BorderLayout.CENTER);
			break;
		case VIDEO:
			MemPanelVideo videoMem = new MemPanelVideo(mem);
			add(videoMem, BorderLayout.CENTER);
		}
		setSize(700, getMinimumSize().height);
		validate();
	}
	
}
