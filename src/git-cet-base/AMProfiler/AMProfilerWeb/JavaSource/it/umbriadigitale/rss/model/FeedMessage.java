package it.umbriadigitale.rss.model;

public class FeedMessage {

	String  version;
	String	title;
	String	description;
	String	link;
	String	guid;
	String	pubDate;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public String getShortDescription() {
		String descShort= "";
		
		if (description!=null){
			if (description.length()>20)
				descShort = description.substring(0,20)+"...";
			else
				descShort = description;
		}
        return descShort;
	}
	
	
	@Override
	public String toString() {
		return "FeedMessage [version=" + version + ", title=" + title + ", description=" + description + ", link=" + link + ", guid=" + guid + ", pubDate=" + pubDate + "]";
	}

    

}