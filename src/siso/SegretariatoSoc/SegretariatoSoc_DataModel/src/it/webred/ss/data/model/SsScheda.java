package it.webred.ss.data.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the SS_SCHEDA database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA")
@NamedQuery(name="SsScheda.findAll", query="SELECT c FROM SsScheda c")
public class SsScheda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_ID_GENERATOR")
	private Long id;
	
	private Long identificativo;
	
	//private Long accesso;
	
	public SsScheda() {
		this.lstCategorieSociali = new ArrayList<SsCCategoriaSociale>();
	}

	//bi-directional many-to-one association to SsSchedaAccesso
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="ACCESSO")
	private SsSchedaAccesso accesso;
	
	//bi-directional many-to-one association to SsSchedaSegnalante
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="SEGNALANTE")
	private SsSchedaSegnalante segnalante;
	
	//bi-directional many-to-one association to SsSchedaSegnalato
//	@ManyToOne
//	@JoinColumn(name="SEGNALATO")
//	private SsSchedaSegnalato segnalato;
	
	private Long segnalato;
	
	//bi-directional many-to-one association to SsSchedaRiferimento
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="RIFERIMENTO")
	private SsSchedaRiferimento riferimento;
	
	//SISO-947
	//bi-directional many-to-one association to SsSchedaRiferimento
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="RIFERIMENTO2")
	private SsSchedaRiferimento riferimento2;
	
	//SISO-947
	//bi-directional many-to-one association to SsSchedaRiferimento
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="RIFERIMENTO3")
	private SsSchedaRiferimento riferimento3;
	
	//bi-directional many-to-one association to SsSchedaMotivazione
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="MOTIVAZIONE")
	private SsSchedaMotivazione motivazione;
	
	//bi-directional many-to-one association to SsSchedaInterventi
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="INTERVENTI")
	private SsSchedaInterventi interventi;
	
	private Boolean completa;
	private Boolean eliminata;
	private Boolean esterna;
	
	private Long tipo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
 
	@ManyToMany(fetch = FetchType.EAGER )
	@Fetch(FetchMode.SELECT)
    @JoinTable(name = "SS_REL_SCHEDA_CAT_SOC",
            joinColumns = {@JoinColumn(name = "SCHEDA_ID", referencedColumnName = "id" )},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORIA_SOCIALE_ID" ,referencedColumnName = "id" )})	
	private List<SsCCategoriaSociale>  lstCategorieSociali = null;
	
	public List<SsCCategoriaSociale> getLstCategorieSociali() {
		return lstCategorieSociali;
	}

	public void setLstCategorieSociali(List<SsCCategoriaSociale> lSsCategorieSociali) {
		lstCategorieSociali = lSsCategorieSociali;
	}

	public Boolean getEsterna() {
		return esterna;
	}

	public void setEsterna(Boolean esterna) {
		this.esterna = esterna;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public SsSchedaAccesso getAccesso() {
		return accesso;
	}

	public void setAccesso(SsSchedaAccesso accesso) {
		this.accesso = accesso;
	}

	public SsSchedaSegnalante getSegnalante() {
		return segnalante;
	}

	public void setSegnalante(SsSchedaSegnalante segnalante) {
		this.segnalante = segnalante;
	}

	public SsSchedaRiferimento getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(SsSchedaRiferimento riferimento) {
		this.riferimento = riferimento;
	}
    //SISO-947
	public SsSchedaRiferimento getRiferimento2() {
		return riferimento2;
	}

	public void setRiferimento2(SsSchedaRiferimento riferimento2) {
		this.riferimento2 = riferimento2;
	}
	//SISO-947
	public SsSchedaRiferimento getRiferimento3() {
		return riferimento3;
	}

	public void setRiferimento3(SsSchedaRiferimento riferimento3) {
		this.riferimento3 = riferimento3;
	}

	public SsSchedaMotivazione getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(SsSchedaMotivazione motivazione) {
		this.motivazione = motivazione;
	}
	
	public SsSchedaInterventi getInterventi() {
		return interventi;
	}

	public void setInterventi(SsSchedaInterventi interventi) {
		this.interventi = interventi;
	}

//	public SsSchedaSegnalato getSegnalato() {
//		return segnalato;
//	}
//
//	public void setSegnalato(SsSchedaSegnalato segnalato) {
//		this.segnalato = segnalato;
//	}

	public Long getSegnalato() {
		return segnalato;
	}

	public void setSegnalato(Long segnalato) {
		this.segnalato = segnalato;
	}
	

	public Boolean getCompleta() {
		return completa;
	}

	public void setCompleta(Boolean completa) {
		this.completa = completa;
	}

	public Boolean getEliminata() {
		return eliminata;
	}

	public void setEliminata(Boolean eliminata) {
		this.eliminata = eliminata;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public String getUserIns() {
		return userIns;
	}

	public String getUsrMod() {
		return usrMod;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Long getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	
}