package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.bean.DatiChiBean;
import it.webred.cs.jsf.interfaces.IDatiChi;
import it.webred.jsf.utils.DateValidator;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

@ManagedBean
@NoneScoped
public class DatiChiMan implements IDatiChi {
	
	@ManagedProperty(value="#{datiChiBean}")
	private DatiChiBean datiChiBean;

	public DatiChiBean getDatiChiBean() {
		return datiChiBean;
	}

	public void setDatiChiBean(DatiChiBean datiChiBean) {
		this.datiChiBean = datiChiBean;
	}
	
	public ArrayList<SelectItem> getLstPeriodiChiusura() {
		//TODO query
		ArrayList<SelectItem> lstPeriodiChiusura = new ArrayList<SelectItem>();
		lstPeriodiChiusura.add(new SelectItem(null, "- seleziona -"));
		lstPeriodiChiusura.add(new SelectItem("1° SEM 09", "1° SEM 09"));
		lstPeriodiChiusura.add(new SelectItem("2° SEM 09", "2° SEM 09"));
		return lstPeriodiChiusura;
	}
	
	public ArrayList<SelectItem> getLstMotiviChiusura() {
		//TODO query?
		ArrayList<SelectItem> lstMotiviChiusura = new ArrayList<SelectItem>();
		lstMotiviChiusura.add(new SelectItem(null, "- seleziona -"));
		lstMotiviChiusura.add(new SelectItem("2", "Deceduto/a"));
		lstMotiviChiusura.add(new SelectItem("3", "Trasferito/a"));
		lstMotiviChiusura.add(new SelectItem("4", "Int. breve"));
		lstMotiviChiusura.add(new SelectItem("6", "Non collaborazione"));
		lstMotiviChiusura.add(new SelectItem("7", "Dimissioni"));
		lstMotiviChiusura.add(new SelectItem("8", "Passaggio ad altro ufficio"));
		lstMotiviChiusura.add(new SelectItem("9", "Maggiore età"));
		lstMotiviChiusura.add(new SelectItem("10", "Non luogo a procedere AG"));
		lstMotiviChiusura.add(new SelectItem("11", "Percorso in autonomia"));
		lstMotiviChiusura.add(new SelectItem("13", "Passaggio ad altro servizio (esterno)"));
		return lstMotiviChiusura;
	}
	
	public Validator getDateValidator() {
		return new DateValidator();
	}

}
