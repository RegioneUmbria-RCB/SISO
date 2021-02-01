package it.webred.rulengine.brick.loadDwh.load.pubblicita.v1;

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

public class EnvSitPubblicitaPratTestata extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 27;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitPubblicitaPratTestata(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String codice = rs.getString("TIPO_PRATICA")+"|"+rs.getString("NUM_PRATICA")+"|"+rs.getString("ANNO_PRATICA");
		
		params.put("ID_ORIG", codice);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		
		params.put("TIPO_PRATICA", rs.getString("TIPO_PRATICA"));
		params.put("NUM_PRATICA", rs.getInt("NUM_PRATICA"));
		params.put("ANNO_PRATICA", rs.getString("ANNO_PRATICA"));
		params.put("DESCRIZIONE", rs.getString("DESCRIZIONE"));
		params.put("DT_INIZIO", rs.getObject("DT_INIZIO") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_INIZIO")).getTime()));
		params.put("DT_DEC_TERMINI", rs.getObject("DT_DEC_TERMINI") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_DEC_TERMINI")).getTime()));
		params.put("PROVVEDIMENTO", rs.getString("PROVVEDIMENTO"));
		
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
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE TIPO_PRATICA=? AND NUM_PRATICA=? AND ANNO_PRATICA=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}