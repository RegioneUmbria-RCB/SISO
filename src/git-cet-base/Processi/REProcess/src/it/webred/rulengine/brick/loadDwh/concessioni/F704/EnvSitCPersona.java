package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCPersona extends EnvInsertDwh {
	
	public EnvSitCPersona(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap <String, Object>> ret = new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("")) {
			
			LinkedHashMap<String, Object> params = null;
			
			String richied = rs.getObject("E") != null ? rs.getString("E").trim() : "";
			String indRichied = rs.getObject("F") != null ? rs.getString("F").trim() : "";
			String progett = rs.getObject("K") != null ? rs.getString("K").trim() : "";
			String dirLav = rs.getObject("L") != null ? rs.getString("L").trim() : "";
			String impresa = rs.getObject("M") != null ? rs.getString("M").trim() : "";
			int indice = 1;
			
			if (!richied.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				String indirizzo = null;
				String comuneResidenza = null;
				if (!indRichied.equals("")) {
					String[] valori = indRichied.split("-");
					indirizzo = valori[0].trim();
					if (valori.length > 1) {
						comuneResidenza = valori[1].trim();
					}
				}
				
				params.put("ID_ORIG", indice + "@" + rs.getString("A"));
				params.put("FK_ENTE_SORGENTE", 3);
				params.put("TIPO_SOGGETTO", null);
				params.put("TIPO_PERSONA", null);
				params.put("CODICE_FISCALE", null);
				params.put("COGNOME", null);
				params.put("NOME", null);
				params.put("DENOMINAZIONE", richied);
				params.put("PROVENIENZA", "C");
				params.put("TITOLO", null);				
				params.put("DATA_NASCITA", null);
				params.put("COMUNE_NASCITA", null);
				params.put("PROV_NASCITA", null);				
				params.put("INDIRIZZO", indirizzo);
				params.put("CIVICO", null);
				params.put("CAP", null);
				params.put("COMUNE_RESIDENZA", comuneResidenza);
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
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);				
				params.put("DATA_INI_RES", null);

				ret.add(params);
				indice++;
			}
			
			if (!progett.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				String denominazione = progett;
				String valori[] = progett.split(" ");
				String titolo = null;
				if (valori.length > 1 && valori[0].trim().endsWith(".")) {
					titolo = valori[0].trim();
					denominazione = "";
					for (int i = 1; i < valori.length; i++) {
						if (i > 1) {
							denominazione += " ";							
						}
						denominazione += valori[i].trim();
					}
				}
				
				params.put("ID_ORIG", indice + "@" + rs.getString("A"));
				params.put("FK_ENTE_SORGENTE", 3);
				params.put("TIPO_SOGGETTO", null);
				params.put("TIPO_PERSONA", null);
				params.put("CODICE_FISCALE", null);
				params.put("COGNOME", null);
				params.put("NOME", null);
				params.put("DENOMINAZIONE", denominazione);
				params.put("PROVENIENZA", "C");
				params.put("TITOLO", titolo);				
				params.put("DATA_NASCITA", null);
				params.put("COMUNE_NASCITA", null);
				params.put("PROV_NASCITA", null);
				params.put("INDIRIZZO", null);
				params.put("CIVICO", null);
				params.put("CAP", null);
				params.put("COMUNE_RESIDENZA", null);
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
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);				
				params.put("DATA_INI_RES", null);

				ret.add(params);
				indice++;
			}
			
			if (!dirLav.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				String denominazione = dirLav;
				String valori[] = dirLav.split(" ");
				String titolo = null;
				if (valori.length > 1 && valori[0].trim().endsWith(".")) {
					titolo = valori[0].trim();
					denominazione = "";
					for (int i = 1; i < valori.length; i++) {
						if (i > 1) {
							denominazione += " ";							
						}
						denominazione += valori[i].trim();
					}
				}
				
				params.put("ID_ORIG", indice + "@" + rs.getString("A"));
				params.put("FK_ENTE_SORGENTE", 3);
				params.put("TIPO_SOGGETTO", null);
				params.put("TIPO_PERSONA", null);
				params.put("CODICE_FISCALE", null);
				params.put("COGNOME", null);
				params.put("NOME", null);
				params.put("DENOMINAZIONE", denominazione);
				params.put("PROVENIENZA", "C");
				params.put("TITOLO", titolo);				
				params.put("DATA_NASCITA", null);
				params.put("COMUNE_NASCITA", null);
				params.put("PROV_NASCITA", null);				
				params.put("INDIRIZZO", null);
				params.put("CIVICO", null);
				params.put("CAP", null);
				params.put("COMUNE_RESIDENZA", null);
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
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);				
				params.put("DATA_INI_RES", null);

				ret.add(params);
				indice++;
			}
			
			if (!impresa.equals("")) {
				params = new LinkedHashMap<String, Object>();
				
				params.put("ID_ORIG", indice + "@" + rs.getString("A"));
				params.put("FK_ENTE_SORGENTE", 3);
				params.put("TIPO_SOGGETTO", null);
				params.put("TIPO_PERSONA", null);
				params.put("CODICE_FISCALE", null);
				params.put("COGNOME", null);
				params.put("NOME", null);
				params.put("DENOMINAZIONE", impresa);
				params.put("PROVENIENZA", "C");
				params.put("TITOLO", null);				
				params.put("DATA_NASCITA", null);
				params.put("COMUNE_NASCITA", null);
				params.put("PROV_NASCITA", null);				
				params.put("INDIRIZZO", null);
				params.put("CIVICO", null);
				params.put("CAP", null);
				params.put("COMUNE_RESIDENZA", null);
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
				params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
				params.put("DT_INI_VAL_DATO", null);
				params.put("DT_FINE_VAL_DATO", null);
				params.put("FLAG_DT_VAL_DATO", 0);				
				params.put("DATA_INI_RES", null);

				ret.add(params);
				indice++;
			}
		}
		
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
