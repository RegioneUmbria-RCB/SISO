/*
 * FileSeeker.java
 *
 * Created on 13 gennaio 2004, 4.44
 */

package it.webred.utils;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

// scopo:{
/**
 * FileSeeker classe usata per risolvere mediante riscrittura i file in modo
 * relativo.
 * 
 * @author Andrea Pradarelli
 * @version $Revision: 1.1 $ - $Date: 2007/05/16 07:15:20 $
 */
// scopo:}
public class FileSeeker
{

	static final String FS = System.getProperty("file.separator");
	static final String THE_FILE = "fileseeker.properties";
	static URL url = FileSeeker.class.getClassLoader().getResource(THE_FILE);
	private static final Logger log = Logger.getLogger(FileSeeker.class.getName());

	/**
	 * Creates a new instance of FileSeeker
	 */
	public FileSeeker()
	{
	}

	// note:{
	/**
	 * Torna il nome assoluto del file RELATIVO passato Il file viene mappato
	 * sul filesystem locale in base alla proprieta url.
	 * 
	 * @param name
	 *            DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	static public String fileName(String name)
	{

		if (url == null)
		{
			System.out.println("-------------------> METTI IN CLASSES IL FILE:" + THE_FILE);
			System.exit(0);
		}
		name = name.replace('\\', '/');
		String p = "";
		p = url.getPath();
		try
		{
			p = URLDecoder.decode(p, "ISO-8859-1");
		}
		catch (Exception e)
		{
			System.out.println("encoding non supportato !");
		}
		p = p.substring(0, p.lastIndexOf('/'));
		// p = p + FS + ".."+FS+name ;
		p = p + FS + name;
		String cp = p;
		try
		{
			File f = new File(p);
			cp = f.getCanonicalPath();
		}
		catch (Exception e)
		{
			log.error("fileName: " + name, e);
			throw new RuntimeException(e.getMessage());
		}


		return cp;
	}

}