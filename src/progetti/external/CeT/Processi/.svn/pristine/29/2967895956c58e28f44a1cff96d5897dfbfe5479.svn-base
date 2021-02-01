package it.webred.rulengine.brick.loadDwh.load.util;


import it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni.bean.TipoRecordVariazione;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import java.sql.ResultSet;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

public abstract class GestoreCorrelazioneVariazioni {
	
	protected static final Logger	log	= Logger.getLogger(GestoreCorrelazioneVariazioni.class.getName());
	
	protected  HashMap<String,List<TipoRecordVariazione>> mappaTab_TipiRecord=new HashMap<String,List<TipoRecordVariazione>>();
	
	protected abstract HashMap<String, List<TipoRecordVariazione>> getMappaTabTipiRecord();

	protected String tipoDato_CIV="CIV";
	protected String tipoDato_VIA="VIA";
	protected String tipoDato_OGG="OGG";
	protected String tipoDato_FAB="FAB";
	protected String tipoDato_SOG="SOG";
	
	protected final String SQL_INSERT = 
			"INSERT INTO SIT_CORRELAZIONE_VARIAZIONI(id_dwh,fk_ente_sorgente,prog_es,tipo_dato,tipo_variazione,note, fields) " +
			"VALUES (?,?,?,?,?,?,?)";
	
	public GestoreCorrelazioneVariazioni(){}
	
	public void aggiornaOnDelete(Connection conn, String nomeTabella, List<String> rs) throws Exception{
		this.aggiorna(conn,nomeTabella, rs, "DEL");
	}
	
	public void aggiornaOnUpdate(Connection conn, String nomeTabella, List<String> rs) throws Exception{
		this.aggiorna(conn,nomeTabella, rs, "UPD");
	}
	
	public void aggiorna(Connection conn, String nomeTabella, List<String> rs, String tipoVariazione) throws Exception{
		
		try{
		List<TipoRecordVariazione> lst = this.getMappaTabTipiRecord().get(nomeTabella);
		if(lst!=null){
			for(TipoRecordVariazione tr : lst){
				
				int i=0;
				for(String id : rs){
					List<String> idCorr = new ArrayList<String>();
					
					if(tr.getTipoDato().equals(this.tipoDato_CIV))
						idCorr = this.getIdCiv(conn,tr, id, nomeTabella);
					if(tr.getTipoDato().equals(this.tipoDato_VIA))
						idCorr = this.getIdVia(conn,tr, id, nomeTabella);
					if(tr.getTipoDato().equals(this.tipoDato_OGG))
						idCorr = this.getIdOgg(conn,tr, id, nomeTabella);
					if(tr.getTipoDato().equals(this.tipoDato_FAB))
						idCorr = this.getIdFab(conn,tr, id, nomeTabella);
					if(tr.getTipoDato().equals(this.tipoDato_SOG))
						idCorr = this.getIdSog(conn,tr, id, nomeTabella);
					
					for( String idC: idCorr){
						this.insertRecord(conn, idC, tr, tipoVariazione);
						i++;
					}
					
					if(i%1000 == 0)
						conn.commit();
				}
			}
		}
		}catch(Exception e){
			log.error("Errore aggiornamento SIT_CORRELAZIONE_VARIAZIONI, tabella "+nomeTabella+" "+e.getMessage(),e);
			throw e;
		}
	}
	
	
	protected void insertRecord(Connection conn,String idDwh,TipoRecordVariazione tr,String tipoVariazione) throws Exception{
		CallableStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareCall(SQL_INSERT);
			st.setString(1, idDwh);
			st.setInt(2, tr.getFkEnteSorgente());
			st.setInt(3, tr.getProgEs());
			st.setString(4, tr.getTipoDato());
			st.setString(5, tipoVariazione);
			st.setString(6, null);
			st.setString(7, null);
			st.execute();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}finally{
			DbUtils.close(st);
		}
		
	} 
	
	
	/*Per default come ID nelle tabelle di correlazione viene preso l'ID della tabella di origine, sovrascrivere nel caso in cui 
	 * l'ID_DWH venga ottenuto come combinazione di più campi e/o con join di tabelle diverse 
	 * (es. ID_DWH civico tributi = idVia||numCivico||numBarrato) */
	
	protected  List<String> getIdOgg(Connection conn,TipoRecordVariazione tr,String id_orig,String tabella) throws SQLException{
		List<String> idCorr = new ArrayList<String>();
		//L'oggetto va con lo stesso id
		idCorr.add(id_orig);
		return idCorr;
	}

	
	protected  List<String> getIdFab(Connection conn,TipoRecordVariazione tr,String id_orig,String tabella) throws SQLException{
		List<String> idCorr = new ArrayList<String>();
		//Il fabbricato va con lo stesso id
		idCorr.add(id_orig);
		return idCorr;
	}

	
	protected  List<String> getIdVia(Connection conn,TipoRecordVariazione tr,String id_orig,String tabella) throws SQLException{
		List<String> idCorr = new ArrayList<String>();
		//La via prende lo stesso id
		idCorr.add(id_orig);
		return idCorr;
	}
	
	protected  List<String> getIdCiv(Connection conn,TipoRecordVariazione tr,String id_orig,String tabella) throws SQLException{
		List<String> idCorr = new ArrayList<String>();
		//Il civico prende lo stesso id
		idCorr.add(id_orig);
		return idCorr;
	}

	
	protected  List<String> getIdSog(Connection conn,TipoRecordVariazione tr,String id_orig,String tabella) throws SQLException{
		List<String> idCorr = new ArrayList<String>();
		//Il soggetto va con lo stesso id
		idCorr.add(id_orig);
		return idCorr;
	}
	
	
}
