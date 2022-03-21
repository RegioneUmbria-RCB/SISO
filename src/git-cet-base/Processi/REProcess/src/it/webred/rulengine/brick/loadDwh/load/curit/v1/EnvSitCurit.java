package it.webred.rulengine.brick.loadDwh.load.curit.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class EnvSitCurit extends EnvInsertDwh {
	
	protected static final int FK_ENTE_SORGENTE = 49;
	protected static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public EnvSitCurit(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = rs.getString("IDENTIFICATIVO_IMPIANTO");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", new BigDecimal(FK_ENTE_SORGENTE));
		
		params.put("IDENTIFICATIVO_IMPIANTO", rs.getString("IDENTIFICATIVO_IMPIANTO"));
		String generatoriNumero = rs.getString("GENERATORI_NUMERO");
		params.put("GENERATORI_NUMERO", generatoriNumero != null ? new BigDecimal(generatoriNumero.replace(",", ".")) : null);
		String potenzaImpiantoRisc = rs.getString("POTENZA_IMPIANTO_RISC");
		params.put("POTENZA_IMPIANTO_RISC", potenzaImpiantoRisc != null ? new BigDecimal(potenzaImpiantoRisc.replace(",", ".")) : null);
		String potenzaImpiantoAcs = rs.getString("POTENZA_IMPIANTO_ACS");
		params.put("POTENZA_IMPIANTO_ACS", potenzaImpiantoAcs != null ? new BigDecimal(potenzaImpiantoAcs.replace(",", ".")) : null);
		String potenzaImpiantoRaff = rs.getString("POTENZA_IMPIANTO_RAFF");
		params.put("POTENZA_IMPIANTO_RAFF", potenzaImpiantoRaff != null ? new BigDecimal(potenzaImpiantoRaff.replace(",", ".")) : null);
		params.put("UBICAZIONE_TOPONIMO", rs.getString("UBICAZIONE_TOPONIMO"));
		params.put("UBICAZIONE_INDIRIZZO", rs.getString("UBICAZIONE_INDIRIZZO"));
		params.put("UBICAZIONE_CIVICO", rs.getString("UBICAZIONE_CIVICO"));
		params.put("UBICAZIONE_COMUNE", rs.getString("UBICAZIONE_COMUNE"));
		params.put("UBICAZIONE_PROVINCIA", rs.getString("UBICAZIONE_PROVINCIA"));
		params.put("UBICAZIONE_CAP", rs.getString("UBICAZIONE_CAP"));
		params.put("UBICAZIONE_CODICE_ISTAT", rs.getString("UBICAZIONE_CODICE_ISTAT"));
		params.put("CATASTO_SEZIONE", rs.getString("CATASTO_SEZIONE"));
		params.put("CATASTO_FOGLIO", rs.getString("CATASTO_FOGLIO"));
		params.put("CATASTO_PARTICELLA", rs.getString("CATASTO_PARTICELLA"));
		params.put("CATASTO_SUBALTERNO", rs.getString("CATASTO_SUBALTERNO"));
		params.put("EDIFICIO_CATEGORIA", rs.getString("EDIFICIO_CATEGORIA"));
		String volumetriaRisc = rs.getString("VOLUMETRIA_RISC");
		params.put("VOLUMETRIA_RISC", volumetriaRisc != null ? new BigDecimal(volumetriaRisc.replace(",", ".")) : null);
		String volumetriaRaff = rs.getString("VOLUMETRIA_RAFF");
		params.put("VOLUMETRIA_RAFF", volumetriaRaff != null ? new BigDecimal(volumetriaRaff.replace(",", ".")) : null);
		params.put("REGOLAZIONE", rs.getString("REGOLAZIONE"));
		params.put("CONTABILIZZAZIONE", rs.getString("CONTABILIZZAZIONE"));
		params.put("EMISSIONE", rs.getString("EMISSIONE"));
		params.put("APE_PRESENZA", rs.getString("APE_PRESENZA"));
		params.put("APE_CODICE", rs.getString("APE_CODICE"));
		params.put("GENERATORE_CATEGORIA", rs.getString("GENERATORE_CATEGORIA"));
		params.put("GENERATORE_PROGRESSIVO", rs.getString("GENERATORE_PROGRESSIVO"));
		String generatorePotenza = rs.getString("GENERATORE_POTENZA");
		params.put("GENERATORE_POTENZA", generatorePotenza != null ? new BigDecimal(generatorePotenza.replace(",", ".")) : null);
		params.put("GENERATORE_SERVIZI", rs.getString("GENERATORE_SERVIZI"));
		params.put("GENERATORE_FABBRICANTE", rs.getString("GENERATORE_FABBRICANTE"));
		params.put("GENERATORE_TIPOLOGIA", rs.getString("GENERATORE_TIPOLOGIA"));
		params.put("GENERATORE_COMBUSTIBILE", rs.getString("GENERATORE_COMBUSTIBILE"));
		String strGeneratoreDataInst = rs.getString("GENERATORE_DATA_INST");
		Date dtGeneratoreDataInst = strGeneratoreDataInst == null ? null : DF.parse(strGeneratoreDataInst);
		Timestamp tGeneratoreDataInst = dtGeneratoreDataInst == null ? null : new Timestamp(dtGeneratoreDataInst.getTime());
		params.put("GENERATORE_DATA_INST", tGeneratoreDataInst);
		String generatoreAnnoInst = rs.getString("GENERATORE_ANNO_INST");
		params.put("GENERATORE_ANNO_INST", generatoreAnnoInst != null ? new BigDecimal(generatoreAnnoInst.replace(",", ".")) : null);
		params.put("GENERATORE_TECNOLOGIA", rs.getString("GENERATORE_TECNOLOGIA"));
		String generatoreRendNom = rs.getString("GENERATORE_REND_NOM");
		params.put("GENERATORE_REND_NOM", generatoreRendNom != null ? new BigDecimal(generatoreRendNom.replace(",", ".")) : null);
		String strRapportoDiControlloData = rs.getString("RAPPORTO_DI_CONTROLLO_DATA");
		Date dtRapportoDiControlloData = strRapportoDiControlloData == null ? null : DF.parse(strRapportoDiControlloData);
		Timestamp tRapportoDiControlloData = dtRapportoDiControlloData == null ? null : new Timestamp(dtRapportoDiControlloData.getTime());
		params.put("RAPPORTO_DI_CONTROLLO_DATA", tRapportoDiControlloData);
		String rapportoDiControlloAnno = rs.getString("RAPPORTO_DI_CONTROLLO_ANNO");
		params.put("RAPPORTO_DI_CONTROLLO_ANNO", rapportoDiControlloAnno != null ? new BigDecimal(rapportoDiControlloAnno.replace(",", ".")) : null);
		params.put("RAP_DI_CONTROLLO_ESITO", rs.getString("RAP_DI_CONTROLLO_ESITO"));
		String rapportoDiControlloRendimento = rs.getString("RAP_DI_CONTROLLO_RENDIMENTO");
		params.put("RAP_DI_CONTROLLO_RENDIMENTO", rapportoDiControlloRendimento != null ? new BigDecimal(rapportoDiControlloRendimento.replace(",", ".")) : null);
		params.put("RAP_DI_CONTROLLO_BACHARACH", rs.getString("RAP_DI_CONTROLLO_BACHARACH"));
		params.put("ISPEZIONE_AUT_COMP", rs.getString("ISPEZIONE_AUT_COMP"));		
		String strIspezioneData = rs.getString("ISPEZIONE_DATA");
		Date dtIspezioneData = strIspezioneData == null ? null : DF.parse(strIspezioneData);
		Timestamp tIspezioneData = dtIspezioneData == null ? null : new Timestamp(dtIspezioneData.getTime());
		params.put("ISPEZIONE_DATA", tIspezioneData);
		params.put("ISPEZIONE_ESITO", rs.getString("ISPEZIONE_ESITO"));
		String strDtFileCsv = rs.getString("DT_FILE_CSV");
		Date dtDtFileCsv = strDtFileCsv == null ? null : new SimpleDateFormat("yyyyMMdd").parse(strDtFileCsv);
		Timestamp tDtFileCsv = dtDtFileCsv == null ? null : new Timestamp(dtDtFileCsv.getTime());
		params.put("DT_FILE_CSV", tDtFileCsv);
		
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
				+ "WHERE IDENTIFICATIVO_IMPIANTO = ? AND DT_EXP_DATO = ? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}