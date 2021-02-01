package it.webred.rulengine.brick.loadDwh.load.superc.concrete;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.exception.RulEngineException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;


public abstract class ConcreteImport<T extends ConcreteImportEnv> {

	protected static final org.apache.log4j.Logger log = Logger.getLogger(ConcreteImport.class);

	protected T concreteImportEnv;
	private PreNormalizzaFilter f;
	/**
	 * @param ctx
	 * @param connectionName
	 * @param con
	 * !!! - ATTENZIONE A CAMBIAR QUESTO COSTRUTTORE , VIENE USATO TRAMITE REFLECTION !!!!!!!!!!!!!
	 */
	public ConcreteImport() {

	}
	
	public abstract GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni();


	/**
	 * False se il processo di normalizzazione è compiuta da una seconda regola
	 * (ex.: Enel)
	 * @throws RulEngineException
	 */
	public abstract  boolean normalizza(String belfiore) throws RulEngineException;

	public  abstract ConcreteImportEnv  getEnvSpec(EnvImport ei);

	public  void  setEnv(EnvImport ei) {
		concreteImportEnv =  (T) getEnvSpec(ei);
	}

	public ArrayList<String> getTabelleFinaliDWH() {
		return concreteImportEnv.getTabelleFinaliDHW();
	}
	public Connection getConnection() {
		return concreteImportEnv.getEnvImport().getConn();
	}
	
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		return concreteImportEnv.getTabelleAndTipiRecord();
	}

	protected  void  setFilter(PreNormalizzaFilter f) {
		this.f =  f;
	}
	

	/**
	 * la funzione effettua il DROP dell'intera fonte dati 
	 * ricavando i nomi dalle tabelle della fonte dai metodi getTabelleAndTipiRecord, getTabelleFinaliDWH
	 */
	public void dropFonteDati() {
		Connection con = concreteImportEnv.getEnvImport().getConn();
		//TODO: Implementare il metodo
		
	}
	/**
	 * Mette il file nella cartella ELABORATI, esegue una commit della transazione e
	 * ricomincia una nuova transazione
	 * @throws RulEngineException
	 */
	public void postLetturaFileAndFilter(String cartellaFiles, String file, boolean gestisciTmp) throws RulEngineException {
		Connection con = concreteImportEnv.getEnvImport().getConn();
		try {
			con.commit();
			con.setAutoCommit(false);

			new File(cartellaFiles+file).renameTo(new File(cartellaFiles+"ELABORATI/"+file));
		
		} catch (SQLException e) {
			throw new RulEngineException("PROBLEMI IMPORTAZIONE FILE SU TABELLE TEMPORANEE",e);
		}
		
		if (f!=null) {
			log.debug("Filtro prima di Normalizzare");
			f.filter(concreteImportEnv.getEnvImport().getConn());
		}

	
		ArrayList<String> tabelleDWH = getTabelleFinaliDWH();
		
		if (gestisciTmp) {
			log.info("DUPLICO LE TABELLE (VUOTE) : FORNITURA IN REPLACE " + file);
			try {
				Util.duplicateTabeleDWH(tabelleDWH, con);
			} catch (Exception e) {
				log.error("Problemi duplicazione tabelle tmp ", e);
				throw new RulEngineException(e.getMessage(), e);
			}
		}	
	
	
	}
	
	/**
	 * METTE A 1 I FLAG_ELABORATI DEI RECORD CHE SONO RIMASTI A ZERO PER MANCANZA DI CHIAVE
	 * o con chiave a null  (cioe' tabelle con chiave=null oppure tabelle senza chiave)
	 * @throws RulEngineException
	 */
	public void setReFlagElaboratoConChiaveNullaONoChiave() throws RulEngineException {
		LinkedHashMap<String, ArrayList<String>> chiavi = concreteImportEnv.getTabelleAndChiavi();
			
		for (String key : chiavi.keySet()) {
			String upd="UPDATE "+ key + " SET RE_FLAG_ELABORATO='2' WHERE RE_FLAG_ELABORATO='0' AND (1=0 "; 
  		    ArrayList<String> a = chiavi.get(key);
			// non ha chiavi, allora setto sullo quello che era a 0 a 1
			if (a!=null && a.size()!=0) {
				 for (String chiave : a) {
					 upd+=" OR " + chiave + " IS NULL";
				 }
			} else {
				upd+=" OR 1=1";
			}
			upd+=")";

			 try {
				 QueryRunner run = new QueryRunner();
				 run.update(concreteImportEnv.getEnvImport().getConn(), upd);
			 } catch (SQLException e) {
				throw new RulEngineException("Problema cercando di settare RE_FLAG_ELABORATO PER CHIAVI NULLE!", e);
			 }
			 
	     }

		
		
	}
	
	
}
