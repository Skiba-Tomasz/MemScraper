package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class MemScraperDemotywatory extends MemScraper{
	private String titleContent;
	String[] splitedRatingAndDateOfPost;
	
	public MemScraperDemotywatory() {
		this.domainUrl = "https://demotywatory.pl/page/";
		titleContent = new String();
	}

	@Override
	public List<MemObject> getMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		try {
			Document jsDoc = Jsoup.connect(domainUrl + pageNumber).get();	
			Elements postContainer = jsDoc
					.select("div#main_container")
					.select("section.demots").select("article");
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
			if(element.select("div.demotivator.pic ") != null)memPosts.addAll(element.select("div.demotivator.pic "));
		}
		return memPosts;
	}

	@Override
	protected String collectTitle(Element post) {
		titleContent = post.select("div.demotivator.pic ").text();
		return titleContent;
	}

	@Override
	protected String collectRating(Element post) {
		if(splitedRatingAndDateOfPost == null) collectTags(post);
		return splitedRatingAndDateOfPost[0] + ")";
	}

	@Override
	protected String[] collectTags(Element post) {
		if(titleContent.isEmpty()) collectTitle(post);
		String ratingAndDate = titleContent.substring(titleContent.lastIndexOf("Mocne S³abe +"));
		splitedRatingAndDateOfPost = ratingAndDate.split("\\)");
		String[] tags = new String[] {splitedRatingAndDateOfPost[1]
										.substring(1, splitedRatingAndDateOfPost[1]
										.lastIndexOf("Skomentuj ("))};
		return tags;
	}

	@Override
	protected String collectImageUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("img").attr("src");	
		return memUrl;
	}

	@Override
	protected String collectVideoUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("video").select("source").attr("src");
		return memUrl;
	}

	@Override
	protected String collectThumbnailUrl(Element post) {
		String memUrl = post.select("div.demot_pic.image600").select("video").attr("poster");
		return memUrl;
	}

	@Override
	protected Dimension collectVideoSize(Element post) {
		String atrributesWidth = post.select("div.demot_pic.image600").select("video").attr("width");
		String atrributesHeight = post.select("div.demot_pic.image600").select("video").attr("height");
		if(atrributesHeight.isEmpty() || atrributesWidth.isEmpty()) return null;
		return new Dimension(Integer.parseInt(atrributesWidth), Integer.parseInt(atrributesHeight));
	}

}
