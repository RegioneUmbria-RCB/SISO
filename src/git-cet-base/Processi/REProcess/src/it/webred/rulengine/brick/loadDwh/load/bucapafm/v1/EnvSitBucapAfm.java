package it.webred.rulengine.brick.loadDwh.load.bucapafm.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;



import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitBucapAfm extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 52;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitBucapAfm(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
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
		
		params.put("NUMERO_WORK_FLOW__VERIFICA", rs.getString("NUMERO_WORK_FLOW__VERIFICA"));
		params.put("VIA", rs.getString("VIA"));
		params.put("SEDE", rs.getString("SEDE"));
		params.put("ANNO_WORKFLOW", rs.getString("ANNO_WORKFLOW"));
		params.put("DESCRIZIONE_SEDE", rs.getString("DESCRIZIONE_SEDE"));
		params.put("NUMERO_PG_DOCUMENTO", rs.getString("NUMERO_PG_DOCUMENTO"));
		params.put("ESIBENTE", rs.getString("ESIBENTE"));
		params.put("CODICE_CLASSIFICAZIONE", rs.getString("CODICE_CLASSIFICAZIONE"));
		params.put("ANNO_WORK_FLOW__VERIFICA", rs.getString("ANNO_WORK_FLOW__VERIFICA"));
		params.put("DS_CODICE_CLASSIFICAZIONE", rs.getString("DS_CODICE_CLASSIFICAZIONE"));
		params.put("NUMERO_CIVICO", rs.getString("NUMERO_CIVICO"));
		params.put("ID_SCATOLA", rs.getString("ID_SCATOLA"));		
		params.put("PIANO_DI_INTERVENTO", rs.getString("PIANO_DI_INTERVENTO"));
		params.put("NUMERO_WORKFLOW", rs.getString("NUMERO_WORKFLOW"));
		params.put("ANNO_PG_DOCUMENTO", rs.getString("ANNO_PG_DOCUMENTO"));
		params.put("DATI_CATASTALI", rs.getString("DATI_CATASTALI"));
		params.put("PERIODO_ARCHIVIO", rs.getString("PERIODO_ARCHIVIO"));
		params.put("DESCRIZIONE_PERIODO_ARCHIVIO", rs.getString("DESCRIZIONE_PERIODO_ARCHIVIO"));
		params.put("CODICE_SETTORE", rs.getString("CODICE_SETTORE"));
		params.put("DESCRIZIONE_CODICE_SETTORE", rs.getString("DESCRIZIONE_CODICE_SETTORE"));
		params.put("OGGETTO_VISURA", rs.getString("OGGETTO_VISURA"));
		params.put("NM_FALDONE", rs.getString("NM_FALDONE"));
		params.put("NM_PRATICA", rs.getString("NM_PRATICA"));
		params.put("MAGAZZINO", rs.getString("MAGAZZINO"));
		params.put("SCAFFALE", rs.getString("SCAFFALE"));
		params.put("RIPIANO", rs.getString("RIPIANO"));
		params.put("BUCA", rs.getString("BUCA"));
		params.put("DATA_INSERIMENTO", rs.getString("DATA_INSERIMENTO"));
		params.put("FILA", rs.getString("FILA"));
		params.put("IMMAGINE_PRESENTE", rs.getString("IMMAGINE_PRESENTE"));
		params.put("DATA_ULTIMO_RIENTRO", rs.getString("DATA_ULTIMO_RIENTRO"));
		params.put("TIPO_PRATICA", rs.getString("TIPO_PRATICA"));
		params.put("RICHIEDENTE", rs.getString("RICHIEDENTE"));
		params.put("DESCRIZIONE_RICHIEDENTE", rs.getString("DESCRIZIONE_RICHIEDENTE"));
		params.put("COD_VIA", rs.getString("COD_VIA"));
		
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
				+ "WHERE ID_SCATOLA = ? AND DT_EXP_DATO = ? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}