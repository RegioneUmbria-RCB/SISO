package it.webred.trainingprj;

import it.webred.trainingprj.jsf.bean.Comune;
import it.webred.trainingprj.jsf.DatiAnaBean;
import it.webred.trainingprj.jsf.interfaces.IDatiAna;
import it.webred.trainingprj.utils.ComuneConverter;
import it.webred.trainingprj.utils.DateValidator;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.Validator;

@ManagedBean
@ViewScoped
public class ManDatiAnaBean implements IDatiAna {
	
	@ManagedProperty(value="#{datiAnaBean}")
	private DatiAnaBean datiAnaBean;

	public DatiAnaBean getDatiAnaBean() {
		return datiAnaBean;
	}

	public void setDatiAnaBean(DatiAnaBean datiAnaBean) {
		this.datiAnaBean = datiAnaBean;
	}
	
	public ArrayList<Comune> getLstComuni(String query) {
		ArrayList<Comune> lstComuni = new ArrayList<Comune>();
		ArrayList<Comune> comuni = ComuneConverter.getLstComuni();
		for(Comune comune : comuni) {  
			if(comune.getDescrizione().toUpperCase().startsWith(query.toUpperCase())) {
				lstComuni.add(comune);
			}
		}  
		return lstComuni;
	}
	
	public ArrayList<SelectItem> getLstSessi() {
		ArrayList<SelectItem> lstSessi = new ArrayList<SelectItem>();
		lstSessi.add(new SelectItem(null, "-->"));
		lstSessi.add(new SelectItem("M", "M"));
		lstSessi.add(new SelectItem("F", "F"));
		return lstSessi;
	}
	
	public ArrayList<SelectItem> getLstStatiCivili() {
		//TODO query?
		ArrayList<SelectItem> lstStatiCivili = new ArrayList<SelectItem>();
		lstStatiCivili.add(new SelectItem(null, "--> scegli"));
		lstStatiCivili.add(new SelectItem("Celibe/Nubile", "Celibe/Nubile"));
		lstStatiCivili.add(new SelectItem("Coniugato/a", "Coniugato/a"));
		lstStatiCivili.add(new SelectItem("Vedovo/a", "Vedovo/a"));
		lstStatiCivili.add(new SelectItem("Convivente", "Convivente"));
		lstStatiCivili.add(new SelectItem("Separato/a", "Separato/a"));
		lstStatiCivili.add(new SelectItem("Divorziato/a", "Divorziato/a"));
		lstStatiCivili.add(new SelectItem("Non documentato", "Non documentato"));
		lstStatiCivili.add(new SelectItem("Libero di stato", "Libero di stato"));
		lstStatiCivili.add(new SelectItem("Altro", "Altro"));
		return lstStatiCivili;
	}
	
	public ArrayList<SelectItem> getLstCittadinanze() {
		//TODO query
		ArrayList<SelectItem> lstCittadinanze = new ArrayList<SelectItem>();
		lstCittadinanze.add(new SelectItem(null, "--> scegli"));
		lstCittadinanze.add(new SelectItem("Italiana", "Italiana"));
		lstCittadinanze.add(new SelectItem("Ivoriana", "Ivoriana"));
		return lstCittadinanze;
	}
	
	public ArrayList<SelectItem> getLstStatus() {
		//TODO query?
		ArrayList<SelectItem> lstStatus = new ArrayList<SelectItem>();
		lstStatus.add(new SelectItem(null, "--> scegli"));
		lstStatus.add(new SelectItem("Permesso umanitario", "Permesso umanitario"));
		lstStatus.add(new SelectItem("Profugo", "Profugo"));
		lstStatus.add(new SelectItem("Rifugiato", "Rifugiato"));
		return lstStatus;
	}
	
	public ArrayList<SelectItem> getLstMediciCuranti() {
		//TODO query
		ArrayList<SelectItem> lstMediciCuranti = new ArrayList<SelectItem>();
		lstMediciCuranti.add(new SelectItem(null, "--> scegli"));
		lstMediciCuranti.add(new SelectItem("1", "AGOSTONI ROBERTO"));
		lstMediciCuranti.add(new SelectItem("2", "ALEANDRI EMILIA"));
		return lstMediciCuranti;
	}
	
	public ArrayList<SelectItem> getLstUffici() {
		//TODO query?
		ArrayList<SelectItem> lstUffici = new ArrayList<SelectItem>();
		lstUffici.add(new SelectItem(null, "--> scegli"));
		lstUffici.add(new SelectItem("Minori", "Minori"));
		lstUffici.add(new SelectItem("Disabili minori", "Minori"));
		lstUffici.add(new SelectItem("Adulti", "Adulti"));
		lstUffici.add(new SelectItem("Disabili adulti", "Disabili adulti"));
		lstUffici.add(new SelectItem("Anziani", "Anziani"));
		return lstUffici;
	}
	
	public ArrayList<SelectItem> getLstAssistentiSociali() {
		//TODO query
		ArrayList<SelectItem> lstAssistentiSociali = new ArrayList<SelectItem>();
		lstAssistentiSociali.add(new SelectItem(null, "--> scegli"));
		lstAssistentiSociali.add(new SelectItem("1", "Albertini Silvia"));
		lstAssistentiSociali.add(new SelectItem("2", "Azzoni Maurizia"));
		return lstAssistentiSociali;
	}
	
	public ArrayList<SelectItem> getLstPeriodiChiusura() {
		//TODO query
		ArrayList<SelectItem> lstPeriodiChiusura = new ArrayList<SelectItem>();
		lstPeriodiChiusura.add(new SelectItem(null, "--> scegli"));
		lstPeriodiChiusura.add(new SelectItem("1° SEM 09", "1° SEM 09"));
		lstPeriodiChiusura.add(new SelectItem("2° SEM 09", "2° SEM 09"));
		return lstPeriodiChiusura;
	}
	
	public ArrayList<SelectItem> getLstMotiviChiusura() {
		//TODO query?
		ArrayList<SelectItem> lstMotiviChiusura = new ArrayList<SelectItem>();
		lstMotiviChiusura.add(new SelectItem(null, "--> scegli"));
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

	public void salvaDatiAna() {
		//TODO
		FacesMessage message = new FacesMessage("TODO salvataggio dati di " + datiAnaBean.getCognome() + " " +  datiAnaBean.getNome() + " " + datiAnaBean.getComuneNascita().getDescrizione() + " " + datiAnaBean.getCodiceFiscale());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("OK", message);
	}
	
	public Converter getComuneConverter() {
		return new ComuneConverter();
	}
	
	public Validator getDateValidator() {
		return new DateValidator();
	}
	
}

