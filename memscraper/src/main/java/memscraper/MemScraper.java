package memscraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MemScraper {
	private String domainURL = "https://jbzdy.net/str/1";
	private Document jsDoc = null;
	
	public static void main(String[] args) {
		MemScraper memScraper = new MemScraper();
	}
	
	public MemScraper(){
		try {
			jsDoc = Jsoup.connect(domainURL).get();
			
			Elements postFeed = jsDoc.select("div#container-left").select("div.content.nobottompad");
			System.out.println("Feed: "+  postFeed.text());
			Elements memPosts = new Elements();
			for(Element element : postFeed) {
				//postFeed;
				if(element.select("article.resource-object ") != null)memPosts.addAll(element.select("article.resource-object "));
			}
			System.out.println(memPosts.size());
			
			for(Element memPost : memPosts) {
				//Nazwa
				System.out.println(memPost.select("div.content-info").select("div.title").text());
				//Tagi
				System.out.println(memPost.select("div.content-info").select("div.info").select("div.tags").text());
				//ImageURL
				String imagesInPost = memPost.select("div.content-info").select("div.media").select("div.image.rolled").select("img").attr("src");
				System.out.println(imagesInPost);
				//Plusy
				System.out.println(memPost.select("div.content-info").select("div.content-actions").select("div").select("span").text());
				
				System.out.println("=====");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
		
