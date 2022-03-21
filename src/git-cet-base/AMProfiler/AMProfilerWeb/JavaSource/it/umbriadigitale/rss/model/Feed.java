package it.umbriadigitale.rss.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {

    final String title;
    final String link;
    final String description;
    final String language;
    final String copyright;
    final String author;

    final List<FeedMessage> entries = new ArrayList<FeedMessage>();

    public Feed(String title, String link, String description, String language, String copyright, String author) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.language = language;
            this.copyright = copyright;
            this.author = author;
    }

    public List<FeedMessage> getMessages() {
            return entries;
    }

    public String getTitle() {
            return title;
    }

    public String getLink() {
            return link;
    }

    public String getDescription() {
            return description;
    }

    public String getLanguage() {
            return language;
    }

    public String getCopyright() {
            return copyright;
    }

	public String getAuthor() {
		return author;
	}

	public List<FeedMessage> getEntries() {
		return entries;
	}
	
	@Override
    public String toString() {
            return "Feed [copyright=" + copyright + ", description=" + description + ", language=" + language + ", link=" + link + ", title=" + title + "]";
    }
	

}
