package it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu.v1;

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

public class EnvSitRuoloTarsu extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 39;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitRuoloTarsu(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		//String codice = rs.getString("ANNO")+"|"+rs.getString("TIPO")+"|"+rs.getString("CU");
		String codice = rs.getString("RID");
		
		params.put("ID_ORIG", codice);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		
		params.put("CU", rs.getInt("CU"));
		params.put("TIPO", rs.getString("TIPO"));
		params.put("ANNO", rs.getString("ANNO"));
		
		params.put("NOMINATIVO_CONTRIB", rs.getString("NOME1"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("CAP", rs.getString("CAP"));
		params.put("COMUNE", rs.getString("DEST"));
		params.put("PROV", rs.getString("PROV"));
		params.put("CODFISC", rs.getString("CODFISC"));
		params.put("ESTERO", rs.getString("ESTERO"));
		params.put("IBAN", rs.getString("IBAN"));
		params.put("DOM", rs.getString("DOM"));
		params.put("TOT_NETTO", getBD(rs.getString("TOT_NETTO")));
		params.put("PERC_ECA", getBD(rs.getString("PERC_ECA")));
		params.put("ADD_ECA", getBD(rs.getString("ADD_ECA")));
		params.put("PERC_MAGG_ECA", getBD(rs.getString("PERC_MAGG_ECA")));
		params.put("MAGG_ECA", getBD(rs.getString("MAGG_ECA")));
		params.put("PERC_TRIB_PROV", getBD(rs.getString("PERC_TRIB_PROV")));
		params.put("TRIB_PROV", getBD(rs.getString("TRIB_PROV")));
		params.put("SANZIONE", getBD(rs.getString("SANZIONE")));
		params.put("INTERESSI", getBD(rs.getString("INTERESSI")));
		params.put("TOTALE", getBD(rs.getString("TOTALE")));
		params.put("DATA_NOT", rs.getString("DATA_NOT"));
		params.put("NUM_PROVV", rs.getString("NUM_PROVV"));
		params.put("DATA_ACC", rs.getString("DATA_ACC"));
		params.put("IMPORTO_NOTIFICA", getBD(rs.getString("IMPORTO_NOTIFICA")));
		params.put("SUP_ANNO", rs.getString("SUP_ANNO"));
		params.put("SUP_ANNO_SUCC", rs.getString("SUP_ANNO_SUCC"));
		params.put("SGR_ANNO_SUCC", rs.getString("SGR_ANNO_SUCC"));
		params.put("ACCONTO_TARSU_ANNO", getBD( rs.getString("ACCONTO_TARSU_ANNO")));
		params.put("TIPO_DOC", rs.getString("TIPO_DOC"));
	
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