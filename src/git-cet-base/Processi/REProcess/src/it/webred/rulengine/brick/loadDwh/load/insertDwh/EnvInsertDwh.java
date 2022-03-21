package it.webred.rulengine.brick.loadDwh.load.insertDwh;



import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.sql.Connection;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import org.apache.log4j.Logger;

public abstract class EnvInsertDwh {

	protected String[] nomeCampiChiave;
	protected String nomeTabellaOrigine;
	protected String sqlUpdateFlagElaborato;
	protected Object[] altriParams;
	private String provenienza;
	protected static final org.apache.log4j.Logger log = Logger.getLogger(EnvInsertDwh.class.getName());
	
	
	public String getProvenienza() {
		return provenienza;
	}

	public String[] getNomeCampiChiave() {
		return nomeCampiChiave;
	}

	public EnvInsertDwh(String nomeTabellaOrigine, String provenienza, String[] nomeCampiChiave) {
		this.nomeCampiChiave = nomeCampiChiave;
		this.provenienza = provenienza;
		this.nomeTabellaOrigine = nomeTabellaOrigine;
	}
	
	/**
	 * Il metodo restituisce data una riga di resultset
	 * un array che ha come elementi le colonne (in una hash) 
	 * del particolare record da inserire
	 * @param rs
	 * @return
	 */
	public abstract ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception ;

	public ArrayList<RigaToInsert> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> righe = getRigheSpec(rs);
		return righe;
		
		
	}
	
	/**
	 * Qualsiasi sql da eseguire dopo l'inserim,ento 
	 * del record corrente nella classe InsertDwh
	 * 
	 * @param conn - connessione sulla quale viene inserio il record in dwh
	 * @param currRecord - hashmap  nella quals ci sono i valori dei campi, nella chiave ci sono i nomi dei campi
	 * @throws Exception
	 */
	public abstract void executeSqlPostInsertRecord (Connection conn, LinkedHashMap<String, Object> currRecord) throws Exception ;

	
	/**
	 * FORNISCE LA STRINGA SQL PER L'UPDATE DI UNA RIGA DELL'ORIGINE DATI
	 * @return
	 */
	public abstract String getSqlUpdateFlagElaborato();


	
    /**
     * metodo che usato dopo il cstruttore permette di settare dei parametri
     * nel caso debbano essere utilizzati dentro la gestione concreta di getRighe
     * @param altriParams
     */
    public void setParametriPerGetRighe(Object... params) {
    	this.altriParams = params;
    }
    

    
    
   
    
    
	

	public String getNomeTabellaOrigine() {
		return nomeTabellaOrigine;
	}
	
	
	
    
    

}
