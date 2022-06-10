package it.webred.rulengine.brick.loadDwh.load.sciajpe.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;




import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitSciaJpe extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 53;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitSciaJpe(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
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
		
		//non è stata rilevata alcuna chiave primaria nella tabella BUCAP_AFM
		params.put("ID_ORIG", null);
		params.put("FK_ENTE_SORGENTE", new BigDecimal(FK_ENTE_SORGENTE));
		
		params.put("NUMERO_PRATICA", rs.getString("NUMERO_PRATICA"));
		params.put("DATA_APERTURA", rs.getString("DATA_APERTURA"));
		params.put("DATA_CHIUSURA", rs.getString("DATA_CHIUSURA"));
		params.put("MACROTIPO", rs.getString("MACROTIPO"));
		params.put("PROCEDIMENTO", rs.getString("PROCEDIMENTO"));
		params.put("OGGETTO", rs.getString("OGGETTO"));
		params.put("DATA_PROTOCOLLO", rs.getString("DATA_PROTOCOLLO"));
		params.put("NUMERO_PROTOCOLLO", rs.getString("NUMERO_PROTOCOLLO"));
		params.put("REFERENTI", rs.getString("REFERENTI"));
		params.put("UBICAZIONI", rs.getString("UBICAZIONI"));
		params.put("FASCICOLO", rs.getString("FASCICOLO"));
		params.put("DATI_CATASTALI_TERRENI", rs.getString("DATI_CATASTALI_TERRENI"));		
		params.put("DATI_CATASTALI_FABBRICATI", rs.getString("DATI_CATASTALI_FABBRICATI"));
		params.put("TIPO_OPERA", rs.getString("TIPO_OPERA"));
		params.put("RESPONSABILI", rs.getString("RESPONSABILI"));
		params.put("TECNICI", rs.getString("TECNICI"));
		params.put("SITUAZIONE_PRATICA", rs.getString("SITUAZIONE_PRATICA"));
		params.put("GG_ISTRUTTORIA", rs.getString("GG_ISTRUTTORIA"));
		params.put("GG_PROP_MOTIV", rs.getString("GG_PROP_MOTIV"));
		params.put("GG_INTERRUZIONE", rs.getString("GG_INTERRUZIONE"));
		params.put("GG_SOSPENSIONE", rs.getString("GG_SOSPENSIONE"));

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