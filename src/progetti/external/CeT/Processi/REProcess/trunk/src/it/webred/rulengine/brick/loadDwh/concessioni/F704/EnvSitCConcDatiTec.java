package it.webred.rulengine.brick.loadDwh.concessioni.F704;

import it.webred.rulengine.brick.superc.InsertDwh.EnvInsertDwh;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EnvSitCConcDatiTec extends EnvInsertDwh {
	
	private static final int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	public EnvSitCConcDatiTec(String nomeTabellaOrigine, String...  nomeCampoChiave) {
		super(nomeTabellaOrigine, nomeCampoChiave);
	}
	
	public ArrayList<LinkedHashMap<String, Object>> getRighe(ResultSet rs) throws Exception {
		
		ArrayList<LinkedHashMap< String, Object>> ret =new ArrayList<LinkedHashMap<String,Object>>();
		
		if (rs.getObject("A") != null && !rs.getString("A").trim().equals("")) {
			LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
			
			params.put("ID_ORIG", null);
			params.put("FK_ENTE_SORGENTE", 3);
			 //Concess@ aggiunto a mano perché la tabella non è multiprovenienza
			params.put("ID_ORIG_C_CONCESSIONI", "C@" + rs.getString("A"));
			params.put("DEST_USO", rs.getString("N"));
			String supEffLotto = rs.getObject("O") == null ? null : rs.getString("O").replace(",", ".");
			BigDecimal bdSupEffLotto = null;
			if (supEffLotto != null && !supEffLotto.trim().equals("")) {
				try {
					bdSupEffLotto = new BigDecimal(supEffLotto);
				} catch (Exception e) {}				
			}
			params.put("SUP_EFF_LOTTO", bdSupEffLotto);
			String supCoperta = rs.getObject("P") == null ? null : rs.getString("P").replace(",", ".");
			BigDecimal bdSupCoperta = null;
			if (supCoperta != null && !supCoperta.trim().equals("")) {
				try {
					bdSupCoperta = new BigDecimal(supCoperta);
				} catch (Exception e) {}				
			}			
			params.put("SUP_COPERTA", bdSupCoperta);
			String volTotale = rs.getObject("R") == null ? null : rs.getString("R").replace(",", ".");
			BigDecimal bdVolTotale = null;
			if (volTotale != null && !volTotale.trim().equals("")) {
				try {
					bdVolTotale = new BigDecimal(volTotale);
				} catch (Exception e) {}				
			}
			params.put("VOL_TOTALE", bdVolTotale);
			String vani = rs.getObject("T") == null ? null : rs.getString("T").replace(",", ".");
			Integer intVani = null;
			if (vani != null && !vani.trim().equals("")) {
				try {
					intVani = Integer.parseInt(vani);
				} catch (Exception e) {}				
			}
			params.put("VANI", intVani);
			String numAbitazioni = rs.getObject("S") == null ? null : rs.getString("S").replace(",", ".");
			Integer intNumAbitazioni = null;
			if (numAbitazioni != null && !numAbitazioni.trim().equals("")) {
				try {
					intNumAbitazioni = Integer.parseInt(numAbitazioni);
				} catch (Exception e) {}				
			}
			params.put("NUM_ABITAZIONI", intNumAbitazioni);
			String dtAgibilita = rs.getString("AI");
			Timestamp tsDtAgibilita = getTsDtAgibilita(dtAgibilita);
			params.put("DT_AGIBILITA", tsDtAgibilita);			
			params.put("DT_EXP_DATO", (Timestamp) altriParams[0]);
			params.put("DT_INI_VAL_DATO", null);
			params.put("DT_FINE_VAL_DATO", null);
			params.put("FLAG_DT_VAL_DATO", 0);

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
	
	public Timestamp getTsDtAgibilita(String dtAgibilita) {
		if (dtAgibilita == null || dtAgibilita.trim().equals("")) {
			return null;
		}
		dtAgibilita = dtAgibilita.replace(".", "/");
		String[] valori = dtAgibilita.split(" ");
		for (String valore : valori) {
			String[] strs = valore.split("/");
			if (strs.length > 2) {
				String[] newValore = new String[strs.length];
				String str = strs[0];
				while (str.length() < 2) {
					str = "0" + str;
				}
				newValore[0] = str;
				str = strs[1];
				while (str.length() < 2) {
					str = "0" + str;
				}
				newValore[1] = str;
				str = strs[2];
				while (str.length() < 2) {
					str = "0" + str;
				}
				newValore[2] = str;
				String annoNum = "";
				for (char car : newValore[2].toCharArray()) {
					if ("1234567890".indexOf("" + car) == -1) {
						break;
					}
					annoNum += car;
				}
				if (annoNum.length() == 2) {
					int intAnnoNum = Integer.parseInt(annoNum);
					if (intAnnoNum < previousCentury) {
						annoNum = "20" + annoNum;
					} else {
						annoNum = "19" + annoNum;
					}
				}				
				newValore[2] = annoNum;
				valore = newValore[0] + "/" + newValore[1] + "/" + newValore[2];
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					java.util.Date data = sdf.parse(valore);
					Timestamp retVal = new Timestamp(data.getTime());
					return retVal;
				}catch (Exception e) {}
			}
		}
		return null;
	}
	
}

