package it.webred.rulengine.brick.loadDwh.load.tributi.v3;

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

public class EnvSitTIciVia extends EnvInsertDwh {
	
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
	
	public EnvSitTIciVia(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("ID_ORIG_VIA"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("ID_ORIG_VIA_U", rs.getString("ID_ORIG_VIA_U"));
		params.put("PREFISSO", rs.getString("PREFISSO"));
		params.put("DESCRIZIONE", rs.getString("DESCRIZIONE"));
		params.put("PREFISSO_ALT", rs.getString("PREFISSO_ALT"));
		params.put("DESCRIZIONE_ALT", rs.getString("DESCRIZIONE_ALT"));
		params.put("TIPO", rs.getString("TIPO"));
		params.put("DT_INI", rs.getObject("DT_INI") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_INI")).getTime()));
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
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE ID_ORIG_VIA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}
