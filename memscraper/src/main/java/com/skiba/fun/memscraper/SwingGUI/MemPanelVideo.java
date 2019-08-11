package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MemPanelVideo extends JPanel{

	public MemPanelVideo(MemObject mem) {
		Thread videoInitializer = new Thread() {
			public void run() {
				setBackground(Color.DARK_GRAY);
		        String url = mem.getContentURL();
		        JFXPanel jfxPanel = new JFXPanel();
		        jfxPanel.setAlignmentX(RIGHT_ALIGNMENT);

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
		        
		        JPanel buttonWrapperPanel = new JPanel();
		        
		        buttonWrapperPanel.setLayout(new BorderLayout());
		        
		        JPanel buttonPanel = new JPanel();
		        buttonPanel.setBackground(Color.DARK_GRAY);
		        setAlignmentX(CENTER_ALIGNMENT);
		        
		        JButton play = new JButton("Play");
		        play.setAlignmentX(CENTER_ALIGNMENT);
		        buttonPanel.add(play);
		        play.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						player.play();
					}
				});
		        
		        JButton stop = new JButton("Stop");
		        buttonPanel.add(stop);
		        stop.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						player.pause();
					}
				});
		        
		        JButton restart = new JButton("Resart");
		        buttonPanel.add(restart);
		        restart.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						player.seek(Duration.ZERO);
					}
				});
		        
		        JButton mute = new JButton("Mute");
		        buttonPanel.add(mute);
		        mute.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						player.setMute(!player.isMute());
					}
				});
		        buttonWrapperPanel.add(buttonPanel, BorderLayout.CENTER);
		        videoPanel.add(buttonWrapperPanel, BorderLayout.SOUTH);
		        add(videoPanel, BorderLayout.CENTER);
			}
		};
		videoInitializer.start();
	}
	
	
}
