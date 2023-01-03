package it.webred.rulengine.brick.loadDwh.load.schedemartelli.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;


import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitSchedeMartelli extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 55;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitSchedeMartelli(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		/*
		 * params.put("ID_ORIG", null);
		 * 
		 * chiave+= rs.getString("NUMERO_BONIFICO");
		chiave+= "|" + rs.getString("CODICE_FISCALE_ORDINANTE");
		chiave+= "|" + rs.getString("DATA_DISPOSIZIONE");
		 * 
		 */
		
		//non è stata rilevata alcuna chiave primaria nella tabella SCHEDE_MARTELLI
		params.put("ID_ORIG", null); 
		//params.put("ID_ORIG", rs.getString("ID") ); 
		params.put("FK_ENTE_SORGENTE", new BigDecimal(FK_ENTE_SORGENTE));
		
		params.put("DS_SERIE_DOCUMENTALE", rs.getString("DS_SERIE_DOCUMENTALE"));
		params.put("FASCICOLO", rs.getString("FASCICOLO"));
		params.put("TIPO", rs.getString("TIPO"));
		params.put("TOPONIMO", rs.getString("TOPONIMO"));
		params.put("CIVICO", rs.getString("CIVICO"));
		params.put("DESCRIZIONE", rs.getString("DESCRIZIONE"));
		params.put("CLASSIFICA", rs.getString("CLASSIFICA"));
		params.put("SEQUENZIALE", rs.getString("SEQUENZIALE"));
		params.put("MULTIPLO", rs.getString("MULTIPLO"));
		params.put("NM_FALDONE", rs.getString("NM_FALDONE"));
		params.put("NM_PRATICA", rs.getString("NM_PRATICA"));
		params.put("NOME_FILE", rs.getString("NOME_FILE"));

		//String generatoriNumero = rs.getString("GENERATORI_NUMERO");
		//params.put("GENERATORI_NUMERO", generatoriNumero != null ? new BigDecimal(generatoriNumero.replace(",", ".")) : null);
		
		//String strGeneratoreDataInst = rs.getString("GENERATORE_DATA_INST");
		//Date dtGeneratoreDataInst = strGeneratoreDataInst == null ? null : DF.parse(strGeneratoreDataInst);
		//Timestamp tGeneratoreDataInst = dtGeneratoreDataInst == null ? null : new Timestamp(dtGeneratoreDataInst.getTime());
		//params.put("GENERATORE_DATA_INST", tGeneratoreDataInst);
		
		params.put("PROVENIENZA", this.getProvenienza());
		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INIZIO_DATO", null);
		params.put("DT_FINE_DATO", null);
		params.put("FLAG_DT_VAL_DATO", new BigDecimal(0));

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' "
				+ "WHERE NUMERO_PRATICA = ? AND DT_EXP_DATO = ? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}