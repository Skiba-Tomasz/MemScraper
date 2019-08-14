package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.Mem.MemObjectVideo;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

public class MemPanelVideo extends JPanel{
	
	private JPanel mediaControllPanel;
	private JPanel videoPlayerWraper;
	private JFXPanel videoPanel;
	private MediaPlayer player;
	private JLabel thumbnail;
	private MemPanelVideoProcessor processor;
	private MemPanelVideoControlls buttonPanel;
	private MemObjectVideo mem;
	private boolean firstTimePlay;

	public MemPanelVideo(MemObjectVideo mem) {
		this.mem = mem;
		firstTimePlay = true;
		setBackground(Color.DARK_GRAY);
		initializeVideoWraper();
        add(videoPlayerWraper, BorderLayout.CENTER);
	}

	private void initializeVideoWraper() {
		videoPlayerWraper = new JPanel();
        videoPlayerWraper.setLayout(new BorderLayout());
        videoPlayerWraper.setBackground(Color.DARK_GRAY);        
        initializeThumbnail();
		videoPlayerWraper.add(thumbnail, BorderLayout.CENTER);	
        initializeMediaControllPanel();   
        videoPlayerWraper.add(mediaControllPanel, BorderLayout.SOUTH);
		refresh(this);
	}
	
	private void initializeThumbnail() {
		thumbnail = new JLabel(mem.getThumbnail(), JLabel.CENTER);
		thumbnail.setPreferredSize(new Dimension(mem.getVideoSize().width, mem.getVideoSize().height));
		thumbnail.setMaximumSize(new Dimension(600, 600));
	}
	
	private void initializeMediaControllPanel() {
		mediaControllPanel = new JPanel();
        mediaControllPanel.setLayout(new BorderLayout());   
        buttonPanel = new MemPanelVideoControlls();
        addButtonMediaPlayerControllsActions();    
        mediaControllPanel.add(buttonPanel, BorderLayout.CENTER);
	}

	private void addButtonMediaPlayerControllsActions() {
		buttonPanel.load.addActionListener((ActionEvent e) -> 		loadPlayer());
		buttonPanel.play.addActionListener((ActionEvent e) -> 		play());			
		buttonPanel.stop.addActionListener((ActionEvent e) -> 		player.pause());
		buttonPanel.restart.addActionListener((ActionEvent e) -> 	player.seek(Duration.ZERO));
		buttonPanel.mute.addActionListener((ActionEvent e) ->		player.setMute(!player.isMute()));
	}
	
	private void loadPlayer() {
		Thread thread = new Thread() {
			public void run() {
				if(processor == null) {
					processor  = new MemPanelVideoProcessor(mem.getVideoUrl());
					processor.start();
					while(!processor.isReady()) {
						try {
							System.out.println("Waiting for processor");
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					videoPanel = processor.getVideoJFXPanel();
					buttonPanel.setControlsToView();
					player = processor.getPlayer();
				}
			}
		};
		thread.start();
	}
	
	private void play() {
		if(firstTimePlay) {
			setVideoPlayerToView();
			firstTimePlay = false;
		}
		player.play();
	}
	
	private void setVideoPlayerToView() {
		videoPlayerWraper.remove(thumbnail);
		videoPlayerWraper.add(videoPanel, BorderLayout.CENTER);
		refresh(videoPlayerWraper);
	}
	
	private void refresh(Component c) {
		c.revalidate();
		c.repaint();
	}
}
