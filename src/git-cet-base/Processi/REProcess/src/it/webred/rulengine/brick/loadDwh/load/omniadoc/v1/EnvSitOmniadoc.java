package it.webred.rulengine.brick.loadDwh.load.omniadoc.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitOmniadoc extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 50;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitOmniadoc(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
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
		
		//non è stata rilevata alcuna chiave primaria nella tabella OMNIADOC
		params.put("ID_ORIG", null);
		params.put("FK_ENTE_SORGENTE", new BigDecimal(FK_ENTE_SORGENTE));
		
		params.put("TIPO_PROT_CAPOFILA", rs.getString("TIPO_PROT_CAPOFILA"));
		params.put("NR_PROT_CAPOFILA", rs.getString("NR_PROT_CAPOFILA"));
		params.put("ANNO_PROT_CAPOFILA", rs.getString("ANNO_PROT_CAPOFILA"));
		params.put("REGISTRO_PROT_DI_SETTORE", rs.getString("REGISTRO_PROT_DI_SETTORE"));
		params.put("SUB_NR_PROT", rs.getString("SUB_NR_PROT"));
		params.put("ANNO_PRAT_ONLYONE", rs.getString("ANNO_PRAT_ONLYONE"));
		params.put("NR_PRAT_ONLYONE", rs.getString("NR_PRAT_ONLYONE"));
		params.put("COD_VIA", rs.getString("COD_VIA"));
		params.put("NUM_CIVICO", rs.getString("NUM_CIVICO"));
		params.put("APPENDICE_CIVICO", rs.getString("APPENDICE_CIVICO"));
		params.put("TIPO_PROT_DOCUMENTO", rs.getString("TIPO_PROT_DOCUMENTO"));
		params.put("NUM_PROT_DOCUMENTO", rs.getString("NUM_PROT_DOCUMENTO"));
		params.put("ANNO_PROT_DOCUMENTO", rs.getString("ANNO_PROT_DOCUMENTO"));
		params.put("REGISTRO_PROT_SET_DOC", rs.getString("REGISTRO_PROT_SET_DOC"));
		params.put("SUB_NUM_PROT_DOCUMENTO", rs.getString("SUB_NUM_PROT_DOCUMENTO"));
		params.put("RICHIEDENTE_ISTANZA", rs.getString("RICHIEDENTE_ISTANZA"));
		params.put("FALDONE", rs.getString("FALDONE"));
		params.put("PDF_FILE", rs.getString("PDF_FILE"));
		params.put("F", rs.getString("F"));
		params.put("M", rs.getString("M"));
		params.put("S", rs.getString("S"));
		params.put("OGGETTO", rs.getString("OGGETTO"));
		params.put("SEGNALAZIONI", rs.getString("SEGNALAZIONI"));
		params.put("DESCRIZIONE_VIA", rs.getString("DESCRIZIONE_VIA"));
		params.put("PAGINE", rs.getString("PAGINE"));
		
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
				+ "WHERE NR_PROT_CAPOFILA = ? AND DT_EXP_DATO = ? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}