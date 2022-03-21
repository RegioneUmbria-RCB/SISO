package it.webred.rulengine.brick.loadDwh.load.rette.soggetti.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.exception.RulEngineException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitRttSoggBollette extends EnvInsertDwh {
		
	public EnvSitRttSoggBollette(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"COD_SOGGETTO", "PROVENIENZA"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("COD_SOGGETTO") + "@";
		chiave += rs.getString("PROVENIENZA");
		
		//params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 18);
		
		params.put("COD_SOGGETTO",rs.getString("COD_SOGGETTO") );
		params.put("PROVENIENZA",rs.getString("PROVENIENZA") );
		params.put("CODICE_FISCALE",rs.getString("CODICE_FISCALE") );
		params.put("COGNOME",rs.getString("COGNOME") );
		params.put("NOME",rs.getString("NOME") );
		params.put("SESSO",rs.getString("SESSO") );
		params.put("DATA_NASCITA",rs.getString("DATA_NASCITA") );
		params.put("PARTITA_IVA",rs.getString("PARTITA_IVA") );
		params.put("COMUNE_NASCITA",rs.getString("COMUNE_NASCITA") );
		params.put("LOCALITA_NASCITA",rs.getString("LOCALITA_NASCITA") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_SOGGETTO=? AND PROVENIENZA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
