package it.webred.cs.data.model.sal;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_PAI_SAL_FASE")
public class CsPaiSALFase {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_SAL_FASE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_SAL_FASE_ID_GENERATOR")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PAI_SAL_ID")
	private CsPaiSAL paiSal;		
	
	@Column(name = "CODICE")
	private Integer codice;

	@Column(name="DATA_A")
	private Date dataA;

	@Column(name="DATA_DA")
	private Date dataDa;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FASE_SAL")
	private Date dataFaseSal;

	@Column(name="PAI_SAL_ID",insertable=false,updatable=false)
	private Long paiSalId;

	public CsPaiSALFase() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public Date getDataA() {
		return this.dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Date getDataDa() {
		return this.dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataFaseSal() {
		return this.dataFaseSal;
	}

	public void setDataFaseSal(Date dataFaseSal) {
		this.dataFaseSal = dataFaseSal;
	}

	public Long getPaiSalId() {
		return paiSalId;
	}

	public void setPaiSalId(Long paiSalId) {
		this.paiSalId = paiSalId;
	}

	public CsPaiSAL getSal() {
		return paiSal;
	}

	public void setSal(CsPaiSAL sal) {
		this.paiSal = sal;
	}

}