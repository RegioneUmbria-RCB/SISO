package it.webred.rulengine.brick.loadDwh.load.cosap.v1;

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

public class EnvSitTCosapTassa extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 14;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitTCosapTassa(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("ID_ORIG", null);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("COD_UNIVOCO_CANONE", rs.getString("COD_UNIVOCO_CANONE"));
		params.put("ID_ORIG_CONTRIB", rs.getString("COD_CONTRIBUENTE"));
		params.put("TIPO_DOCUMENTO", rs.getString("TIPO_DOCUMENTO"));
		params.put("NUMERO_DOCUMENTO", rs.getString("NUMERO_DOCUMENTO"));
		params.put("ANNO_DOCUMENTO", rs.getString("ANNO_DOCUMENTO"));
		params.put("STATO_DOCUMENTO", rs.getString("STATO_DOCUMENTO"));
		params.put("NUMERO_PROTOCOLLO", rs.getString("NUMERO_PROTOCOLLO"));
		params.put("ANNO_CONTABILE_DOCUMENTO", rs.getString("ANNO_CONTABILE_DOCUMENTO"));
		params.put("DT_PROTOCOLLO", rs.getObject("DT_PROTOCOLLO") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_PROTOCOLLO")).getTime()));
		params.put("DT_INI_VALIDITA", rs.getObject("DT_INI_VALIDITA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_INI_VALIDITA")).getTime()));
		params.put("DT_FIN_VALIDITA", rs.getObject("DT_FIN_VALIDITA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_FIN_VALIDITA")).getTime()));
		params.put("DT_RICHIESTA", rs.getObject("DT_RICHIESTA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_RICHIESTA")).getTime()));
		params.put("TIPO_OCCUPAZIONE", rs.getString("TIPO_OCCUPAZIONE"));
		params.put("CODICE_IMMOBILE", rs.getString("CODICE_IMMOBILE"));
		params.put("CODICE_VIA", rs.getString("CODICE_VIA"));
		params.put("SEDIME", rs.getString("SEDIME"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("CIVICO", rs.getString("CIVICO"));
		params.put("ZONA", rs.getString("ZONA"));
		params.put("FOGLIO", rs.getObject("FOGLIO") == null ? null : new BigDecimal(DF.parse(rs.getString("FOGLIO")).doubleValue()));
		params.put("PARTICELLA", rs.getString("PARTICELLA"));
		params.put("SUBALTERNO", rs.getObject("SUBALTERNO") == null ? null : new BigDecimal(DF.parse(rs.getString("SUBALTERNO")).doubleValue()));
		params.put("QUANTITA", rs.getObject("QUANTITA") == null ? null : new BigDecimal("" + DF.parse(rs.getString("QUANTITA")).doubleValue()));
		params.put("UNITA_MISURA_QUANTITA", rs.getString("UNITA_MISURA_QUANTITA"));
		params.put("TARIFFA_PER_UNITA", rs.getObject("TARIFFA_PER_UNITA") == null ? null : new BigDecimal("" + DF.parse(rs.getString("TARIFFA_PER_UNITA")).doubleValue()));
		params.put("IMPORTO_CANONE", rs.getObject("IMPORTO_CANONE") == null ? null : new BigDecimal("" + DF.parse(rs.getString("IMPORTO_CANONE")).doubleValue()));
		params.put("DT_INI_VALIDITA_TARIFFA", rs.getObject("DT_INI_VALIDITA_TARIFFA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_INI_VALIDITA_TARIFFA")).getTime()));
		params.put("DT_FIN_VALIDITA_TARIFFA", rs.getObject("DT_FIN_VALIDITA_TARIFFA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_FIN_VALIDITA_TARIFFA")).getTime()));
		params.put("DESCRIZIONE", rs.getString("DESCRIZIONE"));
		params.put("NOTE", rs.getString("NOTE"));
		params.put("CODICE_ESENZIONE", rs.getString("CODICE_ESENZIONE"));
		params.put("SCONTO_ASSOLUTO", rs.getObject("SCONTO_ASSOLUTO") == null ? null : new BigDecimal("" + DF.parse(rs.getString("SCONTO_ASSOLUTO")).doubleValue()));
		params.put("PERCENTUALE_SCONTO", rs.getObject("PERCENTUALE_SCONTO") == null ? null : new BigDecimal("" + DF.parse(rs.getString("PERCENTUALE_SCONTO")).doubleValue()));
		params.put("DT_INI_SCONTO", rs.getObject("DT_INI_SCONTO") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_INI_SCONTO")).getTime()));
		params.put("DT_FIN_SCONTO", rs.getObject("DT_FIN_SCONTO") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_FIN_SCONTO")).getTime()));
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
	

