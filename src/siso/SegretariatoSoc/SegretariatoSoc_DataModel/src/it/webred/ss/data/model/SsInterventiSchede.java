package it.webred.ss.data.model;


import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the SS_INTERVENTI_SCHEDE database table.
 * 
 */
@Entity
@Table(name="SS_INTERVENTI_SCHEDE")
@NamedQuery(name="SsInterventiSchede.findAll", query="SELECT c FROM SsInterventiSchede c")
public class SsInterventiSchede implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_INTERVENTI_SCHEDE_ID_GENERATOR", sequenceName="SQ_SS_INTERVENTI_SCHEDE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_INTERVENTI_SCHEDE_ID_GENERATOR")
	private Long id;
	
	//bi-directional many-to-one association to SsSchedaAccesso
	@ManyToOne
	@JoinColumn(name="INTERVENTO")
	private SsIntervento intervento;
	
	//bi-directional many-to-one association to SsSchedaAccesso
	@ManyToOne
	@JoinColumn(name="SCHEDA")
	private SsSchedaInterventi scheda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SsIntervento getIntervento() {
		return intervento;
	}

	public void setIntervento(SsIntervento intervento) {
		this.intervento = intervento;
	}

	public SsSchedaInterventi getScheda() {
		return scheda;
	}

	public void setScheda(SsSchedaInterventi scheda) {
		this.scheda = scheda;
	}

	
}