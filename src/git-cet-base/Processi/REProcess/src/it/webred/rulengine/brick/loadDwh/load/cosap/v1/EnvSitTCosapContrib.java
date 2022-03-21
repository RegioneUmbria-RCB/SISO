package it.webred.rulengine.brick.loadDwh.load.cosap.v1;

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

public class EnvSitTCosapContrib extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 14;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitTCosapContrib(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("ID_ORIG", rs.getString("COD_CONTRIBUENTE"));
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		params.put("TIPO_PERSONA", rs.getString("TIPO_PERSONA"));
		params.put("NOME", rs.getString("NOME"));
		params.put("COG_DENOM", rs.getString("COG_DENOM"));
		params.put("CODICE_FISCALE", rs.getString("CODICE_FISCALE"));
		params.put("PARTITA_IVA", rs.getString("PARTITA_IVA"));
		params.put("DT_NASCITA", rs.getObject("DT_NASCITA") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_NASCITA")).getTime()));
		params.put("COD_BELFIORE_NASCITA", rs.getString("COD_BELFIORE_NASCITA"));
		params.put("DESC_COMUNE_NASCITA", rs.getString("DESC_COMUNE_NASCITA"));
		params.put("COD_BELFIORE_RESIDENZA", rs.getString("COD_BELFIORE_RESIDENZA"));
		params.put("DESC_COMUNE_RESIDENZA", rs.getString("DESC_COMUNE_RESIDENZA"));
		params.put("CAP_RESIDENZA", rs.getString("CAP_RESIDENZA"));
		params.put("CODICE_VIA", rs.getString("CODICE_VIA"));
		params.put("SEDIME", rs.getString("SEDIME"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("CIVICO", rs.getString("CIVICO"));
		params.put("DT_ISCR_ARCHIVIO", rs.getObject("DT_ISCR_ARCHIVIO") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_ISCR_ARCHIVIO")).getTime()));
		params.put("DT_COSTIT_SOGGETTO", rs.getObject("DT_COSTIT_SOGGETTO") == null ? null : 
			new Timestamp(SDF.parse(rs.getString("DT_COSTIT_SOGGETTO")).getTime()));
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
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE COD_CONTRIBUENTE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
	}
	
}