package it.webred.utils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ObjectEncoder
{


	public static String encodeObject(Object obj) throws IOException
    {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bout);
            oos.writeObject(obj);
            oos.flush();
            return URLEncoder.encode(bout.toString(),"UTF-8");
	}




	public static Object decodeObject(String str) throws IOException, ClassNotFoundException 
	{
            String URLdec = URLDecoder.decode(str,"UTF-8");
			ByteArrayInputStream  bin = new ByteArrayInputStream(URLdec.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bin);
			return ois.readObject();
	}









}
