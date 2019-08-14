package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObjectJBZD;
import com.skiba.fun.memscraper.Mem.MemObjectVideo;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MemPanelVideo extends JPanel implements Runnable{
	private MemObjectVideo mem;
	private JButton play;
	private JButton stop;
	private JButton restart;
	private JButton mute;
	private JPanel mediaControllPanel;
	private JPanel videoPlayerPanel;
	private JFXPanel videoPanel;
	private MediaPlayer player;
	private Group group;
	private Scene scene;
	private MediaView mediaView;
	private Media media;
	private JLabel thumbnail;
	
	public MemPanelVideo(MemObjectVideo mem) {
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
        System.out.println(mem.getThumbnail());
        thumbnail = new JLabel(mem.getThumbnail(), JLabel.CENTER);
        System.out.println("LABEL :" + mem.getThumbnail().getIconHeight());
		thumbnail.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));
		thumbnail.setPreferredSize(new Dimension(mem.getThumbnail().getIconWidth(), mem.getThumbnail().getIconHeight()));
		videoPlayerPanel.add(thumbnail, BorderLayout.CENTER);
        initializeMediaControllPanel(player);   
        videoPlayerPanel.add(mediaControllPanel, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}

	private void initializeVideoPanel() {
		
        videoPanel = new JFXPanel();
        videoPanel.setAlignmentX(RIGHT_ALIGNMENT);
        
        //scene = new Scene(group, group.getLayoutX(), group.getLayoutY()/*, mem.getVideoSize().width, mem.getVideoSize().height*/);
        //videoPanel.setMaximumSize(new Dimension((int)scene.getWidth(),(int)scene.getHeight()));
       // videoPanel.setScene(scene);
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
        addButtonMediaPlayerControllsActions();    
        mediaControllPanel.add(buttonPanel, BorderLayout.CENTER);
	}

	private void addButtonMediaPlayerControllsActions() {
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("PLAY");
				videoPlayerPanel.remove(thumbnail);
				videoPlayerPanel.add(videoPanel, BorderLayout.CENTER);
				String source = mem.getVideoUrl();
				System.out.println("SOURCE " + source);
				Thread mediaLoader = new Thread() {
					public void run() {
						System.out.println("MEDIALOADER");
						media = new Media(source);
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
						
						
						System.out.println("DIMENSION :       " +  group.getLayoutBounds().getWidth() + "x"+group.getLayoutBounds().getHeight() + " l " + mem.getVideoSize().width + " x " + mem.getVideoSize().height);
				        videoPanel.setMaximumSize(new Dimension((int)(group.getLayoutBounds().getWidth()*group.getScaleX()),(int)(group.getLayoutBounds().getHeight()*group.getScaleY())));
						videoPanel.setScene(scene);
						player.play();
					}
				};
				if(scene == null)playerInitializer.start();
				else player.play();
			}
		});			
        stop.addActionListener((ActionEvent e) -> player.pause());
        restart.addActionListener((ActionEvent e) -> player.seek(Duration.ZERO));
        mute.addActionListener((ActionEvent e) ->player.setMute(!player.isMute()));
	}
}
