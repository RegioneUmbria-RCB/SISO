package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
/**
 * The persistent class for the CS_D_DIARIO database table.
 * 
 */
@Entity
@Table(name="CS_D_DIARIO")
public class CsDDiarioBASIC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_D_DIARIO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_D_DIARIO_ID_GENERATOR")
	private long id;
	
	//bi-directional many-to-one association to CsACaso
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name="CASO_ID")
	private CsACaso csACaso;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS", insertable=false, updatable=false)
	private Date dtIns;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD", insertable=false, updatable=false)
	private Date dtMod;
	
	@Basic
	@Column(name="USER_INS", insertable=false, updatable=false)
	private String userIns;

	@Basic
	@Column(name="USR_MOD", insertable=false, updatable=false)
	private String usrMod;
	
	
	@Basic
	@Column(name="RESPONSABILE_ID", insertable=false, updatable=false)
	private Long responsabileCaso;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_AMMINISTRATIVA", insertable=false, updatable=false)
	private Date dtAmministrativa;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_ATTIVAZIONE_DA", insertable=false, updatable=false)
	private Date dtAttivazioneDa;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_ATTIVAZIONE_A", insertable=false, updatable=false)
	private Date dtAttivazioneA;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_SOSPENSIONE_DA", insertable=false, updatable=false)
	private Date dtSospensioneDa;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_SOSPENSIONE_A", insertable=false, updatable=false)
	private Date dtSospensioneA;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_CHIUSURA_DA", insertable=false, updatable=false)
	private Date dtChiusuraDa;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DT_CHIUSURA_A", insertable=false, updatable=false)
	private Date dtChiusuraA;
	
	//SISO-812
	@Column(name="VIS_SECONDO_LIVELLO")
	private Long visSecondoLivello;
	
	//SISO-812
	@ManyToOne(fetch = FetchType.EAGER)
	// Necessario caricamento contestuale
	@JoinColumn(name = "OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore csOOperatoreSettore;
	
	public CsDDiarioBASIC() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public CsACaso getCsACaso() {
		return this.csACaso;
	}

	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}



	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Long getResponsabileCaso() {
		return responsabileCaso;
	}

	public void setResponsabileCaso(Long responsabileCaso) {
		this.responsabileCaso = responsabileCaso;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}

	public void setDtAmministrativa(Date dtAmministrativa) {
		this.dtAmministrativa = dtAmministrativa;
	}

	public Date getDtAttivazioneDa() {
		return dtAttivazioneDa;
	}

	public void setDtAttivazioneDa(Date dtAttivazioneDa) {
		this.dtAttivazioneDa = dtAttivazioneDa;
	}

	public Date getDtAttivazioneA() {
		return dtAttivazioneA;
	}

	public void setDtAttivazioneA(Date dtAttivazioneA) {
		this.dtAttivazioneA = dtAttivazioneA;
	}

	public Date getDtSospensioneDa() {
		return dtSospensioneDa;
	}

	public Date getDtSospensioneA() {
		return dtSospensioneA;
	}

	public Date getDtChiusuraDa() {
		return dtChiusuraDa;
	}

	public Date getDtChiusuraA() {
		return dtChiusuraA;
	}

	public void setDtSospensioneDa(Date dtSospensioneDa) {
		this.dtSospensioneDa = dtSospensioneDa;
	}

	public void setDtSospensioneA(Date dtSospensioneA) {
		this.dtSospensioneA = dtSospensioneA;
	}

	public void setDtChiusuraDa(Date dtChiusuraDa) {
		this.dtChiusuraDa = dtChiusuraDa;
	}

	public void setDtChiusuraA(Date dtChiusuraA) {
		this.dtChiusuraA = dtChiusuraA;
	}

	public CsOOperatoreSettore getCsOOperatoreSettore() {
		return csOOperatoreSettore;
	}

	public void setCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		this.csOOperatoreSettore = csOOperatoreSettore;
	}

	public Long getVisSecondoLivello() {
		return visSecondoLivello;
	}

	public void setVisSecondoLivello(Long visSecondoLivello) {
		this.visSecondoLivello = visSecondoLivello;
	}
}