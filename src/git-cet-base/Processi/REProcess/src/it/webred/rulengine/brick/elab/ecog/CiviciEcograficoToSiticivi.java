package it.webred.rulengine.brick.elab.ecog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.ct.data.access.basic.ici.IciService;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.access.basic.tarsu.TarsuService;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiTitolariUI;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

public class CiviciEcograficoToSiticivi extends Command implements Rule {

		private static final Logger log = Logger.getLogger(CiviciEcograficoToSiticivi.class.getName());
		private Connection conn=null; 
		private String enteID;
		
		private String SQL_VERIFICA_CIVICI_DOPPI;
		private String SQL_TRUNCATE_SITILOC_UIU;
		private String SQL_DELETE_SITICIVI;
		private String SQL_LOAD_SITICIVI;
		private final String SQL_COUNT_SITICIVI = "select count(*) from siticivi";
		private final String SQL_COUNT_SITICIVI_UIU = "select count(*) from siticivi_uiu";
		private final String SQL_COUNT_UIU_ATTUALI = "select count(*) from sitiuiu where data_fine_val = to_date('99991231','yyyymmdd')";
		
		//uiu cui è associato un civico
		private final String SQL_COUNT_UIU_CIVICO = "select count(*) from(select distinct pkid_uiu from siticivi_uiu where data_fine_val = to_date(\'99991231\',\'yyyymmdd\'))";
	
		public CiviciEcograficoToSiticivi(BeanCommand bc) {
			super(bc);
		}

		public CiviciEcograficoToSiticivi(BeanCommand bc, Properties jrulecfg) {
			super(bc, jrulecfg);
		}

		@Override
		public CommandAck run(Context ctx) throws CommandException {
			log.debug("CiviciEcograficoToSiticivi run()");
			CommandAck retAck = null; 
			enteID= ctx.getBelfiore();
			log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
			conn = ctx.getConnection((String)ctx.get("connessione"));
			
			Connection connSiti  = ctx.getConnection("SITI_" + enteID.toUpperCase());
			
			PreparedStatement pst =null; ResultSet rs =null; Statement st =null;

			try {
				
				this.getJellyParam(ctx);
				
				pst = conn.prepareStatement(SQL_VERIFICA_CIVICI_DOPPI);
				rs = pst.executeQuery();
				String si_no = rs.next() ? " " : " non ";
				log.info("La tabella dell'ecografico catastale"+ si_no +"contiene civici duplicati.");
				
				log.info("Connessione SITI: " + connSiti.getMetaData().getUserName());
				
				pst = connSiti.prepareStatement(SQL_TRUNCATE_SITILOC_UIU);   //Esegue su SITI!
				pst.executeUpdate();
				log.info("La tabella SITILOC_UIU è stata svuotata.");
				
				pst = conn.prepareStatement(SQL_DELETE_SITICIVI);
				pst.executeUpdate();
				log.info("La tabella SITICIVI è stata svuotata.");
				
				pst = conn.prepareStatement(SQL_LOAD_SITICIVI);
				pst.executeUpdate();
				log.info("La tabella SITICIVI è stata caricata con i dati dell'ecografico catastale.");
				
				pst = conn.prepareStatement(SQL_COUNT_SITICIVI);
				rs = pst.executeQuery(); rs.next();
				log.info("La tabella SITICIVI contiene " + rs.getInt(1) + " record");
				
				pst = conn.prepareStatement(SQL_COUNT_SITICIVI_UIU);
				rs = pst.executeQuery(); rs.next();
				log.info("La tabella SITICIVI_UIU contiene " + rs.getInt(1) + " record");
				
				pst = conn.prepareStatement(SQL_COUNT_UIU_ATTUALI);
				rs = pst.executeQuery(); rs.next();
				log.info("Numero di uiu in SITIUIU attuali " + rs.getInt(1));
				
				pst = conn.prepareStatement(SQL_COUNT_UIU_CIVICO);
				rs = pst.executeQuery(); rs.next();
				log.info("Numero di Uiu cui è associato un civico: " + rs.getInt(1));
				
				retAck = new ApplicationAck("ESECUZIONE OK");
				return retAck;
				
			}catch (SQLException e) {
				log.error("ERRORE SQL " + e, e);
				ErrorAck ea = new ErrorAck(e);
				return ea;
			}catch(Exception eg){
				log.error("ERRORE Generico " + eg, eg);
				ErrorAck ea = new ErrorAck(eg);
				return ea;
			}finally {
				try {
					DbUtils.close(rs);
					DbUtils.close(pst);
				}
				catch (SQLException sqle) {
					log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
				}

			}
			
		}
		
		private void getJellyParam(Context ctx) throws Exception {
			try {
				
				int index = 1;
				//log.debug("**************************************************************rengine.rule.param.in."+index+".descr");
				this.SQL_VERIFICA_CIVICI_DOPPI = getJellyParam(ctx, index++, "SQL_VERIFICA_CIVICI_DOPPI");
				this.SQL_TRUNCATE_SITILOC_UIU = getJellyParam(ctx, index++, "SQL_TRUNCATE_SITILOC_UIU");
				this.SQL_DELETE_SITICIVI = getJellyParam(ctx, index++, "SQL_DELETE_SITICIVI");
				this.SQL_LOAD_SITICIVI = getJellyParam(ctx, index++, "SQL_LOAD_SITICIVI");
				
				
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
