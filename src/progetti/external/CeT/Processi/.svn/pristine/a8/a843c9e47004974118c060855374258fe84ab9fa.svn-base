package it.webred.rulengine.brick.loadDwh.load.bonificiBancari.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;


import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class EnvSitBonBan extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitBonBan(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = "";
		
		//String chiave = null;
		
		/* non si trova una chiave univoca!!!
		 * anche facendo distinct con tutti i campi otteniamo un numero di record inferiori a quelli inseriti
		 * select distinct * from re_bon_ban --- per il 2005 risultano 1334/1355	
		 * rs.getString("TIPOLOGIA_FORNITURA");
		chiave+= "|" + rs.getString("ANNO_RIFERIMENTO");
		chiave+= "|" + rs.getString("IDENTIFICATIVO_UTENZA");
		chiave+= "|" + rs.getString("N_MESI_FATTURAZIONE");
		*/
		
		chiave+= rs.getString("NUMERO_BONIFICO");
		chiave+= "|" + rs.getString("CODICE_FISCALE_ORDINANTE");
		chiave+= "|" + rs.getString("DATA_DISPOSIZIONE");
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", new BigDecimal(41) );
		String tipoRecord = rs.getString("TIPO_RECORD");
		params.put("TIPO_RECORD", tipoRecord!=null?new BigDecimal(tipoRecord):null );
		params.put("TIPOLOGIA_FORNITURA", rs.getString("TIPOLOGIA_FORNITURA") );
		String annoRiferimento = rs.getString("ANNO_RIFERIMENTO");
		params.put("ANNO_RIFERIMENTO", annoRiferimento!=null?new BigDecimal(annoRiferimento):null);
		params.put("CODICE_CATASTALE_BENEFICIARIO", rs.getString("CODICE_CATASTALE_BENEFICIARIO") );
		params.put("CODICE_FISCALE_BENEFICIARIO", rs.getString("CODICE_FISCALE_BENEFICIARIO"));
		params.put("ESITO_VALIDAZIONE_CF", rs.getString("ESITO_VALIDAZIONE_CF") );
		params.put("ABI", rs.getString("ABI") );
		params.put("CAB", rs.getString("CAB") );
		params.put("NUMERO_BONIFICO", rs.getString("NUMERO_BONIFICO"));
		String strDataDisposizione = rs.getString("DATA_DISPOSIZIONE");			//formato nel file gg/mm/aaaa
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = dateFormat.parse(strDataDisposizione);
		Timestamp tDataDisposizione = new Timestamp(date.getTime());
		params.put("DATA_DISPOSIZIONE", tDataDisposizione);
		String numeroSoggettiOrdinanti = rs.getString("NUMERO_SOGGETTI_ORDINANTI");
		params.put("NUMERO_SOGGETTI_ORDINANTI", numeroSoggettiOrdinanti!=null?new BigDecimal(numeroSoggettiOrdinanti):null );
		params.put("CODICE_FISCALE_ORDINANTE", rs.getString("CODICE_FISCALE_ORDINANTE") );
		params.put("CODICE_FISCALE_AMMINISTRATORE", rs.getString("CODICE_FISCALE_AMMINISTRATORE") );
		params.put("VALUTA", rs.getString("VALUTA") );
		String importoComplessivo = rs.getString("IMPORTO_COMPLESSIVO");
		Double dblImp = 0d;
		if (importoComplessivo != null && !importoComplessivo.trim().equalsIgnoreCase("")){
			dblImp = new Double(importoComplessivo);
			dblImp = dblImp / 100;
		}
		params.put("IMPORTO_COMPLESSIVO", dblImp!=null && dblImp > 0d? new BigDecimal(dblImp).setScale(2, BigDecimal.ROUND_HALF_EVEN):null );
		params.put("NORMATIVA_RIFERIMENTO", rs.getString("NORMATIVA_RIFERIMENTO") );
		params.put("FINE_RIGA", rs.getString("FINE_RIGA") );
				
//		int consumo = -1;
//		val = null;
//		try {
//			val = rs.getString("CONSUMO_FATTURATO");
//			BigDecimal o = new BigDecimal(val);	
//			consumo = o != null ? o.intValue() : -1;
//		} catch (Exception e) {}
//		params.put("CONSUMO_FATTURATO", consumo);
		
		params.put("PROVENIENZA", this.getProvenienza());

		params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
		params.put("DT_INI_VAL_DATO", null);
		params.put("DT_FINE_VAL_DATO", null);
		params.put("FLAG_DT_VAL_DATO", new BigDecimal(0) );

		ret.add(new RigaToInsert(params));
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' "
				+ "WHERE NUMERO_BONIFICO = ? AND CODICE_FISCALE_ORDINANTE = ? AND DATA_DISPOSIZIONE = ? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
