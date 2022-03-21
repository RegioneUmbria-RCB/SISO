package it.webred.rulengine.brick.loadDwh.load.rette.dettaglio.v1;

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


public class EnvSitRttDettBollette extends EnvInsertDwh {
		
	public EnvSitRttDettBollette(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("DES_VOCE") + "@";
		chiave += rs.getString("COD_BOLLETTA");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 18);
		
		params.put("COD_BOLLETTA",rs.getString("COD_BOLLETTA") );
		params.put("DT_INI_SERVIZIO",rs.getString("DT_INI_SERVIZIO") );
		params.put("DT_FIN_SERVIZIO",rs.getString("DT_FIN_SERVIZIO") );
		params.put("DES_OGGETTO",rs.getString("DES_OGGETTO") );
		params.put("UBICAZIONE",rs.getString("UBICAZIONE") );
		params.put("CATEGORIA",rs.getString("CATEGORIA") );
		params.put("COD_VOCE",rs.getString("COD_VOCE") );
		params.put("DES_VOCE",rs.getString("DES_VOCE") );
		params.put("VALORE",rs.getString("VALORE") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_BOLLETTA=? AND DES_VOCE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
