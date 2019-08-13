package com.skiba.fun.memscraper.Mem;

import javax.swing.ImageIcon;

public class MemObjectImage extends MemObject{
	private ImageIcon memImage;
	private String memImageUrl;
	
	public MemObjectImage() {
		setType(MemType.IMAGE);
	}
	public ImageIcon getMemImage() {
		if(memImage == null)this.memImage = pullImage(memImageUrl);
		return memImage;
	}
	public String getMemImageUrl() {
		return memImageUrl;
	}
	public void setMemImageUrl(String memImageUrl) {
		this.memImageUrl = memImageUrl;
	}
	
}
