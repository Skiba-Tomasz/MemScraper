package memscraper;

import java.util.Arrays;

import org.jsoup.nodes.Element;

public class MemObject {
	private String title;
	private String[] tags;
	private int rating;
	private String url;


	public String getTitle() {
		return title;
	}

	public String[] getTags() {
		return tags;
	}

	public int getRating() {
		return rating;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "MemObject [title=" + title + ", tags=" + Arrays.toString(tags) + ", rating=" + rating + ", url=" + url
				+ "]";
	}
	
	public static class Builder{
		private String title = "Brak tytu³u";
		private String[] tags = new String[] {"Brak tagu"};
		private int rating;
		private String url = "Brak linku";
		
		public MemObject build() {
			return new MemObject(this);
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setTags(String[] tags) {
			this.tags = tags;
			return this;
		}
		public Builder setRating(int rating) {
			this.rating = rating;
			return this;
		}
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}	
	}
	private MemObject(Builder b) {
		this.title = b.title;
		this.rating = b.rating;
		this.tags = b.tags;
		this.url = b.url;
	}
}
