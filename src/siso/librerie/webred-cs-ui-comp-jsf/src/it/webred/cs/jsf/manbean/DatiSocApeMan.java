package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.bean.DatiSocApeBean;
import it.webred.cs.jsf.interfaces.IDatiSocApe;
import it.webred.jsf.utils.DateValidator;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

@ManagedBean
@NoneScoped
public class DatiSocApeMan implements IDatiSocApe {
	
	@ManagedProperty(value="#{datiSocApeBean}")
	private DatiSocApeBean datiSocApeBean;

	public DatiSocApeBean getDatiSocApeBean() {
		return datiSocApeBean;
	}

	public void setDatiSocApeBean(DatiSocApeBean datiSocApeBean) {
		this.datiSocApeBean = datiSocApeBean;
	}

	public ArrayList<SelectItem> getLstUffici() {
		//TODO query?
		ArrayList<SelectItem> lstUffici = new ArrayList<SelectItem>();
		lstUffici.add(new SelectItem(null, "- seleziona -"));
		lstUffici.add(new SelectItem("Minori", "Minori"));
		lstUffici.add(new SelectItem("Disabili minori", "Disabili minori"));
		lstUffici.add(new SelectItem("Adulti", "Adulti"));
		lstUffici.add(new SelectItem("Disabili adulti", "Disabili adulti"));
		lstUffici.add(new SelectItem("Anziani", "Anziani"));
		return lstUffici;
	}
	
	public ArrayList<SelectItem> getLstAssistentiSociali() {
		//TODO query
		ArrayList<SelectItem> lstAssistentiSociali = new ArrayList<SelectItem>();
		lstAssistentiSociali.add(new SelectItem(null, "- seleziona -"));
		lstAssistentiSociali.add(new SelectItem("1", "Albertini Silvia"));
		lstAssistentiSociali.add(new SelectItem("2", "Azzoni Maurizia"));
		return lstAssistentiSociali;
	}
	
	public Validator getDateValidator() {
		return new DateValidator();
	}

}
