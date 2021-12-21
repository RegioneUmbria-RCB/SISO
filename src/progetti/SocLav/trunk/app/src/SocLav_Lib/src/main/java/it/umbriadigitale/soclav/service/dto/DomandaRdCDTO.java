package it.umbriadigitale.soclav.service.dto;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import it.umbriadigitale.soclav.service.dto.anpal.AnpalBeneficiarioDTO;

public class DomandaRdCDTO implements Serializable {
	
	private String protocolloINPS;
	
	private String cfRichiedente;
	
	private Date dataDomanda;
	
	public String getProtocolloINPS() {
		return protocolloINPS;
	}

	public void setProtocolloINPS(String protocolloINPS) {
		this.protocolloINPS = protocolloINPS;
	}

	public String getCfRichiedente() {
		return cfRichiedente;
	}

	public void setCfRichiedente(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}

	public Date getDataDomanda() {
		return dataDomanda;
	}

	public void setDataDomanda(Date dataDomanda) {
		this.dataDomanda = dataDomanda;
	}

}
