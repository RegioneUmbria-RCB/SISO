package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

/*
 * 
 * @author Filippo Mazzini
 * @version $Revision: 1.10 $ $Date: 2010/10/25 11:49:04 $
 */
public class EseguiCaricamentoCaronteRedditi extends Command  implements Rule
{
	private static final Logger log = Logger.getLogger(EseguiCaricamentoCaronteRedditi.class.getName());
	
	private int anno;
	
	private String codToReplace = "";
	
	//per test: in versione definitiva, cancellare o mettere = ""
	//private static final String sqlRownum = " AND ROWNUM <= 10";
	private static final String sqlRownum = "";
	
	//query scritte sotto forma di costanti, in maniera tale da avere una sorta di
	//predisposizione per trasferirle su file .sql (o simili) separati
	private static final String sqlSelectSemaforo = "SELECT NOME_TAB, FLAG_SEMAFORO FROM SEMAFORO WHERE FLAG_SEMAFORO = 1 AND NOME_TAB = ?";
	private static final String sqlUpdateSemaforo = "UPDATE SEMAFORO SET FLAG_SEMAFORO = ? WHERE NOME_TAB = ?";
	private static final String sqlDeleteRedDescrizione = "DELETE RED_DESCRIZIONE WHERE ANNO_IMPOSTA = ? "
		+ "AND EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum
		+ ")";
	private static final String sqlInsertRedDescrizione = "INSERT INTO RED_DESCRIZIONE "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, TIPO_MODELLO, "
		+"  DIC_CORRETTIVA, DIC_INTEGRATIVA, STATO_DICHIARAZIONE, "
		+"  FLAG_VALUTA, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "			
		+"  SUBSTR(RIGA_REDDITI, 2, 1) AS TIPO_MODELLO, "
		+"  SUBSTR(RIGA_REDDITI, 28, 1) AS DIC_CORRETTIVA, "
		+"  SUBSTR(RIGA_REDDITI, 29, 1) AS DIC_INTEGRATIVA, "
		+"  SUBSTR(RIGA_REDDITI, 30, 1) AS STATO_DICHIARAZIONE, "
		+"  SUBSTR(RIGA_REDDITI, 31, 1) AS FLAG_VALUTA, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedDescrizioneNew = "INSERT INTO RED_DESCRIZIONE "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, TIPO_MODELLO, "
		+"  DIC_CORRETTIVA, DIC_INTEGRATIVA, STATO_DICHIARAZIONE, "
		+"  ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "			
		+"  SUBSTR(RIGA_REDDITI, 2, 1) AS TIPO_MODELLO, "
		+"  SUBSTR(RIGA_REDDITI, 28, 1) AS DIC_CORRETTIVA, "
		+"  SUBSTR(RIGA_REDDITI, 29, 1) AS DIC_INTEGRATIVA, "
		+"  SUBSTR(RIGA_REDDITI, 30, 1) AS STATO_DICHIARAZIONE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlDeleteRedDatiAnagrafici = "DELETE RED_DATI_ANAGRAFICI WHERE ANNO_IMPOSTA = ? "
		+ "AND (EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum
		+ ")"
		+ " OR EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('5', '6', '8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum
		+ "))";
	private static final String sqlInsertRedDatiAnagrafici = "INSERT INTO RED_DATI_ANAGRAFICI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, FLAG_PERSONA, "
		+"  COGNOME, NOME, COMUNE_NASCITA, DATA_NASCITA, SESSO, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 48, 1) AS FLAG_PERSONA, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 49, 24))) AS COGNOME, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 73, 20))) AS NOME, "
		+"  SUBSTR(RIGA_REDDITI, 93, 4) AS COMUNE_NASCITA, "
		+"  SUBSTR(RIGA_REDDITI, 97, 8) AS DATA_NASCITA, "
		+"  SUBSTR(RIGA_REDDITI, 105, 1) AS SESSO, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedDatiAnagraficiNew = "INSERT INTO RED_DATI_ANAGRAFICI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, FLAG_PERSONA, "
		+"  COGNOME, NOME, COMUNE_NASCITA, DATA_NASCITA, SESSO, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 47, 1) AS FLAG_PERSONA, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 48, 24))) AS COGNOME, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 72, 20))) AS NOME, "
		+"  SUBSTR(RIGA_REDDITI, 92, 4) AS COMUNE_NASCITA, "
		+"  SUBSTR(RIGA_REDDITI, 96, 8) AS DATA_NASCITA, "
		+"  SUBSTR(RIGA_REDDITI, 104, 1) AS SESSO, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedDatiAnagraficiPG = "INSERT INTO RED_DATI_ANAGRAFICI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, DENOMINAZIONE, NATURA_GIURIDICA, "
		+"  SITUAZIONE, STATO, ONLUS, SETTORE_ONLUS, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 48, 51) AS DENOMINAZIONE, "
		+"  SUBSTR(RIGA_REDDITI, 99, 2) AS NATURA_GIURIDICA, "
		+"  SUBSTR(RIGA_REDDITI, 101, 1) AS SITUAZIONE, "
		+" SUBSTR(RIGA_REDDITI, 102, 1) AS STATO, "
		+"  SUBSTR(RIGA_REDDITI, 103, 1) AS ONLUS, "
		+"  SUBSTR(RIGA_REDDITI, 104, 2) AS SETTORE_ONLUS, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('5', '6', '8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedDatiAnagraficiNewPG = "INSERT INTO RED_DATI_ANAGRAFICI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, DENOMINAZIONE, NATURA_GIURIDICA, "
		+"  SITUAZIONE, STATO, ONLUS, SETTORE_ONLUS, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 47, 51) AS DENOMINAZIONE, "
		+"  SUBSTR(RIGA_REDDITI, 98, 2) AS NATURA_GIURIDICA, "
		+"  SUBSTR(RIGA_REDDITI, 100, 1) AS SITUAZIONE, "
		+"  SUBSTR(RIGA_REDDITI, 101, 1) AS STATO, "
		+"  SUBSTR(RIGA_REDDITI, 102, 1) AS ONLUS, "
		+"  SUBSTR(RIGA_REDDITI, 103, 2) AS SETTORE_ONLUS, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('5', '6', '8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	
	private static final String sqlDeleteRedAttivita = "DELETE RED_ATTIVITA WHERE ANNO_IMPOSTA = ? "
		+ "AND EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum
		+ ")";
	private static final String sqlInsertRedAttivita = "INSERT INTO RED_ATTIVITA "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_RE, "
		+"  CODICE_RF, CODICE_RG, CF_SOSTITUTO_IMPOSTA, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "
		+"  DECODE (SUBSTR(RIGA_REDDITI, 106, 5), "
		+"          '     ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 106, 5) "
		+"         ) AS CODICE_RE, "			
		+"  DECODE (SUBSTR(RIGA_REDDITI, 111, 5), "
		+"          '     ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 111, 5) "
		+"         ) AS CODICE_RF, "			
		+"  DECODE (SUBSTR(RIGA_REDDITI, 116, 5), "
		+"          '     ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 116, 5) "
		+"         ) AS CODICE_RG, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 106, 16))) AS CF_SOSTITUTO_IMPOSTA, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedAttivitaNew = "INSERT INTO RED_ATTIVITA "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_RE, "
		+"  CODICE_RF, CODICE_RG, CF_SOSTITUTO_IMPOSTA, ANNO_IMPOSTA) "
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "
		+"  DECODE (SUBSTR(RIGA_REDDITI, 105, 6), "
		+"          '      ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 105, 6) "
		+"         ) AS CODICE_RE, "			
		+"  DECODE (SUBSTR(RIGA_REDDITI, 111, 6), "
		+"          '      ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 111, 6) "
		+"         ) AS CODICE_RF, "			
		+"  DECODE (SUBSTR(RIGA_REDDITI, 117, 6), "
		+"          '      ', NULL, "
		+"          SUBSTR(RIGA_REDDITI, 117, 6) "
		+"         ) AS CODICE_RG, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 105, 16))) AS CF_SOSTITUTO_IMPOSTA, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlDeleteRedDomicilioFiscale = "DELETE RED_DOMICILIO_FISCALE WHERE ANNO_IMPOSTA = ? "
		+ "AND EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum
		+ ")";
	private static final String sqlInsertRedDomicilioFiscale = "INSERT INTO RED_DOMICILIO_FISCALE "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_CAT_DOM_FISCALE_DIC, "
		+"  CODICE_CAT_DOM_FISCALE_ATTUALE, INDIRIZZO_ATTUALE, ANNO_IMPOSTA) "			
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 122, 4) AS CODICE_CAT_DOM_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 126, 4) AS CODICE_CAT_DOM_FISCALE_ATTUALE, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 130, 35))) AS INDIRIZZO_ATTUALE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlInsertRedDomicilioFiscaleNew = "INSERT INTO RED_DOMICILIO_FISCALE "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_CAT_DOM_FISCALE_DIC, "
		+"  CODICE_CAT_DOM_FISCALE_ATTUALE, INDIRIZZO_ATTUALE, CAP, ANNO_IMPOSTA) "			
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 123, 4) AS CODICE_CAT_DOM_FISCALE_DIC, "
		+"  SUBSTR(RIGA_REDDITI, 127, 4) AS CODICE_CAT_DOM_FISCALE_ATTUALE, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 131, 35))) AS INDIRIZZO_ATTUALE, "
		+"  SUBSTR(RIGA_REDDITI, 166, 5) AS CAP_ATTUALE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) IN ('3', 'S', 'U','5','6','8') "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;
	
	private static final String sqlDeleteRedRedditiDichiarati = "DELETE RED_REDDITI_DICHIARATI WHERE ANNO_IMPOSTA = ? "
		+ "AND EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) = ? "
		+"     		AND RE_FLAG_ELABORATO = 0 "
		+"				AND INSTR(RIGA_REDDITI, 'PL001001', 165) > 0 " + sqlRownum
		+ ")";
	
	private static final String sqlDeleteRedRedditiDichiaratiNew = "DELETE RED_REDDITI_DICHIARATI WHERE ANNO_IMPOSTA = ? "
		+ "AND EXISTS ("
		+ "SELECT 1 "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) = ? "
		+"     		AND RE_FLAG_ELABORATO = 0 "
		+"				AND INSTR(RIGA_REDDITI, 'PL001001', 171) > 0 " + sqlRownum
		+ ")";
	
	/* questa query inserisce anche i codici non trovati mettendo un record con 
	 * valore contabile = 0 */
	
	/*private static final String sqlInsertRedRedditiDichiarati = "INSERT INTO RED_REDDITI_DICHIARATI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_QUADRO, "
		+"  VALORE_CONTABILE, ANNO_IMPOSTA) "		
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  SUBSTR(RIGA_REDDITI, 32, 16) AS CODICE_FISCALE_DIC, "
		+"  'PL001001' AS CODICE_QUADRO, "
		+"  DECODE "
		+"  (INSTR(RIGA_REDDITI, 'PL001001', 165),"
		+"  0, '0',"
		+"  SUBSTR(RIGA_REDDITI, INSTR(RIGA_REDDITI, 'PL001001', 165) + 8, 11)"
		+"  ) AS VALORE_CONTABILE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) = ? "
		+"     		AND RE_FLAG_ELABORATO = 0 " + sqlRownum;*/
	
	/* invece, questa query inserisce solo i codici trovati */
	
	private static final String sqlInsertRedRedditiDichiarati = "INSERT INTO RED_REDDITI_DICHIARATI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_QUADRO, "
		+"  VALORE_CONTABILE, ANNO_IMPOSTA) "		
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 32, 16))) AS CODICE_FISCALE_DIC, "
		+"  'PL001001' AS CODICE_QUADRO, "
		+"  SUBSTR(RIGA_REDDITI, INSTR(RIGA_REDDITI, 'PL001001', 165) + 8, 11)"
		+"  AS VALORE_CONTABILE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) = ? "
		+"     		AND RE_FLAG_ELABORATO = 0 "
		+"				AND INSTR(RIGA_REDDITI, 'PL001001', 165) > 0 " + sqlRownum;
	
	private static final String sqlInsertRedRedditiDichiaratiNew = "INSERT INTO RED_REDDITI_DICHIARATI "
		+" (IDE_TELEMATICO, CODICE_FISCALE_DIC, CODICE_QUADRO, "
		+"  VALORE_CONTABILE, ANNO_IMPOSTA) "		
		+"  SELECT DISTINCT SUBSTR(RIGA_REDDITI, 3, 25) AS IDE_TELEMATICO, "
		+"  RTRIM(LTRIM(SUBSTR(RIGA_REDDITI, 31, 16))) AS CODICE_FISCALE_DIC, "
		+"  'PL001001' AS CODICE_QUADRO, "
		+"  SUBSTR(RIGA_REDDITI, INSTR(RIGA_REDDITI, 'PL001001', 171) + 8, 11)"
		+"  AS VALORE_CONTABILE, "
		+"	'2004' ANNO_IMPOSTA "
		+"	FROM RE_REDDITI_2004_1_0"
		+"   WHERE SUBSTR(RIGA_REDDITI, 1, 1) = '1' "
		+"   	AND SUBSTR(RIGA_REDDITI, 2, 1) = ? "
		+"     		AND RE_FLAG_ELABORATO = 0 "
		+"				AND INSTR(RIGA_REDDITI, 'PL001001', 171) > 0 " + sqlRownum;
	
	private static final String sqlUpdateFlagElaborato = "UPDATE RE_REDDITI_2004_1_0 SET RE_FLAG_ELABORATO = 1 WHERE RE_FLAG_ELABORATO = 0" + sqlRownum;
	private static final String sqlRedTrascodifica = "SELECT * FROM RED_TRASCODIFICA where ANNO_IMPOSTA =  ? AND TIPO_MODELLO = ?";
	//fine costanti per query	
	
	private static final Hashtable<String, String> TABELLE_RE = new Hashtable<String, String>();
	static {
		TABELLE_RE.put("2004", "RE_REDDITI_2004_1_0");
		TABELLE_RE.put("2005", "RE_REDDITI_2005_1_0");
		TABELLE_RE.put("2006", "RE_REDDITI_2006_1_0");
		TABELLE_RE.put("2007", "RE_REDDITI_2007_1_0");
		TABELLE_RE.put("2008", "RE_REDDITI_2008_1_0");
		TABELLE_RE.put("2009", "RE_REDDITI_2009_1_0");
		TABELLE_RE.put("2010", "RE_REDDITI_2010_1_0");
		TABELLE_RE.put("2011", "RE_REDDITI_2011_1_0");
		TABELLE_RE.put("2012", "RE_REDDITI_2012_1_0");
		TABELLE_RE.put("2013", "RE_REDDITI_2013_1_0");
	}
	
	private static final Hashtable<String, Hashtable<String, String>> SQL_TABELLE_RE = new Hashtable<String, Hashtable<String, String>>();
	static{
		
		Hashtable<String, String> hashSql= new Hashtable<String, String>();
		
		hashSql.put("sqlInsertRedAttivita", sqlInsertRedAttivita);
		hashSql.put("sqlInsertRedDatiAnagrafici", sqlInsertRedDatiAnagrafici);
		hashSql.put("sqlInsertRedDatiAnagraficiPG", sqlInsertRedDatiAnagraficiPG);
		hashSql.put("sqlInsertRedDescrizione", sqlInsertRedDescrizione);
		hashSql.put("sqlInsertRedDomicilioFiscale", sqlInsertRedDomicilioFiscale);
		hashSql.put("sqlInsertRedRedditiDichiarati", sqlInsertRedRedditiDichiarati);
		
		SQL_TABELLE_RE.put("2004", hashSql);
		SQL_TABELLE_RE.put("2005", hashSql);
		SQL_TABELLE_RE.put("2006", hashSql);
		
		hashSql= new Hashtable<String, String>();
		hashSql.put("sqlInsertRedDescrizione", sqlInsertRedDescrizioneNew);
		hashSql.put("sqlInsertRedDatiAnagrafici", sqlInsertRedDatiAnagraficiNew);
		hashSql.put("sqlInsertRedDatiAnagraficiPG", sqlInsertRedDatiAnagraficiNewPG);
		hashSql.put("sqlInsertRedAttivita", sqlInsertRedAttivitaNew);
		hashSql.put("sqlInsertRedDomicilioFiscale", sqlInsertRedDomicilioFiscaleNew);
		hashSql.put("sqlInsertRedRedditiDichiarati", sqlInsertRedRedditiDichiaratiNew);
		SQL_TABELLE_RE.put("2007", hashSql);
		SQL_TABELLE_RE.put("2008", hashSql);
		SQL_TABELLE_RE.put("2009", hashSql);
		SQL_TABELLE_RE.put("2010", hashSql);
		SQL_TABELLE_RE.put("2011", hashSql);
		SQL_TABELLE_RE.put("2012", hashSql);
		SQL_TABELLE_RE.put("2013", hashSql);
	}
	
	private static final Hashtable<String, LinkedHashMap<String, String[]>> CODICI_RIGA = new Hashtable<String, LinkedHashMap<String, String[]>>();
	static {
		//2004
		LinkedHashMap<String, String[]> htCodici = new LinkedHashMap<String, String[]>();

		//730
		String[] codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL010001", "PL010002",
								"PL022001", "PL022002",
								"PL029001", "PL029002",
								"PL033001", "PL033002",
								"PL034001", "PL034002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD017001","RE023002","RE024002",
								"RF060002","RG034002","RH016003", 
								"RL003002","RL018001","RL021002","RL029001","RM015001",
								"RN005004","RN019001","RN020004","RV001001","RV010001"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001009",  "DB001001", "DB001002", "DB001006", "DB001007",  "DB001012", "DB001014", "DB001017", "DB001023",  "DB001024", "DB001025", "DB001031", "DB001036", "DB001037"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE
		codici = new String[] {"RA052009","RA052010","RB035008","RD015001","RE023002","RF054002",
										"RG029002","RH015003","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI
		codici = new String[] {"RF059002","RF060001","RF071001","RN005002","RN010001",
										"TN004002","GN005002","GC005002"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI
		codici = new String[] {"RA052009","RA052010","RB035008","RD015001","RE023001","RF054001",
										"RC012001","RG032001","RH016001","RL003002","RL019001","RN016002", 
										"RN020001","RN011001","RN012001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2004", htCodici);
		///////////////////////////
		//-------------2005-----------------------------
		htCodici = new LinkedHashMap<String, String[]>();

		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL011001", "PL011002",
								"PL021001", "PL021002",
								"PL028001", "PL028002",
								"PL032001", "PL032002",
								"PL033001", "PL033002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD016001","RE021001","RE022001",
								"RF054001","RG032001","RH016001", 
								"RL003002","RL018001","RL021002","RL029001","RM015001",
								"RN006001","RN017001","RN018001","RV001001","RV010001"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001009",  "DB001001", "DB001002", "DB001003", "DB001004", "DB001005", "DB001007",  "DB001010", "DB001016", "DB001017", "DB001018",  "DB001019", "DB001021", "DB001026", "DB001027"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE
		codici = new String[] {"RA052009","RA052010","RB035008","RD014001","RE019001","RF056002",
										"RG026002","RH013001","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI
		codici = new String[] {"RF062001","RF063001","RF071001","RN005002","RN010001",
										"TN004001","GN005002","GC005002"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI
		codici = new String[] {"RA052009","RA052010","RB035009","RD014001","RE019001","RF052001",
										"RC009001","RG028001","RH016001","RL003002","RL020001","RN016001", 
										"RN022001","RN011001","RN012001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2005", htCodici);
		///////////////////////////
		//---------------2006---------------
		htCodici = new LinkedHashMap<String, String[]>();

		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL011001", "PL011002",
								"PL022001", "PL022002",
								"PL029001", "PL029002",
								"PL033001", "PL033002",
								"PL034001", "PL034002",
								"PL035001", "PL035002",
								"PL036001", "PL036002",
								"PL037007", "PL037002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD017001","RE023001","RE024001","RE026001",
								"RF054001","RG032001","RH018001", 
								"RL003002","RL018001","RL021002","RL029001","RM015001",
								"RN006001","RN017001","RN018001","RV001001","RV010001"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001009", "DA001010", "DB001001", "DB001002", "DB001003", "DB001004", "DB001005", "DB001007", "DB001907", "DB001010","DB001910", "DB001016", "DB001017", "DB001018",  "DB001019", "DB001021", "DB001026", "DB001027"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE
		codici = new String[] {"RA052009","RA052010","RB035008","RD015001","RE021001","RF056002",
										"RG026002","RH009001","RH010001","RH011001","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI
		codici = new String[] {"RF062001","RF063001","RF071001","RN005002","RN010001",
										"TN004001","GN005002","GC005002"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI
		codici = new String[] {"RA052009","RA052010","RB035009","RD015001","RE023001","RF052001",
										"RC009001","RG028001","RH015001","RL003002","RL020001","RN016001", 
										"RN021001","RN011001","RN012001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2006", htCodici);
		///////////////////////////
		
		
	////--------- 2007 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL009001", "PL009002",
								"PL027001", "PL027002",
								"PL035001", "PL035002",
								"PL039001", "PL039002",
								"PL040001", "PL040002",
								"PL041001", "PL041002",
								"PL042001", "PL042002",
								"PL043001", "PL043002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD018001","RE023001","RE025001",
								"RF053001","RG034001","RH018001", 
								"RL003002","RL019001","RL022002","RL030001","RM015001",
								"RN004001","RN025001","RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", "DB001001", "DB001002", "DB001003", "DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", "DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", "DB001025", "DB001028", "DB001032", "DB001037", "DB001043"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE
		codici = new String[] {"RA052009","RA052010","RB035008","RD017001","RE021001","RF058002",
										"RG030002","RH012001","RH013001","RH014001","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI
		codici = new String[] {"RF065001","RF066001","RF067003","RF077002","RN006002","RN011001",
										"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI
		codici = new String[] {"RA052009","RA052010","RB035009","RD016001","RE023001","RF053001",
										"RC009001","RG031001","RH015001","RL003002","RL021001","RN016001", 
										"RN021001","RN011001","RN012001","PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2007", htCodici);
		
		////--------- 2008 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL009001", "PL009002",
								"PL027001", "PL027002",
								"PL035001", "PL035002",
								"PL039001", "PL039002",
								"PL040001", "PL040002",
								"PL041001", "PL041002",
								"PL042001", "PL042002",
								"PL043001", "PL043002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD015001","RE023001","RE025001",
								"RF051001","RG034001","RH014002", "RH017001", "RH018001",
								"RL003002","RL019001","RL022002","RL030001","RM015001",
								"RN004001","RN026001","RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", "DB001001", "DB001002", "DB001003", "DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", "DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", "DB001025", "DB001028", "DB001035", "DB001044", "DB001050"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE
		codici = new String[] {"RA052009","RA052010","RB035008","RD014001","RE021001","RF055002",
										"RG030002","RH012002","RH013001","RH014001","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI
		codici = new String[] {"RF061001","RF062001","RF063003","RF073002","RN006002","RN011001",
										"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI
		codici = new String[] {"RA052009","RA052010","RB035009","RD013001","RE023001","RF051001",
										"RC007001","RG031001","RH016001","RL003002","RL021001","RN016001", 
										"RN021001","RN011001","RN012001","PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2008", htCodici);
		
		////--------- 2009 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"FD007001", "FC007002",
								"FD008001", "FC008002",
								"PL014001", "PL014002",
								"PL051001", "PL051002",
								"PL071001", "PL071002",
								"PL075001", "PL075002",
								"PL076001", "PL076002",
								"PL077001", "PL077002",
								"PL078001", "PL078002",
								"PL079001", "PL079002"};
		htCodici.put("3", codici);
		//UNICO (UPF)
		codici = new String[] {"CM006001","CM008001","CM010001",
								"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001", "RD017001", "RE023001","RE025001",
								"RF051001","RG034001","RH014002", "RH017001", "RH018001",
								"RL003002","RL019001","RL022002","RL030001","RM015001",
								"RN004001","RN027001","RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", "DB001001", "DB001002", "DB001003", 
								"DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", 
								"DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", 
								"DB001025", "DB001030", "DB001040", "DB001058", "DB001059"};
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE (U50)
		codici = new String[] {"RA052009","RA052010","RB035008","RD016001","RE021001","RF055002",
										"RG030002","RH014002","RH015001","RH016001","RL003002","RL012001",
										"RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI (U60)
		codici = new String[] {"RF061001","RF062001","RF063003","RF073002","RN006002","RN011001",
										"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI (U61)
		codici = new String[] {"RA052009","RA052010","RB035009","RC010001","RD016001","RE023001","RF051001",
										"RG032001","RH016001","RL003002","RL021001","RN016001", 
										"RN021001","RN011001","RN012001","PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2009", htCodici);
		
		////--------- 2010 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL006001", "PL006002",
								"FD007001", "FC007002",
								"FD008001", "FC008002",
								"PL014001", "PL014002",
								"PL051001", "PL051002",
								"PL071001", "PL071002",
								"PL075001", "PL075002",
								"PL076001", "PL076002",
								"PL077001", "PL077002",
								"PL078001", "PL078002",
								"PL079001", "PL079002"};
		htCodici.put("3", codici);
		
		//UNICO (UPF)
		codici = new String[] { "RA011009", "RA011010", 
								"RB011008", 
								"RC005002", "RC009001", 
								"RD018001", 
								"RE023001","RE025001",
								"RF051001","RG034001",
								"RH014002", "RH017001","RH018001",
								"RL003002","RL019001","RL022002","RL030001",
								"RM015001",
								"CM006001","CM008001","CM010001",
								"RN004001","RN026001",
								"RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", 
							   "DB001001", "DB001002", "DB001003", 
								"DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", 
								"DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", 
								"DB001025", "DB001033", "DB001041", "DB001059", "DB001060" };
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE (U50)
		codici = new String[] {"RA052009","RA052010",
							   "RB035008",
							   "RD016001",
							   "RE021001",
							   "RF055002",
							   "RG030002",
							   "RH014002","RH015001","RH016001",
							   "RL003002","RL012001",
							   "RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI (U60)
		codici = new String[] { "RF061001","RF062001","RF063003","RF073002",
								"RN006002","RN011001",
								"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI (U61)
		codici = new String[] { "RA052009","RA052010",
						    	"RB035009",
						    	"RD016001","RE023001","RF051001","RC010001",
								"RG032001",
								"RH016001","RL003002","RL021001",
								"RN016001","RN021001","RN011001","RN012001",
								"PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2010", htCodici);
		
		////--------- 2011 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL006001", "PL006002",
								"FD007001", "FC007002",
								"FD008001", "FC008002",
								"PL014001", "PL014002",
								"PL051001", "PL051002",
								"PL071001", "PL071002",
								"PL075001", "PL075002",
								"PL076001", "PL076002",
								"PL077001", "PL077002",
								"PL078001", "PL078002",
								"PL079001", "PL079002"};
		htCodici.put("3", codici);
		
		//UNICO (UPF)
		codici = new String[] { "RA011009", "RA011010", 
								"RB010011", "RB010013", "RB010014", 
								"RC005002", "RC009001", 
								"RD018001", 
								"RE023001","RE025001",
								"RF051001","RG034001",
								"RH014002", "RH017001","RH018001",
								"RL003002","RL019001","RL022002","RL030001",
								"RM015001",
								"CM006001","CM008001","CM010001",
								"RN004001","RN026001",
								"RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", 
							   "DB001001", "DB001002", "DB001003", 
								"DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", 
								"DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", 
								"DB001025", "DB001033", "DB001108", "DB001127", "DB001128" };
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE (U50)
		codici = new String[] {"RA052009","RA052010",
							   "RB035008",
							   "RD016002",
							   "RE021001",
							   "RF055005",
							   "RG030005",
							   "RH014003","RH015001","RH016001",
							   "RL003002","RL012001",
							   "RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI (U60)
		codici = new String[] { "RF061001","RF062001","RF063003","RF073002",
								"RN006002","RN011001",
								"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI (U61)
		codici = new String[] { "RA052009","RA052010",
						    	"RB035009",
						    	"RD016001","RE023001","RF051001","RC010001",
								"RG032001",
								"RH016001","RL003002","RL021001",
								"RN016001","RN021001","RN011001","RN012001",
								"PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2011", htCodici);
		
		////--------- 2012 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL006001", "PL006002",
								"FD007001", "FC007001",
								"FD008001", "FC008001",
								"PL014001", "PL014002",
								"PL051001", "PL051002",
								"PL071001", "PL071002",
								"PL075001", "PL075002",
								"PL076001", "PL076002",
								"PL077001", "PL077002",
								"PL078001", "PL078002",
								"PL079001", "PL079002"};
		htCodici.put("3", codici);
		
		//UNICO (UPF)
		codici = new String[] { "RA011010", "RA011011", 
								"RB010013", "RB010014", "RB010015", 
								"RC005003", "RC009001", 
								"RD018001", 
								"RE023001","RE025001",
								"RF051001","RG034001",
								"RH014002", "RH017001","RH018001",
								"RL003002","RL019001","RL022002","RL030001",
								"RM015001",
								"LM008001","LM010001",
								"RN004001","RN026001",
								"RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", 
							   "DB001001", "DB001002", "DB001003", 
								"DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", 
								"DB001013", "DB001017", "DB001018",  "DB001019", "DB001020", "DB001024", 
								"DB001025", "DB001038", "DB001108", "DB001129", "DB001130" };
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE (U50)
		codici = new String[] {"RA027010","RA027011",
							   "RB010008",
							   "RD016002",
							   "RE021001",
							   "RF055005",
							   "RG030005",
							   "RH014003","RH015001","RH016001",
							   "RL003002","RL012001",
							   "RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI (U60)
		codici = new String[] { "RF061001","RF062001","RF063003","RF073002",
								"RN006002","RN011001",
								"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI (U61)
		codici = new String[] { "RA052009","RA052010",
						    	"RB035009",
						    	"RD016001","RE023001","RF051001","RC010001",
								"RG032001",
								"RH016001","RL003002","RL021001",
								"RN016001","RN021001","RN011001","RN012001",
								"PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2012", htCodici);
		
		////--------- 2013 -----------------
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002",
								"PL005001", "PL005002",
								"PL006001", "PL006002",
								"FD007001", "FC007001",
								"FD008001", "FC008001",
								"PL014001", "PL014002",
								"PL051001", "PL051002",
								"PL071001", "PL071002",
								"PL075001", "PL075002",
								"PL076001", "PL076002",
								"PL077001", "PL077002",
								"PL078001", "PL078002",
								"PL079001", "PL079002"};
		htCodici.put("3", codici);
		
		//UNICO (UPF)
		codici = new String[] { "RA011011", "RA011012", 
								"RB010013", "RB010014", "RB010015", 
								"RC005003", "RC009001", 
								"RD018001", 
								"RE023001","RE025001",
								"RF101001","RG034001",
								"RH014002", "RH017001","RH018001",
								"RL003002","RL019001","RL022002","RL030001",
								"RM015001",
								"LM008001","LM010001",
								"RN004001","RN026002",
								"RV001001","RV010001","RV010002"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DA000000", "DA001010", "DA001011", 
							   "DB001001", "DB001002", "DB001003", 
								"DB001004", "DB001005", "DB001009", "DB001010", "DB001011","DB001012", 
								"DB001013", "DB001017", "DB001018", "DB001019", "DB001020", "DB001024", 
								"DB001025", "DB001038", "DB001108", "DB001129", "DB001131" };
		htCodici.put("S", codici);
		
		//UNICO SOC. DI PERSONE (U50)
		codici = new String[] {"RA027011","RA027012",
							   "RB010009",
							   "RD016002",
							   "RE021001",
							   "RF066001",
							   "RG034001",
							   "RH012003","RH013001","RH014001",
							   "RL003002","RL012001",
							   "RN007001","RN010001","RN011001"};
		
		htCodici.put("5", codici);
		
		//UNICO SOC. DI CAPITALI (U60)
		codici = new String[] { "RF063001","RF066003","RF073002",
								"RN006002","RN011002",
								"TN004002","GN006002","GC006002","PN001002","PN002001","PN031001"};
		
		htCodici.put("6", codici);
		
		//UNICO ENTI NON COMMERCIALI (U61)
		codici = new String[] { "RA052009","RA052010",
						    	"RB035010",
						    	"RD016001","RE023001","RF065001","RC010001",
								"RG033001",
								"RH016001","RL003002","RL021001",
								"RN016001","RN021002","RN011001","RN012001",
								"PN003001","PN004001","PN031001"};
		
		htCodici.put("8", codici);
		
		CODICI_RIGA.put("2013", htCodici);
		
		//default (come 2004)
		htCodici = new LinkedHashMap<String, String[]>();
		//730
		codici = new String[] {"PL001001", "PL001002", 
								"PL002001", "PL002002", 
								"PL003001", "PL003002", 
								"PL004001", "PL004002"};
		htCodici.put("3", codici);
		//UNICO
		codici = new String[] {"RA011009", "RA011010", 
								"RB011008", "RC005002", 
								"RC009001"};
		htCodici.put("U", codici);
		//770
		codici = new String[] {"DB001031"};
		htCodici.put("S", codici);
		CODICI_RIGA.put("DEF", htCodici);
	}	
	
	/**
	 * @param bc
	 */
	public EseguiCaricamentoCaronteRedditi(BeanCommand bc)
	{
		super(bc);
	}
	
	public EseguiCaricamentoCaronteRedditi(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	private String getNomeTabellaCaronte() {
		String nomeTabellaCaronte = "";
		String anno = "" + getAnno();
		nomeTabellaCaronte = TABELLE_RE.get(anno);
		if (nomeTabellaCaronte == null)
			nomeTabellaCaronte = "";
		if (nomeTabellaCaronte.trim().equals("")) {
			//default
			nomeTabellaCaronte = "RE_REDDITI_" + getAnno() + "_1_0";
		}
		return nomeTabellaCaronte;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.webred.rulengine.Command#run(it.webred.rulengine.Context)
	 */
	public CommandAck run(Context ctx)
	{
		log.debug("Recupero parametro da contesto");
		Connection conn = ctx.getConnection((String)ctx.get("connessione"));
		
		List<RRuleParamIn> parametriIn = this.getParametersIn(_jrulecfg);
		int anno = ((Integer)ctx.get((parametriIn.get(0)).getDescr())).intValue();
		setAnno(anno);
		String tabellaCaronte = getNomeTabellaCaronte();
		PreparedStatement pstmt = null;
		boolean semaforoRosso = false;
		try
		{
		    //attendo il semaforo verde per la tabella
			log.debug("controllaSemaforo");
			pstmt = conn.prepareStatement(sqlSelectSemaforo);
			pstmt.setString(1, tabellaCaronte);
		    controllaSemaforo(pstmt);
		    pstmt.close();
		    pstmt = conn.prepareStatement(sqlUpdateSemaforo);
		    pstmt.setInt(1, 1);
		    pstmt.setString(2, tabellaCaronte);
		    pstmt.executeUpdate();
		    pstmt.close();
		    conn.commit();
		    semaforoRosso = true;
			log.debug("semaforoRosso");
			
			
			
			//eseguo le query
		    List<String> numeroInsUpdDel = new LinkedList<String>();
		    Hashtable<String, Object> ht = getPreparedStatements(conn);
		    ArrayList<?> pstmts = (ArrayList<?>)ht.get("pstmts");
			ArrayList<?> sqls = (ArrayList<?>)ht.get("sqls");
			for (int i = 0; i < pstmts.size(); i++) {
				pstmt = (PreparedStatement)pstmts.get(i);
				String sql = (String)sqls.get(i);
				if (!sql.replace("\t", "").trim().equals(""))
				{
					String nomeTabellaInsert = "(non riconosciuta)";
					String nomeTabellaUpdate = "(non riconosciuta)";
					String nomeTabellaDelete = "(non riconosciuta)";
					try
					{
						if(sql.toUpperCase().indexOf(" INTO ") > 0)
						{
							nomeTabellaInsert = (sql.substring(sql.toUpperCase().indexOf(" INTO ") + " INTO ".length())).trim();
							int idxFineNomeTab = nomeTabellaInsert.indexOf(" ") < nomeTabellaInsert.indexOf("(") ? nomeTabellaInsert.indexOf(" ") : nomeTabellaInsert.indexOf("(");
							nomeTabellaInsert = nomeTabellaInsert.substring(0, idxFineNomeTab);
							log.debug("Nome tabella Insert: " + nomeTabellaInsert);
							log.debug("Esecuzione: \n" + sql + " \n ");
							String logIns ="";
							if (!nomeTabellaInsert.equals("RED_REDDITI_DICHIARATI"))
								logIns = "Ins. " + pstmt.executeUpdate() + " record in tab. " + nomeTabellaInsert;
							else if (nomeTabellaInsert.equals("RED_REDDITI_DICHIARATI")) {
								int recordInseriti= pstmt.executeUpdate();
								if (recordInseriti >0){
									logIns = "Ins." + recordInseriti  ;
									logIns += " (";
									logIns += sql.substring(sql.indexOf("AND INSTR(RIGA_REDDITI, '") + "AND INSTR(RIGA_REDDITI, '".length(), sql.lastIndexOf("'"));
									logIns +=  "')";
								}
							}
							logIns +=  "\n";
							numeroInsUpdDel.add(logIns) ;					
						}
						else if(sql.toUpperCase().indexOf("UPDATE ") > -1)
						{
							nomeTabellaUpdate = (sql.substring(sql.toUpperCase().indexOf("UPDATE ") + "UPDATE ".length())).trim();
							nomeTabellaUpdate = nomeTabellaUpdate.substring(0,nomeTabellaUpdate.indexOf(" "));
							log.debug("Nome tabella Update: " + nomeTabellaUpdate);
							log.debug("Esecuzione: \n" + sql + " \n ");
							numeroInsUpdDel.add("Aggiornati " + pstmt.executeUpdate() + " record in tabella " + nomeTabellaUpdate + " \n") ;					
						}
						else if(sql.toUpperCase().indexOf("DELETE ") > -1)
						{
							nomeTabellaDelete = (sql.substring(sql.toUpperCase().indexOf("DELETE ") + "DELETE ".length())).trim();
							nomeTabellaDelete = nomeTabellaDelete.substring(0,nomeTabellaDelete.indexOf(" "));
							log.debug("Nome tabella Delete: " + nomeTabellaDelete);
							log.debug("Esecuzione: \n" + sql + " \n ");
							numeroInsUpdDel.add("Cancellati " + pstmt.executeUpdate() + " record in tabella " + nomeTabellaDelete + " \n") ;					
						}
					}catch(Exception nonric){
						
						log.error("Message: "+ nonric.getMessage());
						
					}
				}
				
				pstmt.close();
			}
			
			Iterator<String> it = numeroInsUpdDel.iterator();
			String risNumRecord = "";
			while (it.hasNext())
				risNumRecord += " " + it.next();			
			
			return (new ApplicationAck(("Caricamento da sql eseguito correttamente. Dettaglio record inseriti:\n " + risNumRecord).replace("\n", "<br>")));
			
			
			
		}
		catch(Exception ex)
		{
			try
			{
				conn.rollback();
			}
			catch (SQLException e) {}
			log.error("Errore Esegui Caricamenti Sql", ex);
			ErrorAck ea = new ErrorAck(ex.getMessage());
			return (ea);
		}
		finally
		{			
			try
			{
				if (semaforoRosso)
				{
					pstmt = conn.prepareStatement(sqlUpdateSemaforo);
				    pstmt.setInt(1, 0);
				    pstmt.setString(2, tabellaCaronte);
				    pstmt.executeUpdate();
				    conn.commit();
				}
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception e) {}
		}		
	}

	private void controllaSemaforo(PreparedStatement pstmt)
	throws Exception
	{
		ResultSet rset = pstmt.executeQuery();
		if (rset.next())
		{
			log.debug("Trovato lock in semaforo su tabella : " + rset.getString("NOME_TAB") + " riprovo tra un minuto");
			synchronized (this)
			{				
				wait(1 * 1000 * 60);
				controllaSemaforo(pstmt);
			}
		}		
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	private Hashtable<String, Object> getPreparedStatements(Connection conn) throws SQLException {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		ArrayList<PreparedStatement> pstmts = new ArrayList<PreparedStatement>();
		ArrayList<String> sqls = new ArrayList<String>();
		PreparedStatement pstmt = null;
		String anno = "" + getAnno();
		String sql = "";
		//per ogni query di inserimento viene fatta preliminarmente la delete per
		//l'anno considerato: infatti, non esiste l'aggiornamento, ma solo il caricamento
		//ex novo
		
		Hashtable<String, String> hashSql= SQL_TABELLE_RE.get(anno);
		
		
		sql = replaceAnnoNomeTabella(sqlDeleteRedDescrizione);
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, anno);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(hashSql.get("sqlInsertRedDescrizione"));
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(sqlDeleteRedDatiAnagrafici);
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, anno);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(hashSql.get("sqlInsertRedDatiAnagrafici"));
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(hashSql.get("sqlInsertRedDatiAnagraficiPG"));
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		
		sql = replaceAnnoNomeTabella(sqlDeleteRedAttivita);
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, anno);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(hashSql.get("sqlInsertRedAttivita"));
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		sql = replaceAnnoNomeTabella(sqlDeleteRedDomicilioFiscale);
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, anno);
		pstmts.add(pstmt);		
		sql = replaceAnnoNomeTabella(hashSql.get("sqlInsertRedDomicilioFiscale"));
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		
		if (anno.equals("2004") || anno.equals("2005") || anno.equals("2006")) {
			sql = replaceAnnoNomeTabella(sqlDeleteRedRedditiDichiarati);
		} else if (anno.equals("2007") || anno.equals("2008") || anno.equals("2009") || anno.equals("2010") || anno.equals("2011") || anno.equals("2012") || anno.equals("2013")) {
			sql = replaceAnnoNomeTabella(sqlDeleteRedRedditiDichiaratiNew);
		}	
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, anno);
		pstmt.setString(2, anno);
		pstmts.add(pstmt);		
		//secondo il documento di analisi, i codici che identificano i dati del dichiarato
		//possono variare a seconda dell'anno
		LinkedHashMap<String, String[]> hashCodici = CODICI_RIGA.get(anno);
		if (hashCodici == null || hashCodici.size() == 0) {
			hashCodici = CODICI_RIGA.get("DEF");
		}
		Iterator<String> it = hashCodici.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String[] codici = hashCodici.get(key);
			for (String codice : codici) {				
				sql = replaceCodice(conn, replaceAnnoNomeTabella(hashSql.get("sqlInsertRedRedditiDichiarati")), codice, key);
				sqls.add(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, key);
				pstmts.add(pstmt);
			}	
		}	
		
		//query per RE_FLAG_ELABORATO		
		sql = replaceAnnoNomeTabella(sqlUpdateFlagElaborato);
		sqls.add(sql);
		pstmt = conn.prepareStatement(sql);
		pstmts.add(pstmt);
		
		ht.put("sqls", sqls);
		ht.put("pstmts", pstmts);
		
		return ht;
	}
	
	private String replaceAnnoNomeTabella(String sql) {
		sql = sql.replace("\t", " ").replace("\n", " ");
		Iterator<String> it = TABELLE_RE.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = TABELLE_RE.get(key);
			int anno = getAnno();
			String tabellaCaronte = getNomeTabellaCaronte();
			if (Integer.parseInt(key) != anno) {
				sql = sql.replace("'" + key + "'", "'" + anno + "'");
			}
			if (!value.equals(tabellaCaronte)) {
				sql = sql.replace("INSERT INTO " + value, "INSERT INTO " + tabellaCaronte)
						.replace("UPDATE " + value, "UPDATE " + tabellaCaronte)
						.replace("DELETE " + value, "DELETE " + tabellaCaronte)
						.replace(" FROM " + value, " FROM " + tabellaCaronte);
			}
		}
		return sql;
	}
	
	private String replaceCodice(Connection conn, String sql, String codice, String key) throws SQLException {
		sql = sql.replace("\t", " ").replace("\n", " ");
		if (codToReplace == null || codToReplace.equals("")) {
			PreparedStatement pstmt = conn.prepareStatement(sqlRedTrascodifica);
			int anno = getAnno();
			pstmt.setString(1,String.valueOf(anno));
			pstmt.setString(2,key);
			
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()) {
				String codiceRiga = rset.getString("CODICE_RIGA").trim();
				if (sql.indexOf("'" + codiceRiga + "'") > -1) {
					codToReplace = codiceRiga;
					rset.close();
					break;
				}
			}
		}
		if (codToReplace != null && !codToReplace.equals(codice)) {
			sql = sql.replace("'" + codToReplace + "'", "'" + codice + "'");
		}		
		return sql;
	}
	
}

