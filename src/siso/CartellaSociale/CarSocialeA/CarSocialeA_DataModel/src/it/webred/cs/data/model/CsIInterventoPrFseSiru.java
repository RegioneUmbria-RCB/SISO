package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="CS_I_INTERVENTO_PR_FSE_SIRU")
public class CsIInterventoPrFseSiru implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long interventoPrId;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_PR_ID")
	@MapsId
	private CsIInterventoPr csIInterventoPr;
	
	@Column(name="CITTADINANZA")
	private String cittadinanza;
	
	@Column(name="COD_ATECO_ANNO")
	private String codAtecoAnno;
	
	@Column(name="COD_VULNERABILE_PA")
	private String codVulnerabilePa;
	
	@Column(name="COMUNE_AZIENDA_ID")
	private String comuneAziendaId;
	
	@Column(name="COMUNE_DOM_ID")
	private String comuneDomId;
	
	@Column(name="COMUNE_NASCITA_ID")
	private String comuneNascitaId;
	
	@Column(name="COMUNE_RES_ID")
	private String comuneResId;
	
	@Column(name="COND_MERCATO_INGRESSO")
	private String condMercatoIngresso;
	
	@Column(name="DIMENSIONE_AZIENDA_ID")
	private String dimensioneAziendaId;
	
	@Column(name="DURATA_RICERCA")
	private String durataRicerca;
	
	@Column(name="FRM_GIURIDICA_COD")
	private String frmGiuridicaCod;
	
	@Column(name="NAZIONE_NASCITA_ID")
	private String nazioneNascitaId;
	
	@Column(name="STATO_PARTECIPANTE")
	private String statoPartecipante;
	
	@Column(name="TIPOLOGIA_LAVORO_ID")
	private String tipologiaLavoroId;
	
	@Column(name="TIPO_ORARIO_LAVORO_ID")
	private String tipoOrarioLavoroId;
	
	@Column(name="TITOLO_STUDIO")
	private String titoloStudio;
	
	@Column(name="SESSO")
	private Character sesso;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="FLAG_RESIDENZA")
	private Integer flagResidenza;

	public Long getInterventoPrId() {
		return interventoPrId;
	}

	public void setInterventoPrId(Long interventoPrId) {
		this.interventoPrId = interventoPrId;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCodAtecoAnno() {
		return codAtecoAnno;
	}

	public void setCodAtecoAnno(String codAtecoAnno) {
		this.codAtecoAnno = codAtecoAnno;
	}

	public String getCodVulnerabilePa() {
		return codVulnerabilePa;
	}

	public void setCodVulnerabilePa(String codVulnerabilePa) {
		this.codVulnerabilePa = codVulnerabilePa;
	}

	public String getComuneAziendaId() {
		return comuneAziendaId;
	}

	public void setComuneAziendaId(String comuneAziendaId) {
		this.comuneAziendaId = comuneAziendaId;
	}

	public String getComuneDomId() {
		return comuneDomId;
	}

	public void setComuneDomId(String comuneDomId) {
		this.comuneDomId = comuneDomId;
	}

	public String getComuneNascitaId() {
		return comuneNascitaId;
	}

	public void setComuneNascitaId(String comuneNascitaId) {
		this.comuneNascitaId = comuneNascitaId;
	}

	public String getComuneResId() {
		return comuneResId;
	}

	public void setComuneResId(String comuneResId) {
		this.comuneResId = comuneResId;
	}

	public String getCondMercatoIngresso() {
		return condMercatoIngresso;
	}

	public void setCondMercatoIngresso(String condMercatoIngresso) {
		this.condMercatoIngresso = condMercatoIngresso;
	}

	public String getDimensioneAziendaId() {
		return dimensioneAziendaId;
	}

	public void setDimensioneAziendaId(String dimensioneAziendaId) {
		this.dimensioneAziendaId = dimensioneAziendaId;
	}

	public String getDurataRicerca() {
		return durataRicerca;
	}

	public void setDurataRicerca(String durataRicerca) {
		this.durataRicerca = durataRicerca;
	}

	public String getFrmGiuridicaCod() {
		return frmGiuridicaCod;
	}

	public void setFrmGiuridicaCod(String frmGiuridicaCod) {
		this.frmGiuridicaCod = frmGiuridicaCod;
	}

	public String getNazioneNascitaId() {
		return nazioneNascitaId;
	}

	public void setNazioneNascitaId(String nazioneNascitaId) {
		this.nazioneNascitaId = nazioneNascitaId;
	}

	public String getStatoPartecipante() {
		return statoPartecipante;
	}

	public void setStatoPartecipante(String statoPartecipante) {
		this.statoPartecipante = statoPartecipante;
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

	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
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

	public CsIInterventoPr getCsIInterventoPr() {
		return csIInterventoPr;
	}

	public void setCsIInterventoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}

	
	
}
