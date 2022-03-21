package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CS_I_SCHEDA_LAVORO database table.
 * 
 */
@Entity
@Table(name="CS_I_SCHEDA_LAVORO")
@NamedQuery(name="CsISchedaLavoro.findAll", query="SELECT c FROM CsISchedaLavoro c")
public class CsISchedaLavoro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	@Column(name="COD_UTENTE")
	private String codUtente;

	private BigDecimal anno;
	
	@Column(name="TIPO_CIRC4_ID")
	private Long tipoCirc4Id;
	
	@Column(name="INTERVENTI_UOL_ID")
	private Long interventiUOLId;
	
	@Column(name="TIPO_PROGETTO_ID")
	private Long tipoProgettoId;
	
	private String note; 
	
	//uni-directional many-to-one association to CsOOperatore
	@ManyToOne
	@JoinColumn(name="EDUCATORE_ID")
	private CsOOperatore csOOperatore;
	
	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsISchedaLavoro() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public String getCodUtente() {
		return codUtente;
	}

	public void setCodUtente(String codUtente) {
		this.codUtente = codUtente;
	}

	public BigDecimal getAnno() {
		return anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public Long getTipoCirc4Id() {
		return tipoCirc4Id;
	}

	public void setTipoCirc4Id(Long tipoCirc4Id) {
		this.tipoCirc4Id = tipoCirc4Id;
	}

	public Long getInterventiUOLId() {
		return interventiUOLId;
	}

	public void setInterventiUOLId(Long interventiUOLId) {
		this.interventiUOLId = interventiUOLId;
	}

	public Long getTipoProgettoId() {
		return tipoProgettoId;
	}

	public void setTipoProgettoId(Long tipoProgettoId) {
		this.tipoProgettoId = tipoProgettoId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CsOOperatore getCsOOperatore() {
		return csOOperatore;
	}

	public void setCsOOperatore(CsOOperatore csOOperatore) {
		this.csOOperatore = csOOperatore;
	}

	public CsIIntervento getCsIIntervento() {
		return csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

}