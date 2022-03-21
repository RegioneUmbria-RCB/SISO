package it.webred.rulengine.brick.elab.ruoloTar;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;


public class IndicizzaPdfRuoliTaresTarsu extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(IndicizzaPdfRuoliTaresTarsu.class.getName());
	
	//nome della proprietà di configurazione
	public static final String DIR_PDF_RUOLI = "PdfRuoloTar";
	private String SQL_CREATE_SIT_RUOLO_TAR_PDF;
	
	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	private Connection conn=null; 
	private String enteID;
	
	
	public IndicizzaPdfRuoliTaresTarsu(BeanCommand bc) {
		super(bc);
	}

	public IndicizzaPdfRuoliTaresTarsu(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	
	private String getRootPathDatiDiogene()
	{
		ParameterService paramService = 
				(ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	    ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("dir.files.datiDiogene");
			criteria.setComune(null);
			criteria.setSection(null);
		
		try
		{
			if (paramService==null)
			{
				log.warn("ParameterService == NULL");
				return "";
			}
			
			List<AmKeyValueDTO> l = paramService.getListaKeyValue(criteria);
			if (l!=null && l.size()>0)
				return ((AmKeyValueDTO)l.get(0)).getAmKeyValueExt().getValueConf();
			else
			{
				log.warn(" LISTA RITORNO DA parameterService.getListaKeyValue VUOTA");
				return "";
			}
		}
		catch (Exception ex)
		{
			log.error(" doGetListaKeyValue ERRORE = " + ex.getMessage());
			return "";
		}

	}
	
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("IndicizzaPdfRuoliTaresTarsu run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		//recupero del path locale dell ente/fd
		String pathDatiDiogene = getRootPathDatiDiogene();
		String rootPathEnte= pathDatiDiogene + enteID;
		
		String path = rootPathEnte + File.separatorChar + DIR_PDF_RUOLI ;
		log.info("Percorso DIR_PDF_RUOLI:" +path);
		
	
		PreparedStatement pst =null; ResultSet rs =null; 
		try {
			this.getJellyParam(ctx);
			
			this.createTabelleRe(conn);
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
		
			File filesDati = new File(path);
			String[] listaFiles = filesDati.list();
			
			String sql = "INSERT INTO SIT_RUOLO_TAR_PDF VALUES(?,?,?,?,?,?,?,SYSDATE)";
			log.debug(sql);
			pst = conn.prepareStatement(sql);
		
			int id = 0;
			for(int i=0; i<listaFiles.length; i++){
				String nomeFile = listaFiles[i];
				
				if (nomeFile.startsWith("&")) {
					continue; //file da scartare
				}
				id++;
				
				String n = nomeFile.substring(0,nomeFile.indexOf(".pdf"));
				String[] ss = n.split("_");
				String cf = ss[0];
				String cu = ss[1];
				String anno = ss[2];
				String mese = ss[3];
				String tipoTributo = "TARI";
				if (Integer.parseInt(anno) < 2014) {
					tipoTributo = "TARES";
				} else if (Integer.parseInt(anno) > 2014) {
					if (ss.length > 4) {
						tipoTributo = ss[4].toUpperCase();
					}
				}
				
				try {
					pst.setInt(1,id);
					pst.setString(2, cf);
					pst.setInt(3, Integer.parseInt(cu));
					pst.setInt(4, Integer.parseInt(anno));
					pst.setInt(5, Integer.parseInt(mese));
					pst.setString(6, tipoTributo);
					pst.setString(7, nomeFile);					
					
					pst.executeUpdate();
				} catch (SQLException e) {
					log.warn("Impossibile inserire il record "+nomeFile);
					log.error(e.getMessage(),e);	
				}
			}
			
		}catch (SQLException e) {
			log.error("IndicizzaPdfRuoliTaresTarsu - ERRORE SQL " + e, e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		} catch (StringIndexOutOfBoundsException e) {
			log.error("IndicizzaPdfRuoliTaresTarsu - ERRORE file " + e, e);
			ErrorAck ea = new ErrorAck("La cartella deve contenere solo file pdf - non cartelle - no altri files");
			return ea;
		}catch(Exception eg){
			log.error("IndicizzaPdfRuoliTaresTarsu - ERRORE Generico " + eg, eg);
			ErrorAck ea = new ErrorAck(eg);
			return ea;
		}finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
			catch (SQLException sqle) {
				log.error("IndicizzaPdfRuoliTaresTarsu - ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
		
		
		retAck = new ApplicationAck("IndicizzaPdfRuoliTaresTarsu - ESECUZIONE OK");
		return retAck;
	}


	private void createTabelleRe(Connection con){
	
		String drop ="DROP TABLE SIT_RUOLO_TAR_PDF";
		this.eseguiScript(con, drop);
		this.eseguiScript(con, this.SQL_CREATE_SIT_RUOLO_TAR_PDF);
		
		String idx1 = "CREATE INDEX IDX_RUOLO_TAR_PDF_CF ON SIT_RUOLO_TAR_PDF (CODFISC)";
		String idx2 = "CREATE INDEX IDX_RUOLO_TAR_PDF_CU ON SIT_RUOLO_TAR_PDF (CU)";
		String idx3 = "CREATE INDEX IDX_RUOLO_TAR_PDF_CFCU ON SIT_RUOLO_TAR_PDF (CODFISC,CU)";
		String idx4 = "CREATE UNIQUE INDEX SIT_RUOLO_TAR_PDF_PK ON SIT_RUOLO_TAR_PDF(ID)";
		String idx5 = "ALTER TABLE SIT_RUOLO_TAR_PDF ADD "
					+ "(CONSTRAINT SIT_RUOLO_TAR_PDF_PK PRIMARY KEY (ID) USING INDEX SIT_RUOLO_TAR_PDF_PK)";

		this.eseguiScript(con,idx1);
		this.eseguiScript(con,idx2);
		this.eseguiScript(con,idx3);
		this.eseguiScript(con, idx4);
		this.eseguiScript(con, idx5);
	}
	
	private void eseguiScript(Connection con, String sql){
		Statement st=null;
		try {
			st = con.createStatement();
			log.debug(sql);
			st.execute(sql);
		} catch (SQLException e1) {
			
			log.warn(e1.getMessage());
			
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
	}
	
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.debug("*******************************INIZIO CARICAMENTO PARAMETRI****************************************");
			
			this.SQL_CREATE_SIT_RUOLO_TAR_PDF = getJellyParam(ctx, index, "SQL_CREATE_SIT_RUOLO_TAR_PDF");
			
			log.debug("*******************************FINE CARICAMENTO PARAMETRI****************************************");
			
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	
	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
		String variabile = "";
		
		log.info("rengine.rule.param.in."+index+".descr");
		
		ComplexParam paramSql= (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+index+".descr"));
		
		HashMap<String,ComplexParamP> p = paramSql.getParams();
		Set set = p.entrySet();
		Iterator it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP cpp = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
			variabile = o.toString();
		}
		
		log.info("Query "+desc+" da eseguire:"+ variabile);
		
		return variabile;
	}

}
