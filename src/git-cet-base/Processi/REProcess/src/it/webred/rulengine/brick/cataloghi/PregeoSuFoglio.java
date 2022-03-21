package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import oracle.sql.STRUCT;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * Regola che in base ad una stringa esegue un SQL.
 * Se il sql è stato esguito la regola ritorna un CommandAck con un messaggio di conferma
 * Invece se non è stato possibile eseguire il sql la regola ritorna un ErrorAck
 * con il messaggio di errore.
 * 
 * Possono essere passati dei parametri all'sql che verranno sostituiti ai ? del PreparedStatement
 *
 * @author sisani
 * @version $Revision: 1.7 $ $Date: 2010/09/28 12:12:27 $
 */
public class PregeoSuFoglio extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(PregeoSuFoglio.class.getName());

	/**
	 * @param bc
	 */
	public PregeoSuFoglio(BeanCommand bc){
		super(bc);
	}
	
	public PregeoSuFoglio(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		final String NOME_TABELLA = "CAT_PREGEO_FOGLIO";
		final String NOME_SINONIMO = "CAT_PREGEO_FOGLIO";
		final String NOME_INDICE = "CAT_PREGEO_FOGLIO_SDX";
		final String CODIFICA = "PF"; 
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		Connection connSiti = ctx.getConnection("SITI_" + belfiore);
		Connection connDbTotale = ctx.getConnection("DBTOTALE_" + belfiore);
		
		String nomeSchemaSiti = "";
		String nomeSchemaDiogene = "";
		String nomeSchemaDbtotale = "";
		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			
			DatabaseMetaData dmdSiti = connSiti.getMetaData();
			nomeSchemaSiti = dmdSiti.getUserName();
			
			DatabaseMetaData dmdDbTotale = connDbTotale.getMetaData();
			nomeSchemaDbtotale = dmdDbTotale.getUserName();
		
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
			System.out.println("SCHEMA SITI: " + nomeSchemaSiti);
			System.out.println("SCHEMA DBTOTALE: " + nomeSchemaDbtotale);
		}catch (Exception e)
		{
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			try {
				conn.close();
				connDbTotale.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		
		String message = "";
		try
		{
			/*
			 * Controllo l'esistenza della tabella
			 */
			sql = "select * from ALL_CATALOG " +
			"where owner='" + nomeSchemaSiti + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug(sql);
			pstmt = connSiti.prepareCall(sql);
			rs = pstmt.executeQuery();
			boolean esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			//Se vuoi rimanere sullo stesso schema puoi usare le seguenti select
			//select * from cols where column_name = '' table_name = ''
			//select * from tabs where table_name = ''
			//select * from syn where synonym_name = 'SITIDECO_CATALOG' 
			//select * from seq where sequence_name = 'SITI.CAV_SEQ_001'
			//se devi interrogare altri schemas
			//select * from ALL_CATALOG where owner='SITI' and table_type = 'SEQUENCE' and  table_name = 'CAV_SEQ_001'
			if (esisteTab){
				/*
				 * Svuoto la tabella 
				 */
				sql = "truncate table "  + nomeSchemaSiti + "." + NOME_TABELLA;
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				boolean okTruncate = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Svuotamento " + NOME_TABELLA + " eseguito con successo";
				log.debug(message);
				/*
				 * Creo i sinonimi - se non esistono - della NOME_TABELLA in DBTOTALE e DIOGENE
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDiogene + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteSynSuDiogene = false;
				if (rs != null){
					while(rs.next()){
						esisteSynSuDiogene = true;
					}
				}
				if (!esisteSynSuDiogene){
					sql = "CREATE SYNONYM " + nomeSchemaDiogene + "." + NOME_TABELLA + " FOR " + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDbtotale + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esiteSynSuDbtotale = false;
				if (rs != null){
					while(rs.next()){
						esiteSynSuDbtotale = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esiteSynSuDbtotale){
					sql = "CREATE SYNONYM " + nomeSchemaDbtotale + "." + NOME_SINONIMO + " FOR " + nomeSchemaSiti +  "." + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				/*
				 * Verifico l'esistenza delle colonne della NOME_TABELLA e in 
				 * caso negativo altero la tabella
				 */
				sql = "select * from cols where column_name = 'OCCORRENZE' and table_name = '" + NOME_TABELLA + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColVm = false;
				if (rs != null){
					while(rs.next()){
						esisteColVm = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColVm){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (OCCORRENZE NUMBER) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				sql = "select * from ALL_TAB_COLUMNS where column_name = 'FOGLIO' and table_name = '" + NOME_TABELLA + "' and owner = '" + nomeSchemaSiti + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColFo = false;
				if (rs != null){
					while(rs.next()){
						esisteColFo = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColFo){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (FOGLIO  NUMBER) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
			
				
				sql="select sp.foglio, NVL(occorrenze,'0') as occorrenze from( " +
						"select distinct foglio, count(*) as occorrenze from(" +
						"select distinct ltrim(substr(foglio,0,3),'0') foglio, nome_file_pdf from "+nomeSchemaDiogene+".pregeo_info) " +
						"group by foglio) fglc right join "+nomeSchemaSiti+".sitifglc sp " +
						"on fglc.foglio=ltrim(sp.foglio,'0') where sp.shape is not null ";
				
				log.debug("SQL: " + sql);
				
				int minOcc = 0;
				int maxOcc = 0;
				ArrayList<String[]> alOcc = new ArrayList<String[]>();
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				
				int totaleRecord = 1;
				if (rs != null){
					while(rs.next()){
						String foglio = rs.getString("foglio");
						String occorrenze = rs.getString("occorrenze");
						
						if (occorrenze != null && !occorrenze.equalsIgnoreCase("")){
							int currentOcc = Integer.valueOf(occorrenze).intValue();
							if (currentOcc < minOcc)
								minOcc = currentOcc;
							if (currentOcc > maxOcc)
								maxOcc = currentOcc;
						}
						alOcc.add(new String[]{foglio, occorrenze});
						totaleRecord++;
					}
					DbUtils.close(rs);
				}
				
				DbUtils.close(pstmt);
				log.debug("MAX OCC.: " + maxOcc + "MIN OCC: " + minOcc + "TOT REC:"+totaleRecord);
				
				
				//int range = this.calcolaAmpiezzaIntervalli(numIntervalli, minOcc, maxOcc);
				
				/*int rangeRnd = 0;
				int maxInt = (int)maxOcc;
				rangeRnd = maxInt / 10;
				range = (int)rangeRnd;*/
			
				/*
				 * Recupero l'ID_CAT da SITICATALOG attraverso il nome della tabella 
				 */
				sql = "select * from " + nomeSchemaSiti + ".SITICATALOG where TABELLA = '" + NOME_TABELLA + "' ";
				log.debug("SQL: " + sql);
				
				String idCat = "";
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						idCat = rs.getString("id_cat");
					}
				}
				/*
				 * Se non esiste la riga nella tabella CAT_PREGEO_FOGLIO la inseriamo
				 * recuperando il MAX ID_CAT+1
				 */
				if (idCat != null && !idCat.trim().equalsIgnoreCase("")){
					/*
					 * ESISTE LA RIGA NELLA SITICATALOG: Recuperato ID_CAT
					 */
					log.info("NELLA SITICATALOG ESISTE IL CATALOGO DENOMINATO " + NOME_TABELLA + " con ID_CAT = " + idCat);
				}else{
					/*
					 * NON ESISTE LA RIGA NELLA SITICATALOG: Va inserita
					 */
					log.info("NELLA SITICATALOG NON ESISTE IL CATALOGO DENOMINATO " + NOME_TABELLA + " PER CUI VIENE INSERITO ");
					/*
					 * Recupero il max codice in SITICATALOG per le insert successive
					 */				
					Long maxIdCat = new Long(0);
					sql = "select max(ID_CAT) as maxSeq from " + nomeSchemaSiti + ".siticatalog";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					rs = pstmt.executeQuery();
					if (rs != null){
						while(rs.next()){
							maxIdCat = rs.getLong("maxSeq");
						}
						DbUtils.close(rs);
					}
					DbUtils.close(pstmt);
					
					if (maxIdCat == null)
						maxIdCat = new Long(0);

					maxIdCat++;
					idCat = maxIdCat.toString();
					/*
					 * Insert into SITI_F526.SITICATALOG
					   (ID_CAT, TABELLA, CODE_FIELD, DESCRIZIONE, PLENCODE, 
					    PLDECODE, ID_FIELD, SQLDESCR, PLDECODE_DESCR, SHAPE_TYPE, 
					    SQLTOOLTIPS, CONGELATO, NASCOSTO, INTERAGISCE, ACTIVE_CATALOG, 
					    STORICIZZATO)
					 Values
					   (16, 'CAT_PREGEO_FOGLIO', 'CODICE', 'PREGEO PRESENTATI SU FOGLIO', 'CATALOG_DATA.SETCODEPRGC', 
					    'CATALOG_DATA.GETCODEPRGC', 'SE_ROW_ID', 'SELECT 16 AS ID_CAT,CODICE,A.codut || '' - '' || A.DESCRIZIONE AS DESCRIZIONE, A.COLORE,A.RIEMPIMENTO AS RIEMPIMENTO,A.SPESSORE AS SPESSORE,A.COLORELINEA AS COLORELINEA FROM SITIDECO_CATALOG A WHERE ID_CAT=16', 'CATALOG_DATA.GETCODEPRGC_DESCR', 'LINE', 
					    'GET_PRG_PART', 0, 0, 1, 0, 0);
					COMMIT;
					 */
					sql = "insert into " + nomeSchemaSiti + ".SITICATALOG " +
							"(ID_CAT, TABELLA, CODE_FIELD, DESCRIZIONE, PLENCODE, PLDECODE, ID_FIELD, SQLDESCR, PLDECODE_DESCR, SHAPE_TYPE, SQLTOOLTIPS, CONGELATO, NASCOSTO, INTERAGISCE, ACTIVE_CATALOG, STORICIZZATO) " +
							"values " +
							"(" + idCat + ", '" + NOME_TABELLA + "', 'CODICE', 'PREGEO PRESENTATI SU FOGLIO', 'CATALOG_DATA.SETCODEPRGC', "
									+ "'CATALOG_DATA.GETCODEPRGC', 'SE_ROW_ID', 'SELECT " + idCat + " AS ID_CAT,CODICE,A.codut || '' - '' || A.DESCRIZIONE AS DESCRIZIONE, A.COLORE,A.RIEMPIMENTO AS RIEMPIMENTO,A.SPESSORE AS SPESSORE,A.COLORELINEA AS COLORELINEA FROM SITIDECO_CATALOG A WHERE ID_CAT=" + idCat + "', 'CATALOG_DATA.GETCODEPRGC_DESCR', 'LINE', "
											+ "'GET_PRG_PART', 0, 0, 1, 0, 0) ";
					pstmt = connSiti.prepareCall(sql);
					message = "Esito di " + sql + ": ";
					boolean okInsert = pstmt.execute();
					DbUtils.close(pstmt);
					message += (!okInsert)?"OK":"KO";
					log.debug(message);
					
					log.info("NELLA SITICATALOG E' STATO INSERITO IL CATALOGO DENOMINATO " + NOME_TABELLA + " con ID_CAT = " + idCat);
					
				}
				/*
				 * Elimino i vecchi record in sitideco_catalog con il corrente idCat 
				 * per poi rigenerare la codifica; se ID_CAT è stato appena generato 
				 * dalla nuova insert in teoria non esisteranno record in SITIDECO_CATALOG
				 * collegati 
				 */
				Integer[] intValoreOcc = null;
				String[] strCodiceOcc = null;
				List<int[]> lstEstremi = new ArrayList();
				if (idCat != null && !idCat.trim().equalsIgnoreCase("")){
					sql = "delete from " + nomeSchemaSiti + ".sitideco_catalog where id_cat = " + idCat;
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					boolean okDelete = pstmt.execute();
					DbUtils.close(pstmt);
					/*
					 * Recupero il max codice in SITIDECO_CATALOG per le insert successive
					 */				
					Long maxId = new Long(0);
					sql = "select max(codice) as maxSeq from " + nomeSchemaSiti + ".sitideco_catalog";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					rs = pstmt.executeQuery();
					if (rs != null){
						while(rs.next()){
							maxId = rs.getLong("maxSeq");
						}
						DbUtils.close(rs);
					}
					DbUtils.close(pstmt);
					/*
					 * Inizio Decodifica e insert dentro la SITIDECO_CATALOG dopo aver
					 * recuperato l'id_cat da SITICATALOG attraverso il nome della tabella
					 * specifica per questo catalogo (vedi costante nomeTabella)
					 */
					
					int start = 0;
					
					int colore = 0;
					/*
					 * Il numero delle classificazioni è pari a 10 (come sopra) 
					 * e l'11° loop è per gestire i valori oltre maxRnd
					 */
					
					int delta = maxOcc-minOcc;
					
					int numIntervalli = 1;
					double avgDensita = totaleRecord;
					if(delta>0){
						avgDensita = totaleRecord/(maxOcc-minOcc);
					}
					
					
					//TODO: Calcolo numero intervalli necessari
					lstEstremi  = this.calcolaIntervalliVariabili(connSiti, avgDensita, maxOcc, nomeSchemaDiogene, nomeSchemaSiti);
					
					int[] colori = this.getListaColori(lstEstremi.size(), connSiti, nomeSchemaDiogene);
					
					strCodiceOcc = new String[lstEstremi.size()];
					intValoreOcc = new Integer[lstEstremi.size()];
					
					for (int i=0; i<lstEstremi.size(); i++){
						
						int[] estremi = lstEstremi.get(i);
						
						start = estremi[0];
						int end = estremi[1];
						
						String desc = "";
						/*
						 * I colori hanno un senso logico (=dal piu chiaro al piu scuro )
						 */
						strCodiceOcc[i] = CODIFICA+(i == 10?i:"0"+i);
						
						if(start==end-1)
							desc = Integer.toString(start);
						else
							desc = (start) + "-" + (end-1);
						colore = colori[i];
						intValoreOcc[i] = (start);
						
						maxId++;
						sql = "insert into " + nomeSchemaSiti + ".sitideco_catalog " +
								"(codice, codut, descrizione, id_cat, colore, riempimento, spessore, colorelinea) " +
								"values " +
								"(" + maxId + ", '" + strCodiceOcc[i] + "', '" + desc + "', " + idCat + ", '" + colore + "', '1', '1'," + "'" + colore + "') ";
						pstmt = connSiti.prepareCall(sql);
						message = "Esito di " + sql + ": ";
						boolean okInsert = pstmt.execute();
						DbUtils.close(pstmt);
						message += (!okInsert)?"OK":"KO";
						log.debug(message);
					}
							
				}
				/*
				 * Recupero le info dalla siti_fglc, per popolare la NOME_TABELLA
				 */

					for (int index=0; index<alOcc.size(); index++){
						String[] riga = alOcc.get(index);
						
						//scarto di eventuali fogli non numerici
						if (!isFoglioNumeric(riga[0])) {
							log.warn("Il foglio " + riga[0] + " sarà scartato, perchè non numerico");
							continue;
						}
						
						
						
						sql="select sp.foglio, sp.shape from( " +
							"select distinct foglio, count(*) as occorrenze from(" +
							"select distinct ltrim(substr(foglio,0,3),'0') foglio, nome_file_pdf from "+nomeSchemaDiogene+".pregeo_info) " +
							"group by foglio) fglc right join "+nomeSchemaSiti+".sitifglc sp " +
							"on fglc.foglio=ltrim(sp.foglio,'0') where sp.foglio='" + riga[0] + "' ";
								
						log.debug("SQL: " + sql);
						Double occorrenza = Double.valueOf(riga[1]);
						String codClasse = "";
						int indice = lstEstremi.size();
						if (intValoreOcc != null){
							for (int ind=0; ind<intValoreOcc.length; ind++ ){
								if (occorrenza >= intValoreOcc[ind]){
									
								}else{
									indice = ind;
									break;
								}
							}
						}
						if ( (indice - 1) < 0 )
							indice = 1;
	
						if (strCodiceOcc != null)
							codClasse = strCodiceOcc[indice-1];
						
						pstmt = connSiti.prepareCall(sql);
						try{
	
							rs = pstmt.executeQuery();
							if (rs != null && idCat != null && !idCat.trim().equalsIgnoreCase("") ){
								while(rs.next()){
									STRUCT st = (oracle.sql.STRUCT)rs.getObject("shape");
									if(st != null && st.getAttributes() != null)
									{
										CallableStatement cstmt = null;
										try {
											String sqlIns = "insert into " + NOME_TABELLA + " " +
													"(shape, codice, id_cat, occorrenze, foglio) " +
													"values (?,?,?,?,?)";
											cstmt = connSiti.prepareCall(sqlIns);
				
											cstmt.setObject(1, st);
											cstmt.setString(2, codClasse);
											cstmt.setInt(3, Integer.parseInt(idCat) );
											cstmt.setInt(4, Integer.parseInt(riga[1]) );
											cstmt.setInt(5, Integer.parseInt(riga[0]) );
											
											boolean okInsert = cstmt.execute();
											log.debug(sqlIns);
										} finally {
											DbUtils.close(cstmt); 
										}
									}
								}
								DbUtils.close(rs);
							}
						} finally {
							DbUtils.close(pstmt);
						}
						
					}

				log.debug("Inizio COMMIT delle insert in " + NOME_TABELLA);
				connSiti.commit();
				/*
				 * Creazione degli indici spaziali su SITI {call SITI.crea_idx_spatial()}
				 */
				log.debug("Inizio creazione indici spaziali: " + NOME_INDICE);
				sql = "{call " + nomeSchemaSiti + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				boolean creaIdx = pstmt.execute();
				DbUtils.close(pstmt);
								
				return (new ApplicationAck("SQL ESEGUITO CON SUCCESSO"));
			}else{
				/*
				 * log di tabella NOME_TABELLA inesistente
				 * P.S. la descrizione seguente della regola:
				 * PREGEO PRESENTATI SU FOGLIO
				 * è fissa perché non esiste fino a 
				 * quando da SITI non si è inserito un catalogo  
				 */
				log.debug("PREGEO PRESENTATI SU FOGLIO: Tabella " + NOME_TABELLA + " inesistente");
				return (new ApplicationAck("Tabella " + NOME_TABELLA + " inesistente"));
			}
		}
		catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try
			{
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}

		}
	}
	
	private  List<int[]> calcolaIntervalliVariabili(Connection connSiti, double avgDensita, double maxOcc, String nomeSchemaDiogene, String nomeSchemaSiti) throws Exception{
		
		List<int[]> lstEstremi = new ArrayList<int[]>();
		
		String sql = " select  occorrenze ,count(foglio) freq_assoluta from( "+
                       " select sp.foglio, NVL(occorrenze,'0') as occorrenze from(  "+
                       " select distinct foglio, count(*) as occorrenze from( "+
                       " select distinct ltrim(substr(foglio,0,3),'0') foglio, nome_file_pdf from "+nomeSchemaDiogene+".pregeo_info)  "+
                       " group by foglio) fglc right join "+nomeSchemaSiti+".sitifglc sp  "+
                       " on fglc.foglio=ltrim(sp.foglio,'0') where sp.shape is not null ) "+
                       " group by occorrenze "+
                       " order by occorrenze";
		
		CallableStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = connSiti.prepareCall(sql);
			rs = pstmt.executeQuery();
			
			int iniIntervallo = 0;
			int fineIntervallo = 1;
			int freqIntervallo=0;
			
			
			
			if (rs != null){
				while(rs.next()){
					
					int occorrenze = rs.getInt("occorrenze");
					int frequenza = rs.getInt("freq_assoluta");
					
					fineIntervallo = occorrenze+1;
					
					int ampiezza = fineIntervallo-iniIntervallo;
					
					freqIntervallo += frequenza;
					double densita = freqIntervallo/ampiezza;
					
					if(densita >= avgDensita || fineIntervallo >= maxOcc){
						
						//Definisco i dati per il nuovo intervallo
						int[] estremi = {iniIntervallo, fineIntervallo}; 
						
						lstEstremi.add(estremi);
						
						iniIntervallo = fineIntervallo;
						freqIntervallo = 0;
						
					}
				}
			}
			
			
			//Verifico che al massimo ci siano 10 intervalli
			
		}catch(Exception e){
			log.error("Errore:", e);
			throw e;
		} finally {
			try
			{
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}
		}
	
		return lstEstremi;
	}
	
	private  int calcolaNumIntervalli(int totaleRecord){
		
		int numIntervalli = 1;
		
		numIntervalli = (int)Math.floor(1 + (Math.log10(totaleRecord)/Math.log10(2)));
		
		return numIntervalli;
	}
	
	private  int calcolaAmpiezzaIntervalli(int numIntervalli, int minOcc, int maxOcc){
		
		int ampiezza = 0;
		
		ampiezza = (maxOcc-minOcc)/numIntervalli;
		
		return ampiezza;
	}
	
	private int[] getListaColori(int numIntervalli,  Connection connSiti, String nomeSchemaDiogene)throws Exception{
		int[] coloriBase = {9,10,7,13,15,14,2,3,4,5,1};
		int[] listaColori = new int[numIntervalli];
		
		for(int i=0; (i<numIntervalli && i<11 ); i++)
			listaColori[i]=coloriBase[i];
		
		if(numIntervalli>11){
		   
			CallableStatement pstmt = null;
			ResultSet rs = null;
			
			String colori = "(0,7,9,10,7,13,15,14,2,3,4,5,1)";
			
			String sql = "SELECT * FROM "+nomeSchemaDiogene+".PGT_SQL_PALETTE where id not in "+colori+" ORDER BY id";
			
			try{
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				
				int i=11;
				while(rs.next() && i<numIntervalli){
					listaColori[i]=rs.getInt("ID");	
					i++;
				}
				
				
			}catch(Exception e){
				log.error("Errore:", e);
				throw e;
			} finally {
				try
				{
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				}
				catch (Exception ec)
				{
				}
			}
		}
		return listaColori;
	}
	
	
	private boolean isFoglioNumeric(String foglio) {
		if (foglio != null) {
			for (char car : foglio.toCharArray()) {
				if ("1234567890".indexOf("" + car) == -1) {
					return false;
				}
			}
		}
		return true;
	}

}
