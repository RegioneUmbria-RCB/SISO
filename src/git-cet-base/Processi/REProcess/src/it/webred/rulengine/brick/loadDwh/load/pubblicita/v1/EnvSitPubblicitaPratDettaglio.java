package it.webred.rulengine.brick.loadDwh.load.pubblicita.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitPubblicitaPratDettaglio extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 27;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitPubblicitaPratDettaglio(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
	//	String codice = rs.getString("TIPO_PRATICA")+"|"+rs.getString("NUM_PRATICA")+"|"+rs.getString("ANNO_PRATICA")+"|"+rs.getString("COD_CLS")+"|"+rs.getString("COD_OGGETTO");
		
		params.put("ID_ORIG", null);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		
		params.put("TIPO_PRATICA", rs.getString("TIPO_PRATICA"));
		params.put("NUM_PRATICA", rs.getInt("NUM_PRATICA"));
		params.put("ANNO_PRATICA", rs.getString("ANNO_PRATICA"));
		params.put("COD_CLS", rs.getString("COD_CLS"));
		params.put("DESC_CLS", rs.getString("DESC_CLS"));
		
		params.put("COD_OGGETTO", rs.getString("COD_OGGETTO"));
		params.put("DESC_OGGETTO", rs.getString("DESC_OGGETTO"));
		
		params.put("ANNOTAZIONI", rs.getString("ANNOTAZIONI"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("VIA", rs.getString("VIA"));
		params.put("CIVICO", rs.getString("CIVICO"));
		
		params.put("DT_INIZIO", rs.getObject("DT_INIZIO") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_INIZIO")).getTime()));
		params.put("DT_FINE",   rs.getObject("DT_FINE") == null ? null : new Timestamp(SDF.parse(rs.getString("DT_FINE")).getTime()));
		
		params.put("GIORNI_OCCUPAZIONE", rs.getInt("GIORNI_OCCUPAZIONE"));
		
		params.put("COD_ZONA_CESPITE", rs.getString("COD_ZONA_CESPITE"));
		params.put("DESC_ZONA_CESPITE", rs.getString("DESC_ZONA_CESPITE"));
	
		params.put("BASE", rs.getObject("BASE") == null ? null : new BigDecimal("" + DF.parse(rs.getString("BASE")).doubleValue()));
		params.put("ALTEZZA", rs.getObject("ALTEZZA") == null ? null : new BigDecimal("" + DF.parse(rs.getString("ALTEZZA")).doubleValue()));
		params.put("SUP_IMPONIBILE", rs.getObject("SUP_IMPONIBILE") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SUP_IMPONIBILE")).doubleValue()));
		params.put("SUP_ESISTENTE", rs.getObject("SUP_ESISTENTE") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SUP_ESISTENTE")).doubleValue()));
		
		params.put("COD_CARATTERISTICA", rs.getString("COD_CARATTERISTICA"));
		params.put("DESC_CARATTERISTICA", rs.getString("DESC_CARATTERISTICA"));
		
		params.put("NUM_ESEMPLARI", rs.getInt("NUM_ESEMPLARI"));
		params.put("NUM_FACCE", rs.getInt("NUM_FACCE"));
		
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
		return null;
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}

}
	

