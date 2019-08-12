package com.skiba.fun.memscraper.JBZDmemscraper;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import com.skiba.fun.memscraper.Mem.MemInterface;


public class MemObject implements MemInterface{
/*	public enum MemType{
		IMAGE, VIDEO, UNDEFINED
	}*/
	private MemType type = MemType.UNDEFINED;
	
	private String contentURL;
	private ImageIcon image;
	private Dimension videoSize;
	private String title = "Brak tytu³u";
	private String[] tags = new String[] {"Brak tagu"};
	private String rating;
	private String url = "Brak linku";
	

	
	public String getContentURL() {
		return contentURL;
	}

	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}

	public ImageIcon getImage() {
		if(image == null) pullImage();
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public Dimension getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(Dimension videoSize) {
		this.videoSize = videoSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MemType getType() {
		return type;
	}

	public void setType(MemType type) {
		this.type = type;
	}
	
	private void pullImage() {
		URL url = null;
		try {
			url = new URL(contentURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.image = new ImageIcon(image);
	}
}
