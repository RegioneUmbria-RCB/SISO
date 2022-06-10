package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_POTESTA database table.
 * 
 */
@Entity
@Table(name="CS_TB_SOTTOCARTELLA_DOC")
@NamedQuery(name="CsTbSottocartellaDoc.findAll", query="SELECT c FROM CsTbSottocartellaDoc c order by descrizione")
public class CsTbSottocartellaDoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	// #ROMACAPITALE inizio
	private Boolean protocollo;
	
	@Column(name = "PROTOCOLLO_DA_SIGESS")
	private Boolean protocolloDaSigess;
	// #ROMACAPITALE fine	

	public CsTbSottocartellaDoc() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	// #ROMACAPITALE inizio
	public Boolean getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(Boolean protocollo) {
		this.protocollo = protocollo;
	}

	public Boolean getProtocolloDaSigess() {
		return protocolloDaSigess;
	}

	public void setProtocolloDaSigess(Boolean protocolloDaSigess) {
		this.protocolloDaSigess = protocolloDaSigess;
	}
	// #ROMACAPITALE fine
}