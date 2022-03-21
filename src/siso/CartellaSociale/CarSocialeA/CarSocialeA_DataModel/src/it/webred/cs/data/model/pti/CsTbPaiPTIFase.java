package it.webred.cs.data.model.pti;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the CS_PB_PAI_PTI_FASE database table.
 * 
 */
@Entity
@Table(name = "CS_TB_PAI_PTI_FASE")
public class CsTbPaiPTIFase {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "COD_STATO")
	private String codiceStato;

	@Column(name = "DESC_STATO")
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
