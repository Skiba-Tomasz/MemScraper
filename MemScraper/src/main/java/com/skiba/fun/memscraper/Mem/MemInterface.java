package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;

import javax.swing.ImageIcon;

public interface MemInterface {
	enum MemType{
		IMAGE, VIDEO, UNDEFINED
	}
	public MemType getType();
	public ImageIcon getImage();
	public ImageIcon getThumbnail();
	public String getContentURL();
	public Dimension getVideoSize();
	
	public String getTitle();
	public String getRating();
	public String[] getTags();
	
}
