package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.v1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

public class EnvSitLicenzeCommercioAnag extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 13;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	public EnvSitLicenzeCommercioAnag(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("ID_ORIG", rs.getString("CHIAVE"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("ID_ORIG_AUTORIZZAZIONE", rs.getString("CHIAVE_AUTORIZZAZIONE"));
		params.put("NUMERO", rs.getString("NUMERO"));
		params.put("CODICE_FISCALE", rs.getString("CODICE_FISCALE"));
		params.put("COGNOME", rs.getString("COGNOME"));
		params.put("NOME", rs.getString("NOME"));
		params.put("DENOMINAZIONE", rs.getString("DENOMINAZIONE"));
		params.put("FORMA_GIURIDICA", rs.getString("FORMA_GIURIDICA"));
		params.put("TITOLO", rs.getString("TITOLO"));
		params.put("DATA_NASCITA", rs.getObject("DATA_NASCITA") == null ? null : 
					new Timestamp(SDF.parse(rs.getString("DATA_NASCITA")).getTime()));
		params.put("COMUNE_NASCITA", rs.getString("COMUNE_NASCITA"));
		params.put("PROVINCIA_NASCITA", rs.getString("PROVINCIA_NASCITA"));
		params.put("INDIRIZZO_RESIDENZA", rs.getString("INDIRIZZO_RESIDENZA"));
		params.put("CIVICO_RESIDENZA", rs.getString("CIVICO_RESIDENZA"));
		params.put("CAP_RESIDENZA", rs.getString("CAP_RESIDENZA"));
		params.put("COMUNE_RESIDENZA", rs.getString("COMUNE_RESIDENZA"));
		params.put("PROVINCIA_RESIDENZA", rs.getString("PROVINCIA_RESIDENZA"));
		params.put("DATA_INIZIO_RESIDENZA", rs.getObject("DATA_INIZIO_RESIDENZA") == null ? null : 
					new Timestamp(SDF.parse(rs.getString("DATA_INIZIO_RESIDENZA")).getTime()));
		params.put("TEL", rs.getString("TEL"));
		params.put("FAX", rs.getString("FAX"));
		params.put("EMAIL", rs.getString("EMAIL"));
		params.put("PIVA", rs.getString("PIVA"));		
		params.put("PROVENIENZA", this.getProvenienza());
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE CHIAVE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}

