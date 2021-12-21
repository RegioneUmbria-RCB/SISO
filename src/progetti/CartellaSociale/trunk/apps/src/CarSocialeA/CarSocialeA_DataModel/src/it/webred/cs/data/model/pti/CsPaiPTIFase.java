package it.webred.cs.data.model.pti;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_PAI_PTI_FASE database table.
 * 
 */
@Entity
@Table(name = "CS_PAI_PTI_FASE")
public class CsPaiPTIFase {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "PTI_ID_GENERATOR", sequenceName = "SQ_PAI_PTI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PTI_ID_GENERATOR")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "PAI_PTI_ID")
	private CsPaiPTI paiPTI;

	@OneToOne
	@JoinColumn(name = "ID_STATO", referencedColumnName = "ID", insertable = false, updatable = false)
	private CsTbPaiPTIFase fase;
	
	@Column(name = "ID_STATO")
	private Long idStato;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DA")
	private Date validaDA;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_A")
	private Date validaA;

	public CsPaiPTIFase() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPaiPTI getPaiPTI() {
		return paiPTI;
	}

	public void setPaiPTI(CsPaiPTI paiPTI) {
		this.paiPTI = paiPTI;
	}

	public CsTbPaiPTIFase getFase() {
		return fase;
	}

	public void setFase(CsTbPaiPTIFase fase) {
		this.fase = fase;
	}

	public Date getValidaDA() {
		return validaDA;
	}

	public void setValidaDA(Date validaDA) {
		this.validaDA = validaDA;
	}

	public Date getValidaA() {
		return validaA;
	}

	public void setValidaA(Date validaA) {
		this.validaA = validaA;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	
}
