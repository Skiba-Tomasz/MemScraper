package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Color;

import javax.swing.JPanel;

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
		
		setSize(700, getMinimumSize().height);
		
		switch(mem.getType()) {
		case IMAGE:
			imageDisplayLabel = new JLabel("");
			imageDisplayLabel.setIcon(mem.getImage());
			//imageDisplayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(imageDisplayLabel);
			break;
		case VIDEO:
			getVideo(mem);
		}
		
	}
	
	public void getVideo(MemObject mem) {
         String url = mem.getContentURL();
         JFXPanel jfxPanel = new JFXPanel();
         JPanel videoPanel = new JPanel();
         videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.Y_AXIS));
         videoPanel.setBackground(Color.DARK_GRAY);
         videoPanel.setPreferredSize(mem.getVideoSize());
         videoPanel.setMaximumSize(mem.getVideoSize());

         Media media = new Media(url);
         MediaPlayer player = new MediaPlayer(media);
         MediaView view = new MediaView(player);
         Group g = new Group(view);
         Scene s = new Scene(g);         
         jfxPanel.setScene(s);
         videoPanel.add(jfxPanel);
         JButton play = new JButton("Play!");
         videoPanel.add(play);
         play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				player.seek(Duration.ZERO);
				player.play();
			}
		});
         videoPanel.validate();
         add(videoPanel);
	}
}
