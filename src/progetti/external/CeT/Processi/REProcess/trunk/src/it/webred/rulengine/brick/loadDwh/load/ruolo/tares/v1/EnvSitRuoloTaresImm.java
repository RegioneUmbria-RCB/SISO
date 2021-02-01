package it.webred.rulengine.brick.loadDwh.load.ruolo.tares.v1;

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

public class EnvSitRuoloTaresImm extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 40;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitRuoloTaresImm(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		
		//String codice = rs.getString("ANNO")+"|"+rs.getString("TIPO")+"|"+rs.getString("CU")+"|"+rs.getString("PROG");
		String codice = rs.getString("RID")+"|"+rs.getString("PROG");
		
		params.put("ID_ORIG", codice);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		
		params.put("ANNO", rs.getString("ANNO"));
		params.put("PROG", rs.getInt("PROG"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("TIPO", rs.getString("TIPO_IMM"));
		params.put("COD_TIPO", rs.getString("CODTIP"));
		params.put("DES_CAT", rs.getString("CAT"));
		params.put("COD_CAT", rs.getString("CODCAT"));
		params.put("RIF_CATASTO", rs.getString("RIFCAT"));
		params.put("MQ", getBD(rs.getString("MQ")));
		params.put("TARIFFA_QF", getBD(rs.getString("TARIFFA_QF")));
		params.put("TARIFFA_QV", getBD(rs.getString("TARIFFA_QV")));
		params.put("TARIFFA_QS", getBD(rs.getString("TARIFFA_QS")));
		params.put("RIDUZIONE_QF", getBD(rs.getString("RIDUZIONE_QF")));
		params.put("RIDUZIONE_QV", getBD(rs.getString("RIDUZIONE_QV")));
		params.put("RIDUZIONE_QS", getBD(rs.getString("RIDUZIONE_QS")));
		params.put("CAUSALE", rs.getString("CAUSALE"));
		params.put("IMPORTO", getBD(rs.getString("IMPORTO")));
		params.put("IMP_QF", getBD(rs.getString("IMP_QF")));
		params.put("IMP_QV", getBD(rs.getString("IMP_QV")));
		params.put("IMP_QS", getBD(rs.getString("IMP_QS")));
		params.put("COD_TRIBUTO", rs.getString("TRIBUTO"));
		params.put("PERIODO_DA", rs.getString("PERIODO_DA"));
		params.put("PERIODO_A", rs.getString("PERIODO_A"));
	
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", 0);


		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	private BigDecimal getBD(String s){
		BigDecimal bd = null;
		
		
		bd = s!=null ? new BigDecimal(s.replace(",",".")) : null;
		
		return bd;
	}
	
	@Override
	public String getSqlUpdateFlagElaborato() {
		return null; //"UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE ANNO=? AND CU=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}