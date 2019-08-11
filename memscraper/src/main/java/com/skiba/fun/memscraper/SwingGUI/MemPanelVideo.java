package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

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

public class MemPanelVideo extends JPanel implements Runnable{
	private MemObject mem;
	private JButton play;
	private JButton stop;
	private JButton restart;
	private JButton mute;
	private JPanel mediaControllPanel;
	private JPanel videoPlayerPanel;
	private JFXPanel videoPanel;
	private MediaPlayer player;
	
	public MemPanelVideo(MemObject mem) {
		this.mem = mem;
		setBackground(Color.DARK_GRAY);
		Thread videoProcesor = new Thread(this);
		videoProcesor.start();
	}
	
	public void run() {
        initializeVideoPlayer();
        add(videoPlayerPanel, BorderLayout.CENTER);
	}

	private void initializeVideoPlayer() {
		videoPlayerPanel = new JPanel();
        videoPlayerPanel.setLayout(new BorderLayout());
        videoPlayerPanel.setBackground(Color.DARK_GRAY);     
        initializeVideoPanel();
        videoPlayerPanel.add(videoPanel, BorderLayout.CENTER);
        initializeMediaControllPanel(player);   
        videoPlayerPanel.add(mediaControllPanel, BorderLayout.SOUTH);
	}

	private void initializeVideoPanel() {
		String source = mem.getContentURL();
        videoPanel = new JFXPanel();
        videoPanel.setAlignmentX(RIGHT_ALIGNMENT);
        Media media = new Media(source);
        player = new MediaPlayer(media);
        MediaView view = new MediaView(player);    
        Group g = new Group(view);
        Scene s = new Scene(g, mem.getVideoSize().width, mem.getVideoSize().height);
        videoPanel.setScene(s);
	}

	private void initializeMediaControllPanel(MediaPlayer player) {
		mediaControllPanel = new JPanel();
        mediaControllPanel.setLayout(new BorderLayout());    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        setAlignmentX(CENTER_ALIGNMENT);
        play = new JButton("Play");
        play.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(play);
        stop = new JButton("Stop");
        buttonPanel.add(stop);    
        restart = new JButton("Resart");
        buttonPanel.add(restart);   
        mute = new JButton("Mute");
        buttonPanel.add(mute);      
        addButtonMediaPlayerControllsActions(player);    
        mediaControllPanel.add(buttonPanel, BorderLayout.CENTER);
	}

	private void addButtonMediaPlayerControllsActions(MediaPlayer player) {
		play.addActionListener((ActionEvent e) -> player.play());			
        stop.addActionListener((ActionEvent e) -> player.pause());
        restart.addActionListener((ActionEvent e) -> player.seek(Duration.ZERO));
        mute.addActionListener((ActionEvent e) ->player.setMute(!player.isMute()));
	}
}
