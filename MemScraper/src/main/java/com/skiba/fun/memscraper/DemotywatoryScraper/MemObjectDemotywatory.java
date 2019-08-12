package com.skiba.fun.memscraper.DemotywatoryScraper;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.skiba.fun.memscraper.Mem.MemInterface;


public class MemObjectDemotywatory implements MemInterface{
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

	public void setDataFromTitleString(String title) {
		System.out.println("===MEME===");
		this.title = title.substring(0, title.lastIndexOf("Mocne S³abe +"));
		System.out.println("TIT: "+this.title);
		String ratingAndDate = title.substring(title.lastIndexOf("Mocne S³abe +"));
		//System.out.println(ratingAndDate);
		//System.out.println(ratingAndDate.indexOf(')'));
		String[] splitedRatingAndDate = ratingAndDate.split("\\)");
		//System.out.println(splitedRatingAndDate[0] + "////" + splitedRatingAndDate[1]);
		this.rating = splitedRatingAndDate[0] + ")";
		//System.out.println(splitedRatingAndDate[1]);
		this.tags[0] = splitedRatingAndDate[1].substring(1, splitedRatingAndDate[1].lastIndexOf("Skomentuj ("));
		System.out.println("RAT: " +this.rating);
		System.out.println("TAG: " + this.tags[0]);
		System.out.println("===MEME===");
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
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

	@Override
	public String getRating() {
		return rating;
	}
}
