package it.webred.rulengine.brick.loadDwh.concessioni.F205;


import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCConcessioniZoneInform extends EnvInsertDwh {



	public EnvSitCConcessioniZoneInform(String nomeTabellaOrigine, String... nomeCampiChiave) {
		super(nomeTabellaOrigine, nomeCampiChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

	LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("PK_CONC"));
		
				
		params.put("FK_ENTE_SORGENTE", 3);
		params.put("CONCESSIONE_NUMERO", rs.getString("CODICE_FASCICOLO"));
		params.put("PROGRESSIVO_NUMERO", rs.getString("RIF_NUMERO"));
		params.put("PROGRESSIVO_ANNO", rs.getString("RIF_ANNO"));
		params.put("PROTOCOLLO_DATA", rs.getTimestamp("DATA_PROTOCOLLO"));
		params.put("PROTOCOLLO_NUMERO", rs.getString("RIF_NUMERO"));
		params.put("TIPO_INTERVENTO", rs.getString("TIPO_DOCUMENTO"));
		
		String oggetto = "";
		oggetto += "SI".equals(rs.getString("RECUPERO_SOTTOTETTO"))?"|RECUPERO SOTTOTETTO|":"";
		oggetto += "SI".equals(rs.getString("CAMBIO_USO"))?"|CAMBIO_USO|":"";
		oggetto += "SI".equals(rs.getString("INSERIMENTO_BAGNO"))?"|INSERIMENTO BAGNO|":"";
		oggetto += "SI".equals(rs.getString("INSTALLAZIONE_ASCENSORE"))?"|INSTALLAZIONE ASCENSORE|":"";
		oggetto += "SI".equals(rs.getString("FUSIONE_FRAZIONAMENTO"))?"|FUSIONE FRAZIONAMENTO|":"";
		oggetto += "SI".equals(rs.getString("DEMOLIZIONE"))?"|DEMOLIZIONE|":"";
		oggetto += "SI".equals(rs.getString("MANUTENZIONE_STRAORDINARIA"))?"|MANUTENZIONE STRAORDINARIA|":"";

		params.put("OGGETTO", oggetto);
		
		params.put("PROCEDIMENTO", null);
		
		params.put("ZONA", null);
		params.put("DATA_RILASCIO",rs.getTimestamp("DATA_RILASCIO"));
		params.put("DATA_INIZIO_LAVORI", rs.getTimestamp("DATA_INI_LAVORI"));
		params.put("DATA_CHIUSURA", rs.getTimestamp("DATA_ULT_LAVORI"));
		params.put("PROVENIENZA", "I");

		params.put("DT_EXP_DATO", (Timestamp) altriParams[0] );
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		
		ret.add(params);
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return null;
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}







}
