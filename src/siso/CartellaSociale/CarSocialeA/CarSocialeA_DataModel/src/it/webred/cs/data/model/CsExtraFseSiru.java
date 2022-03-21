package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

//<!-- SISO-1305-1306 -->

@Entity
@Table(name="CS_EXTRA_FSE_SIRU")
@NamedQuery(name="CsExtraFseSiru.findAll", query="SELECT c FROM CsExtraFseSiru c")
public class CsExtraFseSiru implements Serializable {

	private static final long serialVersionUID = 3783426287560097542L;

	@Id
	@Column(name="EXTRA_FSE_MAST_ID")
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EXTRA_FSE_MAST_ID")
	@MapsId
	private CsExtraFseMast master;
	
	@Column(name="COMUNE_NASCITA_ID")
	private String comuneNascitaId;

	@Column(name="NAZIONE_NASCITA_ID")
	private String nazioneNascitaId;
	
	@Column(name="CITTADINANZA")
	private String cittadinanza;
	
	@Column(name="COMUNE_RES_ID")
	private String comuneResId;
	
	@Column(name="COMUNE_DOM_ID")
	private String comuneDomId;

	@Column(name="COND_MERCATO_INGRESSO")
	private String condMercatoIngresso;
	
	@Column(name="DURATA_RICERCA")
	private String durataRicerca;
	
	@Column(name="TIPOLOGIA_LAVORO_ID")
	private String tipologiaLavoroId;
	
	@Column(name="TIPO_ORARIO_LAVORO_ID")
	private String tipoOrarioLavoroId;
	
	@Column(name="COMUNE_AZIENDA_ID")
	private String comuneAziendaId;
	
	@Column(name="COD_ATECO_ANNO")
	private String codAtecoAnno;
	
	@Column(name="FRM_GIURIDICA_COD")
	private String frmGiuridicaCod;
	
	@Column(name="DIMENSIONE_AZIENDA_ID")
	private String dimensioneAziendaId;
	
	@Column(name="TITOLO_STUDIO")
	private String titoloStudio;
	
	@Column(name="COD_VULNERABILE_PA")
	private String codVulnerabilePa;
	
	@Column(name="STATO_PARTECIPANTE")
	private String statoPartecipante;
	
	@Column(name="SESSO")
	private Character sesso;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="FLAG_RESIDENZA")
	private Integer flagResidenza;
	

	public CsExtraFseSiru() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getComuneNascitaId() {
		return comuneNascitaId;
	}


	public void setComuneNascitaId(String comuneNascitaId) {
		this.comuneNascitaId = comuneNascitaId;
	}


	public String getNazioneNascitaId() {
		return nazioneNascitaId;
	}


	public void setNazioneNascitaId(String nazioneNascitaId) {
		this.nazioneNascitaId = nazioneNascitaId;
	}


	public String getCittadinanza() {
		return cittadinanza;
	}


	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}


	public String getComuneResId() {
		return comuneResId;
	}


	public void setComuneResId(String comuneResId) {
		this.comuneResId = comuneResId;
	}


	public String getComuneDomId() {
		return comuneDomId;
	}


	public void setComuneDomId(String comuneDomId) {
		this.comuneDomId = comuneDomId;
	}


	public String getCondMercatoIngresso() {
		return condMercatoIngresso;
	}


	public void setCondMercatoIngresso(String condMercatoIngresso) {
		this.condMercatoIngresso = condMercatoIngresso;
	}


	public String getDurataRicerca() {
		return durataRicerca;
	}


	public void setDurataRicerca(String durataRicerca) {
		this.durataRicerca = durataRicerca;
	}


	public String getTipologiaLavoroId() {
		return tipologiaLavoroId;
	}


	public void setTipologiaLavoroId(String tipologiaLavoroId) {
		this.tipologiaLavoroId = tipologiaLavoroId;
	}


	public String getTipoOrarioLavoroId() {
		return tipoOrarioLavoroId;
	}


	public void setTipoOrarioLavoroId(String tipoOrarioLavoroId) {
		this.tipoOrarioLavoroId = tipoOrarioLavoroId;
	}


	public String getComuneAziendaId() {
		return comuneAziendaId;
	}


	public void setComuneAziendaId(String comuneAziendaId) {
		this.comuneAziendaId = comuneAziendaId;
	}


	public String getCodAtecoAnno() {
		return codAtecoAnno;
	}


	public void setCodAtecoAnno(String codAtecoAnno) {
		this.codAtecoAnno = codAtecoAnno;
	}

	public String getFrmGiuridicaCod() {
		return frmGiuridicaCod;
	}


	public void setFrmGiuridicaCod(String frmGiuridicaCod) {
		this.frmGiuridicaCod = frmGiuridicaCod;
	}


	public String getDimensioneAziendaId() {
		return dimensioneAziendaId;
	}


	public void setDimensioneAziendaId(String dimensioneAziendaId) {
		this.dimensioneAziendaId = dimensioneAziendaId;
	}


	public String getTitoloStudio() {
		return titoloStudio;
	}


	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}


	public String getCodVulnerabilePa() {
		return codVulnerabilePa;
	}


	public void setCodVulnerabilePa(String codVulnerabilePa) {
		this.codVulnerabilePa = codVulnerabilePa;
	}


	public String getStatoPartecipante() {
		return statoPartecipante;
	}


	public void setStatoPartecipante(String statoPartecipante) {
		this.statoPartecipante = statoPartecipante;
	}

	public Character getSesso() {
		return sesso;
	}


	public void setSesso(Character sesso) {
		this.sesso = sesso;
	}


	public Date getDataNascita() {
		return dataNascita;
	}


	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Integer getFlagResidenza() {
		return flagResidenza;
	}


	public void setFlagResidenza(Integer flagResidenza) {
		this.flagResidenza = flagResidenza;
	}


	public CsExtraFseMast getMaster() {
		return master;
	}


	public void setMaster(CsExtraFseMast master) {
		this.master = master;
	}	

}