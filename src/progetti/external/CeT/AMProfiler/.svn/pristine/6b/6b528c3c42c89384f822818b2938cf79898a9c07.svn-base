package it.webred.AMProfiler.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class NetIsAvailable {

	public NetIsAvailable() {
	}//-------------------------------------------------------------------------

	public static boolean internetIsAvailable(String urlTest) {
	    try {
	        final URL url = new URL( urlTest );
	        final URLConnection conn = url.openConnection();
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        conn.connect();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}//-------------------------------------------------------------------------
	
	public static boolean internetIsAvailable(String urlTest, String urlProxy, int portProxy) {
	    try {
	    	InetSocketAddress proxyInet = new InetSocketAddress(urlProxy, portProxy);
	    	Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyInet);
	        final URL url = new URL( urlTest );
	        final URLConnection conn = url.openConnection(proxy);
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	        conn.connect();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}//-------------------------------------------------------------------------

	
}
