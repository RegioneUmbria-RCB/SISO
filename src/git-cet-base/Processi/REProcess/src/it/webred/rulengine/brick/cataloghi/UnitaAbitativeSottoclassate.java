package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * @version $Revision: 1.5 $ $Date: 2010/09/28 12:12:27 $
 */
public class UnitaAbitativeSottoclassate extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(UnitaAbitativeSottoclassate.class.getName());

	/**
	 * @param bc
	 */
	public UnitaAbitativeSottoclassate(BeanCommand bc){
		super(bc);
	}

	public UnitaAbitativeSottoclassate(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{

		String belfiore = ctx.getBelfiore();
		
		final String NOME_TABELLA = "CAT_UNI_ABI_SOTTOCLASSATE";
		final String NOME_SINONIMO = "CAT_UNI_ABI_SOTTOCLASSATE";
		final String NOME_INDICE = "CAT_UNI_ABI_SOTTOCLASSATE_SDX";
		final String CODIFICA = "UAS"; //Unita abitative sottoclassate
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
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
		try
		{
			/*
			 * Controllo l'esistenza della tabella
			 */
			sql = "select * from ALL_CATALOG " +
			"where owner='" + nomeSchemaSiti + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug("SQL: " + sql);
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
			if (esisteTab){
				/*
				 * Svuoto la tabella 
				 */
				sql = "truncate table "  + nomeSchemaSiti + "." + NOME_TABELLA;
				pstmt = connSiti.prepareCall(sql);
				boolean okTruncate = pstmt.execute();
				DbUtils.close(pstmt);
				log.debug("Eseguita " + sql + " con il seguente esito: " + okTruncate);
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
				sql = "select * from ALL_TAB_COLUMNS where column_name = 'PARTICELLA' and table_name = '" + NOME_TABELLA + "' and owner = '" + nomeSchemaSiti + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColPa = false;
				if (rs != null){
					while(rs.next()){
						esisteColPa = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColPa){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (PARTICELLA  CHAR(5)) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				/*
				 * cav_001
				 */
				/*
				 * Recupero il valore rapporto per il comune corrente 
				 */
				sql = "select * from " + nomeSchemaDiogene + ".SIT_TAB_PARAMS where NOME = 'DOCFA_RAPPORTO' ";
				log.debug("SQL: " + sql);
				String valoreRapporto = "";
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						valoreRapporto = rs.getString("VALORE");
					}
				}
				sql = "select foglio, part, count(*) as occorrenze " +
				"from (select foglio, part, sub from " + nomeSchemaDiogene + ".docfa_report where rapp_2 > " + valoreRapporto + ") " +
				"group by foglio, part ";
				log.debug("SQL: " + sql);
				double minOcc = 0d;
				double maxOcc = 0d;
				ArrayList<String[]> alOcc = new ArrayList<String[]>();
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
		
				if (rs != null){
					while(rs.next()){
						/*
						 * determino min(vm_rendita_vano) e max(vm_rendita_vano)
						 */
						String foglio = rs.getString("foglio");
						String particella = rs.getString("part");
						String occorrenze = rs.getString("occorrenze");
						
						if (occorrenze != null && !occorrenze.equalsIgnoreCase("")){
							Double currentOcc = Double.valueOf(occorrenze);
							if (currentOcc < minOcc)
								minOcc = currentOcc;
							if (currentOcc > maxOcc)
								maxOcc = currentOcc;
						}
						alOcc.add(new String[]{foglio, particella, occorrenze});
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				log.debug("MAX OCC.: " + maxOcc + "MIN OCC: " + minOcc);
				int range = 0;
				int rangeRnd = 0;
				int maxInt = (int)maxOcc;
				rangeRnd = maxInt / 10;
				range = (int)rangeRnd;
				
				
//				String maxStr = String.valueOf(maxInt); 
//				int nCifre = maxStr.length();
//				if ( nCifre == 1 ){
//					/*
//					 * unita 0-9 (x = x - (x % 1))
//					 */
//					int resto = maxInt % 1;
//					maxRnd = maxInt - resto;
//					/*
//					 * il numero delle classificazioni è pari a 10
//					 */
//					range = maxRnd / 10;
//				}else if ( nCifre == 2 ) {
//					/*
//					 * decine 0-99  (x = x - (x % 10))
//					 */
//					int resto = maxInt % 10;
//					maxRnd = maxInt - resto;
//					/*
//					 * il numero delle classificazioni è pari a 10
//					 */
//					range = maxRnd / 10;
//				}else if ( nCifre == 3 ) {
//					/*
//					 * centinaia 0-999 (x = x - (x % 100))
//					 */
//					int resto = maxInt % 100;
//					maxRnd = maxInt - resto;
//					/*
//					 * il numero delle classificazioni è pari a 10
//					 */
//					range = maxRnd / 10;
//					
//				}else if ( nCifre == 4 ) {
//					/*
//					 * migliaia - 9999 (x = x - (x % 1000))
//					 */
//					int resto = maxInt % 1000;
//					maxRnd = maxInt - resto;
//					/*
//					 * il numero delle classificazioni è pari a 10
//					 */
//					range = maxRnd / 10;					
//				}	
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
				 * Elimino i vecchi record in sitideco_catalog con il corrente idCat 
				 * per poi rigenerare la codifica 
				 */
				Integer[] intValoreOcc = new Integer[11];
				String[] strCodiceOcc = new String[11];
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
					for (int i=0; i<11; i++){
						int end = start + range;
						String desc = "";
						/*
						 * I colori hanno un senso logico (=dal piu chiaro al piu scuro )
						 */
						strCodiceOcc[i] = CODIFICA+(i == 10?i:"0"+i);
						switch (i) {
						case 0:
							desc = "0-" + end;
							colore = 9;
							intValoreOcc[i] = 0;
							break;
						case 1:
							desc = (start) + "-" + end;
							colore = 10;
							intValoreOcc[i] = (start);
							break;
						case 2:
							desc = (start) + "-" + end;
							colore = 7;
							intValoreOcc[i] = (start);
							break;
						case 3:
							desc = (start) + "-" + end;
							colore = 13;
							intValoreOcc[i] = (start);
							break;
						case 4:
							desc = (start) + "-" + end;
							colore = 15;
							intValoreOcc[i] = (start);
							break;
						case 5:
							desc = (start) + "-" + end;
							colore = 14;
							intValoreOcc[i] = (start);
							break;
						case 6:
							desc = (start) + "-" + end;
							colore = 2;
							intValoreOcc[i] = (start);
							break;
						case 7:
							desc = (start) + "-" + end;
							colore = 3;
							intValoreOcc[i] = (start);
							break;
						case 8:
							desc = (start) + "-" + end;
							colore = 4;
							intValoreOcc[i] = (start);
							break;
						case 9:
							desc = (start) + "-" + end;
							colore = 5;
							intValoreOcc[i] = (start);
							break;
						case 10:
							desc = (start) + "-";
							colore = 1;
							intValoreOcc[i] = (start);
							break;

						default:

							break;
						}
						start = end;
						maxId++;
						sql = "insert into " + nomeSchemaSiti + ".sitideco_catalog " +
								"(codice, codut, descrizione, id_cat, colore, riempimento, spessore, colorelinea) " +
								"values " +
								"(" + maxId + ", '" + strCodiceOcc[i] + "', '" + desc + "', " + idCat + ", '" + colore + "', '1', '1', '7') ";
						pstmt = connSiti.prepareCall(sql);
						String message = "Esito di " + sql + ": ";
						boolean okInsert = pstmt.execute();
						DbUtils.close(pstmt);
						message += (!okInsert)?"OK":"KO";
						log.debug(message);
					}
				}
				/*
				 * Recupero le info dalla siti_part per popolare la NOME_TABELLA
				 */
				for (int index=0; index<alOcc.size(); index++){
					String[] riga = alOcc.get(index);
					
					//scarto di eventuali fogli non numerici
					if (!isFoglioNumeric(riga[0])) {
						log.warn("Il foglio " + riga[0] + " sarà scartato, perchè non numerico");
						continue;
					}
					
					sql = "select sp.foglio, sp.particella, sp.shape " +
							"from " + nomeSchemaSiti + ".sitipart sp " +
							"where sp.foglio = '" + riga[0] + "' " +
							"and sp.particella = '" + riga[1] + "' " +
							"and sp.data_fine_val=to_date('99991231','yyyymmdd') ";
					log.debug("SQL: " + sql);
					Double occorrenza = Double.valueOf(riga[2]);
					String codClasse = "";
					int indice = 11;
					for (int ind=0; ind<intValoreOcc.length; ind++ ){
						if (occorrenza > intValoreOcc[ind]){
							
						}else{
							indice = ind;
							break;
						}
					}
					if ( (indice - 1) < 0 )
						indice = 1;

					codClasse = strCodiceOcc[indice-1];
					pstmt = connSiti.prepareCall(sql);
					try{

						rs = pstmt.executeQuery();
						if (rs != null){
							while(rs.next()){
								STRUCT st = (oracle.sql.STRUCT)rs.getObject("shape");
								if(st != null && st.getAttributes() != null)
								{
									sql = "insert into " + NOME_TABELLA + " " +
											"(shape, codice, id_cat, occorrenze, foglio, particella) " +
											"values (?,?,?,?,?,?)";
									log.debug("SQL: " + sql);
									
									CallableStatement cstmt = connSiti.prepareCall(sql);
		
									cstmt.setObject(1, st);
									cstmt.setString(2, codClasse);
									cstmt.setInt(3, Integer.parseInt(idCat) );
									cstmt.setInt(4, Integer.parseInt(riga[2]) );
									cstmt.setInt(5, Integer.parseInt(riga[0]) );
									cstmt.setString(6, riga[1]);
									boolean okInsert = cstmt.execute();
									log.debug(sql);
									DbUtils.close(cstmt);
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
				 * STA03-U.I. RES. COINVOLTE DA DOCFA SOTTOCLASSATE
				 * è fissa perché non esiste fino a 
				 * quando da SITI non si è inserito un catalogo  
				 */
				log.debug("STA03-U.I. RES. COINVOLTE DA DOCFA SOTTOCLASSATE: Tabella " + NOME_TABELLA + " inesistente");
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
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}

		}
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
