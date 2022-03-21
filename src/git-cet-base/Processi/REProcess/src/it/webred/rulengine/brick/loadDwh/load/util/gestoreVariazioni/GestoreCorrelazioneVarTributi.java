package it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni;


import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni.bean.TipoRecordVariazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import java.sql.ResultSet;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

public class GestoreCorrelazioneVarTributi extends GestoreCorrelazioneVariazioni{
	
	public GestoreCorrelazioneVarTributi(){
		super();	
	}

	@Override
	protected List<String> getIdCiv(Connection conn, TipoRecordVariazione tr, String id_orig,String tabella) throws SQLException  {
		List<String> idCorr = new ArrayList<String>();
		
		String sql= null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String sqlOggetto = 
			"select iciv.ID || '|' || NVL(icio.NUM_CIV, '') || '|' || NVL(icio.ESP_CIV, '') id_dwh " +
			"from @TAB_OGGETTO@ icio INNER JOIN @TAB_VIA@ iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT ) " +
			"where icio.id = ? ";
		
		String sqlVia = 
			"select iciv.ID || '|' || NVL(icio.NUM_CIV, '') || '|' || NVL(icio.ESP_CIV, '') id_dwh " +
			"from @TAB_OGGETTO@ icio INNER JOIN @TAB_VIA@ iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT ) " +
			"where iciv.id = ? ";
		
		if(id_orig!=null){
		
			if(tabella.equals("SIT_T_ICI_OGGETTO") || tabella.equals("SIT_T_TAR_OGGETTO")){
			
				if(tabella.equals("SIT_T_ICI_OGGETTO")){
					sqlOggetto = sqlOggetto.replace("@TAB_OGGETTO@", "SIT_T_ICI_OGGETTO");
					sqlOggetto = sqlOggetto.replace("@TAB_VIA@", "SIT_T_ICI_VIA");
				}
				
				else if(tabella.equals("SIT_T_TAR_OGGETTO")){
					sqlOggetto = sqlOggetto.replace("@TAB_OGGETTO@", "SIT_T_TAR_OGGETTO");
					sqlOggetto = sqlOggetto.replace("@TAB_VIA@", "SIT_T_TAR_VIA");
				}
				
			sql = sqlOggetto;
			
				
			}else if(tabella.equals("SIT_T_ICI_VIA") || tabella.equals("SIT_T_TAR_VIA")){	
				//devo fare join con la tabella delle vie e recuperare come id idVia||numCivico||numBarrato
				 if(tabella.equals("SIT_T_ICI_VIA") ){
					sqlVia = sqlVia.replace("@TAB_OGGETTO@", "SIT_T_ICI_OGGETTO");
					sqlVia = sqlVia.replace("@TAB_VIA@", "SIT_T_ICI_VIA");
				}
				
				else if( tabella.equals("SIT_T_TAR_VIA")){
					sqlVia = sqlVia.replace("@TAB_OGGETTO@", "SIT_T_TAR_OGGETTO");
					sqlVia = sqlVia.replace("@TAB_VIA@", "SIT_T_TAR_VIA");
				}
				 
				sql = sqlVia;
			}
			
			try {
				if(sql!=null){
					ps = conn.prepareStatement(sql);
					ps.setString(1, id_orig);
					rs = ps.executeQuery();
					while(rs.next()){
						String idDwh = rs.getString(1);
						idCorr.add(idDwh);
					}
				}
			
			}catch(SQLException e){
					throw e;
			}finally{
				DbUtils.close(rs);
				DbUtils.close(ps);
			}
		
		}
		
		return idCorr;
	}



	@Override
	protected HashMap<String, List<TipoRecordVariazione>> getMappaTabTipiRecord() {
		this.mappaTab_TipiRecord = new HashMap<String, List<TipoRecordVariazione>>();
		
		List<TipoRecordVariazione> lstTAR = new ArrayList<TipoRecordVariazione>();
		List<TipoRecordVariazione> lstICI = new ArrayList<TipoRecordVariazione>();
		
		lstICI.add(new TipoRecordVariazione(2,2,this.tipoDato_OGG));
		lstICI.add(new TipoRecordVariazione(2,2,this.tipoDato_FAB));
		lstICI.add(new TipoRecordVariazione(2,2,this.tipoDato_CIV));
		
		lstTAR.add(new TipoRecordVariazione(2,3,this.tipoDato_OGG));
		lstTAR.add(new TipoRecordVariazione(2,3,this.tipoDato_FAB));
		lstTAR.add(new TipoRecordVariazione(2,3,this.tipoDato_CIV));
		
		this.mappaTab_TipiRecord.put("SIT_T_ICI_OGGETTO", lstICI);
		this.mappaTab_TipiRecord.put("SIT_T_TAR_OGGETTO", lstTAR);
		
		//L'oggetto finisce in sit_oggetto_totale, sit_fabbricato_totale, sit__civico_totale
		
		lstTAR = new ArrayList<TipoRecordVariazione>();
		lstICI = new ArrayList<TipoRecordVariazione>();
		
		lstICI.add(new TipoRecordVariazione(2,2,this.tipoDato_VIA));
		lstICI.add(new TipoRecordVariazione(2,2,this.tipoDato_CIV));
		
		lstTAR.add(new TipoRecordVariazione(2,3,this.tipoDato_VIA));
		lstTAR.add(new TipoRecordVariazione(2,3,this.tipoDato_CIV));
		
		this.mappaTab_TipiRecord.put("SIT_T_ICI_VIA", lstICI);
		this.mappaTab_TipiRecord.put("SIT_T_TAR_VIA", lstTAR);
		
		//L'oggetto finisce in sit_via_totale, sit_civico_totale
		
		lstTAR = new ArrayList<TipoRecordVariazione>();
		lstICI = new ArrayList<TipoRecordVariazione>();
		
		lstICI.add(new TipoRecordVariazione(2,1,this.tipoDato_SOG));
		
		lstTAR.add(new TipoRecordVariazione(2,4,this.tipoDato_SOG));
		
		this.mappaTab_TipiRecord.put("SIT_T_ICI_SOGG", lstICI);
		this.mappaTab_TipiRecord.put("SIT_T_TAR_SOGG", lstTAR);
		
		//L'oggetto finisce in sit_soggetto_totale		
	
		return this.mappaTab_TipiRecord;
	}

}
