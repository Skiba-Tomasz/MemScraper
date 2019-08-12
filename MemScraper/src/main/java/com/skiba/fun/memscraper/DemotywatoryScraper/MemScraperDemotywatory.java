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
	private final String domainURL = "https://demotywatory.pl/page/";
	private String memContentURL;
	
	public List<MemObjectDemotywatory> loadMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		try {
			Document jsDoc = Jsoup.connect(domainURL+pageNumber).get();	
			Elements postContainer = jsDoc
					//.select("div.page-wrapper")
					//.select("div.page-container")
					//.select("div.page")
					.select("div#main_container")
					.select("section.demots").select("article");
			System.out.println(postContainer.html());
			memPosts = getMemPosts(postContainer);	
			System.out.println(memPosts.size());
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
			System.out.println(getMemPostTitle(post));
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
			mem.setContentURL(memContentURL);
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
		System.out.println("IMG: " + memUrl);
		memContentURL = memUrl;
		return memUrl;
	}

	private String getMemPostVideoUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("video").select("source").attr("src");
		System.out.println("VID: " + memUrl);
		memContentURL = memUrl;
		return memUrl;
	}
	
	private Dimension getMemPostVideoSize(Element post) {
		String atrributesWidth = post.select("div.demot_pic.image600").select("video").attr("width");
		String atrributesHeight = post.select("div.demot_pic.image600").select("video").attr("height");
		System.out.println("H: " + atrributesHeight + " W: " + atrributesWidth);
		if(atrributesHeight.isEmpty() || atrributesWidth.isEmpty()) return null;
		return new Dimension(Integer.parseInt(atrributesWidth), Integer.parseInt(atrributesHeight));
	}
}
