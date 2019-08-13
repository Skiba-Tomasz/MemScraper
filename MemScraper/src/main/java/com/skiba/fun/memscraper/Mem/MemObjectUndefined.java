package com.skiba.fun.memscraper.Mem;

import java.net.URL;

import javax.swing.ImageIcon;

public class MemObjectUndefined extends MemObject{
	private static MemObjectUndefined instance;
	private ImageIcon errorImage;
	
	public static MemObjectUndefined getInstance() {
		if(instance == null) {
			instance = new MemObjectUndefined();
			instance.setType(MemType.UNDEFINED);
			instance.setErrorImage();
		}
		return instance;
	}
	public ImageIcon getErrorImage() {
		return errorImage;
	}
	
	private MemObjectUndefined() {
		
	}
	private void setErrorImage() {
		URL url = getClass().getResource("/img/UndefinedProblem.png");
		errorImage = new ImageIcon(url);
	}	
}
