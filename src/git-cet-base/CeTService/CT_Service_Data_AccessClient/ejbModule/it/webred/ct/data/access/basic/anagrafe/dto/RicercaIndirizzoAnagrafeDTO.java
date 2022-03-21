package it.webred.ct.data.access.basic.anagrafe.dto;

import java.io.Serializable;

public class RicercaIndirizzoAnagrafeDTO extends AnagrafeBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String sitDCivicoId;
	private String sitDCivicoIdExt;
	private String sitDViaIdExt;
	private String sitDCivicoViaDescrizione;
	
	public RicercaIndirizzoAnagrafeDTO() {
		
	}
	public RicercaIndirizzoAnagrafeDTO(String sitDCivicoIdExt) {
		this.sitDCivicoIdExt =sitDCivicoIdExt;
	}
	public String getSitDCivicoIdExt() {
		return sitDCivicoIdExt;
	}
	public void setSitDCivico_IdExt(String sitDCivicoIdExt) {
		this.sitDCivicoIdExt = sitDCivicoIdExt;
	}
	public String getSitDViaIdExt() {
		return sitDViaIdExt;
	}
	public void setSitDViaIdExt(String sitDViaIdExt) {
		this.sitDViaIdExt = sitDViaIdExt;
	}
	public void setSitDCivicoIdExt(String sitDCivicoIdExt) {
		this.sitDCivicoIdExt = sitDCivicoIdExt;
	}
	public String getSitDCivicoId() {
		return sitDCivicoId;
	}
	public void setSitDCivicoId(String sitDCivicoId) {
		this.sitDCivicoId = sitDCivicoId;
	}
	public String getSitDCivicoViaDescrizione() {
		return sitDCivicoViaDescrizione;
	}
	public void setSitDCivicoViaDescrizione(String sitDCivicoViaDescrizione) {
		this.sitDCivicoViaDescrizione = sitDCivicoViaDescrizione;
	}
		
}
