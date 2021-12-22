package it.webred.ss.data.model;




import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the SS_MOTIVAZIONE database table.
 * 
 */
@Entity
@Table(name="SS_MOTIVAZIONE")
@NamedQuery(name="SsMotivazione.findAll", query="SELECT c FROM SsMotivazione c ORDER BY c.classificazione.id, c.motivo")
public class SsMotivazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String motivo;
	
	private Boolean economico;
	
	private String abilitato;

	@ManyToOne
	@JoinColumn(name="CLASSIFICAZIONE_ID")
	private SsClassificazioneMotivazione classificazione;
	
	public SsClassificazioneMotivazione getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(SsClassificazioneMotivazione classificazione) {
		this.classificazione = classificazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Boolean getEconomico() {
		return economico;
	}

	public void setEconomico(Boolean economico) {
		this.economico = economico;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}
	
}