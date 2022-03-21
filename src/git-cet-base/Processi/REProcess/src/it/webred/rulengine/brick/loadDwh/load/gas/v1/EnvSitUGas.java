package it.webred.rulengine.brick.loadDwh.load.gas.v1;

import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.bean.RigaToInsert;
import it.webred.rulengine.exception.RulEngineException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class EnvSitUGas extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitUGas(String nomeTabellaOrigine, String provenienza, String[]  nomeCampoChiave) {
		super(nomeTabellaOrigine, provenienza, nomeCampoChiave);
	}
	
	public ArrayList<RigaToInsert> getRigheSpec(ResultSet rs) throws Exception {
		
		ArrayList<RigaToInsert> ret =new ArrayList<RigaToInsert>();
				
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		String chiave = null;
			
		/* non si trova una chiave univoca!!!	rs.getString("TIPOLOGIA_FORNITURA");
		chiave+= "|" + rs.getString("ANNO_RIFERIMENTO");
		chiave+= "|" + rs.getString("IDENTIFICATIVO_UTENZA");
		chiave+= "|" + rs.getString("N_MESI_FATTURAZIONE");
		*/
		
		params.put("ID_ORIG", chiave);
		params.put("FK_ENTE_SORGENTE", 12);
		
		params.put("TIPOLOGIA_FORNITURA",rs.getString("TIPOLOGIA_FORNITURA") );
		String annoRiferimento = rs.getString("ANNO_RIFERIMENTO");
		params.put("ANNO_RIFERIMENTO", annoRiferimento);
		params.put("CODICE_CATASTALE_UTENZA",rs.getString("CODICE_CATASTALE_UTENZA") );
		params.put("CF_EROGANTE", rs.getString("CF_EROGANTE"));
		params.put("CF_TITOLARE_UTENZA",rs.getString("CF_TITOLARE_UTENZA") );
		String t=null;
			if ("0".equals(rs.getString("TIPO_SOGGETTO")))
				t="Persona Fisica";
			else if ("1".equals(rs.getString("TIPO_SOGGETTO")))
				t="Soggetto diverso da persona fisica";
			else if (rs.getString("TIPO_SOGGETTO") !=null)
				t=rs.getString("TIPO_SOGGETTO");
			
		params.put("TIPO_SOGGETTO",t );
		params.put("COGNOME_UTENTE",rs.getString("COGNOME_UTENTE") );
		params.put("NOME_UTENTE", rs.getString("NOME_UTENTE"));
		params.put("SESSO",rs.getString("SESSO") );
		params.put("DATA_NASCITA",rs.getString("DATA_NASCITA") );
		
		params.put("DESC_COMUNE_NASCITA",rs.getString("DESC_COMUNE_NASCITA") );
		params.put("SIGLA_PROV_NASCITA",rs.getString("SIGLA_PROV_NASCITA") );
		params.put("RAGIONE_SOCIALE",rs.getString("RAGIONE_SOCIALE") );
		params.put("IDENTIFICATIVO_UTENZA",rs.getString("IDENTIFICATIVO_UTENZA")!=null?rs.getString("IDENTIFICATIVO_UTENZA").toUpperCase():null );
		t=null;
		boolean is2011 = false;
		if (annoRiferimento != null) {
			try {
				is2011 = Integer.parseInt(annoRiferimento) >= 2011;
			} catch (Exception e) {}
		}
		if (!is2011) {
			if ("0".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza domestica con residenza anagrafica presso il luogo di fornitura";
			else if ("1".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza domestica con residenza anagrafica diversa dal luogo di fornitura";
			else if ("2".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza non domestica";
			else if ("3".equals(rs.getString("TIPO_UTENZA")))
				t="Grande Utenza";
			else if (rs.getString("TIPO_UTENZA") !=null)
				t=rs.getString("TIPO_UTENZA");
		} else {
			if ("1".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza domestica con residenza anagrafica presso il luogo di fornitura";
			else if ("2".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza domestica con residenza anagrafica diversa dal luogo di fornitura";
			else if ("3".equals(rs.getString("TIPO_UTENZA")))
				t="Utenza non domestica";
			else if ("4".equals(rs.getString("TIPO_UTENZA")))
				t="Grande Utenza";
			else if (rs.getString("TIPO_UTENZA") !=null)
				t=rs.getString("TIPO_UTENZA");
		}
		
		
		params.put("TIPO_UTENZA",t );
		params.put("INDIRIZZO_UTENZA",rs.getString("INDIRIZZO_UTENZA") );
		params.put("CAP_UTENZA",rs.getString("CAP_UTENZA") );
		
		String valore;
		String val=null;
		try {
			val = rs.getString("SPESA_CONSUMO_NETTO_IVA");
			BigDecimal o = new BigDecimal(val);	
			valore = o!=null?o.toString():null;
		} catch (Exception e) {
			valore = val;
		}
		
		params.put("SPESA_CONSUMO_NETTO_IVA", valore);
		params.put("N_MESI_FATTURAZIONE",rs.getString("N_MESI_FATTURAZIONE") );
		params.put("IDENTIFICATIVO_UTENZA",rs.getString("IDENTIFICATIVO_UTENZA") );
		params.put("SEGNO_AMMONT_FATTURATO",rs.getString("SEGNO_AMMONT_FATTURATO") );
		
		int ammont = -1;
		val = null;
		try {
			val = rs.getString("AMMONT_FATTURATO");
			BigDecimal o = new BigDecimal(val);	
			ammont = o != null ? o.intValue() : -1;
		} catch (Exception e) {}
		
		params.put("AMMONT_FATTURATO", ammont);
		
		int consumo = -1;
		val = null;
		try {
			val = rs.getString("CONSUMO_FATTURATO");
			BigDecimal o = new BigDecimal(val);	
			consumo = o != null ? o.intValue() : -1;
		} catch (Exception e) {}
		
		params.put("CONSUMO_FATTURATO", consumo);
		params.put("ESITO_CTRL_FORMALE",rs.getString("ESITO_CTRL_FORMALE") );
		params.put("ESITO_CTRL_FORMALE_QUAL",rs.getString("ESITO_CTRL_FORMALE_QUAL") );
		
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
		return "UPDATE " + getNomeTabellaOrigine() + " SET RE_FLAG_ELABORATO='1' WHERE TIPOLOGIA_FORNITURA=? AND ANNO_RIFERIMENTO=? AND IDENTIFICATIVO_UTENZA = ? AND N_MESI_FATTURAZIONE=? AND DT_EXP_DATO=? AND RE_FLAG_ELABORATO='0'";

		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
