package it.webred.rulengine.brick.loadDwh.load.rette.rate.v1;

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


public class EnvSitRttRateBollette extends EnvInsertDwh {
		
	public EnvSitRttRateBollette(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"COD_BOLLETTA", "NUM_RATA"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("NUM_RATA") + "@";
		chiave += rs.getString("COD_BOLLETTA");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 18);
		
		params.put("COD_BOLLETTA",rs.getString("COD_BOLLETTA") );
		params.put("COD_SERVIZIO",rs.getString("COD_SERVIZIO") );
		params.put("NUM_RATA",rs.getString("NUM_RATA") );
		params.put("DT_SCADENZA_RATA",rs.getString("DT_SCADENZA_RATA") );
		params.put("IMPORTO_RATA",rs.getString("IMPORTO_RATA") );
		params.put("IMPORTO_PAGATO",rs.getString("IMPORTO_PAGATO") );
		params.put("DT_PAGAMENTO",rs.getString("DT_PAGAMENTO") );
		params.put("DT_REG_PAGAMENTO",rs.getString("DT_REG_PAGAMENTO") );
		params.put("DES_DISTINTA",rs.getString("DES_DISTINTA") );
		params.put("DT_DISTINTA",rs.getString("DT_DISTINTA") );
		params.put("ID_SERVIZIO",rs.getString("ID_SERVIZIO") );
		params.put("ID_PRATICA",rs.getString("ID_PRATICA") );
		params.put("DES_CANALE",rs.getString("DES_CANALE") );
		params.put("DES_PAGAMENTO",rs.getString("DES_PAGAMENTO") );
		params.put("NOTE",rs.getString("NOTE") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_BOLLETTA=? AND NUM_RATA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
