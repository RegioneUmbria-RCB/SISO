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

public class EnvSitRuoloTares extends EnvInsertDwh {
	
	public static final int ID_ENTE_SORGENTE = 40;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	public static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public EnvSitRuoloTares(String nomeTabellaOrigine, String provenienza, String[] nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret = new ArrayList<RigaToInsert>();
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		//String codice = rs.getString("ANNO")+"|"+rs.getString("TIPO")+"|"+rs.getString("CU");
		String codice = rs.getString("RID");
		
		params.put("ID_ORIG", codice);
		params.put("FK_ENTE_SORGENTE", ID_ENTE_SORGENTE);
		
		params.put("CU", getBD(rs.getString("CU")));
		params.put("TIPO", rs.getString("TIPO"));
		params.put("ANNO", rs.getString("ANNO"));
		
		params.put("NOMINATIVO_CONTRIB", rs.getString("NOME1"));
		params.put("COGNOME_RAGSOC", rs.getString("COGNOME_RAGSOCIALE"));
		params.put("NOME", rs.getString("NOME"));
		params.put("CODFISC", rs.getString("CODFISC"));
		params.put("SESSO", rs.getString("SESSO"));
		params.put("NASCITADATA", rs.getString("NASCITADATA"));
		params.put("NASCITACOMU", rs.getString("NASCITACOMU"));
		params.put("NASCITAPROV", rs.getString("NASCITAPROV"));
		params.put("INDIRIZZO", rs.getString("INDIRIZZO"));
		params.put("CAP", rs.getString("CAP"));
		params.put("COMUNE", rs.getString("DEST"));
		params.put("PROV", rs.getString("PROV"));
		params.put("ESTERO", rs.getString("ESTERO"));
		params.put("NIMM", rs.getInt("NIMM"));
		params.put("SANZIONE", getBD(rs.getString("SANZIONE")));
		params.put("INTERESSI", getBD(rs.getString("INTERESSI")));
		params.put("DATA_NOT", rs.getString("DATA_NOT"));
		params.put("NUM_PROVV", rs.getString("NUM_PROVV"));
		params.put("DATA_ACC", rs.getString("DATA_ACC"));
		params.put("FLAG_ADESIONE", rs.getString("FLAG_ADESIONE"));
		params.put("NUMFATT", rs.getString("NUMFATT"));
		params.put("DATAFATT", rs.getString("DATAFATT"));
		params.put("IMPORTO_NOTIFICA", getBD(rs.getString("IMPORTO_NOTIFICA")));
		
		params.put("TOT_NETTO", getBD(rs.getString("TOT_NETTO")));
		params.put("PERC_IVA", getBD(rs.getString("PERC_IVA")));
		params.put("ADD_IVA", getBD(rs.getString("ADD_IVA")));
		params.put("PERC_TRIB_PROV", getBD(rs.getString("PERC_TRIB_PROV")));
		params.put("TRIB_PROV", getBD(rs.getString("TRIB_PROV")));
		params.put("TOTALE", getBD(rs.getString("TOTALE")));
		params.put("TOTALE_ENTE", getBD(rs.getString("TOTALE_ENTE")));
		params.put("TOTALE_STATO", getBD(rs.getString("TOTALE_STATO")));
		
		params.put("TRIBUTO_NETTO_COMUNE", getBD(rs.getString("TRIBUTO_NETTO_COMUNE")));
		params.put("IMPORTO_NETTO_ACCONTO", getBD(rs.getString("IMPORTO_NETTO_ACCONTO")));
		params.put("DIFFERENZA_NETTO", getBD(rs.getString("DIFFERENZA_NETTO")));
		params.put("TRIBUTO_PROVINCIALE", getBD(rs.getString("TRIBUTO_PROVINCIALE")));
		params.put("DIFFERENZA_LORDO", getBD(rs.getString("DIFFERENZA_LORDO")));
		params.put("ATT_PRO", getBD(rs.getString("ATT_PRO")));
		params.put("ATT_COMUNE", getBD(rs.getString("ATT_COMUNE")));
		params.put("OLD_PRO", getBD(rs.getString("OLD_PRO")));
		params.put("OLD_TOT", getBD(rs.getString("OLD_TOT")));
		params.put("TR_QUOTA_TIA", rs.getString("TR_QUOTA_TIA"));
	
		params.put("SUP_ANNO_PREC", rs.getString("SUP_ANNO_PREC"));
		params.put("SUP_ANNO", rs.getString("SUP_ANNO"));
		params.put("SGR_ANNO", rs.getString("SGR_ANNO"));
		
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