package com.skiba.fun.memscraper.JBZDmemscraper;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.jsoup.nodes.Element;

public class MemObject {
	private String title;
	private String[] tags;
	private int rating;
	private String imageUrl;
	private ImageIcon image;
	

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

	public String getImageUrl() {
		return imageUrl;
	}

	@Override
	public String toString() {
		return "MemObject [title=" + title + ", tags=" + Arrays.toString(tags) + ", rating=" + rating + ", url=" + imageUrl
				+ "]";
	}
	
	public static class Builder{
		private String title = "Brak tytu³u";
		private String[] tags = new String[] {"Brak tagu"};
		private int rating;
		private String url = "Brak linku";
		
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
	}
	
	private MemObject(Builder b) {
		this.title = b.title;
		this.rating = b.rating;
		this.tags = b.tags;
		this.imageUrl = b.url;
	}
	
	private void pullImage() {
		URL url = null;
		try {
			url = new URL(imageUrl);
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
}
