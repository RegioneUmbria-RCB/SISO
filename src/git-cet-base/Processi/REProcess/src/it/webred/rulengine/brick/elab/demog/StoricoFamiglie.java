package it.webred.rulengine.brick.elab.demog;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
//import it.webred.rulengine.type.ComplexParam;
//import it.webred.rulengine.type.bean.ComplexParamP;
//import it.webred.rulengine.type.def.TypeFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Properties;
//import java.util.Set;
//import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class StoricoFamiglie extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(StoricoFamiglie.class.getName());
	private Connection conn=null; 
	private String enteID;
	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	private int COLS_SIT_D_FAM_STORICO = 17;
	private BufferedWriter writer;
	private List<String> lstAnomaliaPf5 = new ArrayList<String>();
	private List<String> lstAnomaliaPf6 = new ArrayList<String>();
	
	public StoricoFamiglie(BeanCommand bc) {
		super(bc);
	}

	public StoricoFamiglie(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("StoricoFamiglia run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		try {
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
			this.getJellyParam(ctx);
			
			preProcessSitDPersona(conn, ctx);
			preProcessSitDPersFam(conn, ctx);
			elaboraStoricoFamiglie(conn);
			
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
		}finally{
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
			}
		}
	}
	
	private void elaboraStoricoFamiglie(Connection conn) throws Exception{
		
		PreparedStatement pstFamiglie=null;
		PreparedStatement pstDate=null;
		PreparedStatement pstComp=null;
		PreparedStatement pst =null; 
		PreparedStatement psUpdate = null;
		PreparedStatement psInfo=null;
		
		ResultSet rsInfo=null; 
		ResultSet rsFamiglie=null; 
		ResultSet rsDate=null; 
		ResultSet rsComp =null; 

		String SQL_SELECT_DATE_FAMIGLIA_COND =
				"from sit_d_pers_fam_st pf , sit_d_persona_st p "
				+ "where pf.id_ext_d_famiglia=:idExtFam and pf.id_ext_d_persona=p.id_ext_d_persona ";
		
		String SQL_TRUNCATE_SIT_D_FAM_STORICO = "truncate table sit_d_fam_storico";
		
		String SQL_SELECT_FAMIGLIE ="select  distinct id_ext_d_famiglia id_ext from sit_d_pers_fam_st "
				+ "where id_ext_d_famiglia not in (select id_ext_d_famiglia from sit_d_fam_storico) "
				+ "group by id_ext_d_famiglia order by count(*) desc ";
				//"select distinct id_ext_d_famiglia id_ext from sit_d_pers_fam_st";
		
		String SQL_SELECT_DATE_FAMIGLIA="select distinct dt, to_char(dt,'dd/MM/yyyy') dt_str from( " +
				"select nvl(pf.dt_inizio_dato,to_date('01/01/0001','dd/MM/yyyy')) dt " + SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select nvl(pf.dt_fine_dato,sysdate) dt " + SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select nvl(p.data_imm,to_date('01/01/0001','dd/MM/yyyy')) dt " + SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select nvl(p.data_emi,sysdate) dt " + SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select nvl(p.data_nascita,to_date('01/01/0001','dd/MM/yyyy')) dt " + SQL_SELECT_DATE_FAMIGLIA_COND + " and P.DATA_NASCITA>= nvl(PF.DT_INIZIO_DATO,to_date('01/01/0001','dd/MM/yyyy')) and P.DATA_NASCITA>= nvl(P.DaTa_INIZIO_RESIDENZA,to_date('01/01/0001','dd/MM/yyyy')) "+
				"union all "+
				"select nvl(p.data_mor,sysdate)  dt " + SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select nvl(p.data_inizio_residenza,to_date('01/01/0001','dd/MM/yyyy'))  dt " + SQL_SELECT_DATE_FAMIGLIA_COND + "and  NVL (P.data_inizio_residenza,TO_DATE ('01/01/0001', 'dd/MM/yyyy')) >=NVL (PF.DT_INIZIO_DATO,TO_DATE ('01/01/0001', 'dd/MM/yyyy'))"+
				")order by dt";
		
		String CAMPI_COMUNI_INFO = " p.id_ext_d_persona, p.n_ord  np, pf.n_ord npf ";
		
		String SQL_SELECT_INFO_DATE_PERSONA="select distinct dt, to_char(dt,'dd/MM/yyyy') dt_str, evento, np, npf from( " +
				"select pf.dt_inizio_dato dt , 'INIZIO_FAM' evento , " +CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select pf.dt_fine_dato dt, 'FINE_FAM' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select p.data_imm dt, 'IMM' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select p.data_emi dt, 'EMI' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select p.data_nascita dt, 'NASCITA' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + " and P.DATA_NASCITA>= nvl(PF.DT_INIZIO_DATO,to_date('01/01/0001','dd/MM/yyyy')) and P.DATA_NASCITA>= nvl(P.DaTa_INIZIO_RESIDENZA,to_date('01/01/0001','dd/MM/yyyy')) "+
				"union all "+
				"select p.data_mor  dt, 'MORTE' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + 
				"union all "+
				"select p.data_inizio_residenza  dt, 'INIZIO_RES' evento , " + CAMPI_COMUNI_INFO+ SQL_SELECT_DATE_FAMIGLIA_COND + "and  NVL (P.data_inizio_residenza,TO_DATE ('01/01/0001', 'dd/MM/yyyy')) >=NVL (PF.DT_INIZIO_DATO,TO_DATE ('01/01/0001', 'dd/MM/yyyy'))"+
				") where dt is not null and id_ext_d_persona=:id_ext and dt<=TO_DATE(:dtRif,'dd/MM/yyyy') order by np, npf, dt ";
		
		
		String SQL_SELECT_COMPONENTI_FAM_ALLA_DATA=
				"SELECT DISTINCT " +
				"p2.id_ext_d_persona||'|'||pf2.id_ext_d_famiglia||'|'||TO_CHAR(TO_DATE(:dtRif,'dd/mm/yyyy'),'yyyyMMdd') as id, " + 
				"pf2.id_ext_d_famiglia, p2.id_ext_d_persona as id_ext_pers, " +
				"TO_DATE (:dtRif, 'dd/mm/yyyy') data_ini_rif, TO_DATE(:dtFinRif,'dd/MM/yyyy') data_fin_rif,  " +
				"pf2.dt_inizio_dato as dt_ini_pers_fam, pf2.dt_fine_dato as dt_fin_pers_fam,  " +
				"p2.codfisc, p2.cognome, p2.nome, p2.sesso, p2.data_nascita, p2.data_mor,  " +
				"p2.data_inizio_residenza, p2.data_imm, p2.data_emi, p2.n_ord prog_p, pf2.n_ord prog_pf, p2.processid " +
	            "FROM Sit_D_Pers_Fam_St pf2, Sit_D_Persona_st p2 "+
	            "WHERE pf2.id_Ext_D_Famiglia = :idExtFamiglia "+
	            "    AND pf2.id_Ext_D_Persona = p2.id_Ext_D_Persona "+
	            "    AND TO_DATE(:dtRif, 'dd/MM/yyyy') >= nvl(data_inizio_residenza, TO_DATE ('01/01/1000','DD/MM/YYYY')) "+
	            "    AND TO_DATE(:dtRif, 'dd/MM/yyyy') >=  NVL(p2.data_Nascita,TO_DATE('01/01/1000','DD/MM/YYYY')) AND TO_DATE(:dtRif, 'dd/MM/yyyy')< NVL(p2.data_Mor,sysdate) "+
	            "    AND((NVL(p2.data_Imm,TO_DATE('01/01/1000','DD/MM/YYYY'))< NVL(p2.data_Emi,sysdate) "+ 
	            "        AND TO_DATE(:dtRif, 'dd/MM/yyyy')>=NVL(p2.data_Imm,TO_DATE('01/01/1000','DD/MM/YYYY')) AND TO_DATE(:dtRif, 'dd/MM/yyyy')<NVL(p2.data_Emi,sysdate))  "+
	            "     OR NVL(p2.data_Imm,TO_DATE('01/01/1000','DD/MM/YYYY'))>=NVL(p2.data_Emi,sysdate)) "+
	            "    AND TO_DATE(:dtRif, 'dd/MM/yyyy')>=NVL(pf2.dt_Inizio_Dato,TO_DATE('01/01/1000','DD/MM/YYYY')) AND TO_DATE(:dtRif, 'dd/MM/yyyy')<NVL(pf2.dt_fine_Dato,sysdate) "+
	            "    order by P2.ID_EXT_D_PERSONA, P2.N_ORD desc, pf2.n_ord desc"
	            /*+
	            "    AND nvl(p2.dt_fine_val,TO_DATE('31/12/9999','dd/mm/yyyy'))="+
	            "             (SELECT Min(nvl(dt_fine_val,TO_DATE('31/12/9999','dd/mm/yyyy'))) FROM Sit_D_Persona "+
	            "              WHERE id_ext = p2.id_ext and TO_DATE(:dtRif, 'dd/MM/yyyy') <= NVL(dt_Fine_Val,TO_DATE('31/12/9999','dd/mm/yyyy')) )"
	            +
	            "    AND NVL(pf2.dt_fine_val, TO_DATE ('31/12/9999', 'dd/mm/yyyy')) ="+
	            "	         (SELECT MIN (NVL (dt_fine_val,TO_DATE ('31/12/9999', 'dd/mm/yyyy'))) FROM Sit_D_pers_fam "+
	            "            WHERE id_ext_d_persona = p2.id_ext and id_ext_d_famiglia=:idExtFamiglia "+
	            "            AND TO_DATE (:dtRif, 'dd/MM/yyyy') <=NVL (dt_Fine_Val,TO_DATE ('31/12/9999', 'dd/mm/yyyy')))" */
	            ;
		
		String SQL_INSERT_SIT_D_FAM_STORICO= "INSERT INTO SIT_D_FAM_STORICO VALUES(";

		try {
			
			log.info("Avvio elaborazione stato famiglie per il caricamento della tabella SIT_D_FAMIGLIE_STORICO...");
			
			//Commento per poter stoppare e riavviare il jboss nel caso di rallentamenti (es.Milano) senza dover ricaricare tutto
			
			log.debug("Query SQL_TRUNCATE_SIT_D_FAM_STORICO da eseguire:\n"+ SQL_TRUNCATE_SIT_D_FAM_STORICO);
			pst = conn.prepareStatement(SQL_TRUNCATE_SIT_D_FAM_STORICO);
			pst.executeUpdate();
			log.debug("La tabella SIT_D_FAM_STORICO è stata svuotata.");
			
			
			log.debug("Query SQL_SELECT_FAMIGLIE da eseguire:\n"+ SQL_SELECT_FAMIGLIE);
			pstFamiglie = conn.prepareStatement(SQL_SELECT_FAMIGLIE);
			rsFamiglie = pstFamiglie.executeQuery();
			
			log.debug("Query SQL_SELECT_DATE_FAMIGLIA da eseguire:\n"+ SQL_SELECT_DATE_FAMIGLIA);			
			
			log.debug("Query SQL_SELECT_INFO_DATE_PERSONA da eseguire:\n"+ SQL_SELECT_INFO_DATE_PERSONA);
			
			log.debug("Query SQL_SELECT_COMPONENTI_FAM_ALLA_DATA da eseguire:\n"+ SQL_SELECT_COMPONENTI_FAM_ALLA_DATA);
			
			for(int i=1; i<COLS_SIT_D_FAM_STORICO; i++)
				SQL_INSERT_SIT_D_FAM_STORICO += "?,";
			SQL_INSERT_SIT_D_FAM_STORICO+= "?)";
			log.debug("Query SQL_INSERT_SIT_D_FAM_STORICO da eseguire:\n"+ SQL_INSERT_SIT_D_FAM_STORICO);
			
			int numFamiglie = 0;
			while(rsFamiglie.next()){
				
				pstDate = conn.prepareStatement(SQL_SELECT_DATE_FAMIGLIA);
				psInfo = conn.prepareStatement(SQL_SELECT_INFO_DATE_PERSONA);
				pstComp = conn.prepareStatement(SQL_SELECT_COMPONENTI_FAM_ALLA_DATA);
				psUpdate = conn.prepareStatement(SQL_INSERT_SIT_D_FAM_STORICO);
				
				String idExtFamiglia = rsFamiglie.getString("ID_EXT");
				
				for(int i=1;i<=7;i++)
					pstDate.setString(i, idExtFamiglia);
				
				rsDate = pstDate.executeQuery();
				Vector<String> dateRif = new Vector<String>();
				while(rsDate.next())
					dateRif.add(rsDate.getString("DT_STR"));
				
				//Rimuovo la data di riferimento iniziale fittizia, se c'è
				if(dateRif.size()>0 && "01/01/0001".equals(dateRif.get(0)))
					dateRif.remove(0);
				
				if(dateRif.size()>1){
					String dateEnd = dateRif.get(dateRif.size()-1);
					if(SDF.format(new Date()).equals(dateEnd))
						dateRif.remove(dateRif.size()-1);
				}
				
				
				int s = 0; //Contatore indice intervalli
				while(s<dateRif.size()){
					String dtRif = dateRif.get(s);
					String dtFinRif = "31/12/9999";
					if(s<dateRif.size()-1)
						dtFinRif=dateRif.get(s+1);
					
					pstComp.setString(1, dtRif);
					pstComp.setString(2, dtRif);
					pstComp.setString(3, dtFinRif);
					pstComp.setString(4, idExtFamiglia);
					for(int i=5;i<=11;i++)
						pstComp.setString(i, dtRif);
				//	pstComp.setString(11, idExtFamiglia);
				//	pstComp.setString(12, dtRif);
					
					rsComp =  pstComp.executeQuery();
					//Verifico che non ci siano duplicati
					//TODO:Gestire quale persona inserire!
					//int numInseriti = 0;
					List<String> lstInseriti = new ArrayList<String>();
					while(rsComp.next()){
						//String cf = rsComp.getString("CODFISC");
						String idExt = rsComp.getString("id_ext_pers");
						String id = rsComp.getString("ID");
						if(!this.existsDuplicati(conn, idExt)){
							if(lstInseriti.contains(id)){
							//	log.warn("Soggetto "+id+" già presente in famiglia "+idExtFamiglia+" "+dtRif);
							}else{
								this.insert(rsComp, psUpdate);
								lstInseriti.add(id);
								//numInseriti++;
							}
						}else{
							//Effettuo il controllo sulla variazione degli stati
							for(int i=1;i<=7;i++)
								psInfo.setString(i, idExtFamiglia);
							psInfo.setString(8, idExt);
							psInfo.setString(9, dtRif);
							
							boolean inserisci=true;
							rsInfo = psInfo.executeQuery();
							while(rsInfo.next()){
								String ev = rsInfo.getString("EVENTO");
								if(ev.equalsIgnoreCase("NASCITA")||ev.equalsIgnoreCase("IMM")||ev.equalsIgnoreCase("INIZIO_FAM")||ev.equalsIgnoreCase("INIZIO_RES"))
									inserisci = true;
								else if(ev.equalsIgnoreCase("MORTE")||ev.equalsIgnoreCase("EMI")||ev.equalsIgnoreCase("FINE_FAM"))
									inserisci = false;
							}
							
							if(inserisci){
								if(lstInseriti.contains(id)){
								//	log.warn("Soggetto "+id+" già presente in famiglia "+idExtFamiglia+" "+dtRif);
								}else{
									this.insert(rsComp, psUpdate);
									//numInseriti++;
									lstInseriti.add(id);
								}
							}else{
								log.warn("Soggetto non inserito nella famiglia, in seguito a verifica eventi [ID_EXT_D_PERSONA|ID_EXT_D_FAMIGLIA|DATA]: "+id);
							}
						}
						
					}
					
				/*	if(numInseriti>0)
						log.debug("Intervallo Data Rif.: "+ dtRif+" - "+dtFinRif+" -> inseriti "+numInseriti+" componenti della famiglia ["+idExtFamiglia+"]");*/
				
					s++;
				}
				
				numFamiglie++;
				if(numFamiglie%1000==0){
					log.debug("Esaminate "+numFamiglie+ " famiglie");
					conn.commit();
				}
				
				try {
					DbUtils.close(rsDate);
					DbUtils.close(rsComp);
					DbUtils.close(rsInfo);
					
					DbUtils.close(pstDate);
					DbUtils.close(pstComp);
					DbUtils.close(psInfo);
					DbUtils.close(psUpdate);
				}
				catch (SQLException sqle) {
					log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
				}
			}
			
	
			log.info("La tabella SIT_D_FAM_STORICO è stata caricata");
			
			
		}catch (SQLException e) {
			log.error("ERRORE SQL elaboraStoricoFamiglie" + e, e);
			throw e;
		}catch(Exception eg){
			log.error("ERRORE elaboraStoricoFamiglie " + eg, eg);
			throw eg;
		}finally {
			try {
				DbUtils.close(rsFamiglie);
				DbUtils.close(rsDate);
				DbUtils.close(rsComp);
				DbUtils.close(rsInfo);
				
				DbUtils.close(pstFamiglie);
				DbUtils.close(pstDate);
				DbUtils.close(pstComp);
				DbUtils.close(psInfo);
				DbUtils.close(pst);
				DbUtils.close(psUpdate);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
	}
	
	private void preProcessSitDPersFam(Connection conn, Context ctx) throws SQLException{
		
		Statement st = null;
		PreparedStatement pstDuplicati = null;
		PreparedStatement pstDettaglio = null;
		PreparedStatement pstUpd=null;
		
		ResultSet rsDuplicati = null;
		ResultSet rsDettaglio = null;
		
		String DROP_TMP = "truncate table SIT_D_PERS_FAM_ST";
		String CREATE_TMP = "insert into sit_d_pers_fam_st ("
				+ "select pf.relaz_par, pf.id_ext_d_famiglia, pf.id_ext_d_persona, "
				+ "PF.DT_INIZIO_DATO, PF.DT_FINE_DATO, 1 as n_ord, '"+ctx.getProcessID()+"' as processid from ("
				+ "select id_ext_d_persona from sit_d_pers_fam where RELAZ_PAR not in ('XE') group by id_ext_d_persona having count(*)=1) pf1, sit_d_pers_fam pf "
				+ "where PF1.ID_EXT_D_PERSONA=PF.ID_EXT_D_PERSONA "
				+ "and PF.RELAZ_PAR not in ('XE'))";
		
		String INSERT_TMP= "insert into sit_d_pers_fam_st values (?,?,?,?,?,?,?) ";
		
		String SQL_SELECT_DUPLICATI="select id_ext_d_persona from sit_d_pers_fam where relaz_par not in ('XE') group by id_ext_d_persona having count(*)>1";
		 
	    String SQL_SELECT_DETT_COND= " from sit_d_pers_fam pf where relaz_par not in ('XE') and id_ext_d_persona=? "; 
		
		
		/*String SQL_SELECT_DETTAGLIO="select distinct * from ("+
				"select dt_inizio_dato dt, 'I' evento, nvl(relaz_par,' ') relaz_par, id_ext_d_famiglia, dt_inizio_val "+SQL_SELECT_DETT_COND+
				"union all "+
				"select dt_fine_dato dt, 'F' evento, nvl(relaz_par,' ') relaz_par, id_ext_d_famiglia, dt_inizio_val "+SQL_SELECT_DETT_COND+
				") where dt is not null order by dt_inizio_val, dt ";
		*/

		String SQL_SELECT_DETTAGLIO="select dt_inizio_dato dtIni,dt_fine_dato dtFin, nvl(relaz_par,'') relaz_par, id_ext_d_famiglia, dt_inizio_val "+
									SQL_SELECT_DETT_COND+ "order by dt_inizio_val ";

		
		try{
			
			int numPersoneElaborate = 0;
			
			log.info("Avvio pre-processing SIT_D_PERS_FAM. Caricamento SIT_D_PERS_FAM_ST in corso...");
			
			st = conn.createStatement();
			log.debug("Truncate tabella SIT_D_PERS_FAM_ST :"+DROP_TMP);
			st.executeUpdate(DROP_TMP);
			
			log.debug("Inserisci in tabella SIT_D_PERS_FAM_ST contente i record unici per persona:"+CREATE_TMP);
			numPersoneElaborate=st.executeUpdate(CREATE_TMP);
			
			//Elaboro i record doppi
			log.debug("Recupero i record duplicati persona:"+SQL_SELECT_DUPLICATI);
			pstDuplicati = conn.prepareStatement(SQL_SELECT_DUPLICATI);
			
			log.debug("Recupero i record per persona:"+SQL_SELECT_DETTAGLIO);
			pstDettaglio = conn.prepareStatement(SQL_SELECT_DETTAGLIO);
			
			pstUpd = conn.prepareStatement(INSERT_TMP);
			
			rsDuplicati = pstDuplicati.executeQuery();
			List<String> lstAnomalia1 = new ArrayList<String>();
			List<String> lstAnomalia2 = new ArrayList<String>();
			List<String> lstAnomalia3 = new ArrayList<String>();
			List<String> lstAnomalia4 = new ArrayList<String>();
			
			int totPersoneAnomalia=0;
			
			while(rsDuplicati.next()){
				//List<SitDPersFamSt> lstOccorrenzePers = new ArrayList<SitDPersFamSt>();
				
				SitDPersFamSt pf = new SitDPersFamSt();
				pf.setIdExtDPersona(rsDuplicati.getString("id_ext_d_persona"));
				
				pstDettaglio.setString(1, pf.getIdExtDPersona());
				//pstDettaglio.setString(2, pf.getIdExtDPersona());
				
				rsDettaglio = pstDettaglio.executeQuery();
				//Creo una lista di eventi per il record
				List<Object[]> lstEventiIn = new ArrayList<Object[]>();
				while(rsDettaglio.next()){
					Object[] obj = {rsDettaglio.getDate("DTINI"),rsDettaglio.getDate("DTFIN"), rsDettaglio.getString("RELAZ_PAR"),rsDettaglio.getString("id_ext_d_famiglia")};
					lstEventiIn.add(obj);
				}
				
		
				List<Object[]> lstEventi=this.elaboraListaEventiPf(pf, lstEventiIn);
	
				int size = lstEventi.size();
				boolean inserito = true;
				boolean scrivi = false;
				int numAnomalia1 = 0;
				int numAnomalia2 = 0;
				int numAnomalia3 = 0;
				int numAnomalia4 = 0;
				int numInserimenti = 0;
				
				//java.sql.Date ultimaDataSalvata = new java.sql.Date(1,1,1);
				for(int i=0;i<size;i++){
					Object[] oprec = i>0 ? lstEventi.get(i-1) : null;
					Object[] ocurr = lstEventi.get(i);
					Object[] osucc = i<size-1 ? lstEventi.get(i+1) : null;
					
					java.sql.Date dt = (java.sql.Date)ocurr[1];

					pf.setRelazPar((String)ocurr[2]);
					pf.setIdExtDFamiglia((String)ocurr[3]);
					
					String statoCorr = pf.getRelazPar()+"@"+pf.getIdExtDFamiglia();
					
					if("I".equalsIgnoreCase((String)ocurr[0])){
						if(inserito){
							pf.setDtInizioDato(dt);
						
							//Controllo
						    if(oprec!=null && (dt==null || (oprec[1]!=null && dt.before((java.sql.Date)oprec[1])))){
						    	numAnomalia4++;
						    	
						    	//pf.setDtInizioDato((java.sql.Date)oprec[1]);
						    }
						}
							
						if(osucc!=null){ 
							String eveSucc = (String)osucc[0];
							//java.sql.Date dtSucc = (java.sql.Date)osucc[1];
							String relSucc = (String)osucc[2];
							String famSucc = (String)osucc[3];
							String statoSucc = relSucc+"@"+famSucc;
							
							if(!statoCorr.equalsIgnoreCase(statoSucc)){ 
								//Caso1: Due date inizio consecutive con cambio stato: imposto come fine stato corrente, l'inizio stato dell'evento successivo e scrivo
								//Caso2: Inizio stato, privo di fine stato seguito da un fine stato per un'altra situazione Relazione/famiglia: registro con dtfineDato=prima data superiore alla corrente dtIni successiva
								
								//Ricerco la prima data successiva, maggiore di quella corrente
								int j=i+1;
								boolean trovato=false;
								while(j<lstEventi.size() && !trovato){
									Object[] oiter = lstEventi.get(j);
									java.sql.Date dataiter = (java.sql.Date)oiter[1];
									
									if(dataiter!=null && (pf.getDtInizioDato()==null ||dataiter.after(pf.getDtInizioDato()))){
										pf.setDtFineDato(dataiter);
										trovato=true;
										
										//Caso 2 (mancano date di aggiornamento intermedie del cambiostato)
										if("F".equalsIgnoreCase(eveSucc))
											numAnomalia2++;
										
										//if(j>i+1)
										//log.warn("Impostata prima data successiva alla corrente come fine validità per "+pf.getIdExtDPersona()+" nella famiglia "+pf.getIdExtDFamiglia()+"("+pf.getRelazPar()+")");
										
									}
									j++;
								}
								scrivi=true;
							}
							
							if("I".equalsIgnoreCase(eveSucc) && statoCorr.equalsIgnoreCase(statoSucc)){
								/*if(dtSucc.before(dt)){
									pf.setDtInizioDato(dtSucc);
									log.warn("Sovrescritta data iniziale per "+pf.getIdExtDPersona());
								}*/
								//Due inizi stato consecutivi senza variazione della situazione: ignoro la nuova data (prendo la vecchia)
								numAnomalia1++;
								//log.warn("ANOMALIA 1: Due inizio dato pers_fam consecutive senza cambiamento di Relazione/Famiglia per "+pf.getIdExtDPersona()+" nella famiglia "+pf.getIdExtDFamiglia()+"("+pf.getRelazPar()+")");
							}
							
						}
								
					}else if("F".equalsIgnoreCase((String)ocurr[0])){
						pf.setDtFineDato(dt);
						
						if(pf.getDtInizioDato()==null && oprec!=null){
							String statoPrec = oprec[2]+"@"+oprec[3];
							//String evePrec = (String)oprec[0];
							
							if(!statoPrec.equals(statoCorr)){ 
							
								//Ricerco la prima data precedente, minore di quella corrente
								int j=i-1;
								boolean trovato=false;
								while(j>=0 && !trovato){
									Object[] oiter = lstEventi.get(j);
									java.sql.Date dataiter = (java.sql.Date)oiter[1];
									if(dataiter!=null && dataiter.before(pf.getDtFineDato())){
										pf.setDtInizioDato(dataiter);
										trovato=true;
										//if(j<i-1)
										//	log.warn("Impostata prima data precedente alla corrente come inizio validità per "+pf.getIdExtDPersona()+" nella famiglia "+pf.getIdExtDFamiglia()+"("+pf.getRelazPar()+")");
									}
									j--;
								}
								
							}
							scrivi=true;
						}
						
						if(osucc!=null){ 
							String statoSucc = osucc[2]+"@"+osucc[3];
							String eveSucc = (String)osucc[0];
							if("F".equalsIgnoreCase(eveSucc) && statoSucc.equals(statoCorr)){ 
								//Due fine evento consecutive-> Anomalia: ignoro la data corrente come fine evento e prendo la successiva
								pf.setDtFineDato(null);
								numAnomalia3++;
								//log.warn("ANOMALIA 3: Due fine dato pers_fam consecutive senza variazione di stato per "+pf.getIdExtDPersona()+" nella famiglia "+pf.getIdExtDFamiglia()+"("+pf.getRelazPar()+")");
							}else
								scrivi=true;
							
						}else
							scrivi=true;
							
					
					}
					
					
					if(osucc==null || scrivi){
					/*	if(pf.getDtInizioDato()!=null && pf.getDtFineDato()!=null && pf.getDtInizioDato().compareTo(pf.getDtFineDato())==0){
							//Non inserisco il record e resetto il bean precedente
							//log.warn("Record non inserito (date coincidenti) per: "+pf.getIdExtDPersona());
						}else{*/
							numInserimenti++;
							if(pf.getDtInizioDato()!=null && "0001-01-01".equals(pf.getDtInizioDato().toString()))
								pf.setDtInizioDato(null);
									
							this.insertPf(pf, pstUpd, numInserimenti,ctx.getProcessID());

					//	}
						
						pf.setDtFineDato(null);
						pf.setDtInizioDato(null);
						pf.setRelazPar(null);
						pf.setIdExtDFamiglia(null);
						
						inserito = true;
						scrivi=false;
					}else{
						inserito = false;
					}
					
				}
				
				
				if(numAnomalia1>0 || numAnomalia2>0 || numAnomalia3>0 || numAnomalia4>0){
					totPersoneAnomalia++;
				}
				
				if(numAnomalia1>0)
					lstAnomalia1.add(pf.getIdExtDPersona());
					
				if(numAnomalia2>0)
					lstAnomalia2.add(pf.getIdExtDPersona());
					
				if(numAnomalia3>0)
					lstAnomalia3.add(pf.getIdExtDPersona());
					
				if(numAnomalia4>0)
					lstAnomalia4.add(pf.getIdExtDPersona());
			
				numPersoneElaborate++;
				
				if(numInserimenti==0){
					log.warn("NESSUN INSERIMENTO EFFETTUATO PER: "+pf.getIdExtDPersona());
					
				}
				
				if(numPersoneElaborate%5000==0)
					log.warn(ctx.getBelfiore()+" ELABORATI: "+numPersoneElaborate+" SOGGETTI");
				
			}
			
			
			String msg ="Anomalia 1 - SIT_D_PERS_FAM - Due inizio dato pers_fam consecutive senza cambiamento di Relazione/Famiglia:"+lstAnomalia1.size();
			String path = this.scriviLog(ctx, msg, lstAnomalia1);
			if(lstAnomalia1.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			msg="Anomalia 2 - SIT_D_PERS_FAM - Inizio/Fine dato consecutive per due situazioni diverse di Relazione/Famiglia:"+lstAnomalia2.size();
			this.scriviLog(ctx, msg, lstAnomalia2);
			if(lstAnomalia2.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			
			msg="Anomalia 3 - SIT_D_PERS_FAM - Due fine dato pers_fam consecutive senza variazione di stato:"+lstAnomalia3.size();
			this.scriviLog(ctx, msg, lstAnomalia3);
			if(lstAnomalia3.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			msg="Anomalia 4 - SIT_D_PERS_FAM - Aggiornamento con data precedente rispetto nell'ordine delle date validita:"+lstAnomalia4.size();
			this.scriviLog(ctx, msg, lstAnomalia4);
			if(lstAnomalia4.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			msg="Anomalia 5 - SIT_D_PERS_FAM - Anomalia ordinamento 'dt_inizio dato' soggetto (senza cambiamento di stato):"+this.lstAnomaliaPf5.size();
			this.scriviLog(ctx, msg, lstAnomaliaPf5);
			if(lstAnomaliaPf5.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			msg="Anomalia 6 - SIT_D_PERS_FAM - Anomalia ordinamento 'dt_inizio dato' soggetto (con cambiamento di stato):"+this.lstAnomaliaPf6.size();
			this.scriviLog(ctx, msg, lstAnomaliaPf6);
			if(lstAnomaliaPf6.size()>0 && path!=null)
				log.warn(msg+ "(Elenco ID_EXT soggetti presente in:"+path+")");
			
			totPersoneAnomalia += this.lstAnomaliaPf5.size()+this.lstAnomaliaPf6.size();
			
			log.warn("Totale Persone con anomalia "+totPersoneAnomalia);
			
			log.info("La tabella SIT_D_PERS_FAM_ST è stata caricata: elaborati "+numPersoneElaborate+" soggetti.");
			
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtils.close(st);
			DbUtils.close(pstDuplicati);
			DbUtils.close(pstDettaglio);
			DbUtils.close(pstUpd);
			
			DbUtils.close(rsDuplicati);
			DbUtils.close(rsDettaglio);
		}
	}
		
	
	private List<Object[]>  elaboraListaEventiPf(SitDPersFamSt p, List<Object[]> lstEventiIn){
		//Elaborare intervalli
		//Pre-elaboro lista eventi per rimuovere record t.c. la data di inizio/fine dato è maggiore dei successivi
		
			int k= lstEventiIn.size()-1;
			List<Object[]> lstEventi = new ArrayList<Object[]>();
			
			while(k>=0){
				Object[] ocurr = lstEventiIn.get(k);
			
				java.sql.Date dtIniCurr = (java.sql.Date)lstEventiIn.get(k)[0];
				//java.sql.Date dtFinCurr = (java.sql.Date)lstEventiIn.get(k)[1];
				
				//Aggiungo alla lista l'elemento corrente e valuto il precedente
				lstEventi.add(0, ocurr);
				
				if(k>0){
					java.sql.Date dtIniPrec = (java.sql.Date)lstEventiIn.get(k-1)[0];
					//java.sql.Date dtFinPrec = (java.sql.Date)lstEventiIn.get(k-1)[1];
					
					//java.sql.Date lastData = dtIniCurr!=null ? dtIniCurr : (dtFinCurr!=null ? dtFinCurr : null);
					//java.sql.Date cfrData = dtFinPrec!=null ? dtFinPrec : (dtIniPrec!=null ? dtIniPrec : null);
					
					String s1 = lstEventiIn.get(k-1)[2]+"@"+lstEventiIn.get(k-1)[3];
					String s2 = lstEventiIn.get(k)[2]+"@"+lstEventiIn.get(k)[3];
					
					boolean condizioneStato = s1.equalsIgnoreCase(s2);
					
					if(dtIniCurr!=null && dtIniPrec!=null && dtIniCurr.before(dtIniPrec)){
						if(condizioneStato){
							if(!this.lstAnomaliaPf5.contains(p.getIdExtDPersona()))
								lstAnomaliaPf5.add(p.getIdExtDPersona());
							lstEventiIn.remove(k-1);
							lstEventi = new ArrayList<Object[]>();
							k=lstEventiIn.size()-1;
						}else{
							k--;
							if(!this.lstAnomaliaPf6.contains(p.getIdExtDPersona()))
								lstAnomaliaPf6.add(p.getIdExtDPersona());
						}
					}else
						k--;  
				}else
					k--;
		}
			
		
			lstEventi = lstEventiIn;
		
			//Creare mappa ordinata eventi
			List<Object[]> lstEventiOut = new ArrayList<Object[]>();
			List<String> lstStatiInseriti = new ArrayList<String>();
			for(int i=0; i<lstEventi.size();i++){
				Object[] ocurr = lstEventi.get(i);
				String stato = ocurr[2]+"@"+ocurr[3];
				
				boolean inserito = false;
				
				if(ocurr[0]!=null){
					Object[] oi = {"I",ocurr[0],ocurr[2],ocurr[3]};
					lstEventiOut.add(oi);
					inserito = true;
					lstStatiInseriti.add(stato);
				}
				
				if(ocurr[1]!=null){
					Object[] of = {"F",ocurr[1],ocurr[2],ocurr[3]};
					lstEventiOut.add(of);
					inserito = true;
					lstStatiInseriti.add(stato);
				}
				
				if(!inserito && (lstStatiInseriti.isEmpty() || !lstStatiInseriti.get(lstStatiInseriti.size()-1).equals(stato)) ){
					Object[] oi = {"I", java.sql.Date.valueOf("0001-01-01") ,ocurr[2],ocurr[3]};
					lstEventiOut.add(oi);
					lstStatiInseriti.add(stato);
				}
				
			}
			
			/*boolean ordinato = bubbleSort(lstEventiOut);
			if(ordinato)
				log.warn("Ordinamento effettuato per "+p.getIdExtDPersona());*/
			
			
		return lstEventiOut;
	}
	
	 public boolean bubbleSort(List<Object[]> x) 
	    {
	    boolean ordinato = false;
	    int j = x.size()-1;
	    while(j>0) 
	      {
	      for(int i=0; i<j; i++) 
	        {
	    	  Object[] x1 = x.get(i);
	    	  Object[] x2 = x.get(i+1);
	        if(((java.sql.Date)x1[1]).after((java.sql.Date)x2[1]))   //scambiare il '>' con '<' per ottenere un ordinamento decrescente
	          {
	          x.set(i,x2);
	          x.set(i+1,x1);
	          ordinato = true;
	          }
	        }
	      j--; 
	      }
	    
	    return ordinato;
	    }
	
	
	private void preProcessSitDPersona(Connection conn, Context ctx) throws SQLException{
		
		Statement st = null;
		PreparedStatement pstDuplicati = null;
		PreparedStatement pstDettaglio = null;
		PreparedStatement pstDettaglioOrd = null;
		PreparedStatement pstUpd=null;
		PreparedStatement pstDate=null;
		
		ResultSet rsDuplicati = null;
		ResultSet rsDettaglio = null;
		ResultSet rsDettaglioOrd = null;
		ResultSet rsDate=null;
		
		String DROP_TMP = "truncate table SIT_D_PERSONA_ST";
		String CREATE_TMP = "insert into SIT_D_PERSONA_ST ("+
				"select p.id_ext as id_ext_d_persona, cognome, nome, sesso, codfisc,"+ 
				"id_ext_provincia_nascita, id_ext_comune_nascita, data_nascita, "+
				"id_ext_provincia_mor, id_ext_comune_mor, data_mor, "+
				"stato_civile, data_inizio_residenza, id_ext_stato, "+
				"id_ext_provincia_imm, id_ext_comune_imm, data_imm, "+
				"id_ext_provincia_emi, id_ext_comune_emi, data_emi, indirizzo_emi, "+
				" 1 as n_ord, '"+ctx.getProcessID()+"' as processid " +
				"from sit_d_persona p,( "+
				"select id_ext, count(*) from sit_d_persona group by id_ext having count(*)=1) p1 where p1.id_ext=p.id_ext)";
		
		String INSERT_TMP= "insert into SIT_D_PERSONA_ST values (?";
		
		String SQL_SELECT_DUPLICATI="select id_ext as id_ext_d_persona from sit_d_persona group by id_ext having count(*)>1";
		 
	    String SQL_SELECT_DETTAGLIO="select distinct id_ext as id_ext_d_persona, cognome, nome, sesso, codfisc, DATA_NASCITA "+
	    							"from sit_d_persona where id_ext=:idExtPersona";
	    
	    String SQL_SELECT_DETTAGLIO_ORD="select id_ext as id_ext_d_persona, cognome, nome, sesso, codfisc, DATA_NASCITA "+
				"from sit_d_persona where id_ext=:idExtPersona order by dt_inizio_val desc";
		
		String SQL_SELECT_DATE_COND= " from sit_d_persona  where id_ext=? "; 

		String SQL_SELECT_DATE="select data_imm dt_imm, data_emi dt_emi, stato_civile, data_inizio_residenza, dt_inizio_val "+SQL_SELECT_DATE_COND+ " order by dt_inizio_val";
		
		try{
			int numPersoneElaborate = 0;
			log.info("Avvio pre-processing SIT_D_PERSONA. Caricamento SIT_D_PERSONA_ST in corso...");
			
			st = conn.createStatement();
			log.debug("Truncate tabella SIT_D_PERSONA_ST :"+DROP_TMP);
			st.executeUpdate(DROP_TMP);
			
			log.debug("Inserisci in tabella SIT_D_PERSONA_ST contente i record unici per persona:"+CREATE_TMP);
			numPersoneElaborate = st.executeUpdate(CREATE_TMP);
			
			//Elaboro i record doppi
			log.debug("Recupero i record duplicati persona:"+SQL_SELECT_DUPLICATI);
			pstDuplicati = conn.prepareStatement(SQL_SELECT_DUPLICATI);
			
			log.debug("Recupero i record per persona:"+SQL_SELECT_DETTAGLIO);
			pstDettaglio = conn.prepareStatement(SQL_SELECT_DETTAGLIO);
			
			log.debug("Recupero i record per persona:"+SQL_SELECT_DETTAGLIO_ORD);
			pstDettaglioOrd = conn.prepareStatement(SQL_SELECT_DETTAGLIO_ORD);
			
			log.debug("Recupero le date Imm/Emi:"+SQL_SELECT_DATE);
			pstDate = conn.prepareStatement(SQL_SELECT_DATE);
			
			for(int i=1; i<23;i++)
				INSERT_TMP += ",?";
			pstUpd = conn.prepareStatement(INSERT_TMP+")");
			
			rsDuplicati = pstDuplicati.executeQuery();
		
			HashMap<String,List<SitDPersonaSt>> mappaDuplDatiBase = new HashMap<String,List<SitDPersonaSt>>();
			
			while(rsDuplicati.next()){

				String idExtPersona = rsDuplicati.getString("id_ext_d_persona");
				
				pstDettaglio.setString(1, idExtPersona);
				rsDettaglio = pstDettaglio.executeQuery();
				
				//Lista di dati anagrafici diversi associati alla persona
				List<SitDPersonaSt> lstPersone = new ArrayList<SitDPersonaSt>();
				List<SitDPersonaSt> lstOccorrenzePers = new ArrayList<SitDPersonaSt>();
				while(rsDettaglio.next()){
					SitDPersonaSt p = new SitDPersonaSt();
					p.setIdExtDPersona(idExtPersona);
					p.setCognome(rsDettaglio.getString("COGNOME"));
					p.setNome(rsDettaglio.getString("NOME"));
					p.setCodfisc(rsDettaglio.getString("CODFISC"));
					p.setSesso(rsDettaglio.getString("SESSO"));
					lstPersone.add(p);
				}
				
				if(lstPersone.size()>1){
					//Creo tabella temporanea da elaborare successivamente
					mappaDuplDatiBase.put(idExtPersona,lstPersone);
					
					pstDettaglioOrd.setString(1, idExtPersona);
					rsDettaglioOrd = pstDettaglioOrd.executeQuery();
					
					lstPersone = new ArrayList<SitDPersonaSt>();
					while(rsDettaglioOrd.next()){
						SitDPersonaSt p = new SitDPersonaSt();
						p.setIdExtDPersona(idExtPersona);
						p.setCognome(rsDettaglioOrd.getString("COGNOME"));
						p.setNome(rsDettaglioOrd.getString("NOME"));
						p.setCodfisc(rsDettaglioOrd.getString("CODFISC"));
						p.setSesso(rsDettaglioOrd.getString("SESSO"));
						lstPersone.add(p);
					}
					
					SitDPersonaSt p0 = lstPersone.get(0);
					if(p0.getCognome()==null){
						int c=1;
						boolean trovato = false;
						while(c<lstPersone.size() && !trovato){
							SitDPersonaSt pr = lstPersone.get(c);
							if(pr.getCognome()!=null){
								trovato=true;
								p0.setCognome(pr.getCognome());
							}
							c++;
						}
					}
					
					if(p0.getNome()==null){
						int c=1;
						boolean trovato = false;
						while(c<lstPersone.size() && !trovato){
							SitDPersonaSt pr = lstPersone.get(c);
							if(pr.getNome()!=null){
								trovato=true;
								p0.setNome(pr.getNome());
							}
							c++;
						}
					}
					
					if(p0.getCodfisc()==null){
						int c=1;
						boolean trovato = false;
						while(c<lstPersone.size() && !trovato){
							SitDPersonaSt pr = lstPersone.get(c);
							if(pr.getCodfisc()!=null){
								trovato=true;
								p0.setCodfisc(pr.getCodfisc());
							}
							c++;
						}
					}
					
					if(p0.getSesso()==null){
						int c=1;
						boolean trovato = false;
						while(c<lstPersone.size() && !trovato){
							SitDPersonaSt pr = lstPersone.get(c);
							if(pr.getSesso()!=null){
								trovato=true;
								p0.setSesso(pr.getSesso());
							}
						  c++;
						}
					}
					lstPersone.set(0, p0);
				}
					
				if(lstPersone.size()>0){
					//Elaboro gli altri dati
					SitDPersonaSt p =lstPersone.get(0);
					
					/*String sql="select @FIELD@ from sit_d_persona where id_ext=? and @FIELD@ is not null order by dt_inizio_val desc";
					String sqlc = "select @FIELD@ from sit_d_persona p left join sit_comune c on (@FIELD@=c.id_ext) "+
							"where C.DESCRIZIONE is not null and p.id_ext=? order by p.dt_inizio_val desc";
					
					p.setDataNascita((java.sql.Date)this.getCampoPersona(sql, "DATA_NASCITA", idExtPersona, null));
					p.setIdExtProvinciaNascita((String)this.getCampoPersona(sql, "id_ext_provincia_nascita", idExtPersona, null));
					p.setIdExtComuneNascita((String)this.getCampoPersona(sqlc, "id_ext_comune_nascita", idExtPersona, null));
					p.setIdExtStato((String)this.getCampoPersona(sql, "id_ext_stato", idExtPersona, null));
					
					p.setDataMor((java.sql.Date)this.getCampoPersona(sql, "DATA_MOR", idExtPersona, null));
					p.setIdExtProvinciaMor((String)this.getCampoPersona(sql, "id_ext_provincia_mor", idExtPersona, null));
					p.setIdExtComuneMor((String)this.getCampoPersona(sqlc, "id_ext_comune_mor", idExtPersona, null));*/
					
					String sql = "select p.*, c.id_ext as my_id_ext_comune_nas, c.descrizione as my_desc_comune_nas, cc.id_ext as my_id_ext_comune_mor, cc.descrizione as my_desc_comune_mor " +
							"from sit_d_persona p " +
							"left outer join sit_comune c on (p.id_ext_comune_nascita = c.id_ext) " +
							"left outer join sit_comune cc on (p.id_ext_comune_mor = cc.id_ext) " +
							"where p.id_ext = ? order by p.dt_inizio_val desc";
					
					PreparedStatement ps = null;
					ResultSet rs = null;
					
					java.sql.Date dataNascita = null;
					String idExtProvinciaNascita = null;
					String idExtComuneNascita = null;
					String idExtStato = null;
					java.sql.Date dataMor = null;
					String idExtProvinciaMor = null;
					String idExtComuneMor = null;
					
					try {
						ps = conn.prepareStatement(sql);
						ps.setString(1, idExtPersona);
						rs = ps.executeQuery();
						while (rs.next()) {
							if (dataNascita == null && rs.getObject("data_nascita") != null) {
								dataNascita = rs.getDate("data_nascita");
							}
							if (idExtProvinciaNascita == null && rs.getObject("id_ext_provincia_nascita") != null) {
								idExtProvinciaNascita = rs.getString("id_ext_provincia_nascita");
							}
							if (idExtComuneNascita == null && rs.getObject("id_ext_comune_nascita") != null &&
							rs.getObject("my_id_ext_comune_nas") != null && rs.getObject("my_desc_comune_nas") != null) {
								idExtComuneNascita = rs.getString("id_ext_comune_nascita");
							}
							if (idExtStato == null && rs.getObject("id_ext_stato") != null) {
								idExtStato = rs.getString("id_ext_stato");
							}
							if (dataMor == null && rs.getObject("data_mor") != null) {
								dataMor = rs.getDate("data_mor");
							}
							if (idExtProvinciaMor == null && rs.getObject("id_ext_provincia_mor") != null) {
								idExtProvinciaMor = rs.getString("id_ext_provincia_mor");
							}
							if (idExtComuneMor == null && rs.getObject("id_ext_comune_mor") != null &&
							rs.getObject("my_id_ext_comune_mor") != null && rs.getObject("my_desc_comune_mor") != null) {
								idExtComuneMor = rs.getString("id_ext_comune_mor");
							}
						}					
					} catch(SQLException e) { 
						throw e; 
					} finally {
						DbUtils.close(ps);
						DbUtils.close(rs);
					}
					
					p.setDataNascita(dataNascita);
					p.setIdExtProvinciaNascita(idExtProvinciaNascita);
					p.setIdExtComuneNascita(idExtComuneNascita);
					p.setIdExtStato(idExtStato);					
					p.setDataMor(dataMor);
					p.setIdExtProvinciaMor(idExtProvinciaMor);
					p.setIdExtComuneMor(idExtComuneMor);

					//Altre Date
					//Creo una lista di eventi imm/emi per il record
					List<Object[]> lstEventi = new ArrayList<Object[]>();
					pstDate.setString(1, idExtPersona);
					rsDate = pstDate.executeQuery();
					while(rsDate.next()){
						java.sql.Date dtImm = rsDate.getDate("DT_IMM");
						java.sql.Date dtEmi = rsDate.getDate("DT_EMI");
						String sc = rsDate.getString("STATO_CIVILE");
						java.sql.Date dir = rsDate.getDate("DATA_INIZIO_RESIDENZA");
						Object[] obj = {dtImm,dtEmi,sc,dir};
						lstEventi.add(obj);
					}
					
					lstOccorrenzePers=this.elaboraImmEmi(p, lstEventi);
				}
				
				this.insertP(lstOccorrenzePers, pstUpd, ctx.getProcessID());
					
				if(lstOccorrenzePers.size()==0)
					log.warn("NESSUN INSERIMENTO EFFETTUATO PER: "+idExtPersona);
				
				numPersoneElaborate++;
				
				
				if(numPersoneElaborate%5000==0){
					log.warn(ctx.getBelfiore()+" ELABORATI: "+numPersoneElaborate+" SOGGETTI");
					conn.commit();
				}
			}
			
			log.info("La tabella SIT_D_PERSONA_ST è stata caricata: inseriti "+numPersoneElaborate+" soggetti.");
			
		}catch(SQLException e){
			throw e;
			
		}finally{
			DbUtils.close(st);
			DbUtils.close(pstDuplicati);
			DbUtils.close(pstDettaglio);
			DbUtils.close(pstDettaglioOrd);
			DbUtils.close(pstUpd);
			DbUtils.close(pstDate);
			
			DbUtils.close(rsDuplicati);
			DbUtils.close(rsDettaglio);
			DbUtils.close(rsDettaglioOrd);
			DbUtils.close(rsDate);
			
		}
		
		
	}
	
	private List<SitDPersonaSt> elaboraImmEmi(SitDPersonaSt p, List<Object[]> lstEventi) throws SQLException{
		
		List<SitDPersonaSt> lst = new ArrayList<SitDPersonaSt>();
		
		//Elaborare intervalli
		//int size = lstEventi.size();
		
		SitDPersonaSt pi = null;
		List<String> inseriti = new ArrayList<String>();
		for(int i=0;i<lstEventi.size();i++){
			//Object[] oprec = i>0 ? lstEventi.get(i-1) : null;
			Object[] ocurr = lstEventi.get(i);
			//Object[] osucc = i<size-1 ? lstEventi.get(i+1) : null;
			
			pi = new SitDPersonaSt(p);
			pi.setStatoCivile((String)ocurr[2]);
			
			//Imposto temporaneamente quelli trovati
			pi.setDataImm((java.sql.Date)ocurr[0]);
			pi.setDataEmi((java.sql.Date)ocurr[1]);
			pi.setDataInizioResidenza((java.sql.Date)ocurr[3]);
			
			if(lst.size()>0 && pi.getDataEmi()==null && pi.getDataInizioResidenza()==null && pi.getDataImm()==null){ 
				//Assumo come valido l'ultimo record inserito
				SitDPersonaSt plast = lst.get(lst.size()-1);
				pi.setDataInizioResidenza(plast.getDataInizioResidenza());
				pi.setDataImm(plast.getDataImm());
				pi.setDataEmi(plast.getDataEmi());
			}
			
			if(pi.getDataInizioResidenza()==null){
				int j=i-1;
				boolean trovato = false;
				while(j>=0 && !trovato){
					Object[] o =  lstEventi.get(j);
					if(o[3]!=null){
						pi.setDataInizioResidenza((java.sql.Date)o[3]);
						trovato=true;
					}
					j--;
				}
			}
			
			if(pi.getDataImm()==null){//Ricerco l'ultima data precedente di immigrazione
				int j=i-1;
				boolean trovato = false;
				while(j>=0 && !trovato){
					Object[] o =  lstEventi.get(j);
					if(o[0]!=null){
						pi.setDataImm((java.sql.Date)o[0]);
						trovato=true;
					}
					j--;
				}
			}
		
			if(pi.getDataEmi()==null){ 
				//Ricerco la prima data di emigrazione dei record successivi, se c'è 
				//purchè non sia preceduta da una data di immigrazione maggiore della data di inizio residenza o di immigrazione corrente
				int j=i+1;
				boolean trovatoEmi = false;
				boolean trovatoIni = false;
				java.sql.Date dtIniSucc = null;
				while(j<lstEventi.size()&& !trovatoEmi){ 
					Object[] o =  lstEventi.get(j);
					
					if(o[0]!=null){
						dtIniSucc= (dtIniSucc==null || dtIniSucc.compareTo((java.sql.Date)o[0])<0) ? (java.sql.Date)o[0] : dtIniSucc;
						trovatoIni=true;
					}
					
			        if(o[3]!=null){
						dtIniSucc= (dtIniSucc==null || dtIniSucc.compareTo((java.sql.Date)o[3])<0) ? (java.sql.Date)o[3] : dtIniSucc;
						trovatoIni=true;
					}
					
					if(o[1]!=null){
						boolean dirOk = pi.getDataInizioResidenza()!=null && dtIniSucc!=null && dtIniSucc.compareTo(pi.getDataInizioResidenza())>0 ? false : true;
						boolean immOk = pi.getDataImm()!=null && dtIniSucc!=null && dtIniSucc.compareTo(pi.getDataImm())>0 ? false : true;
						if(!trovatoIni || (trovatoIni && dirOk && immOk))
							pi.setDataEmi((java.sql.Date)o[1]);
						trovatoEmi=true;
					}
					
					j++;
				}
			}
			
			if(lst.size()==0 && pi.getDataInizioResidenza()==null && pi.getDataImm()==null){
				//Verifica che non esista una data di immigrazione nei record successivi 
				int j=i+1;
				
				log.debug(pi.getIdExtDPersona()+" primo record nullo ");
				
				boolean trovatoImm = false;
				boolean trovatoDir = false;
				while(j<lstEventi.size() && !trovatoImm && !trovatoDir){
					Object[] o =  lstEventi.get(j);
					if(o[0]!=null && !trovatoImm){
						pi.setDataImm((java.sql.Date)o[0]);
						trovatoImm=true;
					}
					if(o[3]!=null && !trovatoDir){
						pi.setDataInizioResidenza((java.sql.Date)o[3]);
						trovatoDir=true;
					}
					
					j++;
				}
				
				//Verifico che non siano precedentemete emigrati, per poi essere nuovamente immigrati
				if(pi.getDataEmi()!=null && pi.getDataImm()!=null && pi.getDataImm().compareTo(pi.getDataEmi())>=0)
					pi.setDataImm(null);
				
				if(pi.getDataEmi()!=null && pi.getDataInizioResidenza()!=null && pi.getDataInizioResidenza().compareTo(pi.getDataEmi())>=0)
					pi.setDataInizioResidenza(null);
			}

			
			String chk = (pi.getDataImm()!=null ? SDF.format(pi.getDataImm()) : "") +"@"+
						 (pi.getDataEmi()!=null ? SDF.format(pi.getDataEmi()) : "") +"@"+
						 (pi.getDataInizioResidenza()!=null ? SDF.format(pi.getDataInizioResidenza()) : "") +"@"+
						 pi.getStatoCivile();
			
			boolean esiste = false;
			if(inseriti.size()>0 && inseriti.get(inseriti.size()-1).equalsIgnoreCase(chk))
				esiste = true;
			
			if(!esiste){
				
				this.valorizzaLuogoImm(pi);
				this.valorizzaLuogoEmi(pi);
					
				lst.add(pi);
			    inseriti.add(chk);
			}
					
		}
		
		return lst;
		
	}
	
	/*private Object getCampoPersona(String sql, String campo, String idExtPersona, java.sql.Date data) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object o = null;
		try{
			ps = conn.prepareStatement(sql.replaceAll("@FIELD@", campo));
			ps.setString(1, idExtPersona);
			if(data!=null)
				ps.setDate(2, data);
			rs = ps.executeQuery();
			if(rs.next())
				if(campo.startsWith("DATA"))
					o = rs.getDate(campo);
				else
					o = rs.getObject(campo);
		
		}catch(SQLException e){ 
			throw e; 
		}finally{
			DbUtils.close(ps);
			DbUtils.close(rs);
		}
		return o;
	}*/
	
	private void valorizzaLuogoImm(SitDPersonaSt p) throws SQLException{
		
		if(p.getDataImm()!=null){
			/*String sqlc = "select @FIELD@  "+
					"from sit_d_persona p left join sit_comune c on (@FIELD@=c.id_ext) "+
					"where C.DESCRIZIONE is not null and p.id_ext=? and data_imm=? " +
					"order by p.dt_inizio_val desc";
			
			String sql="select @FIELD@ from sit_d_persona where id_ext=? and data_imm=? and @FIELD@ is not null order by dt_inizio_val desc";
		
			p.setIdExtProvinciaImm((String)this.getCampoPersona(sql, "id_ext_provincia_imm", p.getIdExtDPersona(), p.getDataImm()));
			p.setIdExtComuneImm((String)this.getCampoPersona(sqlc, "id_ext_comune_imm", p.getIdExtDPersona(), p.getDataImm()));*/

			String sql = "select p.*, c.id_ext as my_id_ext_comune_imm, c.descrizione as my_desc_comune_imm " +
					"from sit_d_persona p " +
					"left outer join sit_comune c on (p.id_ext_comune_imm = c.id_ext) " +
					"where p.id_ext = ? and p.data_imm = ? order by p.dt_inizio_val desc";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			String idExtProvinciaImm = null;
			String idExtComuneImm = null;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, p.getIdExtDPersona());
				ps.setDate(2, p.getDataImm());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (idExtProvinciaImm == null && rs.getObject("id_ext_provincia_imm") != null) {
						idExtProvinciaImm = rs.getString("id_ext_provincia_imm");
					}
					if (idExtComuneImm == null && rs.getObject("id_ext_comune_imm") != null &&
					rs.getObject("my_id_ext_comune_imm") != null && rs.getObject("my_desc_comune_imm") != null) {
						idExtComuneImm = rs.getString("id_ext_comune_imm");
					}
				}					
			} catch(SQLException e) { 
				throw e; 
			} finally {
				DbUtils.close(ps);
				DbUtils.close(rs);
			}
		
			p.setIdExtProvinciaImm(idExtProvinciaImm);
			p.setIdExtComuneImm(idExtComuneImm);
		}
	}
		
	private void valorizzaLuogoEmi(SitDPersonaSt p) throws SQLException{
		
		if(p.getDataEmi()!=null){
		
			/*String sql="select @FIELD@ from sit_d_persona where id_ext=? and data_emi=? and @FIELD@ is not null order by dt_inizio_val desc";
			
			String sqlc = "select @FIELD@ "+
					"from sit_d_persona p left join sit_comune c on (@FIELD@=c.id_ext) "+
					"where C.DESCRIZIONE is not null and p.id_ext=? and data_emi=? " +
					"order by p.dt_inizio_val desc";
					
			p.setIdExtProvinciaEmi((String)this.getCampoPersona(sql, "id_ext_provincia_emi", p.getIdExtDPersona(), p.getDataEmi()));
			p.setIndirizzoEmi((String)this.getCampoPersona(sql, "indirizzo_emi", p.getIdExtDPersona(), p.getDataEmi()));
			p.setIdExtComuneEmi((String)this.getCampoPersona(sqlc, "id_ext_comune_emi", p.getIdExtDPersona(), p.getDataEmi()));
			*/
			
			String sql = "select p.*, c.id_ext as my_id_ext_comune_emi, c.descrizione as my_desc_comune_emi " +
					"from sit_d_persona p " +
					"left outer join sit_comune c on (p.id_ext_comune_emi = c.id_ext) " +
					"where p.id_ext = ? and p.data_emi = ? order by p.dt_inizio_val desc";
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			String idExtProvinciaEmi = null;
			String indirizzoEmi = null;
			String idExtComuneEmi = null;
			
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, p.getIdExtDPersona());
				ps.setDate(2, p.getDataEmi());
				rs = ps.executeQuery();
				while (rs.next()) {
					if (idExtProvinciaEmi == null && rs.getObject("id_ext_provincia_emi") != null) {
						idExtProvinciaEmi = rs.getString("id_ext_provincia_emi");
					}
					if (indirizzoEmi == null && rs.getObject("indirizzo_emi") != null) {
						indirizzoEmi = rs.getString("indirizzo_emi");
					}
					if (idExtComuneEmi == null && rs.getObject("id_ext_comune_emi") != null &&
					rs.getObject("my_id_ext_comune_emi") != null && rs.getObject("my_desc_comune_emi") != null) {
						idExtComuneEmi = rs.getString("id_ext_comune_emi");
					}
				}					
			} catch(SQLException e) { 
				throw e; 
			} finally {
				DbUtils.close(ps);
				DbUtils.close(rs);
			}
			
			p.setIdExtProvinciaEmi(idExtProvinciaEmi);
			p.setIndirizzoEmi(indirizzoEmi);
			p.setIdExtComuneEmi(idExtComuneEmi);
			
		}
	}
	
	private void insertP(List<SitDPersonaSt> lstp, PreparedStatement ps, String processid) throws SQLException{
		int i=1;
		for(SitDPersonaSt p : lstp){
			int index=0;
			ps.setString(++index, p.getIdExtDPersona());
			ps.setString(++index, p.getCognome());
			ps.setString(++index, p.getNome());
			ps.setString(++index, p.getSesso());
			ps.setString(++index, p.getCodfisc());
			
			ps.setString(++index, p.getIdExtProvinciaNascita());
			ps.setString(++index, p.getIdExtComuneNascita());
			ps.setDate(++index, p.getDataNascita());
			
			ps.setString(++index, p.getIdExtProvinciaMor());
			ps.setString(++index, p.getIdExtComuneMor());
			ps.setDate(++index, p.getDataMor());
			
			ps.setString(++index, p.getStatoCivile());
			ps.setDate(++index, p.getDataInizioResidenza());
			ps.setString(++index, p.getIdExtStato());
			
			ps.setString(++index, p.getIdExtProvinciaImm());
			ps.setString(++index, p.getIdExtComuneImm());
			ps.setDate(++index, p.getDataImm());
			
			ps.setString(++index, p.getIdExtProvinciaEmi());
			ps.setString(++index, p.getIdExtComuneEmi());
			ps.setDate(++index, p.getDataEmi());
			ps.setString(++index, p.getIndirizzoEmi());
		
			ps.setInt(++index, i);
			ps.setString(++index, processid);
			
			ps.executeUpdate();
			i++;
		}
	}
	
	private void insertPf(SitDPersFamSt pf, PreparedStatement ps, int numInserimenti, String processid) throws SQLException{
	
			//int index=0;
			ps.setString(1, pf.getRelazPar());
			ps.setString(2, pf.getIdExtDFamiglia());
			ps.setString(3, pf.getIdExtDPersona());
			ps.setDate(4, pf.getDtInizioDato());
			ps.setDate(5, pf.getDtFineDato());
			ps.setInt(6, numInserimenti);
			ps.setString(7, processid);
			ps.executeUpdate();
			
	}
	
	private void insert(ResultSet origine, PreparedStatement ps) throws SQLException{
	
		for(int i=1; i<COLS_SIT_D_FAM_STORICO; i++)
			ps.setObject(i, origine.getObject(i));
		
		ps.setString(COLS_SIT_D_FAM_STORICO, origine.getString("PROCESSID"));
		
		try{
			ps.executeUpdate();
		}catch(SQLException e){
			log.error("Errore inserimento "+origine.getString("id")+" "+SDF.format(origine.getDate("data_ini_rif")));
			throw e;
		}
		
	}
	
	
	private boolean existsDuplicati(Connection conn, String idExt) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String SQL_SELECT_NUM_RECORD_PERSONA = "select max(n_ord) num from sit_d_persona_st where id_ext_d_persona=? ";
		boolean duplicati = false;
		try{
			ps = conn.prepareStatement(SQL_SELECT_NUM_RECORD_PERSONA);
			ps.setString(1, idExt);
			rs = ps.executeQuery();
			if(rs.next()){
				int num = rs.getInt("NUM");
				duplicati = num>1;
			}
			
		}catch(SQLException e){ 
			throw e; 
		}finally{
			DbUtils.close(ps);
			DbUtils.close(rs);
		}
		return duplicati;
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.info("**************************************************************rengine.rule.param.in."+index+".descr");
			
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}
	
	private String scriviLog(Context ctx, String descrizione,List<String> lstId){
		
		String pathFile = null;
		 try{
			
			if(writer==null){
				 ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
				 AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune("dir.files", ctx.getBelfiore(), "1");
				 //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				 //String data = sdf.format(new Date());
				 String path = param.getValueConf()+"LOG"+File.separator;
				 pathFile = path+ctx.getProcessID().split("::")[1]+".anomalie";
				 File dir = new File(path);
				 File f = new File(pathFile);
				 if(!f.exists()){
					 dir.mkdirs();
					 dir.setWritable(true);
					 f.createNewFile();
				 }
				 
				 writer=new BufferedWriter(new FileWriter(f.getPath()));
			}
			 
			writer.write(descrizione+"\n");
			 for(String s : lstId)
				 writer.write(s+"\n");
			 
			 writer.flush();
		 }catch(Exception e){
			 log.error(e.getMessage(),e);
		 }
		 return pathFile;
	}
	
	
	/*private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
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
	}*/

}
