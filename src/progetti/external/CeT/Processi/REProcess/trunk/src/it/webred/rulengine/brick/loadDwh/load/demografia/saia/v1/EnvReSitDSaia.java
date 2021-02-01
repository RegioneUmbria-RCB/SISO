package it.webred.rulengine.brick.loadDwh.load.demografia.saia.v1;

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


public class EnvReSitDSaia extends EnvInsertDwh {
		
	public EnvReSitDSaia(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, new String[] {"COD_BOLLETTA"});
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		/*LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("COD_BOLLETTA");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 18);
		
		params.put("COD_SOGGETTO",rs.getString("COD_SOGGETTO") );
		params.put("DES_INTESTATARIO", rs.getString("DES_INTESTATARIO"));
		params.put("CODICE_FISCALE",rs.getString("CODICE_FISCALE") );
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("RECAPITO", rs.getString("RECAPITO"));
		params.put("COD_BOLLETTA",rs.getString("COD_BOLLETTA") );
		params.put("ANNO",rs.getString("ANNO") );
		params.put("COD_SERVIZIO",rs.getString("COD_SERVIZIO") );
		params.put("ID_SERVIZIO",rs.getString("ID_SERVIZIO") );
		params.put("NUM_BOLLETTA",rs.getString("NUM_BOLLETTA") );
		params.put("NUM_RATE",rs.getString("NUM_RATE") );
		params.put("DATA_BOLLETTA",rs.getString("DATA_BOLLETTA") );
		params.put("OGGETTO",rs.getString("OGGETTO") );
		params.put("SPESE_SPEDIZIONE",rs.getString("SPESE_SPEDIZIONE") );
		params.put("TOT_ESENTE_IVA",rs.getString("TOT_ESENTE_IVA") );
		params.put("TOT_IMPONIBILE_IVA",rs.getString("TOT_IMPONIBILE_IVA") );
		params.put("TOT_IVA",rs.getString("TOT_IVA") );
		params.put("ARROTONDAMENTO_PREC",rs.getString("ARROTONDAMENTO_PREC") );
		params.put("ARROTONDAMENTO_ATT",rs.getString("ARROTONDAMENTO_ATT") );
		params.put("IMPORTO_BOLLETTA_PREC",rs.getString("IMPORTO_BOLLETTA_PREC") );
		params.put("TOT_BOLLETTA",rs.getString("TOT_BOLLETTA") );
		params.put("TOT_PAGATO",rs.getString("TOT_PAGATO") );
		params.put("FL_NON_PAGARE",rs.getString("FL_NON_PAGARE") );
		params.put("MOT_NON_PAGARE",rs.getString("MOT_NON_PAGARE") );
		params.put("NOTE",rs.getString("NOTE") );
		
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));*/
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "";//UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_BOLLETTA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
