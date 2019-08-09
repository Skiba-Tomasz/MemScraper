package com.skiba.fun.memscraper.JBZDmemscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MemScraper {
	private final String domainURL = "https://jbzdy.net/str/";

	public List<MemObject> loadMemsFromPage(int pageNumber) {
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

	private List<MemObject> scrapeMemsFromPage(Elements memPosts) {
		List<MemObject> memsFromPosts = new ArrayList<>();
		for(Element post : memPosts) {
			MemObject mem = new MemObject.Builder().setTitle(getMemPostTitle(post))
													.setTags(getMemPostTags(post))
													.setRating(getMemPostRating(post))
													.setUrl(getMemPostImageUrl(post)).build();
			memsFromPosts.add(mem);
		}
		return memsFromPosts;
	}

	private Elements getMemPosts(Elements currentPostContainer) {
		Elements memPosts = new Elements();
		for(Element element : currentPostContainer) {
			if(element.select("article.resource-object ") != null)memPosts.addAll(element.select("article.resource-object "));
		}
		return memPosts;
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
	
	private String getMemPostImageUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("img").attr("src");
		if(!memUrl.isEmpty())return memUrl;
		else return new String("Null URL");
	}

	private int getMemPostRating(Element post) {
		return Integer.parseInt(post.select("div.content-info").select("div.content-actions").select("div").select("span").text());
	}
}
		
