package eu.smartpeg.rievazionepresenze.dto.pai;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


public class ArCsPaiInfoSinteticheDTO implements Serializable  {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idPaiPTI;

	private String codRouting;

	private Long idDocPai;
	
	private Boolean msna;			//Minore Straniero Non Accompagnato

	private Boolean flagCopiaFornita;	
	
	private Boolean flagEtaAccertata;	
	
	private Boolean flagSuppLegale;	
	
	private Boolean flagServizioInfOr;	
	
	private Boolean flagTrattaArt17;	
	
	private Boolean flagAlfabetizzato;

	public ArCsPaiInfoSinteticheDTO() {
		
	}
	
	public ArCsPaiInfoSinteticheDTO(Long idPaiPTI, String codRouting, Long idDocPai, Boolean msna,
			Boolean flagCopiaFornita, Boolean flagEtaAccertata, Boolean flagSuppLegale, Boolean flagServizioInfOr,
			Boolean flagTrattaArt17, Boolean flagAlfabetizzato) {
		super();
		this.idPaiPTI = idPaiPTI;
		this.codRouting = codRouting;
		this.idDocPai = idDocPai;
		this.msna = msna;
		this.flagCopiaFornita = flagCopiaFornita;
		this.flagEtaAccertata = flagEtaAccertata;
		this.flagSuppLegale = flagSuppLegale;
		this.flagServizioInfOr = flagServizioInfOr;
		this.flagTrattaArt17 = flagTrattaArt17;
		this.flagAlfabetizzato = flagAlfabetizzato;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Long getIdDocPai() {
		return idDocPai;
	}

	public void setIdDocPai(Long idDocPai) {
		this.idDocPai = idDocPai;
	}

	public Boolean getMsna() {
		return msna;
	}

	public void setMsna(Boolean msna) {
		this.msna = msna;
	}

	public Boolean getFlagCopiaFornita() {
		return flagCopiaFornita;
	}

	public void setFlagCopiaFornita(Boolean flagCopiaFornita) {
		this.flagCopiaFornita = flagCopiaFornita;
	}

	public Boolean getFlagEtaAccertata() {
		return flagEtaAccertata;
	}

	public void setFlagEtaAccertata(Boolean flagEtaAccertata) {
		this.flagEtaAccertata = flagEtaAccertata;
	}

	public Boolean getFlagSuppLegale() {
		return flagSuppLegale;
	}

	public void setFlagSuppLegale(Boolean flagSuppLegale) {
		this.flagSuppLegale = flagSuppLegale;
	}

	public Boolean getFlagServizioInfOr() {
		return flagServizioInfOr;
	}

	public void setFlagServizioInfOr(Boolean flagServizioInfOr) {
		this.flagServizioInfOr = flagServizioInfOr;
	}

	public Boolean getFlagTrattaArt17() {
		return flagTrattaArt17;
	}

	public void setFlagTrattaArt17(Boolean flagTrattaArt17) {
		this.flagTrattaArt17 = flagTrattaArt17;
	}

	public Boolean getFlagAlfabetizzato() {
		return flagAlfabetizzato;
	}

	public void setFlagAlfabetizzato(Boolean flagAlfabetizzato) {
		this.flagAlfabetizzato = flagAlfabetizzato;
	}
	
	
}
