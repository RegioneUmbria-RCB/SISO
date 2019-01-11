package it.webred.cs.data.model.affido;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CS_PAI_AFFIDO_STATO")
public class CsPaiAffidoStato {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_AFFIDO_STATO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_AFFIDO_STATO_ID_GENERATOR")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "PAI_AFFIDO_ID")
	private CsPaiAffido affido;
	
	@Column(name = "CODICE")
	private Integer codice;
	
	@Column(name = "DATA_DA")
	private Date dataDa;
	
	@Column(name = "DATA_A")
	private Date dataA;
	
	@Column(name = "PAI_AFFIDO_ID",insertable=false,updatable=false)
	private Long paiAffidoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsPaiAffido getAffido() {
		return affido;
	}

	public void setAffido(CsPaiAffido affido) {
		this.affido = affido;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Long getPaiAffidoId() {
		return paiAffidoId;
	}

	public void setPaiAffidoId(Long paiAffidoId) {
		this.paiAffidoId = paiAffidoId;
	}
	
}
