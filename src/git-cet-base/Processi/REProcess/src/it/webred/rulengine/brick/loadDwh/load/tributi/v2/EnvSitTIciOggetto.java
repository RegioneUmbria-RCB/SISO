package it.webred.rulengine.brick.loadDwh.load.tributi.v2;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitTIciOggetto extends it.webred.rulengine.brick.loadDwh.load.tributi.v3.EnvSitTIciOggetto {
	
	public EnvSitTIciOggetto(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("ID_ORIG_OGG_ICI"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);		
		params.put("ID_ORIG_SOGG_CNT", rs.getString("ID_ORIG_SOGG_CNT"));
		params.put("ID_ORIG_SOGG_DICH", rs.getString("ID_ORIG_SOGG_DICH"));		
		params.put("YEA_DEN", rs.getString("YEA_DEN"));
		params.put("NUM_DEN", rs.getString("NUM_DEN"));		
		params.put("YEA_RIF", rs.getString("YEA_RIF"));		
		params.put("TIP_DEN", rs.getString("TIP_DEN"));		
		params.put("DESC_TIP_DEN", rs.getString("DESC_TIP_DEN"));		
		params.put("ID_ORIG_SOGG_CC", rs.getString("ID_ORIG_SOGG_CC"));
		params.put("PAR_CTS", rs.getString("PAR_CTS"));
		params.put("SEZ", rs.getString("SEZ"));
		params.put("FOGLIO", rs.getString("FOGLIO"));
		params.put("NUMERO", rs.getString("NUMERO"));
		params.put("SUB", rs.getString("SUB"));
		params.put("CAT", rs.getString("CAT"));
		params.put("CLS", rs.getString("CLS"));
		params.put("TIP_VAL", rs.getString("TIP_VAL"));
		params.put("DESC_TIP_VAL", rs.getString("DESC_TIP_VAL"));
		params.put("VAL_IMM", rs.getObject("VAL_IMM") == null ? null : new BigDecimal("" + DF.parse(rs.getString("VAL_IMM")).doubleValue()));
		params.put("PRC_POSS", rs.getObject("PRC_POSS") == null ? null : new BigDecimal("" + DF.parse(rs.getString("PRC_POSS")).doubleValue()));
		params.put("CAR_IMM", rs.getString("CAR_IMM"));
		params.put("DTR_ABI_PRI", rs.getObject("DTR_ABI_PRI") == null ? null : new BigDecimal("" + DF.parse(rs.getString("DTR_ABI_PRI")).doubleValue()));
		params.put("NUM_MOD", rs.getObject("NUM_MOD") == null ? null : new BigDecimal(DF.parse(rs.getString("NUM_MOD")).doubleValue()));
		params.put("NUM_RIGA", rs.getObject("NUM_RIGA") == null ? null : new BigDecimal(DF.parse(rs.getString("NUM_RIGA")).doubleValue()));
		params.put("SUF_RIGA", rs.getObject("SUF_RIGA") == null ? null : new BigDecimal(DF.parse(rs.getString("SUF_RIGA")).doubleValue()));
		params.put("FLG_IMM_STO", rs.getString("FLG_IMM_STO"));
		params.put("MESI_POS", rs.getObject("MESI_POS") == null ? null : new BigDecimal(DF.parse(rs.getString("MESI_POS")).doubleValue()));
		params.put("MESI_ESE", rs.getObject("MESI_ESE") == null ? null : new BigDecimal(DF.parse(rs.getString("MESI_ESE")).doubleValue()));
		params.put("MESI_RID", rs.getObject("MESI_RID") == null ? null : new BigDecimal(DF.parse(rs.getString("MESI_RID")).doubleValue()));
		params.put("FLG_POS3112", rs.getString("FLG_POS3112"));
		params.put("FLG_ESE3112", rs.getString("FLG_ESE3112"));
		params.put("FLG_RID3112", rs.getString("FLG_RID3112"));
		params.put("FLG_ABI_PRI3112", rs.getString("FLG_ABI_PRI3112"));
		params.put("FLG_ACQ", rs.getString("FLG_ACQ"));
		params.put("FLG_CSS", rs.getString("FLG_CSS"));
		params.put("YEA_PRO", rs.getString("YEA_PRO"));
		params.put("NUM_PRO", rs.getString("NUM_PRO"));
		params.put("FLG_TRF", rs.getString("FLG_TRF"));
		params.put("DES_IND", rs.getString("DES_IND"));
		params.put("ID_ORIG_VIA", rs.getString("ID_ORIG_VIA"));
		params.put("NUM_CIV", rs.getString("NUM_CIV"));
		params.put("ESP_CIV", rs.getString("ESP_CIV"));
		params.put("SCALA", rs.getString("SCALA"));
		params.put("PIANO", rs.getString("PIANO"));
		params.put("INTERNO", rs.getString("INTERNO"));
		params.put("TMS_AGG", rs.getObject("TMS_AGG") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_AGG")).getTime()));
		params.put("TMS_BON", rs.getObject("TMS_BON") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_BON")).getTime()));
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		params.put("PROVENIENZA", this.getProvenienza());

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

}
