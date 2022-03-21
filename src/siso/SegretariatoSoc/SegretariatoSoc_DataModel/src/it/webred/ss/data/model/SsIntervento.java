package it.webred.ss.data.model;


import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="SS_INTERVENTO")
@NamedQuery(name="SsIntervento.findAll", query="SELECT c FROM SsIntervento c ORDER BY c.intervento")
public class SsIntervento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String intervento;
	
	private Boolean ente_esterno;
	
	private Boolean abilitato;
	
	@Column(name="SOLO_STRANIERI")
	private Boolean soloStranieri;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIntervento() {
		return intervento;
	}

	public void setIntervento(String intervento) {
		this.intervento = intervento;
	}

	public Boolean getEnte_esterno() {
		return ente_esterno;
	}

	public void setEnte_esterno(Boolean ente_esterno) {
		this.ente_esterno = ente_esterno;
	}

	public Boolean getSoloStranieri() {
		return soloStranieri;
	}

	public void setSoloStranieri(Boolean soloStranieri) {
		this.soloStranieri = soloStranieri;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

}