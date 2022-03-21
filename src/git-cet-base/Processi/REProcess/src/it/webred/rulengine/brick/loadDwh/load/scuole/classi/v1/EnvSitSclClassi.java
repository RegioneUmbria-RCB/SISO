package it.webred.rulengine.brick.loadDwh.load.scuole.classi.v1;

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


public class EnvSitSclClassi extends EnvInsertDwh {
		
	public EnvSitSclClassi(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"COD_CLASSE"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("COD_CLASSE");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 25);
		
		params.put("COD_CLASSE",rs.getString("COD_CLASSE") );
		params.put("COD_ISTITUTO", rs.getString("COD_ISTITUTO"));
		params.put("NR_SEZIONE",rs.getString("NR_SEZIONE") );
		params.put("SEZIONE_CLASSE", rs.getString("SEZIONE_CLASSE"));
		params.put("TIPO_CLASSE",rs.getString("TIPO_CLASSE") );
		params.put("MENSA_LUN",rs.getString("MENSA_LUN") );
		params.put("MENSA_MAR",rs.getString("MENSA_MAR") );
		params.put("MENSA_MER",rs.getString("MENSA_MER") );
		params.put("MENSA_GIO",rs.getString("MENSA_GIO") );
		params.put("MENSA_VEN",rs.getString("MENSA_VEN") );
		params.put("MENSA_SAB",rs.getString("MENSA_SAB") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_CLASSE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
