package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.persistence.Query;

import org.apache.log4j.Logger;

//public class LoadDemanioBeniDerivati  extends AbstractLoaderCommand implements Rule{
public class LoadDemanioBeniDerivati extends LoadDemanioBeniBase{
	
	private static final Logger log = Logger.getLogger(LoadDemanioBeniPrincipali.class.getName());
	
	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	public LoadDemanioBeniDerivati(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException{
		
		try {
			//numero di parametri espresso nel properties -1 xchè qui nel codice parte da zero
			List<Object> params = this.caricaParametri(10, ctx);
			//String[] tabDerivatiBene = {"DM_B_BENE_INV","DM_B_FASCICOLO","DM_B_TIPO_USO","DM_E_EVENTO","DM_B_INDIRIZZO","DM_B_MAPPALE","DM_B_INFO","DM_B_TITOLO","DM_B_TERRENO"};
			
			String dato = (String)params.get(0);
			String provenienza = (String)params.get(1);
			String tipo = (String)params.get(2);
			String vista = (String)params.get(3);
			Timestamp dtElab = (Timestamp)params.get(4);
			BigDecimal sostituisci = (BigDecimal)params.get(5);
			BigDecimal accoda = (BigDecimal)params.get(6);
			BigDecimal accodaAnnulla = (BigDecimal)params.get(7);
			String descrizione = (String)params.get(8);
			BigDecimal aggiorna = (BigDecimal)params.get(9);
			String vistas = (String)params.get(10);

			Timestamp dtRif = dtElab!=null ? dtElab : new Timestamp( SDF.parse("01/01/0001").getTime() );
			boolean principale = true;
			
			//String message = "dato: " + dato + " provenienza: " + provenienza + " tipo: " + tipo + " vista: " + vista;
			
			//Carico Beni Derivati
			if(!dato.equals("DM_B_BENE")){
				
				String sql0 = "insert into "+dato+ 
                        "(select SEQ_DEM.NEXTVAL, i.* from "+vista+" i where tipo='?1?' and TRIM(provenienza)=TRIM('?2?') and dt_fine_val is null and "+
                        "(dt_inizio_val > ?3? OR dt_mod > ?4? OR EXISTS (select id FROM DM_B_BENE B WHERE I.DM_B_BENE_ID=B.ID AND TRIM(I.provenienza)=TRIM(b.provenienza) AND B.CHIAVE LIKE '%@FIS')) )";

				if (accoda != null && accoda.intValue() == 1){
	               
					sql0 = sql0.replace("?1?", tipo );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro1[ "+provenienza+" ]");
					
					sql0 = sql0.replace("?2?", provenienza );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+tipo+" ]");

					sql0 = sql0.replace("?3?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+dtRif+" ]");
					
					sql0 = sql0.replace("?4?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+dtRif+" ]");

					log.debug(LoadDemanioBeniDerivati.class + " ACCODA E ANNULLA [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQL0["+sql0+"]");
					
					Statement ps0 = conn.createStatement();
					ps0.addBatch(sql0);
					int[] ris0 = ps0.executeBatch();

	       		   conn.commit();	
	       		   
	       		   principale = false;
				}
				else if (accodaAnnulla != null && accodaAnnulla.intValue()==1){
					//Elimino quelli con validità finita (modificati)
					//Elimino quelli non più presenti come chiave bene
					String sql1 ="update "+dato+" b SET b.dt_fine_val=sysdate where b.tipo = '?1?' and b.provenienza= TRIM('?2?') "
							+ "and b.dt_fine_val is null and ( "
							+ "exists (select 1 from "+vista+" v where v.dm_b_bene_id = b.dm_b_bene_id and TRIM(v.provenienza)=TRIM(b.provenienza) and dt_fine_val is null and dt_mod > ?3? ) OR "
							+ "not exists (select 1 from "+vista+" v where v.dm_b_bene_id = b.dm_b_bene_id and TRIM(v.provenienza)=TRIM(b.provenienza) ))";
					
					sql1 = sql1.replace("?1?", tipo );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL1 - Parametro1[ "+provenienza+" ]");
					
					sql1 = sql1.replace("?2?", provenienza );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL1 - Parametro2[ "+tipo+" ]");

					sql1 = sql1.replace("?3?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL1 - Parametro2[ "+dtRif+" ]");
					
					log.debug(LoadDemanioBeniDerivati.class + " ACCODA E ANNULLA [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQL1["+sql1+"]");
					
					Statement ps1 = conn.createStatement();
					ps1.addBatch(sql1);
					int[] ris1 = ps1.executeBatch();

					sql0 = sql0.replace("?1?", tipo );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro1[ "+provenienza+" ]");
					
					sql0 = sql0.replace("?2?", provenienza );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+tipo+" ]");

					sql0 = sql0.replace("?3?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+dtRif+" ]");
					
					sql0 = sql0.replace("?4?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQL0 - Parametro2[ "+dtRif+" ]");

					log.debug(LoadDemanioBeniDerivati.class + " ACCODA E ANNULLA [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQL0["+sql0+"]");
					
					Statement ps0 = conn.createStatement();
					ps0.addBatch(sql0);
					int[] ris0 = ps0.executeBatch();

	       		   conn.commit();	
	       		   
	       		   principale = false;
				}
				else if(sostituisci != null && sostituisci.intValue()==1){ // sostituisci
					String sqld = "delete from "+dato+" WHERE TRIM(provenienza)= TRIM('?1?') and tipo= '?2?' ";

					sqld = sqld.replace("?1?", provenienza );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQLD - Parametro1[ "+provenienza+" ]");
					
					sqld = sqld.replace("?2?", tipo );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQLD - Parametro2[ "+tipo+" ]");

					log.debug(LoadDemanioBeniDerivati.class + " SOSTITUISCI [provenienza:"+provenienza+"], [tipo:"+tipo+"] - SQLD["+sqld+"]");
					
					Statement psd = conn.createStatement();
					psd.addBatch(sqld);
					int[] risd = psd.executeBatch();

					
					String sqli = "insert into "+dato+" (select SEQ_DEM.NEXTVAL ID, v.* FROM "+vista+" v WHERE TRIM(provenienza)=TRIM('?1?') and tipo='?2?')";

					sqli = sqli.replace("?1?", provenienza );
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQLI - Parametro1[ "+provenienza+" ]");
					
					sqli = sqli.replace("?2?",  tipo);
					log.debug(LoadDemanioBeniDerivati.class + " AGGIORNA - SQLI - Parametro2[ "+tipo+" ]");
					
					log.debug(LoadDemanioBeniDerivati.class + " SOSTITUISCI [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQLI["+sqli+"]");

					Statement psi = conn.createStatement();
					psi.addBatch(sqli);
					int[] risi = psi.executeBatch();
		       		
		       		conn.commit();	
		       		
		       		principale = false;
				}else //aggiorna
				{	
					//1.Termino validità beni non più presenti
					//2.Modifica e Inserimento nuovi dati sulla base della chiave originaria
					if(vista!=null){
						//Elimino quelli non più esistenti e quelli con validità finita
						String sql2 = "update "+dato+" b SET b.dt_fine_val=sysdate where b.tipo = '?1?' and b.provenienza= '?2?' and not exists "
								+ "(select 1 from "+vista+" v where v.dm_b_bene_id = b.dm_b_bene_id and TRIM(v.provenienza)=TRIM(b.provenienza) and dt_fine_val is null)";

						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQL2["+sql2+"]");
						
						sql2 = sql2.replace("?1?", tipo );
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA - SQL2 - Parametro1[ "+tipo+" ]");
						
						sql2 = sql2.replace("?2?", provenienza );
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA - SQL2 - Parametro2[ "+provenienza+" ]");

						Statement preparedStatement = conn.createStatement();
						preparedStatement.addBatch(sql2);
						int[] ris2 = preparedStatement.executeBatch();
						
//						PreparedStatement preparedStatement = conn.prepareStatement( sql2 );
//						preparedStatement.setString(1, tipo );
//			       		preparedStatement.setString(2, provenienza );
//			       		int risd = preparedStatement .executeUpdate();
			       		
			       		String sql3 = "MERGE INTO "+dato+" d " + 
								  "USING (SELECT i.* FROM "+vista+" i WHERE i.dt_fine_val is null and (i.dt_inizio_val > ?1? OR i.dt_mod > ?2? " +
								         "OR EXISTS (select id FROM DM_B_BENE B WHERE I.DM_B_BENE_ID=B.ID AND TRIM(I.provenienza)=TRIM(b.provenienza) AND B.CHIAVE LIKE '%@FIS')) and provenienza= '?3?' ) o "+
						    	  "ON (d.DM_B_BENE_ID = o.DM_B_BENE_ID and d.provenienza=o.provenienza and d.tipo=o.tipo) "+
						    	  " WHEN MATCHED THEN "+
						    	  " UPDATE SET " + this.getMergeClause( dato, tipo, vista, conn ) +
						    	  " WHEN NOT MATCHED THEN "+
						    	  " INSERT  (ID, "+ this.getDestFields( vista, conn ) +" ) "+
						    	  " values (SEQ_DEM.NEXTVAL, "+ this.getOrigFields(vista, conn) +" )";
					
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA [provenienza:"+provenienza+"],[tipo:"+tipo+"] - SQL3["+sql3+"]");
						
						Statement preSta1 = conn.createStatement();
						
						sql3 = sql3.replace("?1?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA - SQL3 - Parametro1[ TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY') ]");
						
						sql3 = sql3.replace("?2?", "TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY')" );
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA - SQL3 - Parametro2[ TO_DATE('"+SDF.format( dtRif )+"', 'DD/MM/YYYY') ]");
						
						sql3 = sql3.replace("?3?", provenienza );
						log.debug(LoadDemanioBeniDerivati.class + "AGGIORNA - SQL3 - Parametro3["+provenienza+"]");
						preSta1.addBatch(sql3);
						int[] riss = preSta1.executeBatch();

						conn.commit();	

					}
					principale = false;
				}
				
			}
			//Aggiornamento data di elaborazione
			if (!principale)
				this.aggiornaDataElaborazione(dato, provenienza, conn);

			//XXX Aggiorno correlazione
			String belfiore = ctx.getBelfiore();
			Connection connDiogene = ctx.getConnection("DWH_" + belfiore);
			this.aggiornaTabellaCorrelazione( connDiogene );
			if (connDiogene != null && !connDiogene.isClosed())
					connDiogene.close();
			
			//RDemanioTerreniDao dao = (RDemanioTerreniDao) DaoFactory.createDao(conn,tab, ctx.getEnteSorgenteById(fk_ente_sorgente)); 
			//boolean salvato = dao.save(ctx.getBelfiore());
			
		} 
		catch (Exception e)
		{
			log.error("LoadDemanioBeniPrincipali",e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}

		String msg = "Record DM_B_DENE inserito";
		
		return(new ApplicationAck(msg));

	}//-------------------------------------------------------------------------
	
