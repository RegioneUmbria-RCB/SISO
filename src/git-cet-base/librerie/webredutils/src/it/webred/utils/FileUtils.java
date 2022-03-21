package it.webred.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;

import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import sun.awt.SunToolkit.InfiniteLoop;


public class FileUtils {

	
	public static void close(Reader r) 
    {
		if (r!=null)
			try {
				r.close();
			} catch (IOException e) {

			}
    }
	public static void close(InputStream r) 
    {
		if (r!=null)
			try {
				r.close();
			} catch (IOException e) {

			}
    }
	
	
	public static boolean isZip(File inFile) {
		try 
		{
			return inFile.isFile() && inFile.getName().toLowerCase().endsWith(".zip");

        }
        catch (Exception e)
        {
             return false;
        }
	}
	         

	
	
	public static boolean unzip(File inFile) throws Exception
    {
		int BUFFER = 1024;

		if (!isZip(inFile))
			return false;
		
		ZipInputStream zis = null;
		 try {
	         BufferedOutputStream dest = null;
	         ZipEntry entry;
	         
	         zis = new ZipInputStream(new BufferedInputStream(
	        		 new FileInputStream(inFile)));

	         boolean retVal = false;
	         
	         while ((entry = zis.getNextEntry()) != null)
	         {

	            retVal = false;
	        	 
	            byte data[] = new byte[BUFFER];
	            
	            String fileName = inFile.getParent() + "\\" +  entry.getName();
	            
	            String ctrlEntryName = entry.getName();
	            boolean hasDirectory = ctrlEntryName.lastIndexOf("/") != -1 || ctrlEntryName.lastIndexOf("\\") != -1;
	            if (hasDirectory) {
	            	String dirPath = inFile.getParent() + "\\";
	            	if (ctrlEntryName.lastIndexOf("/") != -1) {
	            		dirPath += ctrlEntryName.substring(0, ctrlEntryName.lastIndexOf("/"));
	            	} else if (ctrlEntryName.lastIndexOf("\\") != -1) {
	            		dirPath += ctrlEntryName.substring(0, ctrlEntryName.lastIndexOf("\\"));
	            	}
	            	File dirPathF = new File(dirPath);
	            	if (!dirPathF.exists()) {
	            		dirPathF.mkdir();
	            	}
	            }
	            
	            FileOutputStream fos = new FileOutputStream(fileName);
	            dest = new BufferedOutputStream(fos, BUFFER);
	            
	            int got;
		         while ((got = zis.read(data)) >= 0) {
		        	 dest.write(data, 0, got);
		         }

 	            zis.closeEntry();
	            dest.flush();
	            dest.close();
	            retVal = true;
	         }
	         
	         return retVal;
	         
	      } catch(Exception e) {
	         throw e;
	      } finally {
	    	  if (zis!=null)
	    		  zis.close();
	      }

    }
    




	
	public static  String[] cercaFileDaElaborare(String percorsoFiles)
	throws Exception
	{

	try
	{
		// se il percorso non è una directory allora vuol dire che ho indicato un prefisso
		File percorsoFilesFiles = new File(percorsoFiles);
		boolean isPrefix =false;
		if (percorsoFilesFiles!=null)
			isPrefix = !percorsoFilesFiles.isDirectory();
		
		String cartellaDati = null;
		String prefixPossibile = null;
		if (isPrefix) {
			// controllo la possibilità che nel property abbia indicato un prefisso e non una cartella
			
			if (percorsoFiles.lastIndexOf("/") ==-1) {
				prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("\\") + 1);
				cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("\\"));
			} else {
				prefixPossibile = percorsoFiles.substring(percorsoFiles.lastIndexOf("/") + 1);
				cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/"));
			}
		} else {
			cartellaDati = percorsoFiles;
		}
		final String prefissoFile = prefixPossibile;

		File filesDati = new File(cartellaDati);
		

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name)
			{
				if ("ELABORATI".equals(name))
					return false;
				
				if ((prefissoFile==null || prefissoFile.equals("")))
					return true;
				
				return name.startsWith(prefissoFile);
			}
		};
		return  filesDati.list(filter);
	}
	catch (Exception e)
	{
		throw e;

	}		
	}
	
}
