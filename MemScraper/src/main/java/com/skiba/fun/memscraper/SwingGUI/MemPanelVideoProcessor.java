package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import jmapps.ui.VideoPanel;

public class MemPanelVideoProcessor {
	Media media;
	MediaPlayer player;
	MediaView mediaView;
	Group group;
	Scene scene;
	JFXPanel videoPanel;
	private boolean running;
	
	public MemPanelVideoProcessor(String videoUrl) {
		System.out.println("PLAY");
		running = true;
        videoPanel = new JFXPanel();
        videoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		System.out.println("SOURCE " + videoUrl);
		Thread mediaLoader = new Thread() {
			public void run() {
				System.out.println("MEDIALOADER");
				media = new Media(videoUrl);
				player = new MediaPlayer(media);
		        mediaView = new MediaView(player);    
		        group = new Group(mediaView);
		        while(group.getLayoutBounds().getWidth()<=1) {
					try {
						System.out.println("MEDIALOADER WAIT");
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		        if(group.getLayoutBounds().getWidth()> 600) {
		        	System.out.println("GROUP");
		        	double ratio = group.getLayoutBounds().getHeight()/group.getLayoutBounds().getWidth();
		        	double scale = 600/group.getLayoutBounds().getWidth();
		        	group.setTranslateX((-(group.getLayoutBounds().getWidth())/2)+((group.getLayoutBounds().getWidth()*scale)/2));
		        	group.setTranslateY((-(group.getLayoutBounds().getHeight())/2)+((group.getLayoutBounds().getHeight()*ratio)/2));
		        	group.setScaleX(scale);
		        	group.setScaleY(ratio);
		        	
		        } 
			}
		};
		if(media == null || player == null || mediaView == null || group == null)mediaLoader.start();
		Thread playerInitializer = new Thread() {
			public void run() {
				while(mediaLoader.isAlive()) {
					try {
						System.out.println("WAITING FOR MEDIA");
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("FINISHING AND PLAY");
				scene = new Scene(group, group.getLayoutBounds().getWidth()*group.getScaleX(), group.getLayoutBounds().getHeight()*group.getScaleY()/*, mem.getVideoSize().width, mem.getVideoSize().height*/);			
		        videoPanel.setMaximumSize(new Dimension((int)(group.getLayoutBounds().getWidth()*group.getScaleX()),(int)(group.getLayoutBounds().getHeight()*group.getScaleY())));
				videoPanel.setScene(scene);
				running = false;
				//player.play();
			}
		};
		if(scene == null)playerInitializer.start();
		else running = false;
		//else player.play();
	}

	public boolean isReady() {
		return !running;
	}
	
	public MediaPlayer getPlayer() {
		return player;
	}



	public JFXPanel getVideoJFXPanel() {
		return videoPanel;
	}
}
