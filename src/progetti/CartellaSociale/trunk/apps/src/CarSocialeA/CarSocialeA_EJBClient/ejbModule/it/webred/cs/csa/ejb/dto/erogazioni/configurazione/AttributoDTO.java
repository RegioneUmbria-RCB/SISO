package it.webred.cs.csa.ejb.dto.erogazioni.configurazione;

import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AttributoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String label;

	private String messaggioObbligatorio;

	private String tipoAttr;

	private String tooltip;

	private String valoreDefault;

	private List<OpzioneDTO> csCfgAttrOptions;      //Opzioni per attributi di tipo LIST

	public AttributoDTO(){}

	public AttributoDTO(CsCfgAttr a){
		id = a.getId();
		label = a.getLabel();
		messaggioObbligatorio = a.getMessaggioObbligatorio();
		tipoAttr = a.getTipoAttr();
		tooltip = a.getTooltip();
		valoreDefault = a.getValoreDefault();
		csCfgAttrOptions = new ArrayList<OpzioneDTO>();
		if(a.getCsCfgAttrOptions()!=null){
			for(CsCfgAttrOption o : a.getCsCfgAttrOptions()){
				OpzioneDTO on = new OpzioneDTO(o);
				csCfgAttrOptions.add(on);
			}
		}
	}
	
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMessaggioObbligatorio() {
		return this.messaggioObbligatorio;
	}

	public void setMessaggioObbligatorio(String messaggioObbligatorio) {
		this.messaggioObbligatorio = messaggioObbligatorio;
	}

	public String getTipoAttr() {
		return this.tipoAttr;
	}

	public void setTipoAttr(String tipoAttr) {
		this.tipoAttr = tipoAttr;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getValoreDefault() {
		return this.valoreDefault;
	}

	public void setValoreDefault(String valoreDefault) {
		this.valoreDefault = valoreDefault;
	}

	public List<OpzioneDTO> getCsCfgAttrOptions() {
		return this.csCfgAttrOptions;
	}

	public void setCsCfgAttrOptions(List<OpzioneDTO> csCfgAttrOptions) {
		this.csCfgAttrOptions = csCfgAttrOptions;
	}

	
}