package it.webred.rulengine.brick.loadDwh.load.imf.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;






import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitImpiantiFognari extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 54;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitImpiantiFognari(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
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
		
		//non è stata rilevata alcuna chiave primaria nella tabella IMPIANTI_FOGNARI
		params.put("ID_ORIG", null); 
		//params.put("ID_ORIG", rs.getString("ID") ); 
		params.put("FK_ENTE_SORGENTE", new BigDecimal(FK_ENTE_SORGENTE));
		
		params.put("DS_SERIE_DOCUMENTALE", rs.getString("DS_SERIE_DOCUMENTALE"));
		params.put("TIPO_DOCUMENTO", rs.getString("TIPO_DOCUMENTO"));
		params.put("DESCRIZIONE_TIPO_DOCUMENTO", rs.getString("DESCRIZIONE_TIPO_DOCUMENTO"));
		params.put("NUMERO_BUSTA", rs.getString("NUMERO_BUSTA"));
		params.put("TIPO_BUSTA", rs.getString("TIPO_BUSTA"));
		params.put("TIPO_VIA", rs.getString("TIPO_VIA"));
		params.put("VIA", rs.getString("VIA"));
		params.put("CIVICO", rs.getString("CIVICO"));
		params.put("BARRATO", rs.getString("BARRATO"));
		params.put("CODICE_VIA", rs.getString("CODICE_VIA"));
		params.put("ZONA", rs.getString("ZONA"));
		params.put("ANNOTAZIONI", rs.getString("ANNOTAZIONI"));		
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