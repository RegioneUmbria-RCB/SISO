package it.webred.rulengine.brick.reperimento.impl.curit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.attachment.Attachment;
import com.gargoylesoftware.htmlunit.attachment.CollectingAttachmentHandler;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.loadDwh.load.curit.CuritTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.curit.CuritTipoRecordFiles;
import it.webred.rulengine.brick.loadDwh.load.curit.bean.Testata;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ReperimentoCuritImpl extends AbstractReperimentoCommand implements Rule {
	
	public static final String DIR_FILE_KEY = "dir.files";
	
	public ReperimentoCuritImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte, String reProcessId, Context ctx) throws CommandException {
		log.info("Reperimento dati CURIT...");
		
		CommandAck ack = null;
		Connection conn = null;		
		String message = null;
		
		final WebClient webClient = new WebClient();
		
		try {
			conn = RulesConnection.getConnection("DWH_" + belfiore);
			
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setSSLClientProtocols(new String[] {"TLSv1.2","TLSv1.1"});
			
			String codIstat = null;
			String sql = "SELECT COD_ISTAT FROM SIT_ENTE WHERE CODENT = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, belfiore);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				codIstat = rs.getString("COD_ISTAT");
			}
			rs.close();
			pst.close();
			
			String url = "https://www.dati.lombardia.it/resource/d7i4-7rpy.csv?$limit=5000000&ubicazione_codice_istat=" + codIstat;
			
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(DIR_FILE_KEY, belfiore, idFonte.toString());			
			String pathTo = param.getValueConf();
			String pathToOrig = pathTo + "/ORIG";
			
			File fOrig = new File(pathToOrig);
			if (!fOrig.exists()){
				boolean creataOrig = fOrig.mkdir();
				if (creataOrig){
					log.info("Cartella ORIG creata con successo");				
				}else{
					log.info("Impossibile creare cartella ORIG");
				}
			}else{
				log.info("Cartella ORIG gia esistente");
			}
			
			String nomeFile = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_curit_" + belfiore + ".csv";
			String nomeFileOrig = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_curit_" + belfiore + "_ORIG.csv";
			
			InputStream is = null;
	 	    OutputStream os = null;	 	    
			final List<Attachment> attachments = new ArrayList<Attachment>();
 	        webClient.setAttachmentHandler(new CollectingAttachmentHandler(attachments));
 	        final Page page = webClient.getPage(url);
	        final WebResponse attachmentResponse = page.getWebResponse();
	        is = attachmentResponse.getContentAsStream();
	    	os = new FileOutputStream(new File(pathToOrig, nomeFileOrig));
	    	int read = 0;
	    	byte[] bytes = new byte[1024];
	    	while ((read = is.read(bytes)) != -1) {
	    		os.write(bytes, 0, read);
	    	}	    	
	        os.close();
	        is.close();
	    	
	        BufferedReader br = new BufferedReader(new FileReader(new File(pathToOrig, nomeFileOrig)));
	        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(pathTo, nomeFile)));
			String currentLine = null;
			boolean nl = false;
			while ((currentLine = br.readLine()) != null) {
				 if (doWriteTo(ctx, conn, currentLine)) {
					 if (nl) {
						 bw.newLine();
					 }
					 bw.append(currentLine);
					 nl = true;
				 }
			}
			bw.close();
			br.close();
	    	
	    	ack = new ApplicationAck("Procedura di reperimento dati CURIT eseguita correttamente");    	
		} catch (Exception e) {
			message = "Errore di esecuzione del reperimento dati CURIT";
			log.error(message, e);
			ack = new ErrorAck(message);			 
		} finally {
			webClient.close();
			
			try	{
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				log.debug(e);
			}
		}

		return ack;
	}
	
	protected boolean doWriteTo(Context ctx, Connection conn, String currentLine) throws Exception {
		CuritTipoRecordEnv env = new CuritTipoRecordEnv((String)ctx.get("connessione"), ctx);
		CuritTipoRecordFiles<CuritTipoRecordEnv<Testata>> curit = new CuritTipoRecordFiles<CuritTipoRecordEnv<Testata>>(env);
		List<String> values = curit.getValoriFromLine(null, currentLine);
		String key = values != null && values.size() > 0 ? values.get(0) : "";
		String sql = "SELECT * FROM RE_CURIT WHERE IDENTIFICATIVO_IMPIANTO = ? ORDER BY DT_FILE_CSV DESC";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, key);
		ResultSet rs = pst.executeQuery();
		boolean write = false;
		List<String> adds = new ArrayList<String>();
		if (rs.next()) {
			//controllo su tutti i campi (sono tutti varchar2) meno gli ultimi 4 (DT_FILE_CSV più i tre campi di servizio)
			int numCampi = rs.getMetaData().getColumnCount() - 4;
			try {
				for (int i = 0; i < numCampi; i++) {
					//qui base 1:
					String rsVal = rs.getObject(i + 1) == null ? "" : rs.getString(i + 1).trim();
					//qui base 0:
					String rowVal = values.get(i) == null ? "" : values.get(i).trim();
					
					if (!rsVal.equals(rowVal)) {
						write = true;
						break;
					}					
				}
			} catch (Exception e) {
				//si aggiunge comunque la riga al file, delegando ulteriori controlli alla procedura di acquisizione
				write = true;
			}
		} else {
			write = true;
		}
		rs.close();
		pst.close();
		if (!write) {
			for (String add : adds) {
				//se è stata già scritta una riga per lo stesso identificativo, per esso scrivo comunque tutte le righe
				if (add.equals(key)) {
					write = true;
					break;
				}
			}
		}
		if (write) {
			adds.add(key);
		}
		return write;
	}

}
