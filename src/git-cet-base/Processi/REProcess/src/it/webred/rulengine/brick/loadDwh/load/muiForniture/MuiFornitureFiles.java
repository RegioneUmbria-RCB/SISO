package it.webred.rulengine.brick.loadDwh.load.muiForniture;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.mui.AppProperties;
import it.webred.mui.XMLReader;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.FileUtils;

public class MuiFornitureFiles<T extends MuiFornitureEnv> extends ImportFilesFlat<T> {

	public MuiFornitureFiles(T env) {
		super(env);
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return null;
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {
		return null;
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return false;
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);
	}
	
	protected String elabora(Connection conn, Context ctx, String percorsoFiles, boolean saltaIntestazione) throws Exception {
		log.info("Cerco file da elaborare in percorsoFiles=" + percorsoFiles);
		Connection reConn = ctx.getConnection("DEFAULT");
		HashMap<String, String> appProperties = new HashMap<String, String>();
		appProperties.put(AppProperties.BELFIORE, ctx.getBelfiore());
		ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("dir.files.datiDiogene");
		AmKeyValueExt param = cdm.getAmKeyValueExt(criteria);
		appProperties.put(AppProperties.XSL_PATH, param.getValueConf() + ctx.getBelfiore() + "/modelloXlsCompravendite/MUI-TEST.xsl");
		try {
			ResultSet rs = reConn.prepareStatement("select * from r_connection where name = 'VIRGILIO_" + ctx.getBelfiore() + "'").executeQuery();
			while (rs.next()) {
				appProperties.put(AppProperties.DRIVER_CLASS, rs.getString("DRIVER_CLASS"));
				appProperties.put(AppProperties.CONN_STRING, rs.getString("CONN_STRING"));
				appProperties.put(AppProperties.USER_NAME, rs.getString("USER_NAME"));
				appProperties.put(AppProperties.PASSWORD, rs.getString("PASSWORD"));
			}
			
		} catch (SQLException e) {
			throw new RulEngineException("Errore reperimento connessione VIRGILIO_" + ctx.getBelfiore());
		}

		// unzip dei file
		String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(percorsoFiles);
		for (int i = 0; i < fs.length; i++) {
			File f = new File(percorsoFiles + fs[i]);
			if (FileUtils.isZip(f))  {
				FileUtils.unzip(f);
				boolean del = false;
				while (!del) {
					del = f.delete();
				}
			}
		}
		
		//lettura dei file
		fs = it.webred.utils.FileUtils.cercaFileDaElaborare(percorsoFiles);		
		List<String> fileDaElaborare = Arrays.asList(fs);  
		if(fileDaElaborare.size() < 1) {
			log.info("NO FILES!");
			return RETURN_NO_FILE;
		}
		// ordino in base ad un principio specifico
		sortFiles(fileDaElaborare);
		
		for (String f : fileDaElaborare) {
			File file = new File(percorsoFiles + f);
			XMLReader.read(file, appProperties);
			// creo cartella per mettere i file elaborati
			String percorsoFilesElaborati = percorsoFiles + "ELABORATI/";
			if (!new File(percorsoFilesElaborati).exists()) {
				new File(percorsoFilesElaborati).mkdir();
			}
			file.renameTo(new File(percorsoFilesElaborati + f));
			
			// traccia file forniture
			String cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/")+1);
			if (cartellaDati==null)
				cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("\\")+1);
			log.info("cartellaDati=" + cartellaDati);
			tracciaFornitura(f, cartellaDati, new String());
		}
		
		return OK;
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String[] arr = file.split("_");
		if(arr != null && arr.length > 1){
			String data = arr[2].substring(0,6);
			System.out.println(data);
			
			try {
				env.saveFornitura(sdf.parse(data), file);
			} catch (ParseException e) {	
			}
		}
		
		
	}
	
}
