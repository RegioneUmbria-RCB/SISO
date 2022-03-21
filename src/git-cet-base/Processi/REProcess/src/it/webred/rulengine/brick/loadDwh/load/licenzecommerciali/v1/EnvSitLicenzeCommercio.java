package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.v1;

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

public class EnvSitLicenzeCommercio extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 13;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitLicenzeCommercio(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();

			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("ID_ORIG", rs.getString("CHIAVE"));
			params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
			params.put("NUMERO", rs.getString("NUMERO"));
			params.put("NUMERO_PROTOCOLLO", rs.getString("NUMERO_PROTOCOLLO"));
			params.put("ANNO_PROTOCOLLO", rs.getString("ANNO_PROTOCOLLO"));
			params.put("TIPOLOGIA", rs.getString("TIPOLOGIA"));
			params.put("CARATTERE", rs.getString("CARATTERE"));
			params.put("STATO", rs.getString("STATO"));
			params.put("DATA_INIZIO_SOSPENSIONE", rs.getObject("DATA_INIZIO_SOSPENSIONE") == null ? null : 
						new Timestamp(SDF.parse(rs.getString("DATA_INIZIO_SOSPENSIONE")).getTime()));
			params.put("DATA_FINE_SOSPENSIONE", rs.getObject("DATA_FINE_SOSPENSIONE") == null ? null : 
						new Timestamp(SDF.parse(rs.getString("DATA_FINE_SOSPENSIONE")).getTime()));
			params.put("ID_ORIG_VIE", rs.getString("CODICE_VIA"));
			params.put("CIVICO", rs.getString("CIVICO"));
			params.put("CIVICO2", rs.getString("CIVICO2"));
			params.put("CIVICO3", rs.getString("CIVICO3"));
			params.put("SUPERFICIE_MINUTO", rs.getObject("SUPERFICIE_MINUTO") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SUPERFICIE_MINUTO")).doubleValue()));
			params.put("SUPERFICIE_TOTALE", rs.getObject("SUPERFICIE_TOTALE") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SUPERFICIE_TOTALE")).doubleValue()));
			params.put("SEZIONE_CATASTALE", rs.getString("SEZIONE_CATASTALE"));
			params.put("FOGLIO", rs.getString("FOGLIO"));
			params.put("PARTICELLA", rs.getString("PARTICELLA"));
			params.put("SUBALTERNO", rs.getString("SUBALTERNO"));
			params.put("CODICE_FABBRICATO", rs.getString("CODICE_FABBRICATO"));
			params.put("DATA_VALIDITA", rs.getObject("DATA_VALIDITA") == null ? null : 
						new Timestamp(SDF.parse(rs.getString("DATA_VALIDITA")).getTime()));
			params.put("DATA_RILASCIO", rs.getObject("DATA_RILASCIO") == null ? null : 
						new Timestamp(SDF.parse(rs.getString("DATA_RILASCIO")).getTime()));
			params.put("ZONA", rs.getString("ZONA"));
			params.put("RAG_SOC", rs.getString("RAG_SOC"));
			params.put("NOTE", rs.getString("NOTE"));
			params.put("RIFERIM_ATTO", rs.getString("RIFERIM_ATTO"));		
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

