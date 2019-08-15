package com.skiba.fun.memscraper.Mem;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MemScraperKwejk extends MemScraper{
	
	private static int FIRST_INDEX_PAGE;

	public MemScraperKwejk() {
		domainUrl = "https://kwejk.pl/strona/";
	}

	@Override
	public List<MemObject> getMemsFromPage(int pageNumber) {
		Elements memPosts = null;
		pageNumber -= 1;
		try {
			Document jsDoc;
			if(pageNumber == 0) {
				jsDoc = Jsoup.connect("https://kwejk.pl/").get();
				FIRST_INDEX_PAGE = Integer.parseInt(jsDoc.select("div.pagination").select("ul.pager").select("li.current").text());
			}
			else {
				System.out.println(FIRST_INDEX_PAGE);
				jsDoc = Jsoup.connect(domainUrl + (FIRST_INDEX_PAGE - pageNumber)).get();
			}
			Elements postContainer = jsDoc.select("div.col-sm-8.col-xs-12");
			
			memPosts = collectMemPosts(postContainer);				
		} catch (IOException e) {
			//Handle this later
			e.printStackTrace();
		}
		return getMemObjectsFromMemPosts(memPosts);
	}
	
	public int getFirstIndexPage() {
		return FIRST_INDEX_PAGE;
	}

	@Override
	protected Elements collectMemPosts(Elements currentPostContainer) {
		Elements memPosts = new Elements();
		for(Element element : currentPostContainer) {
			if(element.select("div.media-element-wrapper") != null)memPosts.addAll(element.select("div.media-element-wrapper"));
		}
		return memPosts;
	}

	@Override
	protected String collectTitle(Element post) {
		String title = post.select("div.box.fav.picture ").select("div.figure-holder").select("img").attr("alt");
		if(title.isEmpty() || title.equals(new String(" "))) {
			System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
			title = post.select("div.box.fav.mp4 ").select("div.content").select("h2").text();
		}
		System.out.println("title: " + title);
		return title;
	}

	@Override
	protected String collectRating(Element post) {
		String voteUp = post.select("div").attr("data-vote-up");
		String voteDown = post.select("div").attr("data-vote-down");
		//System.out.println(voteUp + "." + voteDown);
		return new String("+" + voteUp + "/-" + voteDown);
	}

	@Override
	protected String[] collectTags(Element post) {
		String tagString = post.select("div.content")
								.select("div.toolbar").select("div.tag-list").text();
		System.out.println(tagString);
		tagString = tagString.substring(1);
		String[] tags = tagString.split("#");
		return tags;
	}

	@Override
	protected String collectImageUrl(Element post) {
		String memUrl = post.select("div").attr("data-image");	
		return memUrl;
	}

	@Override
	protected String collectVideoUrl(Element post) {
		String memUrl = post.select("div.box.fav.mp4 ").select("div.figure-holder").select("player").attr("source");
		return memUrl;
	}

	@Override
	protected String collectThumbnailUrl(Element post) {
		String memUrl = post.select("div.box.fav.mp4 ").select("div.figure-holder").select("player").attr("poster");
		return memUrl;
	}

	@Override
	protected Dimension collectVideoSize(Element post) {
		return new Dimension(1,1);
	}

}
