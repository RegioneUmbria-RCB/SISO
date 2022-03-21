package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public abstract class EnvImport {
	
	private Logger log = Logger.getLogger(EnvImport.class.getName());
	
	private  List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
	private String connectionName;
	private Context ctx;
	private Connection conn;
	private BeanEnteSorgente enteSorgente;
	
	public EnvImport(String fonteDati, String connectionName,Context ctx ) throws RulEngineException {
		this.connectionName = connectionName;
		this.ctx = ctx;

		this.enteSorgente = this.ctx.getEnteSorgenteById(new Integer(fonteDati));
		
		
		
		try {
			// prendo quella del contesto con la autocommit false
			this.conn = ctx.getConnection(connectionName);
		} catch (Exception e) {
			throw new RulEngineException("Impossibile reperire al connessione " + connectionName , e);
		}
	}
	
	public BeanEnteSorgente getEnteSorgente() {
		return enteSorgente;
	}

	public abstract String getPercorsoFiles();
	
	public String getConnectionName() {
		return connectionName;
	}
	public Connection getConn() {
		return conn;
	}
	public  Context getCtx() {
		return ctx;
	}
	
	public void setAbnormals(List<RAbNormal> abnormals) {
		this.abnormals = abnormals;
	}

	
	
	public List<RAbNormal> getAbnormals() {
		return abnormals;
	}



	public String getProperty(String propName) {
		
		String p =  DwhUtils.getProperty(this.getClass(), propName);
		return p;
	}

	
	/**
	 * Il metodo recupera il valore di un param di config di un ente per una FD,
	 * impostato su AMProfiler
	 * 
	 * @param key
	 * @param belfiore
	 * @param idFonte
	 * @return
	 */
	public String getConfigProperty(String key, String belfiore, Long idFonte) {
		String value = null;
		
		try {
			//recupero del path locale dell ente/fd
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(key, belfiore, idFonte.toString());
			if (param != null){
				log.debug("Parametro richiesto ["+key+"]: "+param.getValueConf());
				value = param.getValueConf();
			}else{
				log.debug("Parametro richiesto ["+key+"] INESISTENTE!!!");
			}
		}catch(Exception e) {
			log.error("Eccezione config: "+e.getMessage(),e);
		}
		
		return value;
	}
	
	/**
	 * Il metodo traccia il file della fornitura con la data specificata nel file
	 */
	public void saveFornitura(Date data, String nomeFile) {
		
		if(data != null || nomeFile != null){
			try {
				
				Connection c = BasicLogic.getConnection();
				String sql = "INSERT INTO R_TRACCIA_FORNITURE " +
						"(BELFIORE, ID_FONTE, ISTANTE, PROCESSID, DATA, NOME_FILE)" +
						" VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement st = c.prepareStatement(sql);
				st.setString(1, this.ctx.getBelfiore());
				st.setInt(2, this.ctx.getIdFonte().intValue());
				st.setLong(3, Calendar.getInstance().getTimeInMillis());
				st.setString(4, this.ctx.getProcessID());
				st.setTimestamp(5, new Timestamp(data.getTime()));
				st.setString(6, nomeFile);
				
				int upd = st.executeUpdate();
				st.close();
				c.close();
				
				if(upd <= 0)
					log.warn("_______ ! Errore su traccia file fornitura (nessuna riga aggiornata): " + nomeFile);
				
			}catch(Exception e) {
				log.error("Eccezione config: "+e.getMessage(),e);
			}
		}
	}
}
