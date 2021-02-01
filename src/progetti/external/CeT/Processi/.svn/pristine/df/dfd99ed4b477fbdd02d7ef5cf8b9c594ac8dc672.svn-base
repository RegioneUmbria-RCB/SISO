package it.webred.rulengine.brick.loadDwh.load.tributi.v3;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

public class EnvSitTTarOggetto extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 2;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat SDF_TMS = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitTTarOggetto(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("ID_ORIG_OGG_RSU"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("ID_ORIG_SOGG_CNT", rs.getString("ID_ORIG_SOGG_CNT"));
		params.put("ID_ORIG_SOGG_DICH", rs.getString("ID_ORIG_SOGG_DICH"));
		params.put("DES_CLS_RSU", rs.getString("DES_CLS_RSU"));
		params.put("SEZ", rs.getString("SEZ"));
		params.put("FOGLIO", rs.getString("FOGLIO"));
		params.put("NUMERO", rs.getString("NUMERO"));
		params.put("SUB", rs.getString("SUB"));
		params.put("SUP_TOT", rs.getObject("SUP_TOT") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SUP_TOT")).doubleValue()));
		params.put("DAT_INI", rs.getObject("DAT_INI") == null ? null : new Timestamp(SDF.parse(rs.getString("DAT_INI")).getTime()));
		params.put("DAT_FIN", rs.getObject("DAT_FIN") == null ? null : new Timestamp(SDF.parse(rs.getString("DAT_FIN")).getTime()));
		params.put("TIP_OGG", rs.getString("TIP_OGG"));
		params.put("DES_TIP_OGG", rs.getString("DES_TIP_OGG"));
		params.put("DES_IND", rs.getString("DES_IND"));
		params.put("ID_ORIG_VIA", rs.getString("ID_ORIG_VIA"));
		params.put("NUM_CIV", rs.getString("NUM_CIV"));
		params.put("ESP_CIV", rs.getString("ESP_CIV"));
		params.put("SCALA", rs.getString("SCALA"));
		params.put("PIANO", rs.getString("PIANO"));
		params.put("INTERNO", rs.getString("INTERNO"));
		params.put("TMS_AGG", rs.getObject("TMS_AGG") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_AGG")).getTime()));
		params.put("TMS_BON", rs.getObject("TMS_BON") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_BON")).getTime()));
		//aggiunto sulla v4
		params.put("N_COMP_FAM", null);
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);
		params.put("PROVENIENZA", this.getProvenienza());

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE ID_ORIG_OGG_RSU=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}
