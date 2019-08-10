package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
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
			//imageDisplayLabel.setSize(getWidth(), imageDisplayLabel.getMinimumSize().height);
			add(imageDisplayLabel);
			break;
		case VIDEO:
			getVideo(mem);
		}
		
	}
	
	public void getVideo(MemObject mem) {
		 //File video = new File(mem.getContentURL());
         String url = mem.getContentURL();
         JFXPanel jfxPanel = new JFXPanel();
         JPanel videoPanel = new JPanel();
         videoPanel.setLayout(new BoxLayout(videoPanel, BoxLayout.Y_AXIS));
         videoPanel.setBackground(Color.DARK_GRAY);
/*         if(mem.getVideoSize().width> 500) {
        	 double scale = (double)(mem.getVideoSize().height)/(double)(mem.getVideoSize().width);
        	 System.out.println("New scale: " + scale + " " +mem.getVideoSize().height + " " + mem.getVideoSize().width);
        	 int newHeight =(int)(500*scale);
        	 System.out.println("New size: " + 500 + "x" + newHeight);
        	 mem.setVideoSize(new Dimension(500, newHeight));
         }*/
         videoPanel.setPreferredSize(mem.getVideoSize());
         videoPanel.setMaximumSize(mem.getVideoSize());

         Media media = new Media(url);
         MediaPlayer player = new MediaPlayer(media);
         MediaView view = new MediaView(player);
         Group g = new Group(view);
         Scene s = new Scene(g);
        // s.setFill(Paint.valueOf("TRANSPARENT")); // THIS MAKES IT TRANSPARENT!

         
         jfxPanel.setScene(s);
         videoPanel.add(jfxPanel);
        // videoPanel.setMinimumSize(new Dimension(media.getWidth(), media.getHeight()));
         System.out.println(view.fitHeightProperty().get());
        // videoPanel.setSize(new Dimension(600 ,600));
         //videoPanel.setPreferredSize(new Dimension(media.getWidth(), media.getHeight()));
         JButton play = new JButton("Play!");
         videoPanel.add(play);
         play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				player.seek(Duration.ZERO);
				player.play();
			}
		});
         videoPanel.validate();
         add(videoPanel);
	}
}
