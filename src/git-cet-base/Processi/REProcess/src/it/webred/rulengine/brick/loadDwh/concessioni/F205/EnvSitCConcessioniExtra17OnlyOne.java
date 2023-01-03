package it.webred.rulengine.brick.loadDwh.concessioni.F205;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

public class EnvSitCConcessioniExtra17OnlyOne extends EnvInsertDwh {

	public EnvSitCConcessioniExtra17OnlyOne(String nomeTabellaOrigine, String... nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}	
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		LinkedHashMap< String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("A"));
		
		params.put("FK_ENTE_SORGENTE", 3);
		
		params.put("ID_ORIG_C_CONCESSIONI",rs.getString("A"));	
		params.put("ALTRI_NUMERO_PG", rs.getClob("EXTRA_A"));
		params.put("ALTRI_ANNO_PG", rs.getClob("EXTRA_B"));
		params.put("ALTRI_SUB_PG", rs.getClob("EXTRA_C"));
		params.put("ALTRI_DATA_PG", rs.getClob("EXTRA_D"));
		params.put("ALTRI_OGGETTO_PG", rs.getClob("EXTRA_E"));
		params.put("NPG_ATTO_PRAT_RIF", rs.getClob("EXTRA_F"));
		params.put("ANNO_PG_ATTO_PRAT_RIF", rs.getClob("EXTRA_G"));
		params.put("PRATICHE_COLLEGATE", rs.getClob("EXTRA_H"));

		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		
		params.put("PROVENIENZA", "OO");
		
		ret.add(params);		
				
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE A=? AND DT_EXP_DATO=?";
	}

	@Override
	public void executeSqlPostInsertRecord(Connection conn, LinkedHashMap<String, Object> currRecord) throws Exception {		
		
	}
	
}

