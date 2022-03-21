package it.webred.rulengine.brick.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.dto.StagingDTO;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

public class RsGetBlob extends Command implements Rule {

	private static Logger log = Logger.getLogger(RsGetBlob.class.getName());

	public RsGetBlob(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		ResultSet rs = null;
		Connection connDWH = null;
		Connection connSTAGING = null;
		String stagingRoot = null;
		String belfiore = null;
		
		try {
			belfiore = ctx.getBelfiore();

			log.debug("Recupero parametro da contesto");
			connDWH = ctx.getConnection((String)ctx.get("connessione"));
			
			String sqlStaging = getRuleStringParam(ctx,1);
			String sqlDwhTrace = getRuleStringParam(ctx,2);
			String sqlDwhTraceSave = getRuleStringParam(ctx,3);
			
			//recupero connessione privata STAGING database
			connSTAGING = RulesConnection.getConnection("STAGING_"+belfiore);
			PreparedStatement pstmt = connSTAGING.prepareStatement(sqlStaging);
			pstmt.setString(1, belfiore);
			//pstmt.setString(1, "F858");
			rs = pstmt.executeQuery();
			
			//recupero path da AM_PROFILER
			log.debug("Recupero root staging path da configurazione");
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			
			criteria.setComune(belfiore);
			criteria.setKey("dir.files.staging");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExt(criteria);
			if(amkvext != null) {
				stagingRoot = amkvext.getValueConf();
				log.debug("Root staging files: "+stagingRoot);
			}
			else {
				throw new Exception("Nessuna cartella root configurata");
			}
			
			log.debug("Recupero BLOB e salvataggio su path");
			while (rs.next()) {
				String servizio = rs.getString("TI02_KEYSERVIZIO");
				String currPath = stagingRoot + File.separator + servizio + File.separator;
				//se non esiste lo creo
				File cp = new File(currPath);
				if(!cp.exists()) {
					log.debug("La cartella non esiste quindi viene creata ["+currPath+"]");
					cp.mkdir();
				}
				
				//recupero nome file zip
				String nomefile = rs.getString("NOME_FILE");
				log.debug("Nome del file zip: "+nomefile);
				
				//controllo scarico già effettuato
				boolean exist = fileExist(connDWH,sqlDwhTrace,nomefile);
				if(exist) 
					continue;
				
				//recupero blob
				Blob blob = rs.getBlob("ZIPBLOB");
				int length = (int) blob.length();
				//log.debug("Body Size = "+length);
				
				OutputStream os = new FileOutputStream(currPath+nomefile);
				InputStream in = blob.getBinaryStream();
				byte[] bb = new byte[length];
				int read = in.read(bb);
				//log.debug(new String(bb));
				os.write(bb);
				os.flush();
				os.close();
				in.close();
				log.debug("File scritto su filesystem ["+read+" bytes]");
				
				//in base a flag presenza scrive il file zip su filesystem
				StagingDTO s = new StagingDTO();
				s.setIdFile(rs.getLong("TI11_IDFILE"));
				s.setProgIscrizione(rs.getString("TI04_PROGISCRIZIONE"));
				s.setKeyDett(rs.getLong("TI04_KEYDETT"));
				s.setPeriodoDa(rs.getDate("TI04_PERIODO_DA").getTime());
				s.setPeriodoA(rs.getDate("TI04_PERIODO_A").getTime());
				s.setFlgAss(rs.getLong("TI04_FLAG_ASS"));
				s.setServizio(new Long(servizio));
				s.setNomeFile(nomefile);
				
				saveStagingTrace(connDWH,sqlDwhTraceSave,s);
			}

			//retAck = new ApplicationAck("Impostazioni invio email esito diagnostica");
			retAck = new ApplicationAck("File di staging recuperati con successo ");
			
		} catch (Exception e) {
			log.error("Eccezione elaborazione blob: " + e.getMessage());
			retAck = new ErrorAck(e);
		} finally {
			if (rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.error("Non si è riuscito a chiudere il resultset nel ResultSetIterator",e);
				}
			}
			
			if(connSTAGING != null) {
				try {
					if(!connSTAGING.isClosed()) 
						connSTAGING.close();
				}catch(SQLException sqle) {
					log.error("Problemi nella chiusura della connessione a STAGING_"+belfiore,sqle);
				}
			}
		}

		return retAck;
	}
	
	
	
	private String getRuleStringParam(Context ctx, int numParam) throws Exception {
		String value  = null;
		
		ComplexParam param = 
			(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+numParam+".descr"));
		
		HashMap<String,ComplexParamP> par = param.getParams();
		Set set = par.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP p = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(p.getValore(),p.getType());
			value = o.toString();
		}
		
		log.debug("Valore parametro "+numParam+":\n"+value);
		
		return value;
	}
	
	
	/**
	 * Controllo sulla tabella degli scarichi staging dell'ente se il file è presente
	 * 
	 * @param connDWH
	 * @param sqlDwhTrace
	 * @param nomefile
	 * @return
	 * @throws Exception
	 */
	private boolean fileExist(Connection connDWH, String sqlDwhTrace,String nomefile) throws Exception {
		boolean esito = false;
		
		PreparedStatement ps = connDWH.prepareStatement(sqlDwhTrace);
		ps.setString(1,nomefile);
		ResultSet rsDwh = ps.executeQuery();
		if(rsDwh.next()) {
			esito = true;
			log.debug("Attenzione !! Il file '"+nomefile+"' è stato già scaricato");
		}
		
		ps.close();
		
		return esito;
	}
	
	
	private void saveStagingTrace(Connection connDWH, String sqlDwhTraceSave,StagingDTO staging) throws Exception {
		PreparedStatement ps = connDWH.prepareStatement(sqlDwhTraceSave);
		
		ps.setLong(1, staging.getIdFile());
		ps.setString(2, staging.getProgIscrizione());
		ps.setLong(3, staging.getKeyDett());
		ps.setDate(4, new java.sql.Date(staging.getPeriodoDa()));
		ps.setDate(5, new java.sql.Date(staging.getPeriodoA()));
		ps.setLong(6, staging.getFlgAss());
		ps.setLong(7, staging.getServizio());
		ps.setString(8,staging.getNomeFile());
		
		ps.executeUpdate();
		ps.close();
	}

}
