package it.escsolution.escwebgis.contributi.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class ContributiSib  extends EscObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private LinkedHashMap<String, Object> dati;
	
	private static final Hashtable<String, String> labels = new Hashtable<String, String>();
	static {
		labels.put("CENTRO_RESPONSABILITA", "Centro Responsabilità");
		labels.put("DESCRIZIONE_CDR", "Descrizione CDR");
		labels.put("IMPORTO_SU_IMPEGNO", "Importo su impegno");
		labels.put("N_IDENT", "N. Ident.");
		labels.put("CAP", "CAP");
		labels.put("LOCALITA", "Località");
		labels.put("DATA_NASC", "Data Nascita");
		labels.put("COD_FIS_DELEGATO", "Cod. Fisc. Delegato");
		labels.put("BL2B_CODCIN", "BL2B CodCin");
		labels.put("ABI", "ABI");
		labels.put("CAB", "CAB");
		labels.put("CC", "CC");
		labels.put("MOD_PAG", "Mod. Pag.");
		labels.put("COD_BBAN", "Cod. BBAN");
		labels.put("IBAN_COMPLETO", "IBAN completo");
		labels.put("EU", "EU");
	}
	
	private static final Hashtable<String, Class> dataTypes = new Hashtable<String, Class>();
	static {
		dataTypes.put("IMPORTO_PARTITA", BigDecimal.class);
		dataTypes.put("IMPORTO_SU_IMPEGNO", BigDecimal.class);
	}
	
	private final DecimalFormat DF_IMPORTO_CONTR = new DecimalFormat();
	{
		DF_IMPORTO_CONTR.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DF_IMPORTO_CONTR.setDecimalFormatSymbols(dfs);
	}
	
	private final DecimalFormat DF_VIEW = new DecimalFormat();
	{
		DF_VIEW.setGroupingUsed(true);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		dfs.setGroupingSeparator('.');
		DF_VIEW.setDecimalFormatSymbols(dfs);
		DF_VIEW.setMinimumFractionDigits(2);
	}
	
	public String getChiave() { 
		return dati == null ? null : dati.get("CODICE_FISCALE").toString() + "_" + dati.get("ANNO_RUOLO").toString();
	}
	
	public String getValueStr(String key) {
		if (key == null) {
			return "-";
		}
		//i campi sono tutti varchar...
		Object value = dati.get(key);
		if (value != null && dataTypes.get(key) != null) {
			if (dataTypes.get(key).equals(BigDecimal.class)) {
				//formattazione degli importi
				try {
					return DF_VIEW.format(DF_IMPORTO_CONTR.parse(value.toString()));
				} catch (Exception e) {
					return value.toString();
				}
			}
		}		
		return value == null ? "-" : value.toString();
	}	

	public LinkedHashMap<String, Object> getDati() {
		return dati;
	}

	public void setDati(LinkedHashMap<String, Object> dati) {
		this.dati = dati;
	}
	
	public static String getLabel(String key) {
		if (key == null) {
			return "-";
		}
		if (labels.get(key) != null) {
			return labels.get(key);
		}
		key = key.replace("_", " ");
		StringBuffer sb = new StringBuffer();
		boolean cap = true;
		for (char car : key.toCharArray()) {
			if (cap) {
				sb.append(("" + car).toUpperCase());
				cap = false;
			} else {
				sb.append(("" + car).toLowerCase());
			}
			if (("" + car).equals(" ")) {
				cap = true;
			}
		}
		return sb.toString();
	}
	
}
