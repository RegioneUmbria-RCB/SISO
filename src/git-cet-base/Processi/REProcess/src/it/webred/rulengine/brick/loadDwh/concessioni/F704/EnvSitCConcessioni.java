package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

public class EnvSitCConcessioni extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitCConcessioni(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("")) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
			params.put("ID_ORIG", rs.getString("A"));
			params.put("FK_ENTE_SORGENTE", 3);
			params.put("CONCESSIONE_NUMERO", rs.getString("A"));
			
			String[] chiave = rs.getString("A").split("/");
			String numero = chiave[0];
			String anno = chiave.length == 2 ? chiave[1] : null;
			if (anno != null && !anno.trim().equals("")) {
				try {
					int intAnno = Integer.parseInt(anno);
					if (intAnno < previousCentury) {
						anno = "20" + anno;
					} else {
						anno = "19" + anno;
					}
				} catch (Exception e) {
					anno = null;
				}
			}
			
			params.put("PROGRESSIVO_NUMERO", numero);
			params.put("PROGRESSIVO_ANNO", anno);
			Timestamp protocolloData = null;			
			if (rs.getObject("C") != null && !rs.getString("C").trim().equals("")) {
				String c = rs.getString("C");
				try {
					protocolloData = new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(c).getTime());
				} catch (Exception e) {
					try {
						protocolloData = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(c.substring(0,10)).getTime());
					} catch (Exception e2) {}
				}						
			}
			params.put("PROTOCOLLO_DATA", protocolloData);
			params.put("PROTOCOLLO_NUMERO", rs.getString("A"));			
			params.put("TIPO_INTERVENTO", rs.getString("Q"));			
			params.put("OGGETTO", rs.getString("G"));			
			params.put("PROCEDIMENTO", null);			
			params.put("ZONA", null);
			Timestamp dataRilascio = null;			
			if (rs.getObject("B") != null && !rs.getString("B").trim().equals("")) {
				String b = rs.getString("B");
				try {
					dataRilascio = new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(b).getTime());
				} catch (Exception e) {
					try {
						protocolloData = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(b.substring(0,10)).getTime());
					} catch (Exception e2) {}
				}				
			}
			params.put("DATA_RILASCIO", dataRilascio);
			params.put("DATA_INIZIO_LAVORI", null);
			params.put("DATA_CHIUSURA", null);
			params.put("PROVENIENZA", "C");

			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);
			params.put("ESITO", null);

			ret.add(params);
		}
		
		return ret;
	}

	@Override
	public String getSqlUpdateFlagElaborato() {
		return null;
		
	}
	
	@Override
	public void executeSqlPostInsertRecord(Connection conn,
			LinkedHashMap<String, Object> currRecord) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
