package it.webred.ct.rulengine.web.bean.util;

import it.webred.ct.rulengine.web.bean.ControllerBaseBean;
import it.webred.ct.rulengine.web.bean.monitor.MonitorBaseBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import org.apache.log4j.Logger;

public class UploadBean extends ControllerBaseBean implements
		Serializable {

	private static Logger logger = Logger.getLogger(UploadBean.class
			.getName());
	
	private File allegato;
	private boolean visBtnConferma;
	private String fileName;
	private String parameterPath;

	public String getParameterPath() {
		return parameterPath;
	}

	public void setParameterPath(String parameterPath) {
		this.parameterPath = parameterPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getAllegato() {
		return allegato;
	}

	public void setAllegato(File allegato) {
		this.allegato = allegato;
	}

	public boolean isVisBtnConferma() {
		return visBtnConferma;
	}

	public void setVisBtnConferma(boolean visBtnConferma) {
		this.visBtnConferma = visBtnConferma;
	}

	public String initPanel() {
		String esito = "success";
		doInitPanel();
		return esito;
	}

	private void doInitPanel() {
		allegato = null;
		visBtnConferma = false;
	}

	/**
	 * Prepara il caricamento di un file
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void listener(UploadEvent event) throws Exception {
		logger.debug("Uploading file listener");
		UploadItem item = event.getUploadItem();
		allegato = item.getFile();
		visBtnConferma = true;

		// Inserita per gestire il diverso comportamento di Explorer
		// nell'estrazione del nome del file da UploadItem
		File tempFile = new File(item.getFileName());
		fileName = tempFile.getName();
		logger.debug("Uploading file listener. fileName to upload: " + fileName);
	}

	/**
	 * Effettua il caricamento dell'allegato nel file system
	 */
	public File doUpload() {
		logger.debug("Uploading file");

		if (allegato == null) {
			super.addErrorMessage("allegato.empty", "");
			return null;
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;
		File fout =null;
		try {

			String name = getFilePath(fileName);

			logger.debug("File [" + name + "]");

			fout = new File(name);

			fis = new FileInputStream(allegato);
			fos = new FileOutputStream(fout);

			byte[] buff = new byte[1024];

			while (fis.read(buff) != -1)
				fos.write(buff);

			super.addInfoMessage("allegato.crea");

			doInitPanel();
			
		} catch (Throwable t) {
			super.addErrorMessage("allegato.crea.error", t.getMessage());
			logger.error(t.getMessage(),t);
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (fis != null)
					fis.close();
				return fout;
			} catch (Throwable t) {
				super.addErrorMessage("allegato.crea.error", t.getMessage());
				logger.error(t.getMessage(),t);
				return null;
			}
			
		}
	}

	/**
	 * Compone il nome del file passato a parametro, con il percorso di
	 * directory in cui risiedono gli allegati.
	 * 
	 * @param fileName
	 *            Nome del file completo
	 * @return Percorso del file completo sul file system
	 */
	private String getFilePath(String fileName) {
		String path = parameterPath;
		createDirectoryPath(path);

		String pathFile = path + File.separatorChar + fileName;
		return pathFile;
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	public void createDirectoryPath(String path) {
		// Se non esiste il percorso, lo crea
		File dir = new File(path);
		try {
			if (!dir.exists())
				dir.mkdirs();
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
		}
	}

}
