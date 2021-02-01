package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AR_FF_SERVIZIO_TERRIOTORIO")
public class ArFfServizioTerriotorio implements java.io.Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private long id;
	
	@Column(name = "USER_INS", nullable = false, length = 50)
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", nullable = false, length = 7)
	private Date dtIns;
	
	@Column(name = "USR_MOD", length = 50)
	private String usrMod;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_MOD", length = 7)
	private Date dtMod;
	
	@Column(name = "QUOTA_RIPARTIZ", precision = 10)
	private BigDecimal quotaRipartiz;
	
	@Column(name = "TIPO_GESTIONE", nullable = false, length = 1)
	private String tipoGestione;
	
	@Column(name = "EROG_ORGANIZZAZIONE_ID", precision = 10, scale = 0)
	private Long erogOrganizzazioneId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfServizioTerriotorio")
	private Set<ArFfErogante> arFfErogantes = new HashSet<ArFfErogante>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfServizioTerriotorio")
	private Set<ArFfCapitolo> arFfCapitolos = new HashSet<ArFfCapitolo>(0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONV_ORGANIZZAZIONE_ID")
	private ArOOrganizzazione arOOrganizzazioneByConvOrganizzazioneId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZZAZIONE_ID", nullable = false)
	private ArOOrganizzazione arOOrganizzazioneByOrganizzazioneId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVIZIO_ID", nullable = false)
	private ArFfServizio arFfServizio;

	public ArFfServizioTerriotorio() {
	}

	public ArFfServizioTerriotorio(long id, ArFfServizio arFfServizio,
			ArOOrganizzazione arOOrganizzazioneByOrganizzazioneId,
			String userIns, Date dtIns, String tipoGestione) {
		this.id = id;
		this.arFfServizio = arFfServizio;
		this.arOOrganizzazioneByOrganizzazioneId = arOOrganizzazioneByOrganizzazioneId;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.tipoGestione = tipoGestione;
	}

	public ArFfServizioTerriotorio(long id,
			ArOOrganizzazione arOOrganizzazioneByConvOrganizzazioneId,
			ArFfServizio arFfServizio,
			ArOOrganizzazione arOOrganizzazioneByOrganizzazioneId,
			String userIns, Date dtIns, String usrMod, Date dtMod,
			BigDecimal quotaRipartiz, String tipoGestione,
			Long erogOrganizzazioneId, Set<ArFfErogante> arFfErogantes,
			Set<ArFfCapitolo> arFfCapitolos) {
		this.id = id;
		this.arOOrganizzazioneByConvOrganizzazioneId = arOOrganizzazioneByConvOrganizzazioneId;
		this.arFfServizio = arFfServizio;
		this.arOOrganizzazioneByOrganizzazioneId = arOOrganizzazioneByOrganizzazioneId;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
		this.quotaRipartiz = quotaRipartiz;
		this.tipoGestione = tipoGestione;
		this.erogOrganizzazioneId = erogOrganizzazioneId;
		this.arFfErogantes = arFfErogantes;
		this.arFfCapitolos = arFfCapitolos;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArOOrganizzazione getArOOrganizzazioneByConvOrganizzazioneId() {
		return this.arOOrganizzazioneByConvOrganizzazioneId;
	}

	public void setArOOrganizzazioneByConvOrganizzazioneId(
			ArOOrganizzazione arOOrganizzazioneByConvOrganizzazioneId) {
		this.arOOrganizzazioneByConvOrganizzazioneId = arOOrganizzazioneByConvOrganizzazioneId;
	}

	public ArFfServizio getArFfServizio() {
		return this.arFfServizio;
	}

	public void setArFfServizio(ArFfServizio arFfServizio) {
		this.arFfServizio = arFfServizio;
	}

	public ArOOrganizzazione getArOOrganizzazioneByOrganizzazioneId() {
		return this.arOOrganizzazioneByOrganizzazioneId;
	}

	public void setArOOrganizzazioneByOrganizzazioneId(
			ArOOrganizzazione arOOrganizzazioneByOrganizzazioneId) {
		this.arOOrganizzazioneByOrganizzazioneId = arOOrganizzazioneByOrganizzazioneId;
	}
	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	
	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public BigDecimal getQuotaRipartiz() {
		return this.quotaRipartiz;
	}

	public void setQuotaRipartiz(BigDecimal quotaRipartiz) {
		this.quotaRipartiz = quotaRipartiz;
	}

	
	public String getTipoGestione() {
		return this.tipoGestione;
	}

	public void setTipoGestione(String tipoGestione) {
		this.tipoGestione = tipoGestione;
	}

	public Long getErogOrganizzazioneId() {
		return this.erogOrganizzazioneId;
	}

	public void setErogOrganizzazioneId(Long erogOrganizzazioneId) {
		this.erogOrganizzazioneId = erogOrganizzazioneId;
	}


	public Set<ArFfErogante> getArFfErogantes() {
		return this.arFfErogantes;
	}

	public void setArFfErogantes(Set<ArFfErogante> arFfErogantes) {
		this.arFfErogantes = arFfErogantes;
	}

	public Set<ArFfCapitolo> getArFfCapitolos() {
		return this.arFfCapitolos;
	}

	public void setArFfCapitolos(Set<ArFfCapitolo> arFfCapitolos) {
		this.arFfCapitolos = arFfCapitolos;
	}

}
