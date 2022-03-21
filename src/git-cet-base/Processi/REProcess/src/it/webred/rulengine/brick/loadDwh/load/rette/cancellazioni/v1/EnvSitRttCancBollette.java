package it.webred.rulengine.brick.loadDwh.load.rette.cancellazioni.v1;

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


public class EnvSitRttCancBollette extends EnvInsertDwh {
		
	public EnvSitRttCancBollette(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"ID_SERVIZIO", "COD_BOLLETTA"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_SERVIZIO",rs.getString("ID_SERVIZIO") );
		params.put("COD_BOLLETTA",rs.getString("COD_BOLLETTA") );
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		
		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE ID_SERVIZIO=? AND COD_BOLLETTA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
