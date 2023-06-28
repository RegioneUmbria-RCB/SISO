package it.webred.ct.data.access.basic.concedilizie.dto;


import java.util.Date;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;


public class RicercaConcEdilizieDTO extends RicercaOggettoDTO {
	
	private static final long serialVersionUID = 1L;
	
	private String idConc;
	private String idExtConc;
	
	private String concNumero;
	private String progressivoAnno;
	private String progressivoNumero;
	private String protocolloAnno;
	private String protocolloNumero;
	private Date dtRifDal;
	private Date dtRifAl;
	private String tipoCatasto;
	
	private RicercaCivicoDTO indirizzo;
	
	private Integer limit = 0;
	
	public RicercaConcEdilizieDTO(){
		indirizzo = new RicercaCivicoDTO();
		
	}
	
	
	public String getConcNumero() {
		return concNumero;
	}

	public void setConcNumero(String concNumero) {
		this.concNumero = concNumero;
	}

	public String getProgressivoAnno() {
		return progressivoAnno;
	}

	public void setProgressivoAnno(String progressivoAnno) {
		this.progressivoAnno = progressivoAnno;
	}

	public String getProgressivoNumero() {
		return progressivoNumero;
	}

	public void setProgressivoNumero(String progressivoNumero) {
		this.progressivoNumero = progressivoNumero;
	}

	public String getProtocolloAnno() {
		return protocolloAnno;
	}

	public void setProtocolloAnno(String protocolloAnno) {
		this.protocolloAnno = protocolloAnno;
	}

	public String getProtocolloNumero() {
		return protocolloNumero;
	}

	public void setProtocolloNumero(String protocolloNumero) {
		this.protocolloNumero = protocolloNumero;
	}

	public RicercaCivicoDTO getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(RicercaCivicoDTO indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIdConc() {
		return idConc;
	}

	public void setIdConc(String idConc) {
		this.idConc = idConc;
	}

	public String getIdExtConc() {
		return idExtConc;
	}

	public void setIdExtConc(String idExtConc) {
		this.idExtConc = idExtConc;
	}

	public void setTipoCatasto(String tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
	}


	public String getTipoCatasto() {
		return tipoCatasto;
	}


	public Integer getLimit() {
		return limit;
	}


	public void setLimit(Integer limit) {
		this.limit = limit;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Date getDtRifDal() {
		return dtRifDal;
	}


	public void setDtRifDal(Date dtRifDal) {
		this.dtRifDal = dtRifDal;
	}


	public Date getDtRifAl() {
		return dtRifAl;
	}


	public void setDtRifAl(Date dtRifAl) {
		this.dtRifAl = dtRifAl;
	}
	
	

}
