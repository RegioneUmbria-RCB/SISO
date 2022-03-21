package it.umbriadigitale.rss.read;

import it.umbriadigitale.rss.model.Feed;
import it.umbriadigitale.rss.model.FeedMessage;
import it.umbriadigitale.utility.RssException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser {
	static final String	VERSION		= "version";
	static final String	TITLE		= "title";
	static final String	DESCRIPTION	= "description";
	static final String	CHANNEL		= "channel";
	static final String	LANGUAGE	= "language";
	static final String	COPYRIGHT	= "copyright";
	static final String	LINK		= "link";
	static final String	AUTHOR		= "author";
	static final String	ITEM		= "item";
	static final String	PUB_DATE	= "pubdate";
	static final String	GUID		= "guid";
	
	
	private InputStream in;
	
	public RSSFeedParser(String feedUrl) throws RssException {
		try {
			URL url = new URL(feedUrl);
			in =url.openStream();
		} catch (MalformedURLException e) {
			try {
				in = new FileInputStream(feedUrl); 
			} catch (FileNotFoundException e1) {
				throw new RssException(e1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}//-------------------------------------------------------------------------
	
	public RSSFeedParser(String feedUrl, String urlProxy, int portProxy) throws RssException {
		try {
			InetSocketAddress proxyInet = new InetSocketAddress(urlProxy, portProxy);
	    	Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
			URL url = new URL(feedUrl);
			URLConnection conn = url.openConnection(proxy);
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			in = conn.getInputStream();
		} catch (MalformedURLException e) {
			try {
				in = new FileInputStream(feedUrl); 
			} catch (FileNotFoundException e1) {
				throw new RssException(e1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}//-------------------------------------------------------------------------
	
	public Feed readFeed() {
		Feed feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String version = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";
			
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					switch (localPart) {
						case ITEM:
							if (isFeedHeader) {
								isFeedHeader = false;
								feed = new Feed(title, link, description, language, copyright, author);
								description = "";
								version = "";   
								title = "";      
								link = "";       
								language = "";   
								copyright = "";  
								author = "";     
								pubdate = "";    
								guid = "";  
							}
							event = eventReader.nextEvent();
							break;	
						case VERSION:
							version = getCharacterData(event, eventReader);
							break;
						case TITLE:
							title = getCharacterData(event, eventReader);
							break;
						case DESCRIPTION:
							description = getCharacterData(event, eventReader);
							break;
						case LINK:
							link = getCharacterData(event, eventReader);
							break;
						case GUID:
							guid = getCharacterData(event, eventReader);
							break;
						case LANGUAGE:
							language = getCharacterData(event, eventReader);
							break;
						case AUTHOR:
							author = getCharacterData(event, eventReader);
							break;
						case PUB_DATE:
							pubdate = getCharacterData(event, eventReader);
							break;
						case COPYRIGHT:
							copyright = getCharacterData(event, eventReader);
							break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						FeedMessage message = new FeedMessage();
						if(version!="")message.setVersion(version);
						if(description!="")message.setDescription(description);
						if(guid!="")message.setGuid(guid);
						if(link!="")message.setLink(link);
						if(title!="")message.setTitle(title);
						if(pubdate!="")message.setPubDate(pubdate);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						
						description = "";
						version = "";   
						title = "";      
						link = "";       
						language = "";   
						copyright = "";  
						author = "";     
						pubdate = "";    
						guid = "";       
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}
	
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();//event.getEventType()
		}
		return result;
	}
	
	
}