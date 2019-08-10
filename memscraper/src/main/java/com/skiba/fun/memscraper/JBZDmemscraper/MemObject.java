package com.skiba.fun.memscraper.JBZDmemscraper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.ImageIcon;

import org.jsoup.nodes.Element;

public class MemObject {
	public enum MemType{
		IMAGE, VIDEO, UNDEFINED
	}
	
	private String title;
	private String[] tags;
	private int rating;
	private String contentURL;
	private ImageIcon image;
	//private Component video;
	private Dimension videoSize;
	private MemType type;


	public Object getMemeContent() {
		switch(type) {
		case IMAGE:
			return getImage();
		case VIDEO:
			
		}
		
		return null;
	}
	
/*	public Component getVideo() {
		if(video == null) pullVideo();
		return video;
	}*/
	
	public ImageIcon getImage() {
		if(image == null) pullImage();
		return image;
	}

	public String getTitle() {
		return title;
	}

	public String[] getTags() {
		return tags;
	}

	public int getRating() {
		return rating;
	}

	public String getContentURL() {
		return contentURL;
	}

	public MemType getType() {
		return type;
	}

	
	
	public Dimension getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(Dimension videoSize) {
		this.videoSize = videoSize;
	}

	public String toString() {
		return "MemObject [title=" + title + ", tags=" + Arrays.toString(tags) + ", rating=" + rating + ", imageUrl="
				+ contentURL + ", image=" + image + ", type=" + type + "]";
	}

	public static class Builder{
		private String title = "Brak tytu³u";
		private String[] tags = new String[] {"Brak tagu"};
		private int rating;
		private String url = "Brak linku";
		private MemType type = MemType.IMAGE;
		
		public MemObject build() {
			return new MemObject(this);
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setTags(String[] tags) {
			this.tags = tags;
			return this;
		}
		public Builder setRating(int rating) {
			this.rating = rating;
			return this;
		}
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		public Builder setType(MemType type) {
			this.type = type;
			return this;
		}	
		
		
	}
	
	private MemObject(Builder b) {
		this.title = b.title;
		this.rating = b.rating;
		this.tags = b.tags;
		this.contentURL = b.url;
		this.type = b.type;
	}
	
	private void pullImage() {
		URL url = null;
		System.out.println(contentURL);
		try {
			url = new URL(contentURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.image = new ImageIcon(image);
	}
	
/*	private void pullVideo() {
        URL mediaURL = null;
		try {
			mediaURL = new URL(contentURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        Player mediaPlayer = null;
		try {
			mediaPlayer = Manager.createRealizedPlayer(mediaURL);
		} catch (NoPlayerException e) {
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        video = mediaPlayer.getVisualComponent();
	}*/
}
