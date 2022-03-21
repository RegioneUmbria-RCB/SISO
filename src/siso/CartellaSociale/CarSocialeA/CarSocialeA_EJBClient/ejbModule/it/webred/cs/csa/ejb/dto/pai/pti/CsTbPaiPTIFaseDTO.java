package it.webred.cs.csa.ejb.dto.pai.pti;

import java.io.Serializable;

public class CsTbPaiPTIFaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3722447276980857072L;

	private Long id;

	private String codiceStato;

	private String descrizioneStato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceStato() {
		return codiceStato;
	}

	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

}
