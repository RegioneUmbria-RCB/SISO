package it.webred.ct.rulengine.web.bean.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class ZipUtility {
	private static Logger logger = Logger.getLogger(ZipUtility.class.getName());
		
	public static File decompress(File input, File output) throws IOException	{
	  logger.debug("decompress-path file input: " + input.getAbsolutePath() +"; pathFileOutput: " + output.getAbsolutePath());	
	  ZipInputStream zis = new ZipInputStream(new FileInputStream(input));
	  File dirOut=null;
	  try{
		  ZipEntry zipEntry;
		  while((zipEntry = zis.getNextEntry()) != null)
		  {
				  
		    boolean directory = zipEntry.isDirectory();
		    if(directory)
		    {
		      File dir = new File(output, zipEntry.getName());
		      if(!dir.exists())
		        dir.mkdir();
		      else if(dir.isDirectory())
		        throw new IOException("Output directory \"" + dir.getAbsolutePath() + "\" is a file");
		    }else
		    {
		      //creazione directory zip
		      String nomeExt = input.getName();
		      String nome = nomeExt.substring(0, nomeExt.indexOf("."));
		      String pathDirOut = output.getAbsolutePath() + "/" + nome+"/";
		      logger.debug("directory out" + pathDirOut);	
		      dirOut = new File(pathDirOut);
		      if (!dirOut.exists()) {
		    	  dirOut.mkdir(); 
		    	  logger.debug("creata directory " + pathDirOut);	
		      }
		      String pathOut= output.getAbsolutePath() + "/" + nome+ "/";
		      File output1 = new File(pathOut);
		      logger.debug("directory pathout" + pathOut);
		      //File decompressFile = new File(output, zipEntry.getName());
		      File decompressFile = new File(output1, zipEntry.getName());
		      //decompressione
		      if(decompressFile.exists())
		        throw new IOException("Output file \"" + decompressFile.getAbsolutePath() + "\" already exists");
		      FileOutputStream fos = new FileOutputStream(decompressFile);
		      try{
		        byte[] readBuffer = new byte[4096];
		        int bytesIn = 0;
		        while ((bytesIn = zis.read(readBuffer)) != -1) {
		          fos.write(readBuffer, 0, bytesIn);
		        }
		      }
		      finally{
		        fos.close();
		      }
		    }
		  }
	  }finally
	  {
	    zis.close();
	    return dirOut;
	  }
	  
	}
	/*
	public static void decompress(ZipInputStream input, File output) throws IOException 	{
	   logger.debug("inizio dec");	
	  ZipEntry zipEntry;
	  while((zipEntry = input.getNextEntry()) != null)
	  {
			  
	    boolean directory = zipEntry.isDirectory();
	    if(directory)
	    {
	      logger.debug("directory");	
	      File dir = new File(output, zipEntry.getName());
	      if(!dir.exists())
	        dir.mkdir();
	      else if(dir.isDirectory())
	        throw new IOException("Output directory \"" + dir.getAbsolutePath() + "\" is a file");
	    }else
	    {
	      logger.debug("not directory");		
	      File decompressFile = new File(output, zipEntry.getName());
	      if(decompressFile.exists())
	        throw new IOException("Output file \"" + decompressFile.getAbsolutePath() + "\" already exists");
	      FileOutputStream fos = new FileOutputStream(decompressFile);
	      try{
	        byte[] readBuffer = new byte[4096];
	        int bytesIn = 0;
	        while ((bytesIn = input.read(readBuffer)) != -1) {
	          fos.write(readBuffer, 0, bytesIn);
	        }
	      }
	      finally{
	        fos.close();
	      }
	    }
	  }
	 
	}
 */
}
