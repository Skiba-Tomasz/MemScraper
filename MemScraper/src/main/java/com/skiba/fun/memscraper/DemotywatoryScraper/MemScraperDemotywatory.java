package com.skiba.fun.memscraper.DemotywatoryScraper;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class MemScraperDemotywatory {
	private String domainURL;
	private String memContentURL;
	
	public MemScraperDemotywatory(String domain) {
		this.domainURL = domain;
	}
	
	public List<MemObjectDemotywatory> loadMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		try {
			Document jsDoc = Jsoup.connect(domainURL+pageNumber).get();	
			Elements postContainer = jsDoc
					.select("div#main_container")
					.select("section.demots").select("article");
			memPosts = getMemPosts(postContainer);	
			System.out.println("Mems found: " +memPosts.size());
		} catch (IOException e) {
			//Handle this later
			e.printStackTrace();
		}
		return scrapeMemsFromPage(memPosts);
	}
	
	private Elements getMemPosts(Elements currentPostContainer) {
		Elements memPosts = new Elements();
		for(Element element : currentPostContainer) {
			if(element.select("div.demotivator.pic ") != null)memPosts.addAll(element.select("div.demotivator.pic "));
		}
		return memPosts;
	}

	private List<MemObjectDemotywatory> scrapeMemsFromPage(Elements memPosts) {
		System.out.println("SCRAPING");
		List<MemObjectDemotywatory> memsFromPosts = new ArrayList<>();
		for(Element post : memPosts) {
			MemObjectDemotywatory mem = new MemObjectDemotywatory();
			mem.setDataFromTitleString(getMemPostTitle(post));
			getAndSetMemContent(mem, post);
			memsFromPosts.add(mem);
		}
		return memsFromPosts;
	}
	
	private String getMemPostTitle(Element post) {
		return post.select("div.demotivator.pic ").text();
	}
	
	private void getAndSetMemContent(MemObjectDemotywatory mem, Element post) {
		if(!getMemPostVideoUrl(post).isEmpty()) {
			mem.setType(MemObjectDemotywatory.MemType.VIDEO);
			mem.setThumbnailURL(getThumbNailURL(post));
			mem.setContentURL(memContentURL);
			System.out.println(mem.getThumbnailURL());
			Dimension videoDimension = getMemPostVideoSize(post);
			if(videoDimension == null) mem.setType(MemObjectDemotywatory.MemType.UNDEFINED);
			else mem.setVideoSize(videoDimension);
		}else if(!getMemPostImageUrl(post).isEmpty()) {
			mem.setType(MemObjectDemotywatory.MemType.IMAGE);
			mem.setContentURL(memContentURL);
		}
	}
	
	private String getMemPostImageUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("img").attr("src");	
		memContentURL = memUrl;
		return memUrl;
	}

	private String getMemPostVideoUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("video").select("source").attr("src");
		memContentURL = memUrl;
		return memUrl;
	}
	
	private String getThumbNailURL(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("video").attr("poster");
		return memUrl;
	}
	
	private Dimension getMemPostVideoSize(Element post) {
		String atrributesWidth = post.select("div.demot_pic.image600").select("video").attr("width");
		String atrributesHeight = post.select("div.demot_pic.image600").select("video").attr("height");
		if(atrributesHeight.isEmpty() || atrributesWidth.isEmpty()) return null;
		return new Dimension(Integer.parseInt(atrributesWidth), Integer.parseInt(atrributesHeight));
	}
}
