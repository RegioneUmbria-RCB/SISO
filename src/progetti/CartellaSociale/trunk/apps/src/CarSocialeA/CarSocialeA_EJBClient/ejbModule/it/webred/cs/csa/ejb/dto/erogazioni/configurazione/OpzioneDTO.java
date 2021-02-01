package it.webred.cs.csa.ejb.dto.erogazioni.configurazione;

import it.webred.cs.data.model.CsCfgAttrOption;

import java.io.Serializable;

import java.math.BigDecimal;

public class OpzioneDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String abilitato;

	private BigDecimal ordine;

	private String tooltip;

	private String valore;

	public OpzioneDTO(){}
	
	public OpzioneDTO(CsCfgAttrOption o) {
		id = o.getId();
		valore = o.getValore();
		ordine = o.getOrdine();
		tooltip = o.getTooltip();
		abilitato = o.getAbilitato();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public BigDecimal getOrdine() {
		return this.ordine;
	}

	public void setOrdine(BigDecimal ordine) {
		this.ordine = ordine;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}