	public void aggiornaTabellaCorrelazione(Connection connDiogene) throws Exception{
		//Le tabelle della correlazione sono in DIOGENE
		//Connection connDiogene = RulesConnection.getConnection("DWH");
				
		this.aggiornaUpdateTabCorrelazione(connDiogene);
		this.aggiornaDeleteTabCorrelazione(connDiogene);

		if (connDiogene != null && !connDiogene.isClosed())
				connDiogene.close();
	}//-------------------------------------------------------------------------

	private void aggiornaDeleteTabCorrelazione(Connection  conn) {
		
		String sqlVia = this.getSqlDelSitCorrelazioneVariazioni("VIA", "sit_via_totale", "dm_b_indirizzo");

		String sqlCiv = this.getSqlDelSitCorrelazioneVariazioni("CIV", "sit_civico_totale", "dm_b_indirizzo");

		String sqlFab =this.getSqlDelSitCorrelazioneVariazioni("FAB", "sit_fabbricato_totale", "dm_b_mappale");

		Query q=null;
		PreparedStatement preparedStatement = null;
		try{	
			preparedStatement = conn.prepareStatement( sqlVia );
       		preparedStatement.setString(1, "1");
       		int risVia = preparedStatement.executeUpdate();
       		preparedStatement.close();
			
		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlVia+"]", t);
		}
		try{
			preparedStatement = conn.prepareStatement( sqlCiv );
       		preparedStatement.setString(1, "1");
       		int risCiv = preparedStatement.executeUpdate();
       		preparedStatement.close();

		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlCiv+"]", t);
		}
		try{
			preparedStatement = conn.prepareStatement( sqlFab );
       		preparedStatement.setString(1, "2");
       		int risFab = preparedStatement.executeUpdate();
       		preparedStatement.close();

		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlFab+"]", t);
		}
	}//-------------------------------------------------------------------------
	
	private void aggiornaUpdateTabCorrelazione(Connection  conn) {
		
		String sqlVia = this.getSqlUpdSitCorrelazioneVariazioni("VIA", "sit_via_totale", "dm_b_indirizzo");
		
		String sqlCiv = this.getSqlUpdSitCorrelazioneVariazioni("CIV", "sit_civico_totale", "dm_b_indirizzo");

		String sqlFab =this.getSqlUpdSitCorrelazioneVariazioni("FAB", "sit_fabbricato_totale", "dm_b_mappale");
		
		Query q=null;
		PreparedStatement preparedStatement = null;
		try{	
			preparedStatement = conn.prepareStatement( sqlVia );
       		preparedStatement.setString(1, "1");
       		log.info("SQLVIA PARAM 1: 1");
       		log.info(sqlVia);
       		int risVia = preparedStatement.executeUpdate();
       		preparedStatement.close();
			
		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlVia+"]", t);
		}
		try{
			preparedStatement = conn.prepareStatement( sqlCiv );
       		preparedStatement.setString(1, "1");
       		log.info("SQLCIV PARAM 1: 1");
       		log.info(sqlCiv);
       		int risCiv = preparedStatement.executeUpdate();
       		preparedStatement.close();
       		
		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlCiv+"]", t);
		}
		try{
			preparedStatement = conn.prepareStatement( sqlFab );
       		preparedStatement.setString(1, "2");
       		log.info("SQLFAB PARAM 1: 2");
       		log.info(sqlFab);
       		int risFab = preparedStatement.executeUpdate();
       		preparedStatement.close();
       		
		} catch (Throwable t) {
			log.error("Errore aggiornaTabellaCorrelazione SQL["+sqlFab+"]", t);
		}
	}//-------------------------------------------------------------------------


	private String getSqlDelSitCorrelazioneVariazioni(String tipo, String tabCorr, String tabOrig){
		
		String sql = "insert into sit_correlazione_variazioni "+
		"select id_dwh, fk_ente_sorgente, prog_es,'0' as flg_elaborato,'"+tipo+"','DEL' as tipo_variazione, sysdate, null as note, null as fields, null as dt_elab_correlazione "+
		"from "+tabCorr+" v where fk_ente_sorgente=42 and prog_es=? and not exists (select 1 from "+tabOrig+" where v.id_dwh=id)";

		return sql;
	}//-------------------------------------------------------------------------
	
	private String getSqlUpdSitCorrelazioneVariazioni(String tipo, String tabCorr, String tabOrig){
		
		String sql = "insert into sit_correlazione_variazioni "+
		"select id_dwh, fk_ente_sorgente, prog_es,'0' as flg_elaborato,'"+tipo+"','DEL' as tipo_variazione, sysdate, null as note, null as fields, null as dt_elab_correlazione "+
		"from "+tabCorr+" v where fk_ente_sorgente=42 and prog_es=? and exists (select 1 from "+tabOrig+" o where v.id_dwh=o.id and o.dt_mod > sysdate-1)";

		return sql;
	}//-------------------------------------------------------------------------
	
	

}
