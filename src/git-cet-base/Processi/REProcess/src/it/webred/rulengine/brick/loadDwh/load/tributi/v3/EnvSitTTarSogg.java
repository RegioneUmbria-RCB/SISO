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

public class EnvSitTTarSogg extends EnvInsertDwh {
	
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
	
	public EnvSitTTarSogg(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		params.put("ID_ORIG", rs.getString("ID_ORIG_SOGG"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("ID_ORIG_SOGG_U", rs.getString("ID_ORIG_SOGG_U"));		
		params.put("COD_FISC", rs.getString("COD_FISC"));
		params.put("PART_IVA", rs.getString("PART_IVA"));
		params.put("COG_DENOM", rs.getString("COG_DENOM"));
		params.put("NOME", rs.getString("NOME"));
		params.put("SESSO", rs.getString("SESSO"));
		params.put("TIP_SOGG", rs.getString("TIP_SOGG"));
		params.put("DT_NSC", rs.getObject("DT_NSC") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_NSC")).getTime()));
		params.put("COD_IST_CMN_NSC", rs.getString("COD_IST_CMN_NSC"));
		params.put("COD_BLFR_CMN_NSC", rs.getString("COD_BLFR_CMN_NSC"));
		params.put("COD_CMN_NSC", rs.getString("COD_CMN_NSC"));
		params.put("DES_COM_NSC", rs.getString("DES_COM_NSC"));
		params.put("CAP_COM_NSC", rs.getString("CAP_COM_NSC"));
		params.put("SIGLA_PROV_NSC", rs.getString("SIGLA_PROV_NSC"));
		params.put("DES_PROV_NSC", rs.getString("DES_PROV_NSC"));
		params.put("COD_STATO_NSC", rs.getString("COD_STATO_NSC"));
		params.put("DES_STATO_NSC", rs.getString("DES_STATO_NSC"));
		params.put("COD_IST_CMN_RES", rs.getString("COD_IST_CMN_RES"));
		params.put("COD_BLFR_CMN_RES", rs.getString("COD_BLFR_CMN_RES"));
		params.put("COD_CMN_RES", rs.getString("COD_CMN_RES"));
		params.put("DES_COM_RES", rs.getString("DES_COM_RES"));
		params.put("CAP_COM_RES", rs.getString("CAP_COM_RES"));
		params.put("SIGLA_PROV_RES", rs.getString("SIGLA_PROV_RES"));
		params.put("DES_PROV_RES", rs.getString("DES_PROV_RES"));
		params.put("COD_STATO_RES", rs.getString("COD_STATO_RES"));
		params.put("DES_STATO_RES", rs.getString("DES_STATO_RES"));
		params.put("DES_IND", rs.getString("DES_IND"));
		params.put("ID_ORIG_VIA", rs.getString("ID_ORIG_VIA"));
		params.put("NUM_CIV", rs.getString("NUM_CIV"));
		params.put("ESP_CIV", rs.getString("ESP_CIV"));		
		params.put("SCALA", rs.getString("SCALA"));
		params.put("PIANO", rs.getString("PIANO"));
		params.put("INTERNO", rs.getString("INTERNO"));	
		params.put("IND_RES_EXT", rs.getString("IND_RES_EXT"));
		params.put("NUM_CIV_EXT", rs.getString("NUM_CIV_EXT"));	
		params.put("TMS_AGG", rs.getObject("TMS_AGG") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_AGG")).getTime()));
		params.put("FLG_TRF", rs.getString("FLG_TRF"));
		params.put("TMS_BON", rs.getObject("TMS_BON") == null ? null : new Timestamp(SDF_TMS.parse(rs.getString("TMS_BON")).getTime()));
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
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE ID_ORIG_SOGG=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}
