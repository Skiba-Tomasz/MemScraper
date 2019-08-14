package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Component;
import java.awt.Dimension;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MemPanelVideoProcessor {
	private Media media;
	private MediaPlayer player;
	private MediaView mediaView;
	private Group group;
	private Scene scene;
	private JFXPanel videoPanel;
	private boolean running;
	private Thread mediaLoader;
	private Thread playerInitializer;
	private int threadRefreshRate = 200;
	private int maxConnectionTimeout = 10000;
	private String videoUrl;
	
	public MemPanelVideoProcessor(String videoUrl) {	
		this.videoUrl = videoUrl;
        videoPanel = new JFXPanel();
        videoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mediaLoader = initializeMediaLoaderThread();
		playerInitializer = initializePlayerLoaderThread();	
	}
	
	public void start() {
		running = true;
		if(media == null || player == null || mediaView == null || group == null)mediaLoader.start();
		if(scene == null)playerInitializer.start();
		else running = false;
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

	private Thread initializeMediaLoaderThread() {
		return new Thread() {
			public void run() {
				int totalSleepTime = 0;
				media = new Media(videoUrl);
				player = new MediaPlayer(media);
				mediaView = new MediaView(player);
				group = new Group(mediaView);
				while (group.getLayoutBounds().getWidth() <= 1) {
					try {
						Thread.sleep(threadRefreshRate);
						totalSleepTime += threadRefreshRate;
						if (totalSleepTime >= maxConnectionTimeout)
							interrupt();
					} catch (InterruptedException e) {
						System.out.println("Przerwano. Mo¿liwe ¿e pojawi³ siê problem z odpowiedzi¹ serwera...");
						e.printStackTrace();
					}
				}
				if (group.getLayoutBounds().getWidth() > 600) {
					double ratio = group.getLayoutBounds().getHeight() / group.getLayoutBounds().getWidth();
					double scale = 600 / group.getLayoutBounds().getWidth();
					group.setTranslateX((-(group.getLayoutBounds().getWidth()) / 2) + ((group.getLayoutBounds().getWidth() * scale) / 2));
					group.setTranslateY((-(group.getLayoutBounds().getHeight()) / 2) + ((group.getLayoutBounds().getHeight() * ratio) / 2));
					group.setScaleX(scale);
					group.setScaleY(ratio);
				}
			}
		};
	}
	
	private Thread initializePlayerLoaderThread() {
		return new Thread() {
			public void run() {
				while(mediaLoader.isAlive()) {
					try {
						System.out.println("WAITING FOR MEDIA");
						Thread.sleep(threadRefreshRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("FINISHING AND PLAY");
				scene = new Scene(group, group.getLayoutBounds().getWidth()*group.getScaleX(), group.getLayoutBounds().getHeight()*group.getScaleY());
				double width = group.getLayoutBounds().getWidth()*group.getScaleX();
				double height = group.getLayoutBounds().getHeight()*group.getScaleY();
		        videoPanel.setMaximumSize(new Dimension((int)(width),(int)(height)));
				videoPanel.setScene(scene);
				running = false;
			}
		};
	}

}
