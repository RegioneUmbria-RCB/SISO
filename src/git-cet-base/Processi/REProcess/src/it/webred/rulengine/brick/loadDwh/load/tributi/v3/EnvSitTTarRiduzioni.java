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

public class EnvSitTTarRiduzioni extends EnvInsertDwh {
	
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
	
	public EnvSitTTarRiduzioni(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		params.put("ID_ORIG", null);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("ID_ORIG_OGG_RSU", rs.getString("ID_ORIG_OGG_RSU"));
		params.put("VAL_RID", rs.getObject("VAL_RID") == null ? null : new BigDecimal("" + DF.parse(rs.getString("VAL_RID")).doubleValue()));
		params.put("TIPO_RIDUZIONE", rs.getString("TIPO_RIDUZIONE"));
		params.put("DESCR_RIDUZIONE", rs.getString("DESCR_RIDUZIONE"));
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
		return null;
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}
