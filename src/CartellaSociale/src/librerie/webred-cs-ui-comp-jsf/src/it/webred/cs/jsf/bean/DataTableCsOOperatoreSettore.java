package it.webred.cs.jsf.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;

public class DataTableCsOOperatoreSettore extends CsOOperatoreSettore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String lblOrganizzazione;
	private String lblSettore;
	private String lblTipoOperatore;
	private boolean selezionato;

	public String getLblOrganizzazione() {
		return lblOrganizzazione;
	}

	public void setLblOrganizzazione(String lblOrganizzazione) {
		this.lblOrganizzazione = lblOrganizzazione;
	}

	public String getLblSettore() {
		return lblSettore;
	}

	public void setLblSettore(String lblSettore) {
		this.lblSettore = lblSettore;
	}

	public boolean isSelezionato() {
		return selezionato;
	}

	public String getLblTipoOperatore() {
		return lblTipoOperatore;
	}

	public void setLblTipoOperatore(String lblTipoOperatore) {
		this.lblTipoOperatore = lblTipoOperatore;
	}

	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}
	
	public String getLblRuoli() {
		String amGroup = getAmGroup();
		if (amGroup == null || amGroup.equals("") 
		|| amGroup.lastIndexOf("_") == -1 
		|| amGroup.lastIndexOf("_") != amGroup.length() - 5) {
			return "-";
		}
		String belfiore = amGroup.substring(amGroup.length() - 4);
		LinkedHashMap<String, String> codRuoli = DataTableCsOOperatoreSettore.getCodificaRuoli(belfiore);
		if (codRuoli == null || codRuoli.size() == 0) {
			return "-";
		}
		String lblRuoli = "";
		String[] arr = amGroup.split(",");
		if (arr.length > 0) {
			for (String gr : arr) {
				String lblRuolo = codRuoli.get(gr);
				if (!lblRuoli.equals("")) {
					lblRuoli += "&#10;";
				}
				lblRuoli += (lblRuolo == null ? gr : lblRuolo);
			}
		}
		return lblRuoli == null || lblRuoli.equals("") ? "-" : lblRuoli;
	}
	
	public String getLblAppartiene() {
		return getAppartiene() == 1 ? "SI" : "NO";
	}
	
	public String getLblAttivo() {
		Date now = new Date();
		boolean okInizio = getDataInizioApp() != null &&
		(getDataInizioApp().getTime() <= now.getTime());
		boolean okFine = getDataFineApp() == null ||
		(getDataFineApp().getTime() > now.getTime());
		return okInizio && okFine ? "SI" : "NO";
	}
	
	public static DataTableCsOOperatoreSettore copyFromCsOOperatoreSettore(CsOOperatoreSettore fromObj, CsOOperatoreTipoOperatore fromOpTipoOp) {
		if (fromObj == null) return null;
		DataTableCsOOperatoreSettore obj = new DataTableCsOOperatoreSettore();
		obj.setId(fromObj.getId());
		obj.setDataInizioApp(fromObj.getDataInizioApp());
		obj.setDataFineApp(fromObj.getDataFineApp());
		obj.setCsOOperatore(fromObj.getCsOOperatore());
		obj.setCsOSettore(fromObj.getCsOSettore());
		obj.setAmGroup(fromObj.getAmGroup());
		obj.setAppartiene(fromObj.getAppartiene());
		obj.setFirma(fromObj.getFirma());
		obj.setLblOrganizzazione(fromObj.getCsOSettore().getCsOOrganizzazione().getNome());
		obj.setLblSettore(fromObj.getCsOSettore().getNome());
		if(fromOpTipoOp != null)
			obj.setLblTipoOperatore(fromOpTipoOp.getCsTbTipoOperatore().getDescrizione());
		else obj.setLblTipoOperatore("-");
		return obj;
	}
	
	public static LinkedHashMap<String, String> getCodificaRuoli(String belfiore) {
		LinkedHashMap<String, String> hmRuoli = new LinkedHashMap<String, String>();
		hmRuoli.put("CSOCIALE_AMMINISTRATIVI_" + belfiore, "Amministrativo");
		hmRuoli.put("CSOCIALE_AMMINISTRATORI_" + belfiore, "Amministratore");
		hmRuoli.put("CSOCIALE_RESPO_SETTORE_" + belfiore, "Responsabile Settore");
		hmRuoli.put("CSOCIALE_RESPO_ORG_" + belfiore, "Responsabile Organizzazione");
		hmRuoli.put("CSOCIALE_OPERATORI_" + belfiore, "Operatore");
		hmRuoli.put("CSOCIALE_EROGATORI_" + belfiore, "Erogatore");
		return hmRuoli;
	}

}
