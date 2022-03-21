package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="CS_TB_SCUOLA_ANNO")
public class CsTbScuolaAnno implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsTbScuolaAnnoPK id;

	private Boolean abilitato;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SCUOLA_ID",insertable=false, updatable=false)
	private CsTbScuola csTbScuola;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="ANNO_ID", insertable=false, updatable=false)
	private CsTbAnnoScolastico csTbAnnoScolastico;

	public CsTbScuolaAnno() {
	}

	public CsTbScuolaAnnoPK getId() {
		return id;
	}

	public void setId(CsTbScuolaAnnoPK id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public CsTbScuola getCsTbScuola() {
		return csTbScuola;
	}

	public void setCsTbScuola(CsTbScuola csTbScuola) {
		this.csTbScuola = csTbScuola;
	}

	public CsTbAnnoScolastico getCsTbAnnoScolastico() {
		return csTbAnnoScolastico;
	}

	public void setCsTbAnnoScolastico(CsTbAnnoScolastico csTbAnnoScolastico) {
		this.csTbAnnoScolastico = csTbAnnoScolastico;
	}

}