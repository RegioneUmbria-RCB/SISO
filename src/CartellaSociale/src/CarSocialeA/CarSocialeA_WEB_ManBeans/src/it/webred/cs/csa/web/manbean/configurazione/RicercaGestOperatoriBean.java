package it.webred.cs.csa.web.manbean.configurazione;

import java.io.Serializable;

import it.webred.cs.jsf.bean.DatiOperatore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RicercaGestOperatoriBean extends CsUiCompBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String codiceFiscaleRic;
	private String cognomeRic;
	private String nomeRic;
	private DatiOperatore operatore;

	public String getCodiceFiscaleRic() {
		return codiceFiscaleRic;
	}

	public void setCodiceFiscaleRic(String codiceFiscaleRic) {
		this.codiceFiscaleRic = codiceFiscaleRic;
	}

	public String getCognomeRic() {
		return cognomeRic;
	}

	public void setCognomeRic(String cognomeRic) {
		this.cognomeRic = cognomeRic;
	}

	public String getNomeRic() {
		return nomeRic;
	}

	public void setNomeRic(String nomeRic) {
		this.nomeRic = nomeRic;
	}

	public DatiOperatore getOperatore() {
		return operatore;
	}

	public void setOperatore(DatiOperatore operatore) {
		this.operatore = operatore;
	}

	public boolean isCsOperatore() {
		return getOperatore() != null && getOperatore().getIdOperatore() != null;
	}
	
}
