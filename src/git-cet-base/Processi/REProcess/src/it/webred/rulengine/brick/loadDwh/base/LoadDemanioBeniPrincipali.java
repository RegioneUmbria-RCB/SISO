package it.webred.rulengine.brick.loadDwh.base;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

//public class LoadDemanioBeniPrincipali  extends AbstractLoaderCommand implements Rule{
public class LoadDemanioBeniPrincipali  extends LoadDemanioBeniBase{
	
	
	public LoadDemanioBeniPrincipali(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}//-------------------------------------------------------------------------

	@Override
	public CommandAck runWithConnectionSpec(Context ctx, Connection conn) throws CommandException{
		
		try {
			//numero di parametri espresso nel properties -1 xchè qui nel codice parte da zero
			List<Object> params = this.caricaParametri(4, ctx);
			
			//RDemanioTerreni tab = (RDemanioTerreni)getTabellaDwhInstance(ctx);
			
			String[] tabDerivatiBene = {"DM_B_BENE_INV","DM_B_FASCICOLO","DM_B_TIPO_USO","DM_E_EVENTO","DM_B_INDIRIZZO","DM_B_MAPPALE","DM_B_INFO","DM_B_TITOLO","DM_B_TERRENO"};
			
			String dato = (String)params.get(0);
			String provenienza = (String)params.get(1);
			String tipo = (String)params.get(2);
			String vista = (String)params.get(3);
			Timestamp dtElab = (Timestamp)params.get(4);
			
			String message = "dato: " + dato + " provenienza: " + provenienza + " tipo: " + tipo + " vista: " + vista;
			
			log.info(message);
			
			String sql = "delete FROM @TABELLA@ i WHERE provenienza= ? AND  EXISTS (select id FROM DM_B_BENE B WHERE I.DM_B_BENE_ID=B.ID AND I.provenienza=b.provenienza AND B.CHIAVE LIKE '%@FIS')";
			
			for(String tab : tabDerivatiBene){
				
				if (dato.equalsIgnoreCase(tab)){
					String sqlx = sql.replaceFirst("@TABELLA@", tab);
					log.info("LoadDemanioBeniPrincipali - SQL#1["+sqlx+"]");

					PreparedStatement preparedStatement = conn.prepareStatement( sqlx );
					preparedStatement.setString(1, provenienza);

					preparedStatement .executeUpdate();
					preparedStatement.close();
					
				}
			}
			//conn.commit();			
			boolean principale = false;
			//Carico Immobili
			if(dato.equals("DM_B_BENE") && tipo.equals("I")){

				//1.Termino validità beni non più presenti
				//2.Modifica e Inserimento nuovi dati sulla base della chiave originaria
				
				//Elimino quelli non più esistenti e quelli con validità finita
				this.setFineInesistentiInvalidi(dato, tipo, provenienza, vista, conn);
				
				//Aggiorna e se non esiste inserisci info
				this.upsertBeni(dato, tipo, provenienza, vista, dtElab, conn);
				
				log.info("LoadDemanioBeniPrincipali - Caricamento Beni Principali Immobili");
				principale = true;
			}

			//Carico Fabbricati
			if(dato.equals("DM_B_BENE") && tipo.equals("F")){

				//1.Termino validità beni non più presenti
				//2.Modifica e Inserimento nuovi dati sulla base della chiave originaria
				
				//Elimino quelli non più esistenti e quelli con validità finita
				this.setFineInesistentiInvalidi(dato, tipo, provenienza, vista, conn);
				
				//Aggiorna e se non esiste inserisci info
				this.upsertBeni(dato, tipo, provenienza, vista, dtElab, conn);
				
				log.info("LoadDemanioBeniPrincipali - Caricamento Beni Principali Fabbricati");
				principale = true;
			}

			//Aggiornamento data di elaborazione
			if (principale)
				this.aggiornaDataElaborazione(dato, provenienza, conn);
			
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
		
	private int setFineInesistentiInvalidi(String dato, String tipo, String provenienza, String vista, Connection conn) throws Exception{
		String sql0 = "update "+dato+" b SET b.dt_fine_val=sysdate where b.tipo = ? and b.provenienza= ? and not exists (select 1 from "+vista+" v where v.chiave = b.chiave and v.provenienza=b.provenienza and dt_fine_val is null)";
		log.info("LoadDemanioBeniPrincipali.setFineInesistentiInvalidi - SQL0["+sql0+"]");
		PreparedStatement preparedStatement = conn.prepareStatement( sql0 );
		preparedStatement.setString(1, tipo);
		preparedStatement.setString(2, provenienza);

		int ris = preparedStatement .executeUpdate();
		preparedStatement.close();
		return ris;
	}//-------------------------------------------------------------------------
	
	private int[] upsertBeni(String dato, String tipo, String provenienza, String vista, Timestamp dtElab, Connection conn) throws Exception{
		String sql1 = "MERGE INTO "+dato+" d " + 
				  "USING (SELECT i.* FROM "+vista+" i WHERE i.dt_fine_val is null and (i.dt_inizio_val > ?1? OR i.dt_mod > ?2? OR chiave like '%@FIS') and provenienza= '?3?' ) o "+
		    	  "ON (d.chiave = o.chiave and d.provenienza=o.provenienza and d.tipo=o.tipo) "+
		    	  " WHEN MATCHED THEN "+
		    	  " UPDATE SET " + this.getMergeClause( dato, tipo, vista, conn ) +
		    	  " WHEN NOT MATCHED THEN "+
		    	  " INSERT  (ID,"+ this.getDestFields( vista, conn ) + ") "+
		    	  " values (SEQ_DEM.NEXTVAL , " + this.getOrigFields(vista, conn) + ")";
		/*
		 * Sono stato costretto a non usare il prepareStatement perchè l'istruzione MERGE
		 * non aveva alcun effetto, forse causato dal modo in cui si passano i parametri
		 * che qui sotto ho semplicemente sostituito all'interno della stringa sql
		 */
		//PreparedStatement preparedStatement = conn.prepareStatement( sql1 );
		Statement preSta1 = conn.createStatement();
		log.info("LoadDemanioBeniPrincipali.updInsBeni - SQL1["+sql1+"]");
		SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
		//Timestamp dtRif = dtElab!=null ? dtElab : new Timestamp(SDF.parse("01/01/0001").getTime());
		String dtRif = dtElab!=null ? SDF.format(dtElab) : SDF.format(SDF.parse("01/01/0001") ) ;
		//preSta1.setDate(1, new Date(dtRif.getTime()) );
		sql1 = sql1.replace("?1?", "TO_DATE('"+dtRif+"', 'DD/MM/YYYY')" );
		log.info("LoadDemanioBeniPrincipali.updInsBeni - SQL1 - Parametro1[ TO_DATE('"+dtRif+"', 'DD/MM/YYYY') ]");
		
		//preSta1.setDate(2, new Date(dtRif.getTime()) );
		sql1 = sql1.replace("?2?", "TO_DATE('"+dtRif+"', 'DD/MM/YYYY')" );
		log.info("LoadDemanioBeniPrincipali.updInsBeni - SQL1 - Parametro2[ TO_DATE('"+dtRif+"', 'DD/MM/YYYY') ]");
		
		//preSta1.setString(3, provenienza );
		sql1 = sql1.replace("?3?", provenienza );
		log.info("LoadDemanioBeniPrincipali.updInsBeni - SQL1 - Parametro3["+provenienza+"]");
		//Boolean ris = preSta1.execute();
		preSta1.addBatch(sql1);
		int[] ris = preSta1.executeBatch();
		conn.commit();
		
		return ris;
	}//-------------------------------------------------------------------------
	
//	private int updInsBeni(String dato, String tipo, String provenienza, String vista, Timestamp dtElab, Connection conn) throws Exception{
//		
//		String sql1 = "MERGE INTO DM_B_BENE d " +
//				"     USING (SELECT i.* " +
//				"              FROM V_INSDEM_B_BENE_IMMO i " +
//				"             WHERE     i.dt_fine_val IS NULL " +
//				"                   AND (   i.dt_inizio_val > " +
//				"                              TO_DATE ('0001-01-01', 'YYYY-MM-DD') " +
//				"                        OR i.dt_mod > TO_DATE ('0001-01-01', 'YYYY-MM-DD') " +
//				"                        OR chiave LIKE '%@FIS') " +
//				"                   AND provenienza = 'FB03') o " +
//				"        ON (    d.chiave = o.chiave " +
//				"            AND d.provenienza = o.provenienza " +
//				"            AND d.tipo = o.tipo) " +
//				"WHEN MATCHED " +
//				"THEN " +
//				"   UPDATE SET d.COD_TIPO_BENE = o.COD_TIPO_BENE, " +
//				"              d.DES_TIPO_BENE = o.DES_TIPO_BENE, " +
//				"              d.DT_MOD = o.DT_MOD, " +
//				"              d.COD_CHIAVE1 = o.COD_CHIAVE1, " +
//				"              d.NUM_PARTI = o.NUM_PARTI, " +
//				"              d.NOTE = o.NOTE, " +
//				"              d.CHIAVE_PADRE = o.CHIAVE_PADRE, " +
//				"              d.DESCRIZIONE = o.DESCRIZIONE, " +
//				"              d.CTR_HASH = o.CTR_HASH, " +
//				"              d.COD_CHIAVE3 = o.COD_CHIAVE3, " +
//				"              d.COD_CHIAVE5 = o.COD_CHIAVE5, " +
//				"              d.COD_CHIAVE2 = o.COD_CHIAVE2, " +
//				"              d.DM_B_BENE_PADRE_ID = o.DM_B_BENE_PADRE_ID, " +
//				"              d.COD_ECOGRAFICO = o.COD_ECOGRAFICO, " +
//				"              d.DT_ELAB = o.DT_ELAB, " +
//				"              d.COD_CHIAVE4 = o.COD_CHIAVE4 " +
//				"WHEN NOT MATCHED " +
//				"THEN " +
//				"   INSERT     (ID, " +
//				"               COD_TIPO_BENE, " +
//				"               DES_TIPO_BENE, " +
//				"               DT_INIZIO_VAL, " +
//				"               DT_FINE_VAL, " +
//				"               DT_MOD, " +
//				"               COD_CHIAVE1, " +
//				"               NUM_PARTI, " +
//				"               NOTE, " +
//				"               CHIAVE_PADRE, " +
//				"               DESCRIZIONE, " +
//				"               CTR_HASH, " +
//				"               COD_CHIAVE3, " +
//				"               COD_CHIAVE5, " +
//				"               PROVENIENZA, " +
//				"               TIPO, " +
//				"               COD_CHIAVE2, " +
//				"               DM_B_BENE_PADRE_ID, " +
//				"               COD_ECOGRAFICO, " +
//				"               DT_ELAB, " +
//				"               CHIAVE, " +
//				"               COD_CHIAVE4) " +
//				"       VALUES (SEQ_DEM.NEXTVAL, " +
//				"               o.COD_TIPO_BENE, " +
//				"               o.DES_TIPO_BENE, " +
//				"               o.DT_INIZIO_VAL, " +
//				"               o.DT_FINE_VAL, " +
//				"               o.DT_MOD, " +
//				"               o.COD_CHIAVE1, " +
//				"               o.NUM_PARTI, " +
//				"               o.NOTE, " +
//				"               o.CHIAVE_PADRE, " +
//				"               o.DESCRIZIONE, " +
//				"               o.CTR_HASH, " +
//				"               o.COD_CHIAVE3, " +
//				"               o.COD_CHIAVE5, " +
//				"               o.PROVENIENZA, " +
//				"               o.TIPO, " +
//				"               o.COD_CHIAVE2, " +
//				"               o.DM_B_BENE_PADRE_ID, " +
//				"               o.COD_ECOGRAFICO, " +
//				"               o.DT_ELAB, " +
//				"               o.CHIAVE, " +
//				"               o.COD_CHIAVE4) "; 
//
//		log.info("LoadDemanioBeniPrincipali.updInsBeni - SQL1["+sql1+"]");
//		SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
//		Statement preSta1 = conn.createStatement();
//		preSta1.addBatch(sql1);
//		preSta1.executeBatch();
//		conn.commit();
//
//		
//		return 1;
//	}//-------------------------------------------------------------------------
	
	private String getInsertClause(String tab, String vista, Connection conn) throws Exception{
		String setList = "";
		String selectList = "select SEQ_DEM.NEXTVAL, ";
		List<String> lstDest = this.getListaColonne(tab, conn);
		List<String> lstOrig = this.getListaColonne(vista, conn);

		for(String t : lstDest){
				if(lstOrig.contains(t)){
					setList+=" d."+t+",";
					selectList+=" o."+t+",";
				}
		}
		setList = setList.substring(0, setList.length()-1);
		selectList = selectList.substring(0, selectList.length()-1);
		setList+=")";
		selectList+=" from "+vista+" o ";
		
		return setList + selectList;
	}//-------------------------------------------------------------------------

	private String getUpdateClause(String tab, String tipo, String vista, Connection conn) throws Exception{
		String setList = "(";
		String selectList = "(select ";
		String[] le = {"ID","CHIAVE","PROVENIENZA","TIPO","DT_INIZIO_VAL","DT_FINE_VAL","DM_B_BENE_ID"};
		List<String> lstEsc = Arrays.asList(le);  
		
		List<String> lstDest = this.getListaColonne(tab, conn);
		List<String> lstOrig = this.getListaColonne(vista, conn);
		
		for(String t : lstDest){
			if(!lstEsc.contains(t)){
				if(lstOrig.contains(t)){
					//clause+=" d."+t+"=o."+t+",";
					setList+=" d."+t+",";
					selectList+=" o."+t+",";
				}
			}
		}
		setList = setList.substring(0, setList.length()-1);
		selectList = selectList.substring(0, selectList.length()-1);
		setList+=")";
		selectList+=") from "+vista+" o ";
		
		//return clause = clause.substring(0,clause.length()-1);
		return setList + selectList;
	}//-------------------------------------------------------------------------
		

}
