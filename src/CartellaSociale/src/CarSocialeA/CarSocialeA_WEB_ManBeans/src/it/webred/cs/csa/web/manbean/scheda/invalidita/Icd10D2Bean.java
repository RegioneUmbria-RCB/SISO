package it.webred.cs.csa.web.manbean.scheda.invalidita;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsTbIcd10;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.jsf.interfaces.IDoppioCmbBox;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

public class Icd10D2Bean extends CsUiCompBaseBean implements IDoppioCmbBox{
	
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	

	private String codIniziale;
	private String idScelta;
	private String descrScelta;
	private String descrSceltaSalvata;
	private String idSceltaSalvata;
	private String textIfEmpty = "Nessuna selezione";
	private String widgetVar = "modalIcd10d2";
	private String dialogHeader = "Modifica la classificazione ICD10";
	private String testoIniziale;
	private String labelComboBox = "ICD10";
	
	private List<SelectItem> lstCodIniziali;
	private List<SelectItem> lstCodici;
	
	@Override
	public void codInizialeValueChangeListener(AjaxBehaviorEvent event) {
		UIOutput comp = (UIOutput)event.getSource();
		String value = (String)comp.getValue();
		if("".equals(value))
			codIniziale = null;
		else codIniziale = value;
		idScelta = null;
		descrScelta = null;
		
		lstCodici = new ArrayList<SelectItem>();
		lstCodici.add(new SelectItem(null, "- seleziona -"));
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(value);
		List<CsTbIcd10> lst = confService.getIcd10ByCodIniziali(dto);
		if (lst != null) {
			for (CsTbIcd10 obj : lst) {
				lstCodici.add(new SelectItem(obj.getId(), obj.getCodice() + " | " + obj.getDescrizione()));
			}
		}	
	}
	
	@Override
	public void codiceValueChangeListener(AjaxBehaviorEvent event) {
		UIOutput comp = (UIOutput)event.getSource();
		String value = (String)comp.getValue();
		
		if("".equals(value)){
			idScelta = null;
			descrScelta = null;
		}
		else {
			idScelta = value;
			for(SelectItem item: lstCodici){
				if((new Long(value)).equals(item.getValue())){
					descrScelta = item.getLabel();
					break;
				}
			}	
		}
	}
	
	@Override
	public void salva() {
		descrSceltaSalvata = descrScelta;
		idSceltaSalvata = idScelta;
		reset();
	}
	
	@Override
	public void reset() {
		idScelta = null;
		descrScelta = null;
		codIniziale = null;
	}
	
	@Override
	public String getCodIniziale() {
		return codIniziale;
	}

	public void setCodIniziale(String codIniziale) {
		this.codIniziale = codIniziale;
	}

	@Override
	public String getIdScelta() {
		return idScelta;
	}

	public void setIdScelta(String idScelta) {
		this.idScelta = idScelta;
	}

	@Override
	public String getDescrScelta() {
		return descrScelta;
	}

	public void setDescrScelta(String descrScelta) {
		this.descrScelta = descrScelta;
	}
	
	@Override
	public String getTextIfEmpty() {
		return textIfEmpty;
	}

	public void setTextIfEmpty(String textIfEmpty) {
		this.textIfEmpty = textIfEmpty;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}
	
	@Override
	public String getDialogHeader() {
		return dialogHeader;
	}

	@Override
	public String getLabelComboBox() {
		return labelComboBox;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public void setDialogHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	public void setLabelComboBox(String labelComboBox) {
		this.labelComboBox = labelComboBox;
	}
	
	@Override
	public String getDescrSceltaSalvata() {
		return descrSceltaSalvata;
	}

	public void setDescrSceltaSalvata(String descrSceltaSalvata) {
		this.descrSceltaSalvata = descrSceltaSalvata;
	}

	@Override
	public String getTestoIniziale() {
		return testoIniziale;
	}

	public void setTestoIniziale(String testoIniziale) {
		this.testoIniziale = testoIniziale;
	}

	@Override
	public List<SelectItem> getLstCodIniziali() {
		
		if(lstCodIniziali == null){
			lstCodIniziali = new ArrayList<SelectItem>();
			lstCodIniziali.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<String> lst = confService.getIcd10CodIniziali(bo);
			if (lst != null) {
				for (String obj : lst) {
					lstCodIniziali.add(new SelectItem(obj, obj));
				}
			}		
		}
		
		return lstCodIniziali;
	}

	public void setLstCodIniziali(List<SelectItem> lstCodIniziali) {
		this.lstCodIniziali = lstCodIniziali;
	}

	@Override
	public List<SelectItem> getLstCodici() {
		return lstCodici;
	}

	public void setLstCodici(List<SelectItem> lstCodici) {
		this.lstCodici = lstCodici;
	}

	@Override
	public String getIdSceltaSalvata() {
		return idSceltaSalvata;
	}

	public void setIdSceltaSalvata(String idSceltaSalvata) {
		this.idSceltaSalvata = idSceltaSalvata;
	}	

}
