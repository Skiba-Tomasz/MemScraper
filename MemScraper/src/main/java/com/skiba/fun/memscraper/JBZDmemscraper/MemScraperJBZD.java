package com.skiba.fun.memscraper.JBZDmemscraper;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.skiba.fun.memscraper.Mem.MemInterface;

public class MemScraperJBZD {
	private String domainURL;
	private String memContentURL;
	
	public MemScraperJBZD(String domain) {
		this.domainURL = domain;
	}
	
	public List<MemObjectJBZD> loadMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		try {
			Document jsDoc = Jsoup.connect(domainURL+pageNumber).get();	
			Elements postContainer = jsDoc.select("div#container-left").select("div.content.nobottompad");
			memPosts = getMemPosts(postContainer);				
		} catch (IOException e) {
			//Handle this later
			e.printStackTrace();
		}
		return scrapeMemsFromPage(memPosts);
	}
	
	private Elements getMemPosts(Elements currentPostContainer) {
		Elements memPosts = new Elements();
		for(Element element : currentPostContainer) {
			if(element.select("article.resource-object ") != null)memPosts.addAll(element.select("article.resource-object "));
		}
		return memPosts;
	}

	private List<MemObjectJBZD> scrapeMemsFromPage(Elements memPosts) {
		List<MemObjectJBZD> memsFromPosts = new ArrayList<>();
		for(Element post : memPosts) {
			MemObjectJBZD mem = new MemObjectJBZD();
			mem.setTitle(getMemPostTitle(post));
			mem.setTags(getMemPostTags(post));
			mem.setRating(getMemPostRating(post));
			getAndSetMemContent(mem, post);
			memsFromPosts.add(mem);
		}
		return memsFromPosts;
	}
	
	private String getMemPostTitle(Element post) {
		return post.select("div.content-info").select("div.title").text();
	}
	
	private String[] getMemPostTags(Element post) {
		String tagString = post.select("div.content-info").select("div.info").select("div.tags").text();
		String tagStringWithoutFirstHash = tagString.substring(1);
		String[] tags = tagStringWithoutFirstHash.split("#");
		return tags;
	}
	
	private String getMemPostRating(Element post) {
		return post.select("div.content-info").select("div.content-actions").select("div").select("span").text();
	}
	
	private void getAndSetMemContent(MemObjectJBZD mem, Element post) {
		if(!getMemPostImageUrl(post).isEmpty()) {
			mem.setType(MemInterface.MemType.IMAGE);
			mem.setContentURL(memContentURL);
		}else if(!getMemPostVideoUrl(post).isEmpty()) {
			mem.setType(MemInterface.MemType.VIDEO);
			mem.setThumbnailURL(getMemThumbnailURL(post));
			mem.setContentURL(memContentURL);
			Dimension videoDimension = getMemPostVideoSize(post);
			if(videoDimension == null) mem.setType(MemInterface.MemType.UNDEFINED);
			else mem.setVideoSize(videoDimension);
		}
	}
	
	private String getMemThumbnailURL(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("video").attr("poster");
		return memUrl;
	}
	
	private String getMemPostImageUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("img").attr("src");	
		memContentURL = memUrl;
		return memUrl;
	}

	private String getMemPostVideoUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("source").attr("src");
		memContentURL = memUrl;
		return memUrl;
	}
	
	private Dimension getMemPostVideoSize(Element post) {
		String atrributes = post.select("div.content-info").select("div.media").select("div.image.rolled").select("video").attr("data-setup");
		String sizeString = atrributes.substring(atrributes.indexOf(':')+2, atrributes.indexOf(',') -1);
		String[] dimensions = sizeString.split(":");
		if(atrributes.isEmpty() || sizeString.isEmpty() || dimensions == null) return null;
		return new Dimension(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
	}
}
		