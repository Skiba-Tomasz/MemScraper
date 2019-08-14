package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class MemScraper {
	protected String domainUrl;
	
	public abstract List<MemObject> getMemsFromPage(int pageNumber);
	protected abstract Elements collectMemPosts(Elements currentPostContainer);
	protected abstract String collectTitle(Element post);
	protected abstract String collectRating(Element post);
	protected abstract String[] collectTags(Element post);
	protected abstract String collectImageUrl(Element post);
	protected abstract String collectVideoUrl(Element post);
	protected abstract String collectThumbnailUrl(Element post);
	protected abstract Dimension collectVideoSize(Element post);
	
	protected List<MemObject> getMemObjectsFromMemPosts(Elements memPosts){
		List<MemObject> memsFromPosts = new ArrayList<>();
		for(Element post : memPosts) {
			MemObject mem = createMemBasedOnPostContent(post);
			mem.setTitle(collectTitle(post));
			mem.setRating(collectRating(post));
			mem.setTags(collectTags(post));
			initializeMemContent(mem, post);
			memsFromPosts.add(mem);
		}
		return memsFromPosts;
		
	}
	
	private void initializeMemContent(MemObject mem, Element post) {
		switch(mem.getType()){
		case IMAGE:
			MemObjectImage imageMem = (MemObjectImage) mem;
			imageMem.setMemImageUrl(collectImageUrl(post));
			mem = imageMem;
			break;
		case VIDEO:
			MemObjectVideo videoMem = (MemObjectVideo) mem;
			videoMem.setThumbnailUrl(collectThumbnailUrl(post));
			videoMem.setVideoUrl(collectVideoUrl(post));
			videoMem.setVideoSize(collectVideoSize(post));
			mem = videoMem;
			break;
		case UNDEFINED:
			break;		
		}
	}
	private MemObject createMemBasedOnPostContent(Element post) {
		if(!collectVideoUrl(post).isEmpty()) return new MemObjectVideo();
		else if(!collectImageUrl(post).isEmpty()) return new MemObjectImage();
		else return MemObjectUndefined.getInstance();
	}
}
