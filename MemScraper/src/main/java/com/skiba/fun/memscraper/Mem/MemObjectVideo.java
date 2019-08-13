package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;

import javax.swing.ImageIcon;

public class MemObjectVideo extends MemObject{
	private ImageIcon thumbnail;
	private String thumbnailUrl;
	private String videoUrl;
	private Dimension videoSize;
	
	public MemObjectVideo() {
		setType(MemType.VIDEO);
	}
	
	public ImageIcon getThumbnail() {
		if(thumbnail == null)this.thumbnail = pullImage(thumbnailUrl);
		return thumbnail;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Dimension getVideoSize() {
		return videoSize;
	}
	public void setVideoSize(Dimension videoSize) {
		this.videoSize = videoSize;
	}
	
	
}
