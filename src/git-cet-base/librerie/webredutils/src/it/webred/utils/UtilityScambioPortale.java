package it.webred.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class UtilityScambioPortale {

	private static Logger log = Logger.getLogger(UtilityScambioPortale.class.getName());
	
	static String pathFile = System.getProperty("java.io.tmpdir");
	
	public static void writeXmlFileEsito(FTPClient ftp,String ftpDir,String nomefileEsito,String nomefileLockEsito,String esito,Long idPratica,Long idRichiesta) throws Exception
	{
		try
		{
			uploadFTP(ftp, ftpDir, nomefileLockEsito,true,false);

		    // CANCELLO EVENTUALE ALTRO FILE DI ESITO VECCHIO
		    deleteLocalFile(nomefileEsito);
		    
	    	// SCRITTURA FILE DI ESITO
		    File fileEsito = new File(pathFile+"\\"+nomefileEsito);
			
		    if (fileEsito.createNewFile())
		    {
	    		org.jdom.Element documentoXml = new org.jdom.Element("EsitoPratica");
	    		
	    		org.jdom.Element codicePratica = new org.jdom.Element("CodicePratica");
	    		org.jdom.Element portale = new org.jdom.Element("Portale");
	    		if (idPratica!=null)
	    			portale.setText(idPratica.toString());
	    		org.jdom.Element applicazione = new org.jdom.Element("Applicazione");
	    		if (idRichiesta!=null)
	    			applicazione.setText(idRichiesta.toString());
	    		
	    		codicePratica.addContent(portale);
	    		codicePratica.addContent(applicazione);
	
	    		String[] esi = esito.split("_");
	    		String codEsito = "";
	    		String msgEsito = "";
	    		if (esi!=null && esi.length>0)
	    		{
	    			codEsito = esi[0];
	    			msgEsito = esi[1];
	    		}
	    		
	    		org.jdom.Element esitoTag = new org.jdom.Element("Esito");
	    		esitoTag.setText(codEsito);
	    		
	    		org.jdom.Element protocollo = new org.jdom.Element("Protocollo");
	    		if (idPratica!=null)
	    			protocollo.setText(idPratica.toString());
	    		
	    		documentoXml.addContent(codicePratica);
	    		documentoXml.addContent(esitoTag);
	    		documentoXml.addContent(protocollo);
	    		
	    		if (codEsito!="0" || msgEsito!="")
	    		{
	    			org.jdom.Element errori = new org.jdom.Element("Errori");
	    			org.jdom.Element codice = new org.jdom.Element("Codice");
	    			codice.setText(codEsito);
	    			org.jdom.Element messaggio = new org.jdom.Element("Messaggio");
	    			messaggio.setText(msgEsito);
	    			errori.addContent(codice);
	    			errori.addContent(messaggio);
	    			documentoXml.addContent(errori);
	    		}
	    		
	    		XMLOutputter xmlOutputter = new XMLOutputter();
	    		xmlOutputter.setFormat(Format.getPrettyFormat());
	    		
	    		FileOutputStream fileOutputStream = new FileOutputStream(fileEsito);
	    		xmlOutputter.output(documentoXml, fileOutputStream);
	    		fileOutputStream.close();
	    		fileOutputStream = null;
	    		uploadFTP(ftp, fileEsito,false,true);
			}
		    else 
		    	log.error(" ERRORE NELLA CREAZIONE DEL FILE DI ESITO " + nomefileEsito);
		    
		    // CANCELLAZIONE FILE DI LOCK x ESITO
		    deleteFileFTP(ftp, ftpDir, nomefileLockEsito);
		    deleteLocalFile(nomefileLockEsito);
		}
		catch(Exception ex)
		{
			throw new Exception("Errore in writeXmlFileEsito: "+ ex.getMessage());
		}
	}
	
	public static void writeXmlFileChangeStato(FTPClient ftp,String ftpDir,String nomefileStato,String nomefileLockStato,
			Long idPratica, Long idRichiesta,String macroStato,String statoDettaglio,
			String descStato,String note) throws Exception
	{
		try
		{
			uploadFTP(ftp, ftpDir, nomefileStato,true,false);

		    // CANCELLO EVENTUALE FILE DI CHANGE STATO VECCHIO 
			deleteLocalFile(nomefileStato);
	    	// SCRITTURA FILE DI STATO AVANZAMENTO
		    File fileStato = new File(pathFile+"\\"+nomefileStato);
			
		    if (fileStato.createNewFile())
		    {
	    		org.jdom.Element documentoXml = new org.jdom.Element("StatoPratica");
	    		
	    		org.jdom.Element nodoCodicePratica = new org.jdom.Element("CodicePratica");
	    		org.jdom.Element portale = new org.jdom.Element("Portale");
	    		if (idPratica!=null)
	    			portale.setText(idPratica.toString());
	    		org.jdom.Element applicazione = new org.jdom.Element("Applicazione");
	    		if (idRichiesta!=null)
	    			applicazione.setText(idRichiesta.toString());
	    		
	    		nodoCodicePratica.addContent(portale);
	    		nodoCodicePratica.addContent(applicazione);

	    		org.jdom.Element nodoStati = new org.jdom.Element("Stati");
	    		
	    		org.jdom.Element nodoMacroStato = new org.jdom.Element("MacroStato");
	    		nodoMacroStato.setText(macroStato);
	    		org.jdom.Element nodoStatoDettaglio = new org.jdom.Element("StatoDettaglio");
	    		nodoStatoDettaglio.setText(macroStato);
	    		org.jdom.Element nodoDescrizioneStato = new org.jdom.Element("DescrizioneStato");
	    		nodoDescrizioneStato.setText(descStato);
	    		org.jdom.Element nodoDataOra = new org.jdom.Element("DataOra");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	    		nodoDataOra.setText(formatter.format(new Date()));
	    		org.jdom.Element nodoNote = new org.jdom.Element("Note");
	    		nodoNote.setText(note);
	    		
	    		nodoStati.addContent(nodoMacroStato);
	    		nodoStati.addContent(nodoStatoDettaglio);
	    		nodoStati.addContent(nodoDescrizioneStato);
	    		nodoStati.addContent(nodoDataOra);
	    		nodoStati.addContent(nodoNote);

	    		org.jdom.Element nodoProtocollo = new org.jdom.Element("Protocollo");
	    		if (idPratica!=null)
	    			nodoProtocollo.setText(idPratica.toString());
	    		
	    		documentoXml.addContent(nodoCodicePratica);
	    		documentoXml.addContent(nodoStati);
	    		documentoXml.addContent(nodoProtocollo);
	    		
	    		XMLOutputter xmlOutputter = new XMLOutputter();
	    		xmlOutputter.setFormat(Format.getPrettyFormat());
	    		
	    		FileOutputStream fileOutputStream = new FileOutputStream(fileStato);
	    		xmlOutputter.output(documentoXml, fileOutputStream);   
	    		fileOutputStream.close();
	    		fileOutputStream=null;
	    		uploadFTP(ftp, ftpDir, fileStato,false,true);
		    }
		    else 
		    	log.error(" ERRORE NELLA CREAZIONE DEL FILE DI STATO AVANZAMENTO " + nomefileStato);

		    // CANCELLAZIONE FILE DI LOCK x STATO AVANZAMENTO
		    deleteFileFTP(ftp, ftpDir, nomefileLockStato);
		    deleteLocalFile(nomefileLockStato);
		}
		catch(Exception ex)
		{
			throw new Exception("Errore in writeXmlFileChangeStato: "+ ex.getMessage());
		}
	}
	
	public static void writePdfCartella(FTPClient ftp,File filePdf, String nomeFilePdf) throws Exception {
		// 
		 //crea il temporaneo 
		File pdfTemp= new File(pathFile+"\\"+nomeFilePdf);
	    System.out.println("file da copiare: " + filePdf.getAbsolutePath());
	    System.out.println("dirOutput: " + pathFile);
	    System.out.println("nome file output: " + pdfTemp.getAbsolutePath());
		try {
			 FileInputStream fis = new FileInputStream(filePdf);
			    FileOutputStream fos = new FileOutputStream(pdfTemp);
	
			    byte [] dati = new byte[fis.available()];
			    fis.read(dati);
			    fos.write(dati);
	
			    fis.close();
			    fos.close();	
		}
		catch (Exception e) {
			
		}
		uploadFTP(ftp, pdfTemp,false,true);
	}	
		
	public static FTPClient openConnectionFTP(String host,String user, String pwd) throws Exception
	{
		FTPClient client = null;
		try {
			client = new FTPClient();
			
			try {
				client.connect(host);
			} 
			catch (Exception e) {
				throw new Exception("Server Name:" + host);
			}
			if (!FTPReply.isPositiveCompletion(client.getReplyCode())) 
			{
				client.disconnect();
				System.out.println("FTP server refused connection.");
				return null;
			}
			if (!client.login(user, pwd)) 
				return null;
			else 
				return client;
		} catch (Exception e) {
			Exception err = new Exception("Errore nella connessione a server FTP: "+ e.getMessage());
			throw err;			
		}
	}
	
	public static void closeConnectionFTP (FTPClient ftp) throws Exception
	{
		try { 
			  if (ftp!= null) 
			  {
				  ftp.logout(); 
				  ftp.disconnect (); 
			  }  
		}
		catch (Exception e) {

			throw new Exception("Errore nella connessione a server FTP: "+ e.getMessage());
		}
	}
	
	public static void uploadFTP(FTPClient ftp, String fileName,boolean reWrite,boolean deleteLocalFile) throws Exception{
		
		uploadFTP(ftp, null, new File(pathFile + "\\" + fileName),reWrite,deleteLocalFile);
	}
	
	public static void uploadFTP(FTPClient ftp, String ftpFolder, String fileName,boolean reWrite,boolean deleteLocalFile) throws Exception{
		
		uploadFTP(ftp, ftpFolder, new File(pathFile + "\\" + fileName),reWrite,deleteLocalFile);
	}

	public static void uploadFTP(FTPClient ftp, File localFile,boolean reWrite,boolean deleteLocalFile) throws Exception{
	
		uploadFTP(ftp, null, localFile,reWrite, deleteLocalFile);
	
	}
	
	public static void uploadFTP(FTPClient ftp, String ftpFolder, File localFile,boolean reWrite,boolean deleteLocalFile) throws Exception{
		
		InputStream input =  null;
		try {
			
			if(ftpFolder != null && !ftpFolder.equals(""))
				ftp.changeWorkingDirectory(ftpFolder);
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if (localFile.exists())
			{
				if (reWrite)
				{
					localFile.delete();
					localFile.createNewFile();
				}
			}
			else
				localFile.createNewFile();
			
			input=new FileInputStream(localFile);		
			ftp.storeFile(localFile.getName(), input);

		} catch (Exception e) {
			throw new Exception("Errore nella scrittura dei file in FTP: "+ e.getMessage());
		}
		finally
		{
			if (input!= null)
				input.close();
			if (deleteLocalFile) deleteLocalFile(localFile.getName());
		}
	}
	
	public static File downloadFTP(FTPClient ftp, String fileName) throws Exception{
	
		return downloadFTP(ftp,null, fileName);
		
	}
	
	public static File downloadFTP(FTPClient ftp, String ftpFolder, String fileName) throws Exception{
		
		FileOutputStream fos=null;
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			
			File localFile = new File(pathFile + "\\" + fileName);
			localFile.delete();
			localFile.createNewFile();
			
			fos = new FileOutputStream(localFile);
			ftp.retrieveFile(localFile.getName(), fos);

			return localFile;
			
		} catch (Exception e) {
			throw new Exception("Errore nel download dei file in FTP: "+ e.getMessage());

		}
		finally
		{
			if (fos!=null) fos.close();
		}
	}
	
	public static void deleteFileFTP(FTPClient ftp, String fileName) throws Exception{
		
		deleteFileFTP(ftp, null, fileName);
		
	}
	
	public static void deleteFileFTP(FTPClient ftp, String ftpFolder, String fileName) throws Exception{
		try {
			
			if(ftpFolder != null && !ftpFolder.equals(""))
				ftp.changeWorkingDirectory(ftpFolder);
			
			ftp.deleteFile(fileName);
		} catch (Exception e) {
			throw new Exception("Errore nella cancellazione del file FTP: " + fileName + " ERRORE = "+ e.getMessage());
		}
	}
	
	public static void deleteLocalFile(String fileName) throws Exception{
		try {
			File file = new File(pathFile + "\\" + fileName); 
			file.setWritable(true);
			file.delete();
			
		} catch (Exception e) {
			throw new Exception("Errore nella cancellazione del file locale: " + fileName + " ERRORE = "+ e.getMessage());
		}
	}
	
}
