package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_A_INV_CIV database table.
 * 
 */
@Entity
@Table(name="CS_A_INV_CIV")
@NamedQuery(name="CsAInvCiv.findAll", query="SELECT c FROM CsAInvCiv c")
public class CsAInvCiv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_INV_CIV_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_INV_CIV_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to CsADatiInvalidita
	@ManyToOne
	@JoinColumn(name="INV_CIV_ID")
	private CsADatiInvalidita csADatiInvalidita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="SINA_RISPOSTA_DESCRIZIONE")
	private String sinaRispostaDescrizione;

	@Column(name="SINA_RISPOSTA_ID")
	private Long sinaRispostaId;

	@Column(name="USER_INS")
	private String userIns;

	public CsAInvCiv() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
		
	public CsADatiInvalidita getCsADatiInvalidita() {
		return csADatiInvalidita;
	}

	public void setCsADatiInvalidita(CsADatiInvalidita csADatiInvalidita) {
		this.csADatiInvalidita = csADatiInvalidita;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getSinaRispostaDescrizione() {
		return this.sinaRispostaDescrizione;
	}

	public void setSinaRispostaDescrizione(String sinaRispostaDescrizione) {
		this.sinaRispostaDescrizione = sinaRispostaDescrizione;
	}

	public Long getSinaRispostaId() {
		return sinaRispostaId;
	}

	public void setSinaRispostaId(Long sinaRispostaId) {
		this.sinaRispostaId = sinaRispostaId;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

}