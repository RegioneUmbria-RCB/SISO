package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import it.webred.cs.csa.ejb.util.CustomDateSerializer;

@JsonIgnoreProperties(value = { "paiPTI" })
public class CsPaiPtiFaseDTO implements Serializable {

	private static final long serialVersionUID = -5775479072243569654L;

	private Long id;

	private CsPaiPTIDTO paiPTI;

	private CsTbPaiPTIFaseDTO fase;

	private Long idStato;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date validaDA;

	@JsonSerialize(using = CustomDateSerializer.class)
	private Date validaA;

	public CsPaiPtiFaseDTO() {
		super();
	}

	public CsPaiPtiFaseDTO(Long id) {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPaiPTIDTO getPaiPTI() {
		return paiPTI;
	}

	public void setPaiPTI(CsPaiPTIDTO paiPTI) {
		this.paiPTI = paiPTI;
	}

	public CsTbPaiPTIFaseDTO getFase() {
		return fase;
	}

	public void setFase(CsTbPaiPTIFaseDTO fase) {
		this.fase = fase;
	}

	public Date getValidaDA() {
		return validaDA;
	}

	public void setValidaDA(Date validaDA) {
		this.validaDA = validaDA;
	}

	public Date getValidaA() {
		return validaA;
	}

	public void setValidaA(Date validaA) {
		this.validaA = validaA;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}

}
