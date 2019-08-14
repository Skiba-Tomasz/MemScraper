package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class MemScraperJbzd extends MemScraper{
	
	public MemScraperJbzd(Domains subDomain) {
		switch(subDomain) {
		case JBZD:
			domainUrl = "https://jbzdy.net/str/";
			break;
		case JBZD_VIDEO:
			domainUrl = "https://jbzdy.net/video/";
			break;
		default:
			domainUrl = "https://jbzdy.net/str/";
		}
	}

	@Override
	public List<MemObject> getMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		try {
			Document jsDoc = Jsoup.connect(domainUrl + pageNumber).get();	
			Elements postContainer = jsDoc.select("div#container-left").select("div.content.nobottompad");
			memPosts = collectMemPosts(postContainer);				
		} catch (IOException e) {
			//Handle this later
			e.printStackTrace();
		}
		return getMemObjectsFromMemPosts(memPosts);
	}

	@Override
	protected Elements collectMemPosts(Elements currentPostContainer) {
		Elements memPosts = new Elements();
		for(Element element : currentPostContainer) {
			if(element.select("article.resource-object ") != null)memPosts.addAll(element.select("article.resource-object "));
		}
		return memPosts;
	}

	@Override
	protected String collectTitle(Element post) {
		return post.select("div.content-info").select("div.title").text();
	}

	@Override
	protected String collectRating(Element post) {
		return post.select("div.content-info").select("div.content-actions").select("div").select("span").text();
	}

	@Override
	protected String[] collectTags(Element post) {
		String tagString = post.select("div.content-info").select("div.info").select("div.tags").text();
		String tagStringWithoutFirstHash = tagString.substring(1);
		String[] tags = tagStringWithoutFirstHash.split("#");
		return tags;
	}

	@Override
	protected String collectImageUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("img").attr("src");	
		return memUrl;
	}

	@Override
	protected String collectVideoUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("source").attr("src");
		return memUrl;
	}

	@Override
	protected String collectThumbnailUrl(Element post) {
		String memUrl = post.select("div.content-info").select("div.media").select("div.image.rolled").select("video").attr("poster");
		return memUrl;
	}

	@Override
	protected Dimension collectVideoSize(Element post) {
		String atrributes = post.select("div.content-info").select("div.media").select("div.image.rolled").select("video").attr("data-setup");
		String sizeString = atrributes.substring(atrributes.indexOf(':')+2, atrributes.indexOf(',') -1);
		String[] dimensions = sizeString.split(":");
		if(atrributes.isEmpty() || sizeString.isEmpty() || dimensions == null) return null;
		return new Dimension(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
	}

}
