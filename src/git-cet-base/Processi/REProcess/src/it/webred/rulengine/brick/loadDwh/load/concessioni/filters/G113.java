package it.webred.rulengine.brick.loadDwh.load.concessioni.filters;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.PreNormalizzaFilter;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class G113 extends StandardConcessioniFilter implements PreNormalizzaFilter  {

	private static final org.apache.log4j.Logger log = Logger.getLogger(G113.class.getName());

	

	/* (non-Javadoc)
	 * si ignorano tutte le pratiche con progressivo > 10000
	 * e con anno = 1100
	 * Gianni Bacchin !!!!!!!
	 * inoltre sono stati dati i tipi record D con chiavi duplicate, presupponendo che per una concessione ci fossero solo 1 istanza
	 * di estremi catastali ma invece non è così
	 * dunque annullo la chiave del record D 
	 * per attivare la gestine in sostituzione con hash del sistema di caricamento
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.PreNormalizzaFilter#filter(java.sql.Connection)
	 */
	public  void filterSpecEnte(Connection conn ) throws RulEngineException {
		

		// LE DUE TABELLE QUI SOTTO NON HANNO CHIAVE PRIMARIA! ANNULLO I VALORI CHE HANNO PASSATO
		try {
			QueryRunner upd = new QueryRunner();
			String updSql = "UPDATE  RE_CONCESSIONI_D D SET CHIAVE=NULL" 
			+ "				WHERE RE_FLAG_ELABORATO= 0 ";
			upd.update(conn, updSql);
		} catch (SQLException e) {
			log.error("Errore in update ESTREMI CATASTALI delle concessioni SET CHIAVE =NULL",e);
			throw new RulEngineException("Errore in update ESTREMI CATASTALI delle concessioni SET CHIAVE =NULL");
		}
		
			try {
				QueryRunner upd = new QueryRunner();
				String updSql = "UPDATE  RE_CONCESSIONI_B B SET RE_FLAG_ELABORATO=1" 
				+ "				WHERE RE_FLAG_ELABORATO= 0 AND EXISTS  "
				+ "				(SELECT 1 FROM RE_CONCESSIONI_A A WHERE "
				+ "				(TO_NUMBER_ALL(PROGRESSIVO_NUMERO)> 10000 OR PROGRESSIVO_ANNO='1100') "
				+ "				AND B.CHIAVE_RELAZIONE = A.CHIAVE )";
				upd.update(conn, updSql);
			} catch (SQLException e) {
				log.error("Errore in update delle concessioni > 10000",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}
			
			
			try {
				QueryRunner upd = new QueryRunner();
				String updSql = "UPDATE  RE_CONCESSIONI_C C SET RE_FLAG_ELABORATO=1" 
				+ "				WHERE RE_FLAG_ELABORATO= 0 AND EXISTS  "
				+ "				(SELECT 1 FROM RE_CONCESSIONI_A A WHERE "
				+ "				(TO_NUMBER_ALL(PROGRESSIVO_NUMERO)> 10000 OR PROGRESSIVO_ANNO='1100') "
				+ "				AND C.CHIAVE_RELAZIONE = A.CHIAVE )";
				upd.update(conn, updSql);
			} catch (SQLException e) {
				log.error("Errore in update delle concessioni > 10000",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}
			
			try {
				QueryRunner upd = new QueryRunner();
				String updSql = "UPDATE  RE_CONCESSIONI_D D SET RE_FLAG_ELABORATO=1" 
				+ "				WHERE RE_FLAG_ELABORATO= 0 AND EXISTS  "
				+ "				(SELECT 1 FROM RE_CONCESSIONI_A A WHERE "
				+ "				(TO_NUMBER_ALL(PROGRESSIVO_NUMERO)> 10000 OR PROGRESSIVO_ANNO='1100') "
				+ "				AND D.CHIAVE_RELAZIONE = A.CHIAVE )";
				upd.update(conn, updSql);
			} catch (SQLException e) {
				log.error("Errore in update delle concessioni > 10000",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}
			
			try {
				QueryRunner upd = new QueryRunner();
				String updSql = "UPDATE  RE_CONCESSIONI_E E SET RE_FLAG_ELABORATO=1" 
				+ "				WHERE RE_FLAG_ELABORATO= 0 AND EXISTS  "
				+ "				(SELECT 1 FROM RE_CONCESSIONI_A A WHERE "
				+ "				(TO_NUMBER_ALL(PROGRESSIVO_NUMERO)> 10000 OR PROGRESSIVO_ANNO='1100') "
				+ "				AND E.CHIAVE_A = A.CHIAVE )";
				upd.update(conn,updSql);
			} catch (SQLException e) {
				log.error("Errore in update delle concessioni > 10000",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}
			
			
			try {
				QueryRunner upd = new QueryRunner();
				upd.update(conn, "UPDATE RE_CONCESSIONI_A SET RE_FLAG_ELABORATO=1 WHERE RE_FLAG_ELABORATO= 0 AND (TO_NUMBER_ALL(PROGRESSIVO_NUMERO)> 10000 OR PROGRESSIVO_ANNO='1100')");
			} catch (SQLException e) {
				log.error("Errore in update delle concessioni > 10000 OR PROGRESSIVO_ANNO='1100'",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}

			try {
				QueryRunner upd = new QueryRunner();
				upd.update(conn, "UPDATE RE_CONCESSIONI_B SET CHIAVE=NULL WHERE CHIAVE='0' AND RE_FLAG_ELABORATO='0'");
			} catch (SQLException e) {
				log.error("Errore in update CHIAVE NULL TO RECORD B",e);
				throw new RulEngineException("Errore in update delle concessioni > 10000");
			}
			

		

			
			
		
		
	}




}
