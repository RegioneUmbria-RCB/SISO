package it.webred.rulengine.brick.loadDwh.load.scuole.istituti.v1;

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


public class EnvSitSclIstituti extends EnvInsertDwh {
		
	public EnvSitSclIstituti(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"COD_ISTITUTO"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("COD_ISTITUTO");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 17);
		
		params.put("COD_ISTITUTO",rs.getString("COD_ISTITUTO") );
		params.put("NOME_ISTITUTO", rs.getString("NOME_ISTITUTO"));
		params.put("TIPO_ISTITUTO",rs.getString("TIPO_ISTITUTO") );
		params.put("NR_ISCRITTI", rs.getString("NR_ISCRITTI"));
		params.put("COD_INDIRIZZO",rs.getString("COD_INDIRIZZO") );
		params.put("COD_COMUNE",rs.getString("COD_COMUNE") );
		params.put("COMUNE",rs.getString("COMUNE") );
		params.put("TOPONIMO",rs.getString("TOPONIMO") );
		params.put("VIA",rs.getString("VIA") );
		params.put("CIVICO",rs.getString("CIVICO") );
		params.put("LETTERA",rs.getString("LETTERA") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_ISTITUTO=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
