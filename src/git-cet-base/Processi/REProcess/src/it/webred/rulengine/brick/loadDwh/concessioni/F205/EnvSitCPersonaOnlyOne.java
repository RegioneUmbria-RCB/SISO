package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCPersonaOnlyOne extends EnvInsertDwh {



	public EnvSitCPersonaOnlyOne(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

	LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("A"));
		
				
		params.put("FK_ENTE_SORGENTE", 3);
		params.put("TIPO_SOGGETTO", rs.getString("B"));
		params.put("TIPO_PERSONA", null);
		params.put("CODICE_FISCALE", rs.getString("D"));
		params.put("COGNOME", null);
		params.put("NOME", null);
		params.put("DENOMINAZIONE", rs.getString("C"));
		params.put("PROVENIENZA", "OO");
		params.put("TITOLO", rs.getString("E"));
		
		params.put("DATA_NASCITA",rs.getTimestamp("F"));
		params.put("COMUNE_NASCITA", rs.getString("G"));
		params.put("PROV_NASCITA", rs.getString("H"));
		params.put("INDIRIZZO", rs.getString("I"));
		params.put("CIVICO", null);
		params.put("CAP", rs.getString("L"));
		params.put("COMUNE_RESIDENZA", rs.getString("M"));
		params.put("PROV_RESIDENZA", rs.getString("N"));
		params.put("TELEFONO", rs.getString("O"));
		params.put("FAX", rs.getString("P"));
		params.put("EMAIL", rs.getString("Q"));
		params.put("PIVA", rs.getString("R"));
		params.put("INDIRIZZO_STUDIO", rs.getString("S"));
		params.put("CAP_STUDIO", rs.getString("T"));
		params.put("COMUNE_STUDIO", rs.getString("U"));
		params.put("PROV_STUDIO", rs.getString("V"));
		params.put("ALBO", rs.getString("D1"));
		params.put("RAGSOC_DITTA", rs.getString("I1"));
		params.put("CF_DITTA", rs.getString("L1"));
		params.put("PI_DITTA", rs.getString("M1"));
		params.put("INDIRIZZO_DITTA", rs.getString("N1"));
		params.put("CAP_DITTA", rs.getString("O1"));
		params.put("COMUNE_DITTA", rs.getString("P1"));
		params.put("PROV_DITTA", rs.getString("Q1"));
		params.put("QUALITA", rs.getString("V1"));
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0] );
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		params.put("DATA_INI_RES",null );

		
		ret.add(params);
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE A=? AND DT_EXP_DATO=?";
		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}




}
