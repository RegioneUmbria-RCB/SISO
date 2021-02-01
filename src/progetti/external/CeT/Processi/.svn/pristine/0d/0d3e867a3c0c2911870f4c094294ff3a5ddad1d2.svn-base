package it.webred.rulengine.brick.elab.toponomastica;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class UpdateToponomasticaCat extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(UpdateToponomasticaCat.class.getName());
	private Connection conn=null; 
	private String enteID;
	
	private final String UPDATE ="UPD";
	private final String DELETE ="DEL";
	
	private String SQL_STRADE_TO_DELETE;
	private String SQL_STRADE_TO_UPDATE;
	private String SQL_CIV_TO_UPDATE;
	private String SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI;
	private String SQL_MERGE_AGGR_TOPO;
	private String SQL_MERGE_AGGR_LOCALIZZA;
	
	private String SQL_SITIPART_RELATE;
	private String SQL_SITIUIU;
	private String SQL_INSERT_SITICIVI_UIU;
	private String SQL_SITICIVI_RELATE;
	private String SQL_PRESENZA_SITILOC_UIU_PRINCIPALE;
	
	//private String SQL_CIVICI_FROM_RE_WS_CIVICO;
	
	//Catasto
	private String FK_ENTE_SORGENTE = "4"; 
	//Toponomastica Catastale
	private String PROG_ES = "2";
	
	public UpdateToponomasticaCat(BeanCommand bc) {
		super(bc);
	}

	public UpdateToponomasticaCat(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("UpdateToponomasticaCat run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		Long fonteDati = ctx.getIdFonte();
		
		PreparedStatement pst =null; ResultSet rs =null; Statement st =null;

		try {
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
			this.getJellyParam(ctx);
			
			this.aggiornaStrade(ctx, conn);
			
			this.aggiornaCivici(ctx, conn);
			
			this.aggiornaSiticiviUiu(ctx,conn);
			
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
	
	private void inserisciTabCorrelazioneVar(BigDecimal id_dwh, String tipoDato, String tipoVar, String note, String cols) throws SQLException {
		
		PreparedStatement pstCorr=null;
		
		try{
		
		 pstCorr = conn.prepareStatement(this.SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI);
		
		 pstCorr.setBigDecimal(1, id_dwh);
		 pstCorr.setString(2, this.FK_ENTE_SORGENTE);
		 pstCorr.setString(3, this.PROG_ES);
		 pstCorr.setString(4, tipoDato);
		 pstCorr.setString(5, tipoVar);
		 pstCorr.setString(6, note);
		 pstCorr.setString(7, cols); //Colonne
		 
		 pstCorr.executeUpdate();	
		 
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtils.close(pstCorr);
		}
		
	}
	
	private void gestisciTrigger(Connection conn, boolean attiva, String nomeTrigger) throws Exception{
		
		PreparedStatement pstUpd = null;
		
		try{
			String sqlTrigger =null;
			if(!attiva)
				sqlTrigger = "ALTER TRIGGER "+nomeTrigger+" DISABLE";
			else
				sqlTrigger = "ALTER TRIGGER "+nomeTrigger+" ENABLE";
		
			pstUpd = conn.prepareStatement(sqlTrigger);
			pstUpd.execute();
			
		}catch(Exception e){
			
		}finally{
			DbUtils.close(pstUpd);
		}
		
	}
	
	private void gestisciIndiceSpaziale(Connection conn, boolean attiva) throws Exception{
		
		PreparedStatement pstUpd = null;
		
		try{
			String sql =null;
			if(!attiva)
				sql = "DROP INDEX AGGR_LOCALIZZA_SHAPE_SDX";
			else
				sql = "CREATE INDEX AGGR_LOCALIZZA_SHAPE_SDX ON AGGR_LOCALIZZA(SHAPE) INDEXTYPE IS MDSYS.SPATIAL_INDEX";
		
			pstUpd = conn.prepareStatement(sql);
			pstUpd.execute();
			
		}catch(Exception e){
			
		}finally{
			DbUtils.close(pstUpd);
		}
		
		}
	
	private void aggiornaCivici(Context ctx, Connection conn) throws Exception{
		
		PreparedStatement pstSel =null; ResultSet rs =null; 
		PreparedStatement pstUpd = null;
		PreparedStatement pstUpdReWsCivico = null;
		
		boolean attiva = false;
		
		try{
			
		verificaCiviciDuplicati(ctx, conn);
		
		//Disabilito il trigger della AGGR_LOCALIZZA che aggiorna la SITICIVI_UIU
		this.gestisciTrigger(conn, attiva, "AGGR_LOCALIZZA_TIUA");
		this.gestisciIndiceSpaziale(conn, attiva);
		
		//Seleziono le civici da AGGIORNARE ED INSERIRE
				
		log.debug("Esecuzione ricerca civici da aggiornare: "+ this.SQL_CIV_TO_UPDATE);
		pstSel = conn.prepareStatement(this.SQL_CIV_TO_UPDATE);
		rs = pstSel.executeQuery();
		
		log.debug("Aggiornamento tabella SIT_CORRELAZIONE_VARIAZIONI con CIVICI MODIFICATI: "+this.SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI);
		while(rs.next()){
			 BigDecimal id_dwh = rs.getBigDecimal("IDC");
			 this.inserisciTabCorrelazioneVar(id_dwh,"CIV",this.UPDATE,null,rs.getString("FIELDS"));
		}
		
		log.debug("Aggiornata tabella SIT_CORRELAZIONE_VARIAZIONI con record da MODIFICARE");
		
		log.debug("Esecuzione merge in AGGR_LOCALIZZA: "+this.SQL_MERGE_AGGR_LOCALIZZA);
		pstUpd = conn.prepareStatement(this.SQL_MERGE_AGGR_LOCALIZZA);
		pstUpd.execute();
		
		log.debug("Inseriti e modificati record della tabella AGGR_LOCALIZZA");
		
		
		pstUpdReWsCivico = conn.prepareStatement("UPDATE RE_WS_CIVICO SET RE_FLAG_ELABORATO='1' WHERE IDC IN (SELECT PKID_LOC FROM AGGR_LOCALIZZA)");
		int num = pstUpdReWsCivico.executeUpdate();
		
		log.debug("Elaborati "+num+" record della tabella RE_WS_CIVICO");
		
		}catch(Exception e){
			throw e;
		}finally{
			
			//Riabilito il trigger
			attiva = true;
			this.gestisciTrigger(conn, attiva, "AGGR_LOCALIZZA_TIUA");
			this.gestisciIndiceSpaziale(conn, attiva);
			
			DbUtils.close(rs);
			DbUtils.close(pstSel);
			DbUtils.close(pstUpd);
		}
		
			
	}
	
	private void aggiornaSiticiviUiu(Context ctx, Connection conn) throws SQLException{
		
		PreparedStatement pstUpd = null;
		
		try{
		log.debug("Avvio aggiornamento SITICIVI_UIU...");

		//String sqlUpd = "UPDATE SITICIVI_UIU SET DATA_FINE_VAL = SYSDATE WHERE DATA_FINE_VAL > SYSDATE";
		String sqlUpd = "TRUNCATE TABLE SITILOC_UIU ";
		log.debug("Esecuzione update:"+sqlUpd);
		pstUpd = conn.prepareStatement(sqlUpd);
		pstUpd.executeUpdate();
	
	     
		this.intersectShapeCiviciParticelle();
		
		this.intersectShapeCiviciPertinenze();
		
		log.debug("Fine aggiornamento SITICIVI_UIU");
		
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtils.close(pstUpd);
		}
		
	}
	
/*	private void intersectShapeCiviciParticelle() throws SQLException{
		
		PreparedStatement pstSel1 = null; 
		PreparedStatement pstSel2 = null; 
		PreparedStatement pstSel3 =null;
		PreparedStatement pstSel4 =null;
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null; 
		ResultSet rs4 = null; 
		
		PreparedStatement pstUpd = null;
		
		try{
			
			log.debug("Aggiornamento SITICIVI_UIU - Giro basato su intersezioni shape civici - shape particelle");
			
			//Seleziono i civici da SITICIVI
			// Intersezioni shape civici - shape particelle
			
			int totaleCivici = 0;
			int ordine=0;
			
			String sqlSel = "SELECT COUNT(*) FROM SITICIVI WHERE DATA_FINE_VAL = TO_DATE('31/12/9999','dd/MM/yyyy') AND SHAPE IS NOT NULL";
			pstSel1 = conn.prepareStatement(sqlSel);
			rs1 = pstSel1.executeQuery();
			if(rs1.next())
				totaleCivici = rs1.getInt(1);
			
			sqlSel = "SELECT ROWNUM, C.* FROM SITICIVI C WHERE DATA_FINE_VAL = TO_DATE('31/12/9999','dd/MM/yyyy') AND SHAPE IS NOT NULL";
			pstSel1 = conn.prepareStatement(sqlSel);
			
			rs1 = pstSel1.executeQuery();
			
			pstSel2 = conn.prepareStatement(this.SQL_SITIPART_RELATE);
			pstSel3 = conn.prepareStatement(SQL_SITIUIU);
			pstUpd = conn.prepareStatement(SQL_INSERT_SITICIVI_UIU);
			
			while(rs1.next()){
				ordine = rs1.getInt("ROWNUM");
				BigDecimal pkidCivi = rs1.getBigDecimal("PKID_CIVI");
				Object civShape = rs1.getObject("SHAPE");
				
				pstSel2.setObject(1, civShape); //Particelle che contengono il civico
				pstSel2.setString(2, "QUERYTYPE=WINDOW MASK=CONTAINS");
				
				//log.debug("Ricerca SQL_SITIPART_RELATE per civico con id: "+pkidCivi);
				
				rs2 = pstSel2.executeQuery();
				
				while(rs2.next()){
					
					String codNazionale = rs2.getString("COD_NAZIONALE");
					BigDecimal foglio = rs2.getBigDecimal("FOGLIO");
					String particella = rs2.getString("PARTICELLA");
					String sub = rs2.getString("SUB");
					
					if(particella!=null){
						
						pstSel3.setString(1, codNazionale);
						pstSel3.setBigDecimal(2, foglio);
						pstSel3.setString(3, particella);
						pstSel3.setString(4, sub);
						
						//log.debug("Ricerca SQL_SITIUIU per cod_nazionale["+codNazionale+"] foglio["+foglio+"] particella["+particella+"] sub["+sub+"]");
						
						rs3 = pstSel3.executeQuery();
						
						while(rs3.next()){
							
							BigDecimal pkidUiu = rs3.getBigDecimal("PKID_UIU");
							
							pstUpd.setBigDecimal(1, pkidCivi);
							pstUpd.setBigDecimal(2, pkidUiu);
							pstUpd.setString(3, "Y");
							
							try{
							
								pstUpd.executeUpdate();
								//log.debug("Inserito record in SITICIVI_UIU per pki_civi="+pkidCivi+" e pkid_uiu="+pkidUiu);
							
							}catch(SQLException e){
								//log.warn("Skip record (già presente) per pki_civi="+pkidCivi+" e pkid_uiu="+pkidUiu+" \n "+e.getMessage());
							
							}
							
						}
						
					}
						
					
				}
			
				double perc = 100*ordine/totaleCivici;
				if(ordine%1000 == 0)
					log.debug("Aggiornamento SITICIVI_UIU elaborazione del "+ perc +" % dei civici("+pkidCivi+") completata...");
			}
			
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtils.close(rs1);
			DbUtils.close(rs2);
			DbUtils.close(rs3);
			DbUtils.close(rs4);
			DbUtils.close(pstSel1);
			DbUtils.close(pstSel2);
			DbUtils.close(pstSel3);
			DbUtils.close(pstSel4);
			DbUtils.close(pstUpd);
		}
		
	}*/
	
	
	private void intersectShapeCiviciParticelle() throws SQLException{
		String sql = "{call assegna_civico_particella('WS')}";
		PreparedStatement pstUpd = null;
		
		try{
			
			log.debug("Aggiornamento SITICIVI_UIU - Giro basato su intersezioni shape civici - shape particelle");
			
			//Seleziono i civici da SITICIVI
			// Intersezioni shape civici - shape particelle
			
			pstUpd = conn.prepareCall(sql);
			pstUpd.execute();
			
			
		}catch(SQLException e){
			log.error(e.getMessage()+" "+sql,e);
			throw e;
		}finally{
			DbUtils.close(pstUpd);
		}
		
	}
	
	
	private void intersectShapeCiviciPertinenze() throws SQLException{
		
		String sql = "{call assegna_civico_pertinenze('WS')}";
			PreparedStatement pstUpd = null;
			
			try{
				
				log.debug("Aggiornamento SITICIVI_UIU - Giro basato su pertinenze");
				
				pstUpd = conn.prepareCall(sql);
				pstUpd.execute();
				
				
			}catch(SQLException e){
				log.error(e.getMessage()+" "+sql,e);
				throw e;
			}finally{
				DbUtils.close(pstUpd);
			}
			
		}
	
	
	/*private void intersectShapeCiviciPertinenze() throws SQLException{
		
		PreparedStatement pstSel1 = null; 
		PreparedStatement pstSel2 = null; 
		PreparedStatement pstSel3 =null;
		PreparedStatement pstSel4 =null;
		PreparedStatement pstSel5 =null;
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null; 
		ResultSet rs4 = null; 
		ResultSet rs5 = null; 
		
		PreparedStatement pstUpd = null;
		
		try{
		
		int totalePert = 0;
		int ordine = 0;
			
		//Giro basato su pertinenze
		log.debug("Aggiornamento SITICIVI_UIU - Giro basato su pertinenze");
		pstSel1 = conn.prepareStatement("SELECT COUNT(*) FROM pertinenze P WHERE data_fine_val >= sysdate");
		rs1 = pstSel1.executeQuery();
		if(rs1.next())
			totalePert = rs1.getInt(1);
		
		pstSel1 = conn.prepareStatement("SELECT ROWNUM, P.* FROM pertinenze P WHERE data_fine_val >= sysdate");
		
		pstSel2 = conn.prepareStatement(SQL_SITIPART_RELATE);  //Pertinenze che intersecano la particella
		
		pstSel3=  conn.prepareStatement(SQL_SITICIVI_RELATE);  //Pertinenze che intersecano il civico
		
		pstSel4 = conn.prepareStatement(SQL_SITIUIU);
		pstSel5 = conn.prepareStatement(SQL_PRESENZA_SITILOC_UIU_PRINCIPALE);
		
		pstUpd = conn.prepareStatement(SQL_INSERT_SITICIVI_UIU);
		
		try{
			rs1 = pstSel1.executeQuery();
		}catch(SQLException e){
			rs1 = null;
			log.warn(e.getMessage()+": PERTINENZE");
		}
		
		while(rs1!=null && rs1.next()){
			
			ordine = rs1.getInt("ROWNUM");
			
			Object rPertShape = rs1.getObject("SHAPE");
			
			pstSel2.setObject(1, rPertShape); //Particelle che intersecano la pertinenza	
			pstSel2.setString(2, "QUERYTYPE=WINDOW MASK=ANYINTERACT");
			rs2 = pstSel2.executeQuery();
			while(rs2.next()){  //Per tutte le particelle associate alla pertinenza
					
					String codNazionale = rs2.getString("COD_NAZIONALE");
					BigDecimal foglio = rs2.getBigDecimal("FOGLIO");
					String particella = rs2.getString("PARTICELLA");
					String sub = rs2.getString("SUB");
					
					//log.debug("Ricerco le UIU con: codNazionale="+codNazionale+"+foglio="+foglio+"particella="+particella+"sub="+sub);
					
					pstSel4.setString(1, codNazionale);
					pstSel4.setBigDecimal(2, foglio);
					pstSel4.setString(3, particella);
					pstSel4.setString(4, sub);
					
					rs4 = pstSel4.executeQuery();
					
					while(rs4.next()){ 
						BigDecimal pkidUiu = rs4.getBigDecimal("PKID_UIU");
						
						//log.debug("Verifico che la uiu non sia già presente direttamente:"+ pkidUiu);
						
						pstSel5.setBigDecimal(1, pkidUiu);
						rs5 = pstSel5.executeQuery();
						
						if(rs5.next() && rs5.getInt(1)==0){ //Verifico che la uiu non sia stata già associata al civico per via diretta
							
							//Recuper i civici associati alla pertinenza e Inserisco
							pstSel3.setObject(1, rPertShape); //Civici che intersecano la pertinenza
							rs3 = pstSel3.executeQuery();
						//	log.debug("Ricerco i civici associati alla pertinenza, per aggiungerli");
							while(rs3.next()){ // Per tutti civici associati alla pertinenza
								
								BigDecimal pkidCivi = rs3.getBigDecimal("PKID_CIVI");
							
								//log.debug("Inserisco per pki_civi="+pkidCivi+" e pkid_uiu="+pkidUiu);
								pstUpd.setBigDecimal(1, pkidCivi);
								pstUpd.setBigDecimal(2, pkidUiu);
								pstUpd.setString(3, "N");
							
								try{
									pstUpd.executeUpdate();
								
								}catch(SQLException e){
									log.warn("Skip record (già presente) per pki_civi="+pkidCivi+" e pkid_uiu="+pkidUiu);
								}
							}
						}
					
				}
				
			}
			
			double perc = 100*ordine/totalePert;
			if(ordine%1000 == 0)
				log.debug("Aggiornamento SITICIVI_UIU elaborazione del "+ perc +" % delle pertinenze completata...");
			
		}
		
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtils.close(rs1);
			DbUtils.close(rs2);
			DbUtils.close(rs3);
			DbUtils.close(rs4);
			DbUtils.close(rs5);
			DbUtils.close(pstSel1);
			DbUtils.close(pstSel2);
			DbUtils.close(pstSel3);
			DbUtils.close(pstSel4);
			DbUtils.close(pstSel5);
			DbUtils.close(pstUpd);
		}
		
	}*/
	
	private void verificaVieDuplicate(Context ctx, Connection conn) throws Exception{
		String lstCampi = "cod_via, toponimo,topo_via,topo1,topo2,topo3,topo4,topo5,cod_nazionale";
		String sql = "select distinct cod_via from (select distinct "+lstCampi+" from re_ws_strada) having count(cod_via)>1 group by (cod_via)";
		PreparedStatement pst = null; 
		ResultSet rs= null;
		String listaCodici = "Presenza di codici via duplicati:";
		boolean duplicate = false;
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		
		while(rs.next()){
			duplicate = true;
			listaCodici += rs.getBigDecimal(1)+" ";	
		}
			
		}catch(Exception e){}
		finally{
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		
		
		if(duplicate)
			throw new Exception(listaCodici);
		
	}
	
	private void verificaCiviciDuplicati(Context ctx, Connection conn) throws Exception{
		String lstCampi =  " idc,coordx,coordy,civ_completo,indirizzo,cod_via,topo_via,topo1,topo2,topo3,topo4,topo5," +
							"numero,lettera,barra,cod_stato,desc_stato,data_applicazione,data_sospensione,residenziale," +
							"zdn,cap,utente,url ";
		String sql = "select distinct idc from (select distinct "+lstCampi+" from re_ws_civico where re_flag_elaborato = 0) having count(idc)>1 group by (idc)";
		PreparedStatement pst = null; 
		ResultSet rs= null;
		String listaCodici = "Presenza di codici civico duplicati:";
		boolean duplicate = false;
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		
		while(rs.next()){
			duplicate = true;
			listaCodici += rs.getBigDecimal(1)+" ";	
		}
			
		}catch(Exception e){}
		finally{
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		
		
		if(duplicate)
			throw new Exception(listaCodici);
		
	}
	
	private void aggiornaStrade(Context ctx, Connection conn) throws Exception{
		
		PreparedStatement pstDel = null;
		PreparedStatement pstSel =null; ResultSet rs =null; 
		PreparedStatement pstUpd = null;
		PreparedStatement pstUpdReWsStrada = null;
		
		try{
			
			verificaVieDuplicate(ctx,conn);
			
			//Seleziono le vie da RIMUOVERE
			String strFineVal = "UPDATE AGGR_TOPO SET DATA_FINE_VAL = TO_DATE(TO_CHAR(SYSDATE,'dd/MM/yyyy'),'dd/MM/yyyy') WHERE PKID_TOPO=?";
			pstDel = conn.prepareStatement(strFineVal);
			
			log.debug("Esecuzione ricerca vie da rimuovere: "+this.SQL_STRADE_TO_DELETE);
			pstSel = conn.prepareStatement(this.SQL_STRADE_TO_DELETE);
			rs = pstSel.executeQuery();
			
			int count = 0;
			while(rs.next()){
				
				 BigDecimal id_dwh = rs.getBigDecimal("PKID_STRA");
				 log.debug("Aggiornamento fine validità via ["+id_dwh+"] di AGGR_TOPO e inserimento in SIT_CORRELAZIONE_VARIAZIONI...");
				 
				 this.inserisciTabCorrelazioneVar(id_dwh,"VIA", this.UPDATE, null,"AGGR_TOPO.DATA_FINE_VAL");
				 
				 pstDel.setBigDecimal(1, id_dwh);
				 pstDel.executeUpdate();
				 
				 count++;
				
			}
			
			log.debug("Aggiornata tabella SIT_CORRELAZIONE_VARIAZIONI con record da CANCELLARE");
			log.debug("Rimosse "+count+" vie da AGGR_TOPO");
			
			//Seleziono le vie da AGGIORNARE ED INSERIRE
			
			log.debug("Esecuzione ricerca vie da aggiornare: "+this.SQL_STRADE_TO_UPDATE);
			pstSel = conn.prepareStatement(this.SQL_STRADE_TO_UPDATE);
			rs = pstSel.executeQuery();
			
			log.debug("Aggiornamento tabella SIT_CORRELAZIONE_VARIAZIONI con VIE MODIFICATE: "+this.SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI);
			while(rs.next()){
				 BigDecimal id_dwh = rs.getBigDecimal("PKID_STRA");
				 this.inserisciTabCorrelazioneVar(id_dwh,"VIA",this.UPDATE,rs.getString("NOTE"),rs.getString("FIELDS"));
			}
			
			log.debug("Aggiornata tabella SIT_CORRELAZIONE_VARIAZIONI con record da MODIFICARE");
			
			log.debug("Esecuzione merge in AGGR_TOPO: "+this.SQL_MERGE_AGGR_TOPO);
			pstUpd = conn.prepareStatement(this.SQL_MERGE_AGGR_TOPO);
			pstUpd.execute();
			
			log.debug("Inseriti e modificati record della tabella AGGR_TOPO");
			
			pstUpdReWsStrada = conn.prepareStatement("UPDATE RE_WS_STRADA SET RE_FLAG_ELABORATO='1' WHERE COD_VIA IN (SELECT PKID_TOPO FROM AGGR_TOPO)");
			int num = pstUpdReWsStrada.executeUpdate();
			
			log.debug("Elaborati "+num+" record della tabella RE_WS_STRADA");
			
		}catch(SQLException e){
			log.error("Errore aggiornaStrade: "+e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.close(rs);
			DbUtils.close(pstSel);
			DbUtils.close(pstUpd);
			DbUtils.close(pstDel);
		}
				
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			
			log.debug("*******************************INIZIO CARICAMENTO PARAMETRI****************************************");
			
			this.SQL_STRADE_TO_DELETE = getJellyParam(ctx, index++, "SQL_STRADE_TO_DELETE");
			this.SQL_STRADE_TO_UPDATE = getJellyParam(ctx, index++, "SQL_STRADE_TO_UPDATE");
			this.SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI = getJellyParam(ctx, index++, "SQL_INSERT_SIT_CORRELAZIONE_VARIAZIONI");
			this.SQL_MERGE_AGGR_TOPO = getJellyParam(ctx, index++, "SQL_MERGE_AGGR_TOPO");
			this.SQL_MERGE_AGGR_LOCALIZZA = getJellyParam(ctx, index++, "SQL_MERGE_AGGR_LOCALIZZA");
			this.SQL_CIV_TO_UPDATE = getJellyParam(ctx, index++, "SQL_CIV_TO_UPDATE");
			this.SQL_SITIPART_RELATE = getJellyParam(ctx, index++, "SQL_SITIPART_RELATE");
			this.SQL_SITIUIU = getJellyParam(ctx, index++, "SQL_SITIUIU");
			this.SQL_INSERT_SITICIVI_UIU = getJellyParam(ctx, index++, "SQL_INSERT_SITICIVI_UIU");
			this.SQL_SITICIVI_RELATE = getJellyParam(ctx, index++, "SQL_SITICIVI_RELATE");
			this.SQL_PRESENZA_SITILOC_UIU_PRINCIPALE = getJellyParam(ctx, index++, "SQL_PRESENZA_SITILOC_UIU_PRINCIPALE");
			
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
