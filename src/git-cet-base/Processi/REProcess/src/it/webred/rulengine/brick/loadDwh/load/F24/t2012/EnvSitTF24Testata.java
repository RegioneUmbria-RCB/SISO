package it.webred.rulengine.brick.loadDwh.load.F24.t2012;

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
import it.webred.utils.MathUtils;

public class EnvSitTF24Testata extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 33;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitTF24Testata(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				
		params.put("ID_ORIG", rs.getString("CHIAVE"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("DT_FORNITURA",rs.getString("DT_FORNITURA") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_FORNITURA")).getTime()));
		params.put("PROGRESSIVO",rs.getBigDecimal("PROGRESSIVO"));
		params.put("COD_VALUTA",rs.getString("COD_VALUTA"));
		params.put("BELFIORE",rs.getString("BELFIORE"));
		params.put("COD_INTERMEDIARIO","COD_INTERMEDIARIO");
		params.put("ID_FILE",rs.getString("ID_FILE"));
		params.put("NUM_G1",rs.getBigDecimal("NUM_G1"));
		params.put("NUM_G2",rs.getBigDecimal("NUM_G2"));
		params.put("NUM_G3",rs.getBigDecimal("NUM_G3"));
		params.put("NUM_G4",rs.getBigDecimal("NUM_G4"));
		params.put("NUM_G5",rs.getBigDecimal("NUM_G5"));
		params.put("NUM_G9",rs.getBigDecimal("NUM_G9"));
		params.put("NUM_TOT",rs.getBigDecimal("NUM_TOT"));
		
		params.put("PROVENIENZA", this.getProvenienza());
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);

		ret.add(new RigaToInsert(params));

		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE CHIAVE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}

