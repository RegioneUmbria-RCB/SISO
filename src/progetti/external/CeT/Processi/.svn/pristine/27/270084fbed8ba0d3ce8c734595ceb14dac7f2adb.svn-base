package it.webred.rulengine.brick.loadDwh.load.concessioni.v1;


import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitCPersona extends EnvInsertDwh {



	public EnvSitCPersona(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine,provenienza, nomeCampoChiave);
	}

	
	
	
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				

	LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("CHIAVE"));
		
				
		params.put("FK_ENTE_SORGENTE", 3);
		params.put("TIPO_SOGGETTO", rs.getString("tipo_soggetto"));
		params.put("TIPO_PERSONA", rs.getString("tipo_persona"));
		params.put("CODICE_FISCALE", rs.getString("codice_fiscale"));
		params.put("COGNOME", rs.getString("COGNOME"));
		params.put("NOME", rs.getString("NOME"));
		params.put("DENOMINAZIONE", rs.getString("Denominazione_RagSoc"));
		params.put("PROVENIENZA", this.getProvenienza());

		// il titolo non va qui ma nell'altra tabella
		params.put("TITOLO", null); 
		
		if (rs.getObject("DATA_NASCITA") != null && !rs.getString("DATA_NASCITA").trim().equals("")) {
			try {
				params.put("DATA_NASCITA",
						new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("DATA_NASCITA")).getTime())
				);
			} catch (Exception e) {}				
		}	
		
		params.put("COMUNE_NASCITA", rs.getString("comune_nascita"));
		params.put("PROV_NASCITA", rs.getString("provincia_nascita"));
		params.put("INDIRIZZO",rs.getString("INDIRIZZO_residenza")  );
		params.put("CIVICO", rs.getString("civico_residenza") );
		params.put("CAP", rs.getString("cap_residenza"));
		params.put("COMUNE_RESIDENZA", rs.getString("comune_residenza"));
		params.put("PROV_RESIDENZA", rs.getString("provincia_residenza"));
		params.put("TELEFONO", rs.getString("tel"));
		params.put("FAX", rs.getString("fax"));
		params.put("EMAIL", rs.getString("email"));
		params.put("PIVA", rs.getString("piva"));
		params.put("INDIRIZZO_STUDIO", rs.getString("indirizzo_studio"));
		params.put("CAP_STUDIO", rs.getString("cap_studio"));
		params.put("COMUNE_STUDIO", rs.getString("comune_studio"));
		params.put("PROV_STUDIO", rs.getString("provincia_studio"));
		params.put("ALBO", rs.getString("albo"));
		params.put("RAGSOC_DITTA", rs.getString("rag_Soc_ditta"));
		params.put("CF_DITTA", rs.getString("cf_ditta"));
		params.put("PI_DITTA", rs.getString("pi_ditta"));
		params.put("INDIRIZZO_DITTA", rs.getString("indirizzo_ditta"));
		params.put("CAP_DITTA", rs.getString("cap_ditta"));
		params.put("COMUNE_DITTA", rs.getString("comune_ditta"));
		params.put("PROV_DITTA", rs.getString("provincia_ditta"));
		params.put("QUALITA", rs.getString("qualita"));
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0] );
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		if (rs.getObject("data_inizio_residenza") != null && !rs.getString("data_inizio_residenza").trim().equals("")) {
			try {
				params.put("DATA_INI_RES",
						new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("data_inizio_residenza")).getTime())
				);
			} catch (Exception e) {}				
		}

		
		
		ret.add(new RigaToInsert(params));
		
		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		// l' update viene fatto su sit_c_conc_persona non qui
		return null;
		
	}





	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}




}
