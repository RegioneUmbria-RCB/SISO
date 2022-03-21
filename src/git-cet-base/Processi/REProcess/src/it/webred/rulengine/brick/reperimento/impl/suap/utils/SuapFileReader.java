package it.webred.rulengine.brick.reperimento.impl.suap.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class SuapFileReader {
	
	private static Logger log = Logger.getLogger(SuapFileReader.class.getName());
	
	//cartella che contiene i file properties temporanei
	public static final String PROPERTIES_FOLDER = "properties";
	
	//suffissi ed estensioni tipi file
	public static final String SUFF_FILE_RICEVUTA = "ricevuta.xml";
	public static final String SUFF_FILE_RIEPILOGO = "suap.xml";

	public static final String SUFF_FILE_PROPERTIES = ".properties";
	
	
	public static void readFiles(String dirFiles, String dir, String id, HashMap<String, String> errs) throws Exception {
		dir = setFileSeparator(dir);
		if (!dir.endsWith(File.separator)) {
			dir += File.separator;
		}
		//ricerca e lettura dei file xml
		File f = new File(dir);
		String[] fileList = f.list();
		for (String fileName : fileList) {
			String path = dir + fileName;
			File f1 = new File(path);
			if (f1.isDirectory()) {
				readFiles(dirFiles, path, id, errs);
			} else if (path.toLowerCase().endsWith(SUFF_FILE_RICEVUTA) || path.toLowerCase().endsWith(SUFF_FILE_RIEPILOGO)) {
				readXmlFile(dirFiles, path, id, errs);
			}
		}
	}
	
	private static void readXmlFile(String dirFiles, String path, String id, HashMap<String, String> errs) {
		try {
			path = setFileSeparator(path);
			SuapFileWriter.writeTempPropFile(dirFiles, path, id);
		} catch (Exception e) {
			//verifico se è un problema di escape
			String np = path.replace(".xml", "_tmp.xml");
			File nf = new File(np);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
				String currentLine = null;
				PrintWriter out = new PrintWriter(nf);
				while ((currentLine = br.readLine()) != null) {
					out.println(getEscapedLine(currentLine));
				}
				out.close();
				br.close();
				SuapFileWriter.writeTempPropFile(dirFiles, np, id);
			} catch (Exception e1) {
				//l'eccezione in questo metodo non deve essere bloccante
				errs.put(path, e1.getMessage());
				log.debug("ECCEZIONE NON BLOCCANTE: " + e1.getMessage());
			} finally {
				if (nf.exists()) {
					nf.delete();
				}
			}
		}
    }
	
	private static String getEscapedLine(String line) {
		StringBuffer sb = new StringBuffer();
		for (char c : line.toCharArray()) {
			switch(c) {
				case '\'':
					sb.append(StringEscapeUtils.escapeXml("" + c));
					break;
				case '&':
					sb.append(StringEscapeUtils.escapeXml("" + c));
					break;
				//TODO caratteri < > ", c'è il problema di fare escape solo se non sono quelli dei tag...
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	
	public static void deleteFolder(File folder) throws Exception {
		if (!folder.exists()) {
			return;
		}
	    File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	            if(f.isDirectory()) {
	                deleteFolder(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	public static String setFileSeparator(String path) {
		if (path != null) {
			path = path.replace("\\", "/").replace("/", File.separator);
		}
		return path;
	}

}
