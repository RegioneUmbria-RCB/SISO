package it.webred.rulengine.brick.reperimento.impl;

import java.util.Properties;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.cataloghi.ValoreMedioRenditaVano;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReperimentoRemoteImpl extends AbstractReperimentoCommand {

	private static final Logger log = Logger.getLogger(ReperimentoRemoteImpl.class.getName());
	// nome della proprietà di configurazione
	private static final String FTP_FILE_KEY = "ftp.files";

	public ReperimentoRemoteImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte,
			String reProcessId,  Context ctx) throws CommandException {

		log.debug("Reperimento FD remoto");
		CommandAck ack = null;

		// recupero del path remoto dell ente/fd
		ParameterService cdm = (ParameterService) ServiceLocator.getInstance()
				.getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		AmKeyValueExt paramRemote = cdm.getAmKeyValueExtByKeyFonteComune(
				FTP_FILE_KEY, belfiore, idFonte.toString());
		log.debug("Directory reperimento remoto file: "
				+ paramRemote.getValueConf());

		// recupero del path locale dell ente/fd
		AmKeyValueExt paramLocal = cdm
				.getAmKeyValueExtByKeyFonteComune(
						ReperimentoLocalImpl.DIR_FILE_KEY, belfiore, idFonte
								.toString());
		log.debug("Directory reperimento locale file: "
				+ paramLocal.getValueConf());

		//FORMATO parametro: user:pass@127.0.0.1:21
		String[] connectionData = paramRemote.getValueConf().split("@", -1);
		String[] userData = connectionData[0].split(":", -1);
		String[] ftpData = connectionData[1].split(":", -1);
		String[] ftpPortPath = ftpData[1].split("/", -1);

		FTPClient client = new FTPClient();
		FileOutputStream fos = null;

		try {
			client.connect(ftpData[0], Integer.parseInt(ftpPortPath[0]));
			client.login(userData[0], userData[1]);
			if(ftpPortPath.length > 1)
				client.changeWorkingDirectory(ftpPortPath[1]);

			// controllo presenza fd
			FTPFile[] ftpFiles = client.listFiles();
			if (ftpFiles.length == 0) {
				ack = new NotFoundAck("La directory non contiene file");
			} else {
				ack = new ApplicationAck("File presenti ma già elaborati");
			}

			for (FTPFile ftpFile : ftpFiles) {
				if (ftpFile == null) continue;
				String filename = ftpFile.getName();
				// controllo se è già stato elaborato
				File f = new File(paramLocal.getValueConf() + "ELABORATI/" + filename);
				if (filename.contains(".") && !f.exists()) {
					fos = new FileOutputStream(paramLocal.getValueConf()
							+ ftpFile.getName());
					//
					// Download file from FTP server
					//
					boolean retrieveOk = client.retrieveFile(filename, fos);
					if (!retrieveOk) {
						retrieveOk = client.retrieveFile("/" + filename, fos);
					}

					if (!retrieveOk)  {
						String msg = "File NON RECUPERATO !! : " + filename;
						log.error("File NON RECUPERATO !! : " + filename);
						ack = new ErrorAck(msg);
					} else	
						log.debug("File recuperato: " 	+ filename);
					
					ack = new ApplicationAck("File recuperati");
				}
			}

			client.logout();


		} catch (IOException e) {
			log.error(e);
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return ack;
	}
	
	
    public static void main(String[] args) 
    {
    	
    	//FORMATO parametro: user:pass@127.0.0.1:21
    		

    			FTPClient client = new FTPClient();
    			FileOutputStream fos = null;

    			try {
    				client.connect("www.monzaservizionline.it", 21);
    				client.login("mz_pagmense", "mz_pagmense");
   					boolean b = client.changeWorkingDirectory("/import");

    				// controllo presenza fd
    				FTPFile[] ftpFiles = client.listFiles();
    			

    				client.logout();


    			} catch (IOException e) {
    				log.error(e);
    			} finally {
    				try {
    					client.disconnect();
    				} catch (IOException e) {
    					log.error(e);
    				}
    			}
    	
    }

}
