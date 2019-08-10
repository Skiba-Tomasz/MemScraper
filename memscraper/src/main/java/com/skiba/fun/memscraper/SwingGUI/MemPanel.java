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
			JPanel imagePanel = new JPanel();
			imagePanel.setLayout(new BorderLayout());
			imagePanel.setBackground(Color.DARK_GRAY);
			imageDisplayLabel = new JLabel(mem.getImage(), JLabel.CENTER);
			imageDisplayLabel.setBackground(Color.white);
			imageDisplayLabel.setSize(new Dimension(getWidth(), imageDisplayLabel.getMinimumSize().height));
			imagePanel.add(imageDisplayLabel, BorderLayout.CENTER);
			add(imagePanel, BorderLayout.CENTER);
			break;
		case VIDEO:
			getVideo(mem);
		}
		setSize(700, getMinimumSize().height);
		validate();
	}
	
	public void getVideo(MemObject mem) {
         String url = mem.getContentURL();
         JFXPanel jfxPanel = new JFXPanel();
         jfxPanel.setAlignmentX(RIGHT_ALIGNMENT);
         JPanel wraperPanel = new JPanel();
         wraperPanel.setLayout(new BorderLayout());
         JPanel videoPanel = new JPanel();
         videoPanel.setLayout(new BorderLayout());
         videoPanel.setBackground(Color.DARK_GRAY);

         Media media = new Media(url);
         MediaPlayer player = new MediaPlayer(media);
         MediaView view = new MediaView(player);
         
         Group g = new Group(view);
         Scene s = new Scene(g, mem.getVideoSize().width, mem.getVideoSize().height);
         jfxPanel.setScene(s);

         videoPanel.add(jfxPanel, BorderLayout.CENTER);
         JButton play = new JButton("Play!");
         videoPanel.add(play, BorderLayout.SOUTH);
         play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.seek(Duration.ZERO);
				player.play();
			}
		});
         wraperPanel.add(videoPanel, BorderLayout.CENTER);
         add(wraperPanel, BorderLayout.CENTER);
	}
}
