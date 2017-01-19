package it.webred.ss.data.model;


import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_MOTIVAZIONI_SCHEDE database table.
 * 
 */
@Entity
@Table(name="SS_MOTIVAZIONI_SCHEDE")
@NamedQuery(name="SsMotivazioniSchede.findAll", query="SELECT c FROM SsMotivazioniSchede c")
public class SsMotivazioniSchede implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_MOTIVAZIONI_SCHEDE_ID_GENERATOR", sequenceName="SQ_SS_MOTIVAZIONI_SCHEDE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_MOTIVAZIONI_SCHEDE_ID_GENERATOR")
	private Long id;
	
	//private Long accesso;
	
	//bi-directional many-to-one association to SsSchedaAccesso
	@ManyToOne
	@JoinColumn(name="MOTIVAZIONE")
	private SsMotivazione motivazione;
	
	//bi-directional many-to-one association to SsSchedaAccesso
	@ManyToOne
	@JoinColumn(name="SCHEDA")
	private SsSchedaMotivazione scheda;

	public SsMotivazione getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(SsMotivazione motivazione) {
		this.motivazione = motivazione;
	}

	public SsSchedaMotivazione getScheda() {
		return scheda;
	}

	public void setScheda(SsSchedaMotivazione scheda) {
		this.scheda = scheda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}