package com.skiba.fun.memscraper.Mem;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.skiba.fun.memscraper.Mem.MemType;

public abstract class MemObject {
	private String title = "Brak tytu³u";
	private String[] tags = new String[] {"Brak tagu"};
	private String rating;
	private MemType type = MemType.UNDEFINED;
	//Adding url later for tracking and displaying source of mem
	//private String url = "Brak linku";
	
	protected ImageIcon pullImage(String imageUrl) {
		URL url = null;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Image image = null;
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(image);
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

	public MemType getType() {
		return type;
	}

	public void setType(MemType type) {
		this.type = type;
	}
	
	//Adding url later for tracking and displaying source of mem
/*	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}*/
}
