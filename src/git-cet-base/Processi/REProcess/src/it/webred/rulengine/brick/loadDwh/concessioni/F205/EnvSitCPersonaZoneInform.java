package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCPersonaZoneInform extends EnvInsertDwh {



	public EnvSitCPersonaZoneInform(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		

	LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("PK_PERSONA"));
		
				
		params.put("FK_ENTE_SORGENTE", 3);
		String denominazione = null;
		String cognome = "F".equals(rs.getString("TIPO_PERSONA"))?rs.getString("COGNOME_RAGSOC"):null;
		String nome = rs.getString("NOME");
		String tipoSoggetto = "P";
		if ("F".equals(rs.getString("TIPO_PERSONA")))
			denominazione = cognome + " " + nome;
		if ("G".equals(rs.getString("TIPO_PERSONA"))) {
			denominazione = rs.getString("COGNOME_RAGSOC");
			tipoSoggetto = "A";
		}
		
		
		params.put("TIPO_SOGGETTO", rs.getString("TIPO_PERSONA"));
		params.put("TIPO_PERSONA", tipoSoggetto);
		params.put("CODICE_FISCALE", rs.getString("CODICE_FISCALE"));
		params.put("COGNOME", cognome);
		params.put("NOME", nome);
		params.put("DENOMINAZIONE", denominazione);
		params.put("PROVENIENZA", "I");
		params.put("TITOLO", null);
		
		params.put("DATA_NASCITA",null);
		params.put("COMUNE_NASCITA", null);
		params.put("PROV_NASCITA", null);
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("CIVICO", rs.getString("CIVICO"));
		params.put("CAP", null);
		params.put("COMUNE_RESIDENZA", rs.getString("CITTA"));
		params.put("PROV_RESIDENZA", null);
		params.put("TELEFONO", null);
		params.put("FAX", null);
		params.put("EMAIL", null);
		params.put("PIVA", null);
		params.put("INDIRIZZO_STUDIO", null);
		params.put("CAP_STUDIO", null);
		params.put("COMUNE_STUDIO", null);
		params.put("PROV_STUDIO", null);
		params.put("ALBO", null);
		params.put("RAGSOC_DITTA", null);
		params.put("CF_DITTA", null);
		params.put("PI_DITTA", null);
		params.put("INDIRIZZO_DITTA", null);
		params.put("CAP_DITTA", null);
		params.put("COMUNE_DITTA", null);
		params.put("PROV_DITTA", null);
		params.put("QUALITA", null);
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]  );
		params.put("DT_INI_VAL_DATO", rs.getTimestamp("DATA_RES"));
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 1);
		params.put("DATA_INI_RES",rs.getTimestamp("DATA_RES") );
		
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